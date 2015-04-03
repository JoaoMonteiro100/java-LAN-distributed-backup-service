package protocols;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Receiver implements Runnable{
	
	final static String INET_ADDR_MC = "224.0.0.3";
	final static String INET_ADDR_MDB = "224.0.0.4";
	final static String INET_ADDR_MDR = "224.0.0.5";
    final static int MC_PORT = 8887;
    final static int MDB_PORT = 8888;
    final static int MDR_PORT = 8889;

	@Override
	public void run() {
		 
        try (MulticastSocket clientSocket = new MulticastSocket(MDB_PORT)){
        	
        	InetAddress address = InetAddress.getByName(INET_ADDR_MDB);
        	InetAddress address1 = InetAddress.getByName(INET_ADDR_MC);
        	MulticastSocket clientSocket1 = new MulticastSocket(MC_PORT);
            
            clientSocket.joinGroup(address);
            
            byte[] buf = new byte[65000];
            
            String fileID = Utilities.hashing("lol.dib");
            new File(fileID).mkdir();
     
            int i = 0;
            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                
                byte [] msg = Arrays.copyOfRange(buf, 0, msgPacket.getLength());
                
                Message got = new Message (msg);
                
                String filename = fileID + "\\" + "chunk" + i + ".part"; 
                
                
                OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
                
                try {                    
                    out.write(got.getBody());
                } 
                finally {
                	if (out != null) 
                		out.close();
                }
                
                byte [] empty = new byte[0];
        		char[] fileIDchar = fileID.toCharArray();
        		
        		Thread.sleep(200);

                Message response = new Message("STORED", 1.0, fileIDchar, i, empty);
                byte [] ola = response.getEntireMessage();
                DatagramPacket pacote = new DatagramPacket(ola,
	            		ola.length, address1, MC_PORT);
                clientSocket1.send(pacote);
                i++;
            }
        } catch (IOException ex) {
          ex.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
