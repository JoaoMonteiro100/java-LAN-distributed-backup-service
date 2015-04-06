package protocols;

import java.net.MulticastSocket;

//pull all chunks from a file from other PCs to rebuild it
public class Restore implements Runnable {
	
	private final String filename;
	private final MulticastSocket sendingSocket;

	public Restore(String filename, MulticastSocket sendingSocket) {
		this.filename = filename;
		this.sendingSocket = sendingSocket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
