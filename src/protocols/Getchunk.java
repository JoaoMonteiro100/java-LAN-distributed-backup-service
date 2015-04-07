package protocols;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.NoSuchAlgorithmException;

public class Getchunk implements Runnable{
	
	private final int chunkNo;
	private final char[] fileIDchar;
	private final MulticastSocket sendingSocket;
	
	final static String INET_ADDR_MC = "224.0.0.3";
	final static String INET_ADDR_MDB = "224.0.0.4";
	final static String INET_ADDR_MDR = "224.0.0.5";
	
    final static int MC_PORT = 8887;
    final static int MDB_PORT = 8888;
    final static int MDR_PORT = 8889;
	
	public Getchunk(int chunkNo, char[] fileIDchar, MulticastSocket sendingSocket)
	{
		this.chunkNo = chunkNo;
		this.fileIDchar = fileIDchar;
		this.sendingSocket = sendingSocket;
	}

	@Override
	public void run() {
		
		try{
			Message msg = new Message("GETCHUNK", 1.0, fileIDchar, chunkNo);
			InetAddress addr = InetAddress.getByName(INET_ADDR_MC);
		
			byte [] pot = msg.getEntireMessage();
	        DatagramPacket msgPacket = new DatagramPacket(pot,
	        		pot.length, addr, MC_PORT);
	        
	        sendingSocket.send(msgPacket);
	
	        System.out.println("Sent request for chunk #" + chunkNo);
        }
		catch(IOException ex){
			ex.printStackTrace();
		}
		
	}

}