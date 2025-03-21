package nnsp.util;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class StringUtil {
	private static final  Logger logger = LoggerFactory.getLogger(StringUtil.class);
	private StringUtil() {
	}

	/**
	 * <pre>
	 * 숫자 여부 체크
	 * </pre>
	 * 
	 * @param str
	 * @return 숫자 여부
	 * @return boolean
	 */
	public static boolean checkNumber(String str) {
		if ("".equals(str))
			return false;
		String patternRegex = "^[0-9]*$";
		return Pattern.matches(patternRegex, str);
	}

	/**
	 * <pre>
	 * 이메일 정합성 체크
	 * </pre>
	 * 
	 * @param str
	 * @return 이메일 정합성
	 * @return boolean
	 */
	public static boolean checkEmail(String str) {
		if ("".equals(str))
			return false;
		String patternRegex = "^[._0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$";
		return Pattern.matches(patternRegex, str);
	}

	/**
	 * <pre>
	 * IP 정합성 체크
	 * </pre>
	 * 
	 * @param str
	 * @return IP 정합성
	 * @return boolean
	 */
	public static boolean checkIPAddress(String str) {
		if ("".equals(str))
			return false;
		String patternRegex = "([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})";
		return Pattern.matches(patternRegex, str);
	}

	/**
	 * <pre>
	 * Customer Telephone Number 정합성 체크
	 * </pre>
	 * 
	 * @param str
	 * @return Customer Telephone Number 정합성
	 * @return boolean
	 */
	public static boolean checkCTN(String str) {
		if ("".equals(str))
			return false;
		String patternRegex = "01(?:0|1|[6-9])-?(?:\\d{3}|\\d{4})-?\\d{4}$";
		return Pattern.matches(patternRegex, str);
	}

	/**
	 * <pre>
	 * 전화번호 정합성 체크
	 * </pre>
	 * 
	 * @param str
	 * @return 전화번호 정합성
	 * @return boolean
	 */
	public static boolean checkTel(String str) {
		if ("".equals(str))
			return false;
		String patternRegex = "0(?:[0-9]{1,3})-?(?:\\d{3}|\\d{4})-?\\d{4}$";
		return Pattern.matches(patternRegex, str);
	}

	/**
	 * <pre>
	 * 날짜정합성 체크
	 * </pre>
	 * 
	 * @param str
	 * @return 날짜 정합성
	 * @return boolean
	 */
	public static boolean checkDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (str == null || "".equals(str))
			return false;
		String format = null;
		try {
			format = sdf.format(sdf.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str.equals(format);
	}
	public static boolean checkDate2(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (str == null || "".equals(str))
			return false;
		String format = null;
		try {
			format = sdf.format(sdf.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str.equals(format);
	}

	/**
	 * <pre>
	 * URL decoder
	 * </pre>
	 * 
	 * @param name
	 * @return URL decode된 문자열
	 * @return String
	 */
	public static String getUrlDecoding(String name) {
		String decodeName = "";

		try {
			decodeName = URLDecoder.decode(name, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return decodeName;
	}

	/**
	 * <pre>
	 * URL Encoder
	 * </pre>
	 * 
	 * @param name
	 * @return URL encode된 문자열
	 * @return String
	 */
	public static String getUrlEncoding(String name) {
		String encodeName = "";

		try {
			encodeName = URLEncoder.encode(name, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return encodeName;
	}

	/**
	 * <pre>
	 * CTN masking 처리
	 * </pre>
	 * 
	 * @param ctn
	 * @return masking 처리된 문자열
	 * @return String
	 */
	public static String getMaskingCtn(String ctn) {
		if (ctn == null || "".equals(ctn))
			return "";

		String maskingCtn;
		if (ctn.length() == 10) {
			maskingCtn = ctn.substring(0, 3) + "-" + ctn.substring(3, 4) + "**-" + ctn.substring(6, 8) + "**";
		}
		else {
			maskingCtn = ctn.substring(0, 3) + "-" + ctn.substring(3, 5) + "**-" + ctn.substring(7, 9) + "**";
		}
		return maskingCtn;
	}

	/**
	 * <pre>
	 * null  문자열 처리
	 * </pre>
	 * 
	 * @param input
	 * @param output
	 * @return null 대체 문자열
	 * @return String
	 */
	public static String NVL(String input, String output) {
		if (input == null || input.equals("") || input.toUpperCase().equals("NULL")) {
			input = output;
		}
		return input;
	}

	/**
	 * <pre>
	 * null object 처리
	 * </pre>
	 * 
	 * @param input
	 * @param output
	 * @return 대체 문자열
	 * @return String
	 */
	public static String NVL(Object input, String output) {
		String returnValue = null;
		if (input == null || input.equals("")) {
			returnValue = output;
		}
		else {
			returnValue = (String) input;
		}
		return returnValue;
	}

	/**
	 * <pre>
	 * null object 처리
	 * </pre>
	 * 
	 * @param input
	 * @return empty value
	 * @return String
	 */
	public static String NVL(Object input) {
		return NVL(input, "");
	}

	/**
	 * <pre>
	 * null string 처리
	 * </pre>
	 * 
	 * @param input
	 * @return empty value
	 * @return String
	 */
	public static String NVL(String input) {
		return NVL(input, "");
	}

	/**
	 * <pre>
	 * trim null check
	 * </pre>
	 * 
	 * @param input
	 * @return trim value
	 * @return String
	 */
	public static String NVLTrim(String input) {
		String returnValue = "";

		if (input == null || input.equals("")) {
			returnValue = "";
		}
		else {
			returnValue = (String) input.trim();
		}
		return returnValue;
	}

	/**
	 * <pre>
	 * lpad 처리
	 * </pre>
	 * 
	 * @param str
	 * @param len
	 * @param addStr
	 * @return lpad 문자열
	 * @return String
	 */
	public static String lpad(String str, int len, String addStr) {
		String result = str;
		int templen = len - result.length();

		for (int i = 0; i < templen; i++) {
			result = addStr + result;
		}

		return result;
	}

	/**
	 * <pre>
	 * 각 월 마지막 일자
	 * </pre>
	 * 
	 * @param str
	 * @return 마지막 일자
	 * @return String
	 */
	public static String getMonthLastDay(String str) {
		String result = "01";
		int year = Integer.parseInt(str.substring(0, 4));
		int month = Integer.parseInt(str.substring(4, 6));

		if (month == 4 || month == 6 || month == 9 || month == 11) {
			result = "30";
		}
		else if (month == 2) {
			result = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? "29" : "28";
		}
		else {
			result = "31";
		}

		return result;
	}

	/**
	 * <pre>
	 * 부등호 조회
	 * </pre>
	 * 
	 * @param str
	 * @return 부등호
	 * @return String
	 */
	public static String getCondVars(String str) {
		String[] operators = { "==", "!=", ">", "<", ">=", "<=" };
		String operator = null;
		String var;
		for (String oper : operators) {
			if (str.indexOf(oper) > -1) {
				operator = oper;
				break;
			}
		}
		if (operator == null || operator.isEmpty())
			return null;

		var = str.split(operator)[0];
		return var;
	}

	/**
	 * <pre>
	 * 요일 값 조회
	 * </pre>
	 * 
	 * @param dow
	 * @return 요일 값
	 * @return String
	 */
	public static String getTransDow(int dow) {
		String dayOfWeek = null;

		if (dow == 0) {
			dayOfWeek = "일요일";
		}
		else if (dow == 1) {
			dayOfWeek = "월요일";
		}
		else if (dow == 2) {
			dayOfWeek = "화요일";
		}
		else if (dow == 3) {
			dayOfWeek = "수요일";
		}
		else if (dow == 4) {
			dayOfWeek = "목요일";
		}
		else if (dow == 5) {
			dayOfWeek = "금요일";
		}
		else if (dow == 6) {
			dayOfWeek = "토요일";
		}

		return dayOfWeek;
	}

	/**
	 * <pre>
	 * 날짜 문자열 조회
	 * </pre>
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return 날짜 문자열
	 * @return String
	 */
	public static String getTransDate(String year, String month, String date) {
		StringBuffer sb = new StringBuffer();

		sb.append(year);

		if (month.length() == 1) {
			sb.append("0" + month);
		}
		else {
			sb.append(month);
		}

		if (date.length() == 1) {
			sb.append("0" + date);
		}
		else {
			sb.append(date);
		}

		return sb.toString();
	}

	/**
	 * <pre>
	 * 날짜 리턴
	 * </pre>
	 * 
	 * @param str
	 * @return 날짜 문자열
	 * @return String
	 */
	public static String getStrDate(String str) {
		SimpleDateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
		Date date = null;
		long lDate = 0;
		try {
			date = iFormatter.parse(str);
			lDate = date.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return oFormatter.format(lDate);
	}
	
	public static String getStrDateYMD(String strdate) {
		SimpleDateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		Date date = null;
		long lDate = 0;
		try {
			if(strdate!=null && !strdate.equals("")){
				date = iFormatter.parse(strdate);
			}
			else{
				date = new Date(System.currentTimeMillis());
			}
			lDate = date.getTime();
		} catch (ParseException e) {
			logger.info("error Parse");
		}catch (Exception e) {
			logger.info("error String");
		}

		return oFormatter.format(lDate);
	}

	/**
	 * <pre>
	 * 연도 리턴
	 * </pre>
	 * 
	 * @param str
	 * @return 연도 문자열
	 * @return String
	 */
	public static String getYear(String str) {
		return str.substring(0, 4);
	}

	/**
	 * <pre>
	 * 개월 리턴
	 * </pre>
	 * 
	 * @param str
	 * @return 개월 문자열
	 * @return String
	 */
	public static String getMonth(String str) {
		return str.substring(5, 7);
	}

	/**
	 * <pre>
	 * 일자 리턴
	 * </pre>
	 * 
	 * @param str
	 * @return 일자 문자열
	 * @return String
	 */
	public static String getDay(String str) {
		return str.substring(8, 10);
	}

	/**
	 * <pre>
	 * 해당 날짜 다음 달 리턴
	 * </pre>
	 * 
	 * @param date
	 * @param interval
	 * @return 다음 달 리턴
	 * @return Date
	 */
	public static Date getNextMonth(Date date, int interval) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.MONTH, interval);
		return cal.getTime();
	}

	/**
	 * <pre>
	 * +inteval 된 날짜 리턴
	 * </pre>
	 * 
	 * @param date
	 * @param interval
	 * @return 날짜 문자열
	 * @return String
	 */
	public static String getStrNextMonth(Date date, int interval) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DecimalFormat df = new DecimalFormat("00");

		cal.add(Calendar.MONTH, interval);

		String strYear = Integer.toString(cal.get(Calendar.YEAR));
		String strMonth = df.format(cal.get(Calendar.MONTH) + 1);
		String strDay = df.format(cal.get(Calendar.DATE));

		return strYear + "." + strMonth + "." + strDay;
	}

	/**
	 * <pre>
	 * 날짜 간격 조회
	 * </pre>
	 * 
	 * @param sdate
	 * @param edate
	 * @return 시간 차 리턴
	 * @return long
	 */
	public static long getHourInterval(String sdate, String edate) {
		Calendar sCal = getCalendar(sdate);
		Calendar eCal = getCalendar(sdate);
		long intval = (eCal.getTimeInMillis() - sCal.getTimeInMillis()) / (1000 * 60 * 60);
		return intval;
	}

	/**
	 * <pre>
	 * 날짜 형식 변환
	 * </pre>
	 * 
	 * @param time
	 * @return 해당 날짜 리턴
	 * @return Calendar
	 */
	public static Calendar getCalendar(String time) {
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(time.substring(0, 4)), Integer.parseInt(time.substring(4, 6)) - 1,
				Integer.parseInt(time.substring(6, 8)), Integer.parseInt(time.substring(8, 10)), 0);

		return cal;
	}

	/**
	 * <pre>
	 * -intval 된 시간
	 * </pre>
	 * 
	 * @param intval
	 * @return 날짜 문자열
	 * @return String
	 */
	public static String getStrPrevTime(long intval) {
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyyMMddHH", Locale.KOREA);
		long intVal = 1000 * 60 * 60 * intval;
		Date date = new Date(System.currentTimeMillis() - intVal);

		return oFormatter.format(date);
	}
	
	
	/**
	 * <pre>
	 * -intval 된 날짜
	 * </pre>
	 * 
	 * @param intval
	 * @return 날짜 문자열
	 * @return String
	 */
	public static String getStrPrevDay(long intval) {
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		long intVal = 1000 * 60 * 60 * 24 * intval;
		Date date = new Date(System.currentTimeMillis() - intVal);

		return oFormatter.format(date);
	}
	public static String getStrNextDay(long intval) {
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		long intVal = 1000 * 60 * 60 * 24 * intval;
		Date date = new Date(System.currentTimeMillis() + intVal);

		return oFormatter.format(date);
	}
	public static String getStrDay(long intval) {
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		long intVal = 1000 * 60 * 60 * 24 * intval;
		Date date = new Date(System.currentTimeMillis() + intVal);

		return oFormatter.format(date);
	}
	public static String getStrDayOneHourBefore(long intval) {
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
		long intVal = 1000 * 60 * 60 * 24 * intval;
		long oneHourInMillis = 1000 * 60 * 60;
		
		Date date = new Date(System.currentTimeMillis() + intVal - oneHourInMillis);

		return oFormatter.format(date);
	}
	public static String getStrDayWithTime(long intval) {
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
		long intVal = 1000 * 60 * 60 * 24 * intval;
		Date date = new Date(System.currentTimeMillis() + intVal);

		return oFormatter.format(date);
	}
	public static String getStrThisMonth(long intival)  {
		SimpleDateFormat oFormatter = new SimpleDateFormat("yyyyMM", Locale.KOREA);
		Calendar cal = Calendar.getInstance();
	    cal.setTime(new Date());
	    cal.add(Calendar.MONTH, 1);
	    
		return oFormatter.format(cal.getTime());
	}
	
	
	/**
	 * 숫자에 천단위마다 콤마 넣기
	 * @param num
	 * @return
	 */
	public static String toNumFormat(int num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}
	
	/**
	 * 숫자에 천단위마다 콤마 넣기
	 * @param num
	 * @return
	 */
	public static String toNumFormat(long num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}

	public static String byteCalculation(Double size) {
		String retFormat = "0";

		String[] s = { "Bytes", "KB", "MB", "GB", "TB", "PB", "EB" };

		if (size > 0) {
			int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
			idx = (idx == 0) ? 1 : idx;		// 기본단위 KB 부터
			DecimalFormat df = new DecimalFormat("#,###.##");
			double ret = ((size / Math.pow(1024, Math.floor(idx))));
			retFormat = df.format(ret) + " " + s[idx];
		} else {
			retFormat += " " + s[1];		// 기본단위 KB 부터
		}
		return retFormat;
	}
	
	public static String numCalculation(Double size) {
		String retFormat = "0";
		
		String[] s = { "", "K", "M", "G", "T", "P", "E" };
		
		if (size > 0) {
			int idx = (int) Math.floor(Math.log(size) / Math.log(1000));
			DecimalFormat df = new DecimalFormat("#,###.#");
			double ret = ((size / Math.pow(1000, Math.floor(idx))));
			retFormat = df.format(ret) + " " + s[idx];
		}
		return retFormat;
	}
	
	/**
	 * <pre>
	 * IP String to Long
	 * </pre>
	 * 
	 * @param str
	 * @return IP 문자열 long형으로 변환
	 * @return long
	 */
	public static long ipToLong(String addr) {
        String[] addrArray = addr.split("\\.");

        long num = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;

            num += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));
        }
        return num;
    }
	
	/**
	 * <pre>
	 * IP Long to String
	 * </pre>
	 * 
	 * @param long
	 * @return IP long형태를 문자열로 변환
	 * @return string
	 */
	public static String lognToIp(long l) {
        return ((l >> 24) & 0xFF) + "." +
                ((l >> 16) & 0xFF) + "." +
                ((l >> 8) & 0xFF) + "." +
                (l & 0xFF);
    }
}