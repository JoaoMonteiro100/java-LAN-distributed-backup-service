package protocols;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.NoSuchAlgorithmException;

public class Delete implements Runnable{

	private final String filename;
	private final MulticastSocket sendingSocket;
	private static String INET_ADDR_MC;
	private static int MC_PORT;

	public Delete(String filename, MulticastSocket sendingSocket) {
		this.filename = filename;
		this.sendingSocket = sendingSocket;
		INET_ADDR_MC = Menu.getINET_ADDR_MC();
	    MC_PORT = Menu.getMC_PORT();
	}
	
	@Override
	public void run() {
		try {
//        	MulticastSocket clientSocket = new MulticastSocket(MC_PORT);
        	InetAddress addr = InetAddress.getByName(INET_ADDR_MC);
        	
        	String fileID = Utilities.hashing(filename);
        	char[] fileIDchar = fileID.toCharArray();
        	Message msg = new Message("DELETE", 1.0, fileIDchar);
        	byte [] pot = msg.getEntireMessage();
        	
        	DatagramPacket msgPacket = new DatagramPacket(pot,
            		pot.length, addr, MC_PORT);
        	for(int i = 0; i < 5; i++)
        	{
	        	sendingSocket.send(msgPacket);
	            Thread.sleep(500);
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
