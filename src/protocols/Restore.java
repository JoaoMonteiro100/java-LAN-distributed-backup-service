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
	
	final static String INET_ADDR_MC = "224.0.0.3";
	final static String INET_ADDR_MDB = "224.0.0.4";
	final static String INET_ADDR_MDR = "224.0.0.5";
	
    final static int MC_PORT = 8887;
    final static int MDB_PORT = 8888;
    final static int MDR_PORT = 8889;

	public Restore(String filename, MulticastSocket sendingSocket) {
		this.filename = filename;
		this.sendingSocket = sendingSocket;
	}

	@Override
	public void run() {
        try {
        	
        	String fileID = Utilities.hashing(filename);
        	char[] fileIDchar = fileID.toCharArray();
        	
        	int chunkNo = 8, fileSize = 0;
        	
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
