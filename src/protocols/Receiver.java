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
	
	private static String INET_ADDR_MC;
	private static String INET_ADDR_MDB;
	private static String INET_ADDR_MDR;
	
	private static int MC_PORT;
    private static int MDB_PORT;
    private static int MDR_PORT;
    
    private final MulticastSocket sendingSocket;
    private final String address;
    private final int port;
    
    public Receiver(MulticastSocket sendingSocket, String address, int port){
    	this.sendingSocket = sendingSocket;
    	this.address = address;
    	this.port = port;
    	INET_ADDR_MC = Menu.getINET_ADDR_MC();
	    MC_PORT = Menu.getMC_PORT();
	    INET_ADDR_MDB = Menu.getINET_ADDR_MDB();
	    MDB_PORT = Menu.getMDB_PORT();
	    INET_ADDR_MDR = Menu.getINET_ADDR_MDR();
	    MDR_PORT = Menu.getMDR_PORT();
    }
    
	@Override
	public void run() {

        try {	
        	InetAddress inetAddress = InetAddress.getByName(address);
        	        	
        	MulticastSocket clientSocket = new MulticastSocket(port);
            
            clientSocket.joinGroup(inetAddress);
            
            byte[] buf = new byte[65000];
            
            /*Calendar calendar = Calendar.getInstance();
            int seconds = calendar.get(Calendar.SECOND);*/
            
            String fileID = Utilities.hashing("lol.dib");// + seconds;
            new File(fileID).mkdir();
            
            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                
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
		            		ola.length, inetAddress, port);
	                sendingSocket.send(pacote);
                }
                
                
                else if(got.getMessageType().equals("GETCHUNK")) {
	                String filename = String.valueOf(got.getFileId()) + "\\" + "chunk" + got.getChunkNo() + ".part";
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
	
	        		InetAddress inetAdd = InetAddress.getByName(INET_ADDR_MDR);
	                Message response = new Message("CHUNK", 1.0, got.getFileId(), got.getChunkNo(), data);
	                byte [] ola = response.getEntireMessage();
	                System.out.println("Thank you comagain");
	                DatagramPacket pacote = new DatagramPacket(ola,
		            		ola.length, inetAdd, MDR_PORT);
	                sendingSocket.send(pacote);
                }
                
                
                
                else if(got.getMessageType().equals("DELETE")) {
                        File folderName = new File(String.valueOf(got.getFileId()));
                		String[]entries = folderName.list();
                		for(String s: entries){
                		    File currentFile = new File(folderName.getPath(),s);
                		    currentFile.delete();
                		}
                		folderName.delete();
                	
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
