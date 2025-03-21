/*import java.io.UnsupportedEncodingException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;*/

public class test2 {
		 
		 public static void main(String[] args) {
		  //원문
		  String code = "[X7Bcs";
		  System.out.println(code);
		  test2 test = new test2(); 
		  //암호화
		  String encode = test.XOR(code);
		  System.out.println(encode);
		  //복호화
		  String decode = test.XOR(encode);
		  System.out.println(decode);
		 }
		 
		/***************************************************************************
		  * XOR 암호화 / 복호화
		  * @param code
		  * @return
		  * @throws CobraException
		  **************************************************************************/
		 public String XOR(String code)
		 {
		  //암호화 키
		  byte keyChar[] = "656D2B6A209ACE857601D1042D8A1401413172549F906BB9B2675CF727A397968B255FAF2461C2376B120DAEC765ACEC53C35559B934FEB6CAFD7D759A7CE296".getBytes();
		  
		  //암호화할 대상 
		  byte codeChar[] = new byte[code.getBytes().length]; //code의 문자열 길이만큼의 배열을 만든다.
		  codeChar = code.getBytes(); //code를 Byte형으로 변환한다.
		   
		  //XOR 연산
		  for(int i=0, j=0; i< code.getBytes().length; i++)
		  {
		   codeChar[i] = (byte) (codeChar[i] ^ keyChar[j]); //code의 한문자와 key의 한문자를 ^(XOR)연산을 한후 byte형으로 변환한다.
		   j = (++j < keyChar.length ? j : 0); //j의 값이 key문자열의 길이보다 커질경우 0으로 아닐경우는 j의 값을 갖는다.
		  }
		 
		  return new String(codeChar) ; //byte배열인 code를 String으로 변환하여 반환한다.
		 }
		
}