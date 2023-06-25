package com.forme.biz.member;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Service
@Component
public class MailSendService {
	@Autowired
	private JavaMailSender mailSender;
	private int authNumber; 
	
		@Bean
		public JavaMailSenderImpl mailSender() {
			JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
			javaMailSender.setProtocol("smtp");
			javaMailSender.setHost("smtp.gmail.com");
			javaMailSender.setPort(587);
			javaMailSender.setUsername("for.me.itwill.138@gmail.com");
			javaMailSender.setPassword("bitncpfqaskmtajt");
	
			return javaMailSender;
		}
		
		public void makeRandomNumber() {
			// 난수의 범위 111111 ~ 999999 (6자리 난수)
			Random r = new Random();
			int checkNum = r.nextInt(888888) + 111111;
			System.out.println("인증번호 : " + checkNum);
			authNumber = checkNum;
		}
		
		
				//이메일 보낼 양식! 
		public String joinEmail(String email) {
			makeRandomNumber();
			String setFrom = ".com"; // email-config에 설정한 자신의 이메일 주소를 입력 
			String toMail = email;
			String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목 
			String content = 
					"for味 를 방문해주셔서 감사합니다." + 	//html 형식으로 작성 
	                "<br><br>" + 
				    "인증 번호는 " + authNumber + "입니다." + 
				    "<br>" + 
				    "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; //이메일 내용 삽입
			System.out.println(content);
			mailSend(setFrom, toMail, title, content);
			return Integer.toString(authNumber);
		}
		
		public String mailPwdCheck(String email, String pwd) {
			String setFrom = ".com"; // email-config에 설정한 자신의 이메일 주소를 입력 
			String toMail = email;
			String title = "패스워드 확인 이메일 입니다."; // 이메일 제목 
			String content = 
					"for味 를 방문해주셔서 감사합니다." + 	//html 형식으로 작성 
	                "<br><br>" + 
				    "패스워드는 " + pwd + " 입니다.";
			System.out.println(content);
			mailSend(setFrom, toMail, title, content);
			return pwd;
		}
		
		//이메일 전송 메소드
		public void mailSend(String setFrom, String toMail, String title, String content) { 
			
			MimeMessage message = mailSender.createMimeMessage();
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
				helper.setFrom(setFrom);
				helper.setTo(toMail);
				helper.setSubject(title);
				helper.setText(content,true);
				mailSender.send(message);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		
		
	
}