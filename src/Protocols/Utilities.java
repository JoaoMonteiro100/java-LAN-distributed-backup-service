package Protocols;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;


public class Utilities {
	
	public static String hashing (String key) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.update(key.getBytes("UTF-8"));
		byte[] digest = md.digest();
		
	    String hex = DatatypeConverter.printHexBinary(digest);
	    
	    return hex;
	}

}
