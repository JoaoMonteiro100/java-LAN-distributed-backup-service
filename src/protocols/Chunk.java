package protocols;

import java.util.List;

public class Chunk {
	
	private List<String> ips;
	private int repDegree;
	private int chunkNo;
	private int attempts;
	
	public Chunk(int repDegree, int chunkNo)
	{
		this.repDegree = repDegree;
		this.chunkNo = chunkNo;
		this.attempts = 0;
	}	
	
	
	public void incAttempts ()
	{
		attempts++;
	}
	
	public void addIp (String ip)
	{
		ips.add(ip);
	}
	
	public int getAttempts()
	{
		return attempts;
	}
	
	public int getRepDegree()
	{
		return repDegree;
	}
	
	public int getChunkNo()
	{
		return chunkNo;
	}
	
	public boolean lowRepDegree()
	{
		return repDegree < ips.size();
	}
}
