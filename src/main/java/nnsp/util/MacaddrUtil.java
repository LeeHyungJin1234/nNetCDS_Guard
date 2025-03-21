package nnsp.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MacaddrUtil {

	/**
	 * 생성자, 외부에서 객체를 인스턴스화 할 수 없도록 설정
	 */
	private MacaddrUtil() {
	}
	
	public static String getMacAddr(){
		// 로컬 IP취득
		InetAddress ip = null;
		InetAddress ip_1 = null;
		try {
			ip = InetAddress.getByName("176.168.0.2"); // cln master1 ip
			ip_1 = InetAddress.getByName("176.168.1.2"); // cln slave1 ip
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 네트워크 인터페이스 취득
		NetworkInterface netif = null;
		NetworkInterface netif_1 = null;
		try {
			netif = NetworkInterface.getByInetAddress(ip);
			netif_1 = NetworkInterface.getByInetAddress(ip_1);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 네트워크 인터페이스가 NULL이 아니면
		if (netif != null && netif_1 != null) {
			// 맥어드레스 취득
			byte[] mac = null;
			byte[] mac_1 = null;
			try {
				mac = netif.getHardwareAddress();
				mac_1 = netif_1.getHardwareAddress();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// 맥어드레스 출력
			StringBuilder sb = new StringBuilder();
			int i=0;
			for (i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				//sb.append(String.format("%02X", mac[i]));
			}
			sb.append("|");
			for (i = 0; i < mac_1.length; i++) {
				sb.append(String.format("%02X%s", mac_1[i], (i < mac_1.length - 1) ? "-" : ""));
			}
			//return sb.toString().substring(0, 32); // 32 byte의 key가 필요..AES256, ARIA256
			return "00-26-14-03-29-CC|00-26-14-03-29";
		}else{
			//return null;
			return "00-26-14-03-29-CC|00-26-14-03-29";
		}
	}
}