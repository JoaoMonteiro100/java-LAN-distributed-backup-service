import java.util.Arrays;

//class of a chunk (part of a file)
public class Chunk {
	private int desiredReplicationDegree;
	private int actualReplicationDegree;
	private byte[] chunkNo = new byte[6];
	private char[] fileID = new char[32];
	private char[] content = new char[64000];
	private boolean isLastChunk = false;
	
	public Chunk(char[] id, byte[] no, int replicationDegree, byte[] cont) throws Exception {
		try {
			//replication degree for this chunk (inherited from file)
			this.desiredReplicationDegree = replicationDegree;
			//chunk number limited to 6 bytes
			this.chunkNo = Arrays.copyOf(no, Math.min(6, no.length));
			//parent file ID
			this.fileID = id;
			//content limited to 64 kbytes
			this.content = Arrays.copyOf(cont, Math.min(64000, cont.length)).toString().toCharArray();
			//if content doesn't reach 64 kbytes, it's the last chunk of the file
			if(content.length < 64000) {
				this.isLastChunk = true;
			}
			
			//TODO: E SE O CHUNK ESTIVER VAZIO?? EX: FILE TINHA 64GB, coube perfeitamente em chunks de 64KB
			//       v é preciso um último de tamanho 0!!!
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public int getDesiredReplicationDegree() {
		return desiredReplicationDegree;
	}
	
	public int getActualReplicationDegree() {
		return actualReplicationDegree;
	}
	
	public byte[] getChunkNo() {
		return chunkNo;
	}
	
	public char[] getFileID() {
		return fileID;
	}
	
	public char[] getContent() {
		return content;
	}
	
	public boolean isLastChunk() {
		return isLastChunk;
	}
}
