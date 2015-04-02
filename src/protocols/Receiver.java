package protocols;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver implements Runnable{
	
	final static String INET_ADDR_MC = "224.0.0.3";
    final static int PORT = 8888;
    final static int C_PORT = 8889;

	@Override
	public void run() {
		 
        try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
        	
        	InetAddress address = InetAddress.getByName(INET_ADDR_MC);
            
            clientSocket.joinGroup(address);
            
            byte[] buf = new byte[1000000];
     
            int i = 0;
            while (true) {
                i++;
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                
                /*String filename = "chunk" + i + ".part"; 
                
                OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
                
                try {                    
                    out.write(buf);
                } 
                finally {
                	if (out != null) 
                		out.close();
                }
                
                String response = "File part " + i + " succesfully wrote on disk";
                byte [] ola = response.getBytes();
                DatagramPacket pacote = new DatagramPacket(ola,
	            		ola.length, address, C_PORT);
                clientSocket.send(pacote);*/
            }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
		
	}
}
