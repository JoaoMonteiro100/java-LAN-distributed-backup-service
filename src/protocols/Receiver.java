package protocols;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver implements Runnable{
	
	final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

	@Override
	public void run() {
		 
        try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
        	
        	InetAddress address = InetAddress.getByName(INET_ADDR);
            
            clientSocket.joinGroup(address);
            
            byte[] buf = new byte[1000000];
     
            int i = 0;
            while (true) {
                i++;
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                
                String filename = "chunk" + i + ".part"; 
                
                OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
                
                try {                    
                    out.write(buf);
                } 
                finally {
                	if (out != null) 
                		out.close();
                }
                System.out.println("File part succesfully wrote on disk");
            }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
		
	}
}
