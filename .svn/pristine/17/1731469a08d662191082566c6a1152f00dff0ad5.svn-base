package nnsp.util;

import java.util.regex.Pattern;

public final class InetAddressUtils {
	private final static Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
	private final static Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
	private final static Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
   
	private final static Pattern PORT_PATTERN = Pattern.compile("(^[0-9]*$)");
	
	private final static Pattern DOMAIN_PATTERN = Pattern.compile("^[a-zA-Z0-9.-]{1,64}$");
	
	private final static Pattern MACADDR_PATTERN = Pattern.compile("^([0-9a-fA-F][0-9a-fA-F]-){5}([0-9a-fA-F][0-9a-fA-F])$");
	
	private final static Pattern EMAIL_PATTERN = Pattern.compile("^[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\\.[_0-9a-zA-Z-]+){1,2}$");
	private final static Pattern EMAIL_ID = Pattern.compile("^[a-zA-Z0-9]{1,64}$");
	private final static Pattern EMAIL_PW = Pattern.compile("^[a-zA-Z0-9\\!\\@\\#\\$\\%\\^\\*\\+\\=\\-\\?\\_\\-\\~\\&\\`]{1,128}$");
	
	private final static Pattern TEL_PATTERN = Pattern.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$");
	private final static Pattern MOBILE_PATTERN = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
	
	private InetAddressUtils(){}
    
    public static boolean isIPv4Address(String ip){
    	return IPV4_PATTERN.matcher(ip).matches();
    }
   
    public static boolean isIPv6StdAddress(String ip){
    	return IPV6_STD_PATTERN.matcher(ip).matches();
    }
   
    public static boolean isIPv6HexCompressedAddress(String ip){
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(ip).matches();
    }
    
    public static boolean isIPv6Address(String ip){
        return isIPv6StdAddress(ip) || isIPv6HexCompressedAddress(ip);
    }
   
    public static boolean isIpBandEquals2( String matchIp , String targetIp ){
        if ( matchIp.equals( targetIp ) == true ){
            return true;
        }
        return false;
    }
    
    public static boolean isPort(String port){
    	return PORT_PATTERN.matcher(port).matches();
    }
    
    public static boolean isDomain(String domain){
    	return DOMAIN_PATTERN.matcher(domain).matches();
    }
    
    public static boolean isMacAddr(String macaddr){
    	return MACADDR_PATTERN.matcher(macaddr).matches();
    }
    
    public static boolean isEmailAddr(String emailaddr){
    	return EMAIL_PATTERN.matcher(emailaddr).matches();
    }
    
    public static boolean isEmailId(String email_id){
    	return EMAIL_ID.matcher(email_id).matches();
    }
    
    public static boolean isEmailPw(String email_pw){
    	return EMAIL_PW.matcher(email_pw).matches();
    }
    
    public static boolean isTel(String tel_no){
    	return TEL_PATTERN.matcher(tel_no).matches();
    }
    
    public static boolean isMobile(String mobile_no){
    	return MOBILE_PATTERN.matcher(mobile_no).matches();
    }
}