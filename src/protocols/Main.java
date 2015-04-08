package protocols;

import java.io.File;
import java.io.IOException;
import java.net.MulticastSocket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	final static String INET_ADDR_MC = "224.0.0.3";
	final static String INET_ADDR_MDB = "224.0.0.4";
	final static String INET_ADDR_MDR = "224.0.0.5";
	
    final static int MC_PORT = 8887;
    final static int MDB_PORT = 8888;
    final static int MDR_PORT = 8889;
	
	public static void main (String args[]) throws IOException, InterruptedException
	{
		MulticastSocket sendingSocket = new MulticastSocket();
		new Thread(new Receiver(sendingSocket, INET_ADDR_MC, MC_PORT)).start();
		new Thread(new Receiver(sendingSocket, INET_ADDR_MDB, MDB_PORT)).start();
		new Thread(new Receiver(sendingSocket, INET_ADDR_MDR, MDR_PORT)).start();
		
//		new Thread(new Backup("lol.dib", sendingSocket, 1)).start();
		
		//FILE ID
		
		new Thread(new Restore("lol.dib", sendingSocket)).start();
//		new Thread(new Delete("lol.dib", sendingSocket)).start();
		
//		Main.join();
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
}


