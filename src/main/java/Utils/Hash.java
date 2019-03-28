package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Hash {

    public static String stringHash(String str) {
    	    	
        String myHash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
        return myHash;
    }
    
    public static boolean stringVerify(String str, String strHashed){
        String hashed = Hash.stringHash(str);
        return hashed.equals(strHashed);
    }
}
