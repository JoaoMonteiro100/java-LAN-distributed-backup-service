package protocols;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.List;

//pull all chunks from a file from other PCs to rebuild it
public class Restore implements Runnable {
	
	private final String fileID;
	private final MulticastSocket sendingSocket;
	private final int chunkNo;
	
	final static String INET_ADDR_MC = "224.0.0.3";
	final static String INET_ADDR_MDB = "224.0.0.4";
	final static String INET_ADDR_MDR = "224.0.0.5";
	
    final static int MC_PORT = 8887;
    final static int MDB_PORT = 8888;
    final static int MDR_PORT = 8889;

	public Restore(String fileID, MulticastSocket sendingSocket, int chunkNo) {
		this.fileID = fileID;
		this.sendingSocket = sendingSocket;
		this.chunkNo = chunkNo;
	}

	@Override
	public void run() {
        try {
        	MulticastSocket clientSocket = new MulticastSocket(MDR_PORT);
        	InetAddress addr = InetAddress.getByName(INET_ADDR_MDR);
        	
        	char[] fileIDchar = fileID.toCharArray();
        	Message msg = new Message("GETCHUNK", 1.0, fileIDchar, chunkNo);
        	byte [] pot = msg.getEntireMessage();
        	
        	DatagramPacket msgPacket = new DatagramPacket(pot,
            		pot.length, addr, MDR_PORT);
        	
        	sendingSocket.send(msgPacket);
       	 
            System.out.println("Sent getchunk request for chunk #" + chunkNo);

            Thread.sleep(500);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
	}
}
