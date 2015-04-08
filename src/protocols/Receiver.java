package protocols;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;

public class Receiver implements Runnable{
	
	final static String INET_ADDR_MC = "224.0.0.3";
	final static String INET_ADDR_MDB = "224.0.0.4";
	final static String INET_ADDR_MDR = "224.0.0.5";
	
    final static int MC_PORT = 8887;
    final static int MDB_PORT = 8888;
    final static int MDR_PORT = 8889;
    
    private final MulticastSocket sendingSocket;
    
    public Receiver(MulticastSocket sendingSocket){
    	this.sendingSocket = sendingSocket;
    }
    
	@Override
	public void run() {

        try {	
        	InetAddress address = InetAddress.getByName(INET_ADDR_MDB);
        	InetAddress address1 = InetAddress.getByName(INET_ADDR_MC);
        	InetAddress address2 = InetAddress.getByName(INET_ADDR_MDR);
        	
        	MulticastSocket clientSocket = new MulticastSocket(MDB_PORT);
        	MulticastSocket clientSocket1 = new MulticastSocket(MC_PORT);
        	MulticastSocket clientSocket2 = new MulticastSocket(MDR_PORT);
            
            clientSocket.joinGroup(address);
            clientSocket1.joinGroup(address1);
            clientSocket2.joinGroup(address2);
            
            byte[] buf = new byte[65000];
            
            /*Calendar calendar = Calendar.getInstance();
            int seconds = calendar.get(Calendar.SECOND);*/
            
            String fileID = Utilities.hashing("lol.dib");// + seconds;
            new File(fileID).mkdir();
            
            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket1.receive(msgPacket);
                
                byte [] msg = Arrays.copyOfRange(buf, 0, msgPacket.getLength());
                
                Message got = new Message (msg);
                //-----------------
                //ANSWER TO BACKUP
                if(got.getMessageType().equals("PUTCHUNK")) {
                	String filename = fileID + "\\" + "chunk" + got.getChunkNo() + ".part"; 
	                OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
	                
	                try {                    
	                    out.write(got.getBody());
	                } 
	                finally {
	                	if (out != null) 
	                		out.close();
	                }
	                
	                byte [] empty = new byte[0];
	        		char[] fileIDchar = fileID.toCharArray();
	        		
	        		Thread.sleep(200);
	
	                Message response = new Message("STORED", 1.0, fileIDchar, got.getChunkNo(), empty);
	                byte [] ola = response.getEntireMessage();
	                DatagramPacket pacote = new DatagramPacket(ola,
		            		ola.length, address1, MC_PORT);
	                sendingSocket.send(pacote);
                }
                
                
                else if(got.getMessageType().equals("GETCHUNK")) {
	                String filename = got.getFileId().toString() + "\\" + "chunk" + got.getChunkNo() + ".part"; 
	                InputStream in = new BufferedInputStream(new FileInputStream(filename));
	                byte[] data = new byte[65000];
	                
	                try {
	                	int a = 0, j = 0;

	                	//until end of file
	                	while(a!=-1) {
	                		a = in.read();
	                		data[j] = (byte) a;
	                		j++;
	                	}
	                } 
	                finally {
	                	if (in != null) 
	                		in.close();
	                }

	        		Thread.sleep(200);
	
	                Message response = new Message("CHUNK", 1.0, got.getFileId(), got.getChunkNo(), data);
	                byte [] ola = response.getEntireMessage();
	                DatagramPacket pacote = new DatagramPacket(ola,
		            		ola.length, address2, MDR_PORT);
	                sendingSocket.send(pacote);
                }
                
                
                
                else if(got.getMessageType().equals("DELETE")) {
                        File folderName = new File(String.valueOf(got.getFileId()));
                		String[]entries = folderName.list();
                		for(String s: entries){
                		    File currentFile = new File(folderName.getPath(),s);
                		    currentFile.delete();
                		}
                	
                }
            }
            
        } catch (IOException ex) {
          ex.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
