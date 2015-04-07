package protocols;

import java.io.File;
import java.io.IOException;
import java.net.MulticastSocket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main (String args[]) throws IOException, InterruptedException
	{
		MulticastSocket sendingSocket = new MulticastSocket();
		new Thread(new Receiver(sendingSocket)).start();
		//new Thread(new Backup("C:/abc.jpg", sendingSocket, 1)).start();
		
		//FILE ID
		new Thread(new Restore("1D9721539339B2A2C90E50F87A3E1151CB2DFFA071949CB17FC94C548249CDB230", sendingSocket)).start();
		
		//Main.join();
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


