package protocols;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main (String args[]) throws IOException, InterruptedException
	{
//		new Thread(new Receiver()).start();
//		new Thread(new Backup()).start();
		
		Main.join();
	}
	
	public static void join() throws IOException
	{
		List<byte[]> res = new ArrayList<byte[]>();
		for(int i = 0; i < 8; i++)
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


