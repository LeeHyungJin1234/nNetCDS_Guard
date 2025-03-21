package nnsp.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256Util {
	
	private static volatile AES256Util INSTANCE;

    public static String ivBytes = "Tk5TUE5OU1BOTlNQTk5TUA==";
    public static String fillstr = "\n";

    public static AES256Util getInstance() {
        if (INSTANCE == null) {
            synchronized (AES256Util.class) {
            	if (INSTANCE == null)
            		INSTANCE = new AES256Util();
            }
        }
        return INSTANCE;
    }
    
    // 암호화
    public static String strEncode(String str, String secretKey) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    	byte[] keyData = secretKey.getBytes();
    	SecretKey secureKey = new SecretKeySpec(keyData, "AES");

    	Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    	c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(Base64.decodeBase64(ivBytes.getBytes())));
    	//c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(ivBytes));

    	byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
    	return new String(Base64.encodeBase64(encrypted));
    }
 
    // 암호화-ftp pw, smtp pw만 사용
    public static String strEncode2(String str, String secretKey) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    	byte[] keyData = secretKey.getBytes();
    	SecretKey secureKey = new SecretKeySpec(keyData, "AES");

    	Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    	c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(Base64.decodeBase64(ivBytes.getBytes())));
    	//c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(ivBytes));

    	if(str.length()<16) { // 패딩값을 고정;;
    		for(int i=str.length(); i<16; i++) {
    			str += fillstr;
    		}
    	}
    	
    	byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
    	return new String(Base64.encodeBase64(encrypted));
    }
    
    //복호화
    public static String strDecode(String str, String secretKey) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
    	byte[] keyData = secretKey.getBytes();
    	SecretKey secureKey = new SecretKeySpec(keyData, "AES");
    	
    	Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
    	c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(Base64.decodeBase64(ivBytes.getBytes())));
    	//c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(ivBytes));
    	
    	byte[] byteStr = Base64.decodeBase64(str.getBytes());
    	return new String(c.doFinal(byteStr), "UTF-8");
    }
 
}