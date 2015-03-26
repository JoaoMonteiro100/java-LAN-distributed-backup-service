import java.io.File;

//class of file to be handled
public class NewFile extends File {
	//TODO: generate ID (combine metadata with content, for example -- using SHA256)
	private char[] id = new char[32];
	//each file can have up to 1 million chunks
	private Chunk[] chunks = new Chunk[1000000];
	//desired replication degree, propagated to chunks
	private int replicationDegree;
	//version of file; altered if file is modified
	private double version = 1.0;
	
	public NewFile(String name, byte[] content, int repDegree) throws Throwable {
		super(name);
		this.replicationDegree = repDegree;
		//dividir content por chunks
		//SHA256
	}
	
	public char[] getID() {
		return this.id;
	}
	
	public Chunk[] getChunks() {
		return this.chunks;
	}
	
	public int getReplicationDegree() {
		return this.replicationDegree;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}
	
	public void setReplicationDegree(int r) {
		this.replicationDegree = r;
	}
}
