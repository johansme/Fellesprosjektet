package server;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class Notification {
	public static void send(User u, String subject, String message) {
		final String username = "koieadm1n@gmail.com";
		final String password = "koie1234";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
		});
		
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("test@test.test"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(u.getEmail()));
			msg.setSubject("Calendar alert: " + subject);
			msg.setText("Dear " + u.getName() + "\r\n" + message);
			
			Transport.send(msg);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
