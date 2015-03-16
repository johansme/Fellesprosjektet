package server;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Notification {
	public static void sendInvitationNotification(int aid, int uid) {
		final String fmt =
				"Dear %s %s!!! :)\n" +
				"You have a new invitation in our calendar!!\n\n" +
				"Description: %s\nTime: %s to %s\nLocation: %s\n\n" +
				"Please accept or decline the invitation in the calendar!!!!\n" +
				":) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) :) ";
		try {
			Appointment a = new Appointment(aid);
			User u = new User(uid);
			String msg = String.format(fmt, 
					u.getName(),
					u.getSurname(),
					a.getDescription(),
					a.getStart().toString(),
					a.getEnd().toString(),
					a.getLocation());
			send(u, "New invitation!!!!", msg);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void send(User u, String subject, String message) {
		final String username = "fellesproject@gmail.com";
		final String password = "fpfpfpfp";
		
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
			msg.setFrom(new InternetAddress("fellesproject@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(u.getEmail()));
			msg.setSubject("Calendar alert: " + subject);
			msg.setText(message);
			
			Transport.send(msg);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
