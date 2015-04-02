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
	
	final static String INET_ADDR_MC = "224.0.0.3";
	final static String INET_ADDR_MDB = "224.0.0.4";
	final static String INET_ADDR_MDR = "224.0.0.5";
    final static int MC_PORT = 8887;
    final static int MDB_PORT = 8888;
    final static int MDR_PORT = 8889;

	@Override
	public void run() {
		
        try (MulticastSocket serverSocket = new MulticastSocket()) {
        	
        	MulticastSocket clientSocket = new MulticastSocket(MC_PORT);
        	
        	InetAddress addr = InetAddress.getByName(INET_ADDR_MDB);
        	InetAddress addr1 = InetAddress.getByName(INET_ADDR_MC);
        	
        	clientSocket.joinGroup(addr1);
           
        	File fi = new File("C:\\Users\\Miguel Tavares\\Pictures\\lol.dib");
        	byte[] fileContent = Files.readAllBytes(fi.toPath());
        	
        	byte[][] chunks = divideArray(fileContent, 65000);
        	
        	for(int i = 0; i < chunks.length; i++)
        	{
        		String fileID = Utilities.hashing("lol.dib");

        		char[] fileIDchar = fileID.toCharArray();
        		Message msg = new Message("PUTCHUNK", 1.0, fileIDchar, i, 1, chunks[i]);
        		
        		byte [] pot = msg.getEntireMessage();
	            DatagramPacket msgPacket = new DatagramPacket(pot,
	            		pot.length, addr, MDB_PORT);
	            
	            serverSocket.send(msgPacket);
	 
	            System.out.println("Sent part number" + i);
	            
	            Thread.sleep(500);
	            
	            byte [] ola = new byte [100];
	            DatagramPacket pacote = new DatagramPacket(ola, ola.length);
                clientSocket.receive(pacote);
                String ok = new String(ola);
                System.out.println(ok);
            }
        	
        	clientSocket.close();
            
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
