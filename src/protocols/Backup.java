package protocols;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Backup implements Runnable{
	
	final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    final static int C_PORT = 8889;

	@Override
	public void run() {
		
        try (MulticastSocket serverSocket = new MulticastSocket()) {
        	
        	//MulticastSocket clientSocket = new MulticastSocket(C_PORT);
        	
        	InetAddress addr = InetAddress.getByName(INET_ADDR);
        	
        	//clientSocket.joinGroup(addr);
           
        	File fi = new File("C:\\Users\\Miguel Tavares\\Pictures\\lol.dib");
        	byte[] fileContent = Files.readAllBytes(fi.toPath());
        	
        	byte[][] chunks = divideArray(fileContent, 65000);
        	
        	for(int i = 0; i < chunks.length; i++)
        	{
        		String fileID = Utilities.hashing("lol.dib"); //	public Message(String messageType, double version, char[] fileId, byte[] chunkNo, int replicationDeg, char[] content) throws UnsupportedEncodingException {

        		char[] fileIDchar = fileID.toCharArray();
        		Message msg = new Message("PUTCHUNK", 1.0, fileIDchar, i, 1, chunks[i]);
        		
        		byte [] pot = msg.getEntireMessage();
	            DatagramPacket msgPacket = new DatagramPacket(pot,
	            		pot.length, addr, PORT);
	            
	            serverSocket.send(msgPacket);
	 
	            System.out.println("Sent part number" + i);
	            
	            /*byte [] ola = new byte [50];
	            DatagramPacket pacote = new DatagramPacket(ola, ola.length);
                clientSocket.receive(pacote);
                String ok = new String(ola);
                System.out.println(ok);*/
                
                Thread.sleep(500);
            }
        	
        	//clientSocket.close();
            
        } catch (IOException | InterruptedException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
		
	}
	
	 public static byte[][] divideArray(byte[] source, int chunksize) {


	        byte[][] ret = new byte[(int)Math.ceil(source.length / (double)chunksize)][chunksize];

	        int start = 0;

	        for(int i = 0; i < ret.length; i++) {
	            ret[i] = Arrays.copyOfRange(source,start, start + chunksize);
	            start += chunksize ;
	        }

	        return ret;
	    }
}
