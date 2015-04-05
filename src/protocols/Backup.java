package protocols;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backup implements Runnable{
	
	final static String INET_ADDR_MC = "224.0.0.3";
	final static String INET_ADDR_MDB = "224.0.0.4";
	final static String INET_ADDR_MDR = "224.0.0.5";
	
    final static int MC_PORT = 8887;
    final static int MDB_PORT = 8888;
    final static int MDR_PORT = 8889;
    
    //java -jar McastSnooper.jar 224.0.0.3:8887 224.0.0.4:8888 224.0.0.5:8889
    
    final private static int CHUNK_SIZE = 64000;

	@Override
	public void run() {
		
        try (MulticastSocket serverSocket = new MulticastSocket()) {
        	
        	MulticastSocket clientSocket = new MulticastSocket(MC_PORT);
        	
        	InetAddress addr = InetAddress.getByName(INET_ADDR_MDB);
        	InetAddress addr1 = InetAddress.getByName(INET_ADDR_MC);
        	
        	clientSocket.joinGroup(addr1);
           
        	File fi = new File("C:\\Users\\Miguel Tavares\\Pictures\\lol.dib");
        	byte[] fileContent = Files.readAllBytes(fi.toPath());     	
        	
        	String fileID = Utilities.hashing("lol.dib");
        	
        	List<byte[]> chunks = divideArray(fileContent, CHUNK_SIZE);
        	
        	join(chunks);
        	
        	
        	for(int i = 0; i < chunks.size(); i++)
        	{
        		char[] fileIDchar = fileID.toCharArray();
        		Message msg = new Message("PUTCHUNK", 1.0, fileIDchar, i, 1, chunks.get(i));
        	       		
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
            
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<byte[]> divideArray(byte[] source, int chunksize) {

	    List<byte[]> result = new ArrayList<byte[]>();
	    int start = 0;
	    while (start < source.length) {
	        int end = Math.min(source.length, start + chunksize);
	        result.add(Arrays.copyOfRange(source, start, end));
	        start += chunksize;
	    }

	    return result;
	}
	
	public static void join(List<byte[]> chunks) throws IOException
	{
	    	byte [] res = new byte [chunks.get(0).length*(chunks.size()-1)+chunks.get(chunks.size()-1).length];
	    	System.out.println(chunks.get(0).length*(chunks.size()-1)+chunks.get(chunks.size()-1).length);
	    	System.arraycopy(chunks.get(0), 0, res, 0, chunks.get(0).length);
	    	for(int i = 1; i < chunks.size(); i++)
	    	{
	    		System.arraycopy(chunks.get(i), 0, res, CHUNK_SIZE*i, chunks.get(i).length);
	    	}
	    		    	
	    	OutputStream out = new BufferedOutputStream(new FileOutputStream("example.dib"));
	        
	        try {                    
	            out.write(res);
	        } 
	        finally {
	        	if (out != null) 
	        		out.close();
	        }
	}
}
