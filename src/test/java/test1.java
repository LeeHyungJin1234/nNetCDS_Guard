/*import java.io.UnsupportedEncodingException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;*/

public class test1 {
	/*public static void main(String[] args) throws UnsupportedEncodingException{
		  shift("010000",3);
		 // left_shift("000010",3);
		  String b="!nnsp!123";
		  int a = b.charAt(0);
		  String c = Character.toString((char)a);
		  System.out.println(a);
		  System.out.println(c);
		 }

		 public final static void shift(String str,int size)
		 {
		  byte[] orgByte=null;
		  byte[] resultByte=new byte[str.length()];
		  orgByte=str.getBytes();
		  
		  int strlen=orgByte.length;
		  for(int i=0;i<strlen;i++){
		   resultByte[(i+size)%strlen]=orgByte[i];
		  }
		  String result=null;
		  result=new String(resultByte);
		  System.out.println(result);
		 }
		//LEFT SHFIT

		 public final static void left_shift(String str,int size) throws UnsupportedEncodingException{
			  
			  byte[] orgByte=null;
			  byte[] resultByte=new byte[str.length()];
			  orgByte=str.getBytes("ASCII");
			  String s="0";
			  byte []a=s.getBytes("ASCII");
			  
			  int strlen=orgByte.length;
			  for(int i=1;i<strlen;i++){
			   resultByte[(i-size)%strlen]=orgByte[i];
			   if(i==strlen-1){
			    resultByte[i]=a[0];
			   }
			  }
			  
			  String result=null;
			  result=new String(resultByte);
			  System.out.println(result);
			 }*/
	/*public static void main(String[] args) {
        int a = 23;
       System.out.println(getBit(a));
        int b = a << 2;
       System.out.println(getBit(b));
       int c = a >> 2;
       System.out.println(getBit(c));
       int d = c << 2;
       System.out.println(getBit(d));
}

	static String getBit(int n) {
	  int[] array = new int[8];
	  int iattr = (int) ((byte) n & 0xFF);
	  int bitWise = 1;
	  for (int i = 0; i < 8; i++) {
	         if ((bitWise & iattr) > 0) array[i] = 1;
	         else  array[i] = 0;
	   bitWise = (bitWise << 1) & 0xfe;
	  }
	  StringBuilder sb = new StringBuilder();
	  for (int i = 0; i < array.length; ++i) sb.append(array[7 - i]);
	  return sb.toString();
	}*/

		 
		/*  public String convertStringToHex(String str){
		 
		   char[] chars = str.toCharArray();
		 
		   StringBuffer hex = new StringBuffer();
		   for(int i = 0; i < chars.length; i++){
		     hex.append(Integer.toHexString((int)chars[i]));
		   }
		 
		   return hex.toString();
		  }
		 
		  public String convertHexToString(String hex){
		 
		   StringBuilder sb = new StringBuilder();
		   StringBuilder temp = new StringBuilder();
		 
		   //49204c6f7665204a617661 split into two characters 49, 20, 4c...
		   for( int i=0; i<hex.length()-1; i+=2 ){
		 
		       //grab the hex in pairs
		       String output = hex.substring(i, (i + 2));
		       //convert hex to decimal
		       int decimal = Integer.parseInt(output, 16);
		       //convert the decimal to character
		       sb.append((char)decimal);
		 
		       temp.append(decimal);
		   }
		   System.out.println("Decimal : " + temp.toString());
		 
		   return sb.toString();
		  }
		 
		  public static void main(String[] args) {
		 
			  test1 strToHex = new test1();
		   System.out.println("\n***** Convert ASCII to Hex *****");
		   String str = "I Love Java!";  
		   System.out.println("Original input : " + str);
		 
		   String hex = strToHex.convertStringToHex(str);
		 
		   System.out.println("Hex : " + hex);
		 
		   System.out.println("\n***** Convert Hex to ASCII *****");
		   System.out.println("Hex : " + hex);
		   System.out.println("ASCII : " + strToHex.convertHexToString(hex));
		  }*/
		
}