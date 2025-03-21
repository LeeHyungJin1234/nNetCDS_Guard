/**
 * 전자정부 제공 ARIAUTIL
 * ARIAUtil
 */
package nnsp.security;

import nnsp.services.NcAuditService;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

import java.security.InvalidKeyException;

public class ARIAUtil {

     Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    static
    NcAuditService ncAuditService;

    // TODO: compare own implementation and original one,
    //       if results are same, remove and stick to original one

    @SuppressWarnings("resource")
	public static String ariaEncrypt(String str, String privateKey, String audit_page) {
        if (str == null || str.equals("")) return "";
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        byte[] p;
        byte[] c;
        ARIAEngine instance = null;
        try {
            instance = new ARIAEngine(256, privateKey);

            p = str.getBytes();

            int len = p.length;
            if ((len % 16) != 0) {
                len = (len / 16 + 1) * 16;
            }
            c = new byte[len];
            System.arraycopy(p, 0, c, 0, p.length);
            instance.CBC_256ENCRYPT(p, c, p.length);

            return new String(Hex.encodeHex(c)).toUpperCase().trim();
        } catch (InvalidKeyException e) {
            ncAuditService.mng_log_insert(
                (String) session.getAttribute("loginUserId"),
                (String) session.getAttribute("clientIp"),
                audit_page, "ARIA 암호화 실패" + e.getMessage(), "F", "W");
            throw new RuntimeException(e);
        }
    }

    /*
     *Aria 복호화
     */
    public static String ariaDecrypt(String strHex, String privateKey, String audit_page) {
        if (strHex == null || strHex.equals("")) return "";

        try {
            return ariaDecrypt(strHex, privateKey);
        } catch (InvalidKeyException e) {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession();
            ncAuditService.mng_log_insert(
                (String) session.getAttribute("loginUserId"),
                (String) session.getAttribute("clientIp"),
                audit_page, "ARIA 복호화 실패" + e.getMessage(), "F", "W");
            throw new RuntimeException(e);
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    public static String ariaDecrypt(String strHex, String privateKey) throws InvalidKeyException, DecoderException {
        if (strHex == null || strHex.equals("")) return "";

        byte[] p;
        byte[] c;
        ARIAEngine instance = null;
        try {
            instance = new ARIAEngine(256, privateKey);
            c = Hex.decodeHex(strHex);
            p = new byte[c.length];
            instance.decrypt(c, p, p.length);

            StringBuffer buf = new StringBuffer();
            buf.append(new String(p));

            return buf.toString().trim();
        } catch (InvalidKeyException e) {
            throw e;
        } catch (DecoderException e) {
            throw e;
        }
    }
}