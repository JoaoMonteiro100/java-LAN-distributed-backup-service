package protocols;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Delete implements Runnable{

	private final String fileID;
	private final MulticastSocket sendingSocket;
	
	final static String INET_ADDR_MC = "224.0.0.3";
	final static String INET_ADDR_MDB = "224.0.0.4";
	final static String INET_ADDR_MDR = "224.0.0.5";
	
    final static int MC_PORT = 8887;
    final static int MDB_PORT = 8888;
    final static int MDR_PORT = 8889;

	public Delete(String fileID, MulticastSocket sendingSocket) {
		this.fileID = fileID;
		this.sendingSocket = sendingSocket;
	}
	
	@Override
	public void run() {
		try {
        	MulticastSocket clientSocket = new MulticastSocket(MC_PORT);
        	InetAddress addr = InetAddress.getByName(INET_ADDR_MC);
        	
        	char[] fileIDchar = fileID.toCharArray();
        	Message msg = new Message("DELETE", 1.0, fileIDchar);
        	byte [] pot = msg.getEntireMessage();
        	
        	DatagramPacket msgPacket = new DatagramPacket(pot,
            		pot.length, addr, MC_PORT);
        	
        	sendingSocket.send(msgPacket);
       	 
            System.out.println("Sent delete request for file " + fileID);

            Thread.sleep(500);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
	}

}
