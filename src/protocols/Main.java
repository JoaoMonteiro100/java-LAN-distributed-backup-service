package protocols;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	static String INET_ADDR_MC;
	static String INET_ADDR_MDB;
	static String INET_ADDR_MDR;
	
    static int MC_PORT;
    static int MDB_PORT;
    static int MDR_PORT;
    
    static String LOCAL_IP;
    static int LOCAL_PORT;
	
	public static void main (String args[]) throws IOException, InterruptedException
	{
		//start interface
		Menu.start();
		INET_ADDR_MC = Menu.getINET_ADDR_MC();
	    MC_PORT = Menu.getMC_PORT();
	    INET_ADDR_MDB = Menu.getINET_ADDR_MDB();
	    MDB_PORT = Menu.getMDB_PORT();
	    INET_ADDR_MDR = Menu.getINET_ADDR_MDR();
	    MDR_PORT = Menu.getMDR_PORT();
	    
	    //get variables
		MulticastSocket sendingSocket = new MulticastSocket();
		
		LOCAL_PORT = sendingSocket.getLocalPort();
		LOCAL_IP = InetAddress.getLocalHost().getHostAddress();
		
		new Thread(new Receiver(sendingSocket, INET_ADDR_MC, MC_PORT)).start();
		new Thread(new Receiver(sendingSocket, INET_ADDR_MDB, MDB_PORT)).start();
		new Thread(new Receiver(sendingSocket, INET_ADDR_MDR, MDR_PORT)).start();
		
		//cycle to know what the user wants to do next
		while(true) {
			String[] toDo = Menu.nextAction().split(" ");
			
			//if it's a backup
			if(toDo[0].equals("BACKUP")) {
				new Thread(new Backup(toDo[1], sendingSocket, Integer.parseInt(toDo[2]))).start();
			}
			
			//if it's a restore
			else if(toDo[0].equals("RESTORE")) {
				new Thread(new Restore(toDo[1], sendingSocket)).start();
			}
			
			//if it's a delete
			else if(toDo[0].equals("DELETE")) {
				new Thread(new Delete(toDo[1], sendingSocket)).start();
			}
			
			//if it's a reallocation of space
			else if(toDo[0].equals("REALLOCATE")) {
				//new Thread(new ReclaimSpace(Double.parseDouble(toDo[1]), sendingSocket)).start();
				System.out.println("We're sorry, but the service you chose is not implemented. Choose another one!");
			}
		}

	}
	
	public static void join() throws IOException
	{
		List<byte[]> res = new ArrayList<byte[]>();
		for(int i = 0; i < new File("1D9721539339B2A2C90E50F87A3E1151CB2DFFA071949CB17FC94C548249CDB2").listFiles().length; i++)
		{
			String filename = "1D9721539339B2A2C90E50F87A3E1151CB2DFFA071949CB17FC94C548249CDB2\\chunk" + i + ".part";
			File fi = new File(filename);
	    	byte[] part = Files.readAllBytes(fi.toPath());
	    	res.add(part);
    	}
		System.out.println();
		Backup.join(res);
	}

	public static String getIp() {
		return LOCAL_IP;
	}
	
	public static int getPort() {
		return LOCAL_PORT;
	}

}


