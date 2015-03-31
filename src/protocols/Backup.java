package protocols;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.Arrays;

public class Backup implements Runnable{
	
	final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

	@Override
	public void run() {
		
        try (DatagramSocket serverSocket = new DatagramSocket()) {
        	
        	InetAddress addr = InetAddress.getByName(INET_ADDR);
        	
           
        	File fi = new File("C:\\Users\\Miguel Tavares\\Pictures\\lol.dib");
        	byte[] fileContent = Files.readAllBytes(fi.toPath());
        	
        	byte[][] chunks = divideArray(fileContent, 65000);
        	
        	for(int i = 0; i < chunks.length; i++)
        	{
	            DatagramPacket msgPacket = new DatagramPacket(chunks[i],
	            		chunks[i].length, addr, PORT);
	            
	            serverSocket.send(msgPacket);
	 
	            System.out.println("Sent part number" + i);
            }
            
        } catch (IOException ex) {
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
