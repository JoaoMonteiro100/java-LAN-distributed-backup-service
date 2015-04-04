package protocols;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Message {
	private byte[] header = {0};
	private byte[] body = {0};
	private byte[] entireMessage = {0};
	
	private String messageType = "";
	private double version = 0.0;
	private char[] fileId = {'0'};
	private byte[] chunkNo = {0};
	private int replicationDeg = 0;
	
	//message with all elements (ex: PUTCHUNK)
	public Message(String messageType, double version, char[] fileId, int chunkNo, int replicationDeg, byte[] content) throws UnsupportedEncodingException {
		String ver = Double.toString(version);
		String fId = new String(fileId);
		String cNo = Integer.toString(chunkNo);
		String repDegree = new Integer(replicationDeg).toString();
		//String cont = new String(content);
		
		int cr = 13; //carriage return
		int lf = 10; //new line
		int sp = 32; //space
		String crlf = Character.toString((char) cr) + Character.toString((char) lf);
		String space = Character.toString((char) sp);
		
		String concatenatedHeader = messageType + space + ver + space + fId + space + cNo + space + repDegree + space + crlf + crlf;
		
		header = concatenatedHeader.getBytes();
		body = content;
		
		//concatenation of header + body in entireMessage
		entireMessage = new byte[header.length + body.length];
		System.arraycopy(header, 0, entireMessage, 0, header.length);
		System.arraycopy(body, 0, entireMessage, header.length, body.length);
	}
	
	//message without 'replicationDeg' (ex: STORED, GETCHUNK, CHUNK, REMOVED)
	public Message(String messageType, double version, char[] fileId, int chunkNo, byte[] content) throws UnsupportedEncodingException {
		String ver = Double.toString(version);
		String fId = new String(fileId);
		String cNo = Integer.toString(chunkNo);
		//String cont = new String(content);
		
		int cr = 13; //carriage return
		int lf = 10; //new line
		int sp = 32; //space
		String crlf = Character.toString((char) cr) + Character.toString((char) lf);
		String space = Character.toString((char) sp);
		
		String concatenatedHeader = messageType + space + ver + space + fId + space + cNo + space + crlf + crlf;
		
		header = concatenatedHeader.getBytes();
		body = content;
		
		//concatenation of header + body in entireMessage
		entireMessage = new byte[header.length + body.length];
		System.arraycopy(header, 0, entireMessage, 0, header.length);
		System.arraycopy(body, 0, entireMessage, header.length, body.length);
	}
	
	//message without 'replicationDeg' and 'chunkNo' (ex: DELETE)
	public Message(String messageType, double version, char[] fileId, byte[] content) throws UnsupportedEncodingException {
		String ver = Double.toString(version);
		String fId = new String(fileId);
		//String cont = new String(content);
		
		int cr = 13; //carriage return
		int lf = 10; //new line
		int sp = 32; //space
		String crlf = Character.toString((char) cr) + Character.toString((char) lf);
		String space = Character.toString((char) sp);
		
		String concatenatedHeader = messageType + space + ver + space + fId + space + crlf + crlf;
		
		header = concatenatedHeader.getBytes();
		body = content;
		
		//concatenation of header + body in entireMessage
		entireMessage = new byte[header.length + body.length];
		System.arraycopy(header, 0, entireMessage, 0, header.length);
		System.arraycopy(body, 0, entireMessage, header.length, body.length);
	}
	
	//message with no discernible elements (ex: received message to be interpreted)
	public Message(byte[] message) throws UnsupportedEncodingException {
		String messageStr = new String(message);
		String[] splitMessage = messageStr.split("\\s"); //the spaces divide the components of the message
		
		//common elements to all messages
		messageType = splitMessage[0];
		version = Double.parseDouble(splitMessage[1]);
		fileId = splitMessage[2].toCharArray();
		
		//if message has at least 4 elements
		if(messageType != "DELETE") {
			chunkNo = splitMessage[3].getBytes(Charset.forName("UTF-8"));
			
			//if message has 5 elements
			if(messageType == "PUTCHUNK") {
				replicationDeg = Integer.parseInt(splitMessage[4]);
			}
		}
		
		//TODO: CONFIRMAR SE FUNCIONA ASSIM!!!
		String[] splitBody = messageStr.split("\\r\\n\\r\\n"); //CRLF CRLF divides de body from the header
		header = splitBody[0].getBytes();
		
		body = new byte [splitBody[1].length()];
		
		System.arraycopy(message, splitBody[0].length()+4, body, 0, splitBody[1].length());
				
		//concatenation of header + body in entireMessage
		entireMessage = new byte[header.length + body.length];
		System.arraycopy(header, 0, entireMessage, 0, header.length);
		System.arraycopy(body, 0, entireMessage, header.length, body.length);
	}

	public byte[] getHeader() {
		return header;
	}

	public void setHeader(byte[] header) {
		this.header = header;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public char[] getFileId() {
		return fileId;
	}

	public void setFileId(char[] fileId) {
		this.fileId = fileId;
	}

	public byte[] getChunkNo() {
		return chunkNo;
	}

	public void setChunkNo(byte[] chunkNo) {
		this.chunkNo = chunkNo;
	}

	public int getReplicationDeg() {
		return replicationDeg;
	}

	public void setReplicationDeg(int replicationDeg) {
		this.replicationDeg = replicationDeg;
	}

	public byte[] getEntireMessage() {
		return entireMessage;
	}

	public void setEntireMessage(byte[] entireMessage) {
		this.entireMessage = entireMessage;
	}
	
	
}
