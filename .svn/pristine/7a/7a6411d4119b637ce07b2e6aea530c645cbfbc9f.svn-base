package nnsp.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import nnsp.services.NcAuditService;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RSAUtil {

  @Autowired
  static
  NcAuditService ncAuditService;

  private static PublicKey publicKey;
  private static PrivateKey privateKey;

  private static Cipher cipher;

  private RSAUtil() {

    String audit_page = "RSA 암호키 생성";
    try {
      NcSecurityUtil.setDefaultDrbgConfig();
      Security.addProvider(
        new org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider());

      KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BCFIPS");

      generator.initialize(new RSAKeyGenParameterSpec(4096, RSAKeyGenParameterSpec.F4));

      KeyPair keyPair = generator.generateKeyPair();
      publicKey = keyPair.getPublic();
      privateKey = keyPair.getPrivate();
    } catch (NoSuchAlgorithmException e) {
      ncAuditService.mng_log_insert(
        "SYSTEM",
        "",
        audit_page, "암호키 생성 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    } catch (NoSuchProviderException e) {
      ncAuditService.mng_log_insert(
        "SYSTEM",
        "",
        audit_page, "암호키 생성 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    } catch (InvalidAlgorithmParameterException e) {
      ncAuditService.mng_log_insert(
        "SYSTEM",
        "",
        audit_page, "암호키 생성 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    }

    try {
      cipher = Cipher.getInstance("RSA", "BCFIPS");
    } catch (NoSuchAlgorithmException e) {
      ncAuditService.mng_log_insert(
        "SYSTEM",
        "",
        audit_page, "Cipher 객체 생성 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    } catch (NoSuchProviderException e) {
      ncAuditService.mng_log_insert(
        "SYSTEM",
        "",
        audit_page, "Cipher 객체 생성 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    } catch (NoSuchPaddingException e) {
      ncAuditService.mng_log_insert(
        "SYSTEM",
        "",
        audit_page, "Cipher 객체 생성 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    }
  }

  public static PublicKey getPublicKey() {
    return publicKey;
  }

  public static PrivateKey getPrivateKey() {
    return privateKey;
  }

  public static String decrypt(String encrypted, String audit_page) {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession session = attr.getRequest().getSession();

    try {
      try {
        byte[] encryptedBytes = Hex.decodeHex(encrypted);

        try {
          cipher.init(Cipher.DECRYPT_MODE, privateKey);
          byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
          String decryptedString = null; // 문자 인코딩
          decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);

          return decryptedString;
        } catch (InvalidKeyException e) {
          ncAuditService.mng_log_insert(
            (String) session.getAttribute("loginUserId"),
            (String) session.getAttribute("clientIp"),
            audit_page, "RSA 복호화 실패" + e.getMessage(), "F", "W");
          throw new RuntimeException(e);
        }
      } catch (DecoderException e) {
        ncAuditService.mng_log_insert(
          (String) session.getAttribute("loginUserId"),
          (String) session.getAttribute("clientIp"),
          audit_page, "RSA 복호화 실패" + e.getMessage(), "F", "W");
        throw new RuntimeException(e);
      }
    } catch (IllegalBlockSizeException e) {
      ncAuditService.mng_log_insert(
        (String) session.getAttribute("loginUserId"),
        (String) session.getAttribute("clientIp"),
        audit_page, "RSA 복호화 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    } catch (BadPaddingException e) {
//      ncAuditService.mng_log_insert(
//        (String) session.getAttribute("loginUserId"),
//        (String) session.getAttribute("clientIp"),
//        audit_page, "RSA 복호화 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    }
  }

  public static ImmutablePair<String, String> getPublicKeyModulusAndExponent() {
    KeyFactory keyFactory = null;
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession session = attr.getRequest().getSession();
    try {
      keyFactory = KeyFactory.getInstance("RSA", "BCFIPS");
    } catch (NoSuchAlgorithmException e) {
      ncAuditService.mng_log_insert(
        (String) session.getAttribute("loginUserId"),
        (String) session.getAttribute("clientIp"),
        "로그인", "키 팩토리 생성 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    } catch (NoSuchProviderException e) {
      ncAuditService.mng_log_insert(
        (String) session.getAttribute("loginUserId"),
        (String) session.getAttribute("clientIp"),
        "로그인", "키 팩토리 생성 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    }
    RSAPublicKeySpec publicSpec = null;
    try {
      publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
    } catch (InvalidKeySpecException e) {
      ncAuditService.mng_log_insert(
        (String) session.getAttribute("loginUserId"),
        (String) session.getAttribute("clientIp"),
        "로그인", "키 스펙 생성 실패" + e.getMessage(), "F", "W");
      throw new RuntimeException(e);
    }

    return new ImmutablePair<>(
      publicSpec.getModulus().toString(16),
      publicSpec.getPublicExponent().toString(16)
    );
  }
  

  /**
   * rsa 복호화
   * @param privateKey
   * @param securedValue
   * @return 
	 * @throws Exception 
   */
  public static String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
  	String decryptedValue = "";

		Cipher cipher = Cipher.getInstance("RSA");
		/**
 		* 암호화 된 값은 byte 배열이다.
 		* 이를 문자열 폼으로 전송하기 위해 16진 문자열(hex)로 변경한다. 
 		* 서버측에서도 값을 받을 때 hex 문자열을 받아서 이를 다시 byte 배열로 바꾼 뒤에 복호화 과정을 수행한다.
 		*/
		byte[] encryptedBytes = hexToByteArray(securedValue);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.

  	return decryptedValue;
  }
  
  /**
   * 16진 문자열을 byte 배열로 변환
   * @param hex
   * @return
   */
	public static byte[] hexToByteArray(String hex) {
	  	if (hex == null || hex.length() % 2 != 0) {
	  		return new byte[]{};
	  	}
	  
	  	byte[] bytes = new byte[hex.length() / 2];
	  	for (int i = 0; i < hex.length(); i += 2) {
	  		byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
	  		bytes[(int) Math.floor(i / 2)] = value;
	  	}
	  	return bytes;
	} 
}