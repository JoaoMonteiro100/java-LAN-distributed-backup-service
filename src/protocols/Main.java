package protocols;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class Main {
	
	public static void main (String args[]) throws IOException, InterruptedException
	{
		new Thread(new Receiver()).start();
		new Thread(new Backup()).start();
		
		//join();
	}
}
