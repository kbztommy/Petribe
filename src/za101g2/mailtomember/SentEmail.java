package za101g2.mailtomember;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SentEmail {
	
	public void sendEmail(String to, String subject, String messageText){
		
		try{
			
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			
			final String mail = "za101g2@gmail.com";
			final String pw = "javaza101g2";
			
			Session session = Session.getInstance(props, new Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(mail, pw);
				}
			});
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			
			message.setSubject(subject);
			
			message.setText(messageText);
			
			Transport.send(message);
			
			System.out.println("傳送成功!");
		}
		catch(MessagingException e){
			System.out.println("傳送失敗!");
			e.printStackTrace();
		}
		
	}
	
}
