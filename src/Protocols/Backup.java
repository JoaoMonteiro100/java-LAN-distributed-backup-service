package Protocols;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Backup extends Thread{
	
	public static void main (String args[]) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		Utilities u = new Utilities();
		System.out.println(u.hashing("potato"));
	}
}
