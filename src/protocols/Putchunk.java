package protocols;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.NoSuchAlgorithmException;

public class Putchunk implements Runnable{
	
	private final int chunkNo;
	private final int replicationDegree;
	private final char[] fileIDchar;
	private final byte[] content;
	private final MulticastSocket sendingSocket;
	
	private static String INET_ADDR_MDB;
	private static int MDB_PORT;
	private static String INET_ADDR_MC;
	private static int MC_PORT;
	
	public Putchunk(int chunkNo, int replicationDegree, char[] fileIDchar, byte[] content, MulticastSocket sendingSocket)
	{
		this.chunkNo = chunkNo;
		this.replicationDegree = replicationDegree;
		this.fileIDchar = fileIDchar;
		this.content = content;
		this.sendingSocket = sendingSocket;
		INET_ADDR_MDB = Menu.getINET_ADDR_MDB();
	    MDB_PORT = Menu.getMDB_PORT();
	    INET_ADDR_MC = Menu.getINET_ADDR_MC();
	    MC_PORT = Menu.getMC_PORT();
	}

	@Override
	public void run() {
		
		try{
			Message msg = new Message("PUTCHUNK", 1.0, fileIDchar, chunkNo, replicationDegree, content);
			InetAddress addr = InetAddress.getByName(INET_ADDR_MDB);
		
			byte [] pot = msg.getEntireMessage();
	        DatagramPacket msgPacket = new DatagramPacket(pot,
	        		pot.length, addr, MDB_PORT);
	        
	        sendingSocket.send(msgPacket);
	
	        System.out.println("Sent part number" + chunkNo);	        
	        
        }
		catch(IOException ex){
			ex.printStackTrace();
		}
		
	}

}