package nnsp.scheduler;

import nnsp.services.NcConfigService;
import nnsp.util.EmailUtil;
import nnsp.vo.NcConfig;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

@Component
public class MailSender {
	
	@Autowired
	private NcConfigService ncConfigService;
	
	private static String smtp_host;
	@SuppressWarnings("unused")
	private static String smtp_id;
	@SuppressWarnings("unused")
	private static String smtp_pw;
	private static int smtp_port;
	@SuppressWarnings("unused")
	private static int mail_cycle;
	
	@Scheduled(cron="0 0 7 * * *") // 초 분 시 일 월 요일
	public void alert(){
		
		// step 1. 메일 발송 관련 설정 값 확인
		NcConfig ncconfig = ncConfigService.getConfigeEmail();
		
		smtp_host = ncconfig.getNce_host();
		smtp_id = ncconfig.getNce_id();
		smtp_pw = new String(Base64.decodeBase64(ncconfig.getNce_pw()));
		smtp_port = ncconfig.getNce_port();
		mail_cycle = ncconfig.getNce_cycle();
		
		// step 2. 메일 발송 주기 설정 값에 따라 발송 할지 여부 조회
		
		// step 3. 발송 할 메일 내용 만들기
		String title = "경고 로그 통계";
		String contents = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'></head><body>" +
				"2015년 8월 12일 단방향시스템 로그 통계<br><br>" +
				"상 : 2건<br>중 : 15건<br>하 : 30건<br>정보 : 10건" +
				"</body></html>";
		
		// step 4. 메일 발송
		System.out.println("=====================>메일 발송 시작");
		try {
			EmailUtil.sendMailNoAuthSSL(smtp_host, String.valueOf(smtp_port), title, contents, "ahs@nnsp.co.kr", "ahs@nnsp.co.kr", "단방향시스템");
			//EmailUtil.sendMailNoAuthSSL("mail.nnsp.co.kr", "25", "tet", "test11", "ahs@nnsp.co.kr", "ahs@nnsp.co.kr", "안혜선");
			//EmailUtil.sendMailAuthSSL("smtp.naver.com", "465", "hyuk7885", "", "tt", "tt", "ahs@nnsp.co.kr", "hyuk7885@naver.com", "안혜선");
			//EmailUtil.sendMailAuthSSL("smtp.gmail.com", "465", "mysun8507", "", "tt", "tt", "ahs@nnsp.co.kr", "mysun8507@gmail.com", "안혜선"); // 구글 계정은 보안 수준이 낮은 앱으로부터의 인증을 기본적으로 차단하므로, 계정 정보에서 "보안 수준이 낮은 앱의 액세스"를 차단에서 허용으로 변경
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("=====================>메일 발송 끝");
		
		// step 5. 메일 발송 로그 저장
		
	}
	
	/*
	 * 네이버 smtp 성공 
	 * 
	public void mail_send() throws MessagingException, UnsupportedEncodingException {
		// 메일 관련 정보
	    String host = "smtp.naver.com";
	    final String username = "hyuk7885@naver.com";
	    final String password = "";
	    int port=465;
	     
	    // 메일 내용
	    String recipient = "ahs@nnsp.co.kr";
	    String subject = "네이버를 사용한 발송 테스트입니다.";
	    String body = "내용 무";
	     
	    Properties props = System.getProperties();
	     
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", port);
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.ssl.enable", "true");
	    props.put("mail.smtp.ssl.trust", host);
	      
	    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	        String un=username;
	        String pw=password;
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(un, pw);
	        }
	    });
	    session.setDebug(true); //for debug
	      
	    Message msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress("hyuk7885@naver.com"));
	    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	    msg.setSubject(subject);
	    msg.setSentDate(new Date());
	     
	    // 첨부파일 없이 메시지만 전송할 경우 다음 라인처럼 텍스트를 설정하고 전송하면 된다.
		//msg.setText("Hello! from my first java e-mail...Testing!!/n");
		//Transport.send(msg);

		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();
		
		 // Fill the message
		 messageBodyPart.setText(body);
		 Multipart multipart = new MimeMultipart();
		 multipart.addBodyPart(messageBodyPart);
		 
		 // 첫번째 파일을 바디파트에 설정한다.
		 messageBodyPart = new MimeBodyPart();
		 File file = new File("D:/test.txt");
		 FileDataSource fds = new FileDataSource(file);
		 messageBodyPart.setDataHandler(new DataHandler(fds));
		 messageBodyPart.setFileName(fds.getName());
		 multipart.addBodyPart(messageBodyPart);
		
		 // 두번째 파일(파일명이 한글인 경우)을 바디파트에 설정한다.
		 messageBodyPart = new MimeBodyPart();
		 File file2 = new File("D:/한글파일.txt");
		 fds = new FileDataSource(file2);
		 messageBodyPart.setDataHandler(new DataHandler(fds));
		 String fileName = fds.getName(); // 한글파일명은 영문으로 인코딩해야 첨부가 된다.
		 fileName = new String(fileName.getBytes("KSC5601"), "8859_1");
		 messageBodyPart.setFileName(fileName);
		 multipart.addBodyPart(messageBodyPart);
		 
		// 이메일 메시지의 내용에 Multipart를 붙인다.
		msg.setContent(multipart);
		Transport.send(msg);
	}
	*/
	
}