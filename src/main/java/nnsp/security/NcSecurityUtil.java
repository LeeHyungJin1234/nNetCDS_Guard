package nnsp.security;

import static nnsp.security.Constants.ITERATIONS_CNT;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Base64;
import java.util.Base64.Encoder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nnsp.vo.NcUser;
import nnsp.vo.NcUserDetails;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.EntropySourceProvider;
import org.bouncycastle.crypto.fips.FipsDRBG;
import org.bouncycastle.crypto.util.BasicEntropySourceProvider;
import org.springframework.beans.factory.annotation.Autowired;

import nnsp.services.NcAuditService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class NcSecurityUtil {
	@SuppressWarnings("unused")
	@Autowired
	private NcAuditService ndAuditService;

  public static final ScheduledExecutorService scheduler =
    Executors.newScheduledThreadPool(10);

  private NcSecurityUtil() {

  }

  private static final String[] IP_HEADERS = {
    "X-Forwarded-For",
    "WL-Proxy-Client-IP",
    // "Proxy-Client-IP",
    // "HTTP_X_FORWARDED_FOR",
    // "HTTP_X_FORWARDED",
    // "HTTP_X_CLUSTER_CLIENT_IP",
    // "HTTP_CLIENT_IP",
    // "HTTP_FORWARDED_FOR",
    // "HTTP_FORWARDED",
    // "HTTP_VIA",
    // "REMOTE_ADDR"

    // you can add more matching headers here ...
  };

  public static String getRequestIP(HttpServletRequest request) {
    for (String header: IP_HEADERS) {
      String value = request.getHeader(header);
      if (value == null || value.isEmpty()) {
        continue;
      }
      String[] parts = value.split("\\s*,\\s*");
      return parts[0];
    }
    return request.getRemoteAddr();
  }

  public static SecureRandom getSecureRandom() {
    return CryptoServicesRegistrar.getSecureRandom();
  }

  // The Bouncy Castle FIPS Java API in 100 Examples
  // Creating a FIPS Approved SecureRandom for Key
  // Configuring a Default SecureRandom

  public static void setDefaultDrbgConfig() {
    // The generateSeed() method of a regular SecureRandom is used as the
    // entropy source. This makes use of any entropy source the running
    // JVM is configured for.
    EntropySourceProvider entSource =
      new BasicEntropySourceProvider(new SecureRandom(), true);

    // "setPersonalizationString" is used to reduce the likelihood of
    // two DRBGs somehow producing the same key stream where the entropy
    // source turns out to be similar.
    // The personalization string can be a secret or not but it needs
    // to be unique.
    FipsDRBG.Builder drgbBldr = FipsDRBG.SHA512.fromEntropySource(entSource)
      .setSecurityStrength(256)
      .setEntropyBitsRequired(256)
      .setPersonalizationString(ExValues.PersonalizationString);

    // The “prediction resistance” is set to to true as higer
    // standards are needed for keys or components of keys.
    CryptoServicesRegistrar.setSecureRandom(
      drgbBldr.build(ExValues.Nonce, true));
  }

  public static String getSecureRandomString(int length) {
    SecureRandom random = getSecureRandom();
    Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    byte[] buffer = new byte[length];
    random.nextBytes(buffer);
    return encoder.encodeToString(buffer);
  }

  private static int charCodeRotate(int charCode, int rotate) {
    int result = charCode + (rotate % 94);

    if (result > 126) {
      result -= 94;
    } else if (result < 33) {
      result += 94;
    }

    return result;
  }

  public static String getRotatedString(String str, int rotate) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      int charCode = str.codePointAt(i);
      sb.append((char) charCodeRotate(charCode, rotate));
    }
    return sb.toString();
  }

  public static void logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null){
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    SecurityContextHolder.getContext().setAuthentication(null);
    request.getSession().invalidate();
    request.getSession(true);
  }

  public static NcUser getAuthenticatedNtUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      return ((NcUserDetails) auth.getDetails()).getNcUser();
    }
    return null;
  }
  
//SHA-256 알고리즘 적용
  public static String getSHA256Encrypt(String pwd, String salt) {
      String result = "";
      try {
          MessageDigest md = MessageDigest.getInstance("SHA-256");
          
          md.reset();
          md.update(salt.getBytes());
          
          byte[] pwdSalt = md.digest(pwd.getBytes());
          
          for(int i=0; i<ITERATIONS_CNT; i++) {
        	  md.reset();
        	  pwdSalt = md.digest(pwdSalt);
          }
          
          result = Hex.encodeHexString(pwdSalt);

      } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException(e);
      }

      return result;
  }
}
