package protocols;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

//when the space allocated for chunks in this PC is altered (change replication of chunks stored in this PC, etc)
public class ReclaimSpace implements Runnable {
	
	private double allocatedSpace;
	private final MulticastSocket sendingSocket;
	private static String INET_ADDR_MC;
	private static int MC_PORT;
	
	public ReclaimSpace(double space, MulticastSocket sendingSocket) {
		allocatedSpace = space;
		this.sendingSocket = sendingSocket;
		INET_ADDR_MC = Menu.getINET_ADDR_MC();
	    MC_PORT = Menu.getMC_PORT();
	}

	@Override
	public void run() {
		try {
			FileManager fm = new FileManager();
			fm.removeBackup(fm.getBiggestBackup());
			
			InetAddress addr = InetAddress.getByName(INET_ADDR_MC);
			
			Message msg = new Message("REMOVED", 1.0, null, 0);
        	byte [] pot = msg.getEntireMessage();
        	
        	DatagramPacket msgPacket = new DatagramPacket(pot,
            		pot.length, addr, MC_PORT);
        	
        	for(int i = 0; i < 5; i++)
        	{
	        	sendingSocket.send(msgPacket);
	            Thread.sleep(500);
            }
        	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
