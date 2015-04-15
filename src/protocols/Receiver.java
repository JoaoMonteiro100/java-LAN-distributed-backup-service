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
                        
            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                
                if(Main.getPort() == msgPacket.getPort() && Main.getIp().equals(msgPacket.getAddress().toString().replace("/", "")))
                {
                		System.out.println("Ignore");
                		continue;
                }
                               
                byte [] msg = Arrays.copyOfRange(buf, 0, msgPacket.getLength());
                
                Message got = new Message (msg);
                
                //-----------------
                //ANSWER TO BACKUP
                if(got.getMessageType().equals("PUTCHUNK")) {
                	if(!new File(String.valueOf(got.getFileId())).exists())
                		new File(String.valueOf(got.getFileId())).mkdir();
                	String filename = String.valueOf(got.getFileId()) + "\\" + "chunk" + got.getChunkNo() + ".part"; 
	                OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
	                
	                try {                    
	                    out.write(got.getBody());
	                } 
	                finally {
	                	if (out != null) 
	                		out.close();
	                }
	                
	                byte [] empty = new byte[0];
	        		char[] fileIDchar = got.getFileId();
	        		
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
	                byte[] data = new byte[(int) new File(filename).length()];
	                
	                try {
	                	int a = 0, j = 0;

	                	do {
	                		a = in.read();
	                		if(a != -1)
	                			data[j] = (byte) a;
	                		j++;
	                	} while(a!=-1);
	                	
	                } 
	                finally {
	                	if (in != null) 
	                		in.close();
	                }

	        		Thread.sleep(200);
	
	        		InetAddress inetAdd = InetAddress.getByName(INET_ADDR_MDR);
	                Message response = new Message("CHUNK", 1.0, got.getFileId(), got.getChunkNo(), data);
	                byte [] ola = response.getEntireMessage();
	                DatagramPacket pacote = new DatagramPacket(ola,
		            		ola.length, inetAdd, MDR_PORT);
	                sendingSocket.send(pacote);
                }
                
                
                
                else if(got.getMessageType().equals("DELETE")) {                        
	        		File folderName = new File(String.valueOf(got.getFileId()));
	        		String[]entries = folderName.list();
	        		if(folderName.exists()){
	            		for(String s: entries){
	            		    File currentFile = new File(folderName.getPath(),s);
	            		    currentFile.delete();
	            		}
	            		folderName.delete();
	        		}                	
                }
                else if(got.getMessageType().equals("CHUNK"))
                {
                	String filename = "restore " + String.valueOf(got.getFileId()) + "\\" + "chunk" + got.getChunkNo() + ".part";
	                OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
                	try {                    
	                    out.write(got.getBody());
	                } 
	                finally {
	                	if (out != null) 
	                		out.close();
	                }
                }
            }
            
        } catch (IOException ex) {
          System.out.println("Some serious stuff happened!");
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
