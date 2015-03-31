package protocols;

import java.io.IOException;

public class Main {
	
	public static void main (String args[]) throws IOException, InterruptedException
	{
		new Thread(new Receiver()).start();
		new Thread(new Backup()).start();
	}

}
