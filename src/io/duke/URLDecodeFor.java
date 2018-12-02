package io.duke;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class URLDecodeFor {

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
    public static void main(String[] args) {
    	
    	System.out.println("0336".hashCode());
    	

    	try {
			byte[] bytes = MessageDigest.getInstance("MD5").digest("".getBytes());
			
		    char[] hexChars = new char[bytes.length * 2];
		    for ( int j = 0; j < bytes.length; j++ ) {
		        int v = bytes[j] & 0xFF;
		        hexChars[j * 2] = hexArray[v >>> 4];
		        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		    }
		    System.out.println(new String(hexChars)); 

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        System.out.println(URLDecoder.decode(TXT));

    }
    
    static String TXT = "";
    
}
