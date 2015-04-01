package protocols;
import java.io.UnsupportedEncodingException;

public class Message {
	private byte[] header;
	private byte[] body;
	
	//message with all elements (ex: PUTCHUNK)
	public Message(String messageType, double version, char[] fileId, byte[] chunkNo, int replicationDeg, char[] content) throws UnsupportedEncodingException {
		String ver = Double.toString(version);
		String fId = new String(fileId);
		String cNo = new String(chunkNo, "UTF-8");
		String repDegree = new Integer(replicationDeg).toString();
		String cont = new String(content);
		
		int cr = 13; //carriage return
		int lf = 10; //new line
		int sp = 32; //space
		String crlf = Character.toString((char) cr) + Character.toString((char) lf);
		String space = Character.toString((char) sp);
		
		String concatenatedHeader = messageType + space + ver + space + fId + space + cNo + space + repDegree + space + crlf + crlf;
		
		header = concatenatedHeader.getBytes("US_ASCII");
		body = cont.getBytes("US_ASCII");
	}
	
	//message without 'replicationDeg' (ex: STORED, GETCHUNK, CHUNK, REMOVED)
	public Message(String messageType, double version, char[] fileId, byte[] chunkNo, char[] content) throws UnsupportedEncodingException {
		String ver = Double.toString(version);
		String fId = new String(fileId);
		String cNo = new String(chunkNo, "UTF-8");
		String cont = new String(content);
		
		int cr = 13; //carriage return
		int lf = 10; //new line
		int sp = 32; //space
		String crlf = Character.toString((char) cr) + Character.toString((char) lf);
		String space = Character.toString((char) sp);
		
		String concatenatedHeader = messageType + space + ver + space + fId + space + cNo + space + crlf + crlf;
		
		header = concatenatedHeader.getBytes("US_ASCII");
		body = cont.getBytes("US_ASCII");
	}
	
	//message without 'replicationDeg' and 'chunkNo' (ex: DELETE)
	public Message(String messageType, double version, char[] fileId, char[] content) throws UnsupportedEncodingException {
		String ver = Double.toString(version);
		String fId = new String(fileId);
		String cont = new String(content);
		
		int cr = 13; //carriage return
		int lf = 10; //new line
		int sp = 32; //space
		String crlf = Character.toString((char) cr) + Character.toString((char) lf);
		String space = Character.toString((char) sp);
		
		String concatenatedHeader = messageType + space + ver + space + fId + space + crlf + crlf;
		
		header = concatenatedHeader.getBytes("US_ASCII");
		body = cont.getBytes("US_ASCII");
	}
}
