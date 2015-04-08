package protocols;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.List;

//pull all chunks from a file from other PCs to rebuild it
public class Restore implements Runnable {
	
	private final String filename;
	private final MulticastSocket sendingSocket;
    
    final private static int CHUNK_SIZE = 64000;

	public Restore(String filename, MulticastSocket sendingSocket) {
		this.filename = filename;
		this.sendingSocket = sendingSocket;
	}

	@Override
	public void run() {
        try {
        	
        	File file = new File(filename);
        	String fileID = Utilities.hashing(filename);
        	char[] fileIDchar = fileID.toCharArray();
        	
        	int chunkNo = (int) (file.length()/CHUNK_SIZE);
        	
        	if( ((int) file.length() % CHUNK_SIZE ) > 0)
        		chunkNo += 1;
        	
        	System.out.println(chunkNo);
        	
        	for(int j = 0; j < chunkNo; j++)
        	{
        		new Thread(new Getchunk(j, fileIDchar, sendingSocket)).start();
	            Thread.sleep(500);
        	}
        	
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
