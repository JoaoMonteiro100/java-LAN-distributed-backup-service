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
	
	private static String INET_ADDR_MC;
	private static int MC_PORT;
	
	public Getchunk(int chunkNo, char[] fileIDchar, MulticastSocket sendingSocket)
	{
		this.chunkNo = chunkNo;
		this.fileIDchar = fileIDchar;
		this.sendingSocket = sendingSocket;
		INET_ADDR_MC = Menu.getINET_ADDR_MC();
	    MC_PORT = Menu.getMC_PORT();
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