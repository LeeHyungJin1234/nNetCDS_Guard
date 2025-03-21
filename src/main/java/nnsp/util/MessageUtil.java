package nnsp.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
 
public class MessageUtil {
    private static MessageSourceAccessor messageSourceAccessor = null;
      
    @SuppressWarnings("static-access")
	public void setMessageSourceAccessor(MessageSourceAccessor msAcc) {
        this.messageSourceAccessor = msAcc;
    }
 
    public static Locale getLocale() {
    	Locale defaultLocale = Locale.ENGLISH;
 
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String headerLocale = request.getHeader("Accept-Language"); // 브라우저에서 보내주는 정보
 
        if (StringUtils.isNotBlank(headerLocale)) {
            if (headerLocale.indexOf(Locale.JAPAN.getLanguage()) != -1) {
                defaultLocale = Locale.JAPAN;
            } else if (headerLocale.indexOf(Locale.KOREA.getLanguage()) != -1) {
                defaultLocale = Locale.KOREA;
            }
        }
 
        return defaultLocale;
    }
 
    public static Locale getSessionLocale() {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	HttpSession session = request.getSession();
    	return (Locale) session.getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE");
    }
    
    public static String getMessage(String key) {
    	//return messageSourceAccessor.getMessage(key, getLocale());
        return messageSourceAccessor.getMessage(key, getSessionLocale());
    }
 
    public static String getMessage(String key, Object... args) {
        return messageSourceAccessor.getMessage(key, args, getLocale());
    }
 
    public static String getMessage(Locale locale, String key, Object... args) {
        return messageSourceAccessor.getMessage(key, args, locale);
    }
}