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
    
    private final String filename;
    private final MulticastSocket sendingSocket;
    private final int replicationDegree; 

    public Backup(String filename, MulticastSocket sendingSocket, int replicationDegree){
		this.filename = filename;
		this.sendingSocket = sendingSocket;
		this.replicationDegree = replicationDegree;
	}
    
	@Override
	public void run() {
		
        try {
                   
        	File fi = new File(filename);
        	byte[] fileContent = Files.readAllBytes(fi.toPath());     	
        	
        	String fileID = Utilities.hashing(filename);
        	char[] fileIDchar = fileID.toCharArray();
        	
        	List<byte[]> chunks = divideArray(fileContent, CHUNK_SIZE);        	
        	
        	for(int i = 0; i < chunks.size(); i++)
        	{
        		new Thread(new Putchunk(i, replicationDegree, fileIDchar, chunks.get(i), sendingSocket)).start();
        		Thread.sleep(500);
        	}

        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
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
