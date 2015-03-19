package server;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.Properties;

public class Notification {
	
	public static boolean sendAlertNotification(int aid, int uid) {
		final String fmt =
				"Wake up, you lazy bum. You have an appointment to attend\n" +
				"at %s. In case you forgot, because you're a lazy idiot,\n" +
				"the appointment's description is: \"%s\".\n\n" +
				"We hope the remainder of your day will be delightful.";
		
		boolean result = false;
		
		try {
			Appointment a = new Appointment(aid);
			User u = new User(uid);
			String msg = String.format(fmt, a.getStart().toString(), a.getDescription());
			send(u, "Appointment alert", msg);
			result = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void sendRoomDeletionNotification(int aid) {
		final String fmt = 
				"The room registered for your appointment has been removed.\n" +
				"You should make sure to register a new room for the appointment.";

		try {
			Appointment a = new Appointment(aid);
			User u = new User(a.getCreator());
			String msg = String.format(fmt, 
					u.getName(),
					u.getSurname(),
					a.getDescription(),
					a.getStart().toString(),
					a.getEnd().toString(),
					a.getLocation());
			send(u, "Deleted room", msg);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendAppointmentDeleteNotification(Appointment a, int uid) {
		final String cat =
				"        ._ \n" +
				"      .-'  `-.\n" +
				"   .-'        \\\n" +
				"  ;    .-'\\    ;\n" +
				"  `._.'    ;   |\n" +
				"           |   |\n" +
				"           ;   :\n" +
				"          ;   :\n" +
				"          ;   :\n" +
				"         /   /\n" +
				"        ;   :                   ,\n" +
				"        ;   |               .-\"7|\n" +
				"      .-'\"  :            .-' .' :\n" +
				"   .-'       \\         .'  .'   `.\n" +
				" .'           `-. \"\"-.-'`\"\"    `\",`-._..--\"7\n" +
				" ;    .          `-.J `-,    ;\"`.;|,_,    ;\n" +
				"_.'    |         `\"\" `. .\"\"\"--. o \\:.-. _.'\n" +
				".\"\"       :            ,--`;   ,  `--/}o,' ;\n" +
				";   .___.'        /     ,--.`-. `-..7_.-  /_\n" +
				"\\   :   `..__.._;    .'__;    `---..__.-'-.`\"-,\n" +
				".'   `--. |   \\_;    \\'   `-._.-\")     \\\\  `-,\n" +
				"`.   -.`_):      `.   `-\"\"\"`.   ;__.' ;/ ;   \"\n" +
				"`-.__7\"  `-..._.'`7     -._;'  ``\"-''\n" +
				"                 `--.,__.'              fsc\n";
		
		final String fmt =
				"Dear %s %s!\n" +
				"An appointment to which you have been invited has been cancelled!\n" +
				"Information about the cancelled appointment:\n" +
				"Description: %s\nTime: %s to %s\nLocation: %s\n\n" +
				"!!!Now you won't have to attend a boring meeting!!!\n" +
				":D :D :D :D :D :D :D :D :D :D :D :D :D :D :D :D :D :D\n\n" +
				"Here's a cat instead. Enjoy. :D\n" + 
				cat;

		try {
			User u = new User(uid);
			String msg = String.format(fmt, 
					u.getName(),
					u.getSurname(),
					a.getDescription(),
					a.getStart().toString(),
					a.getEnd().toString(),
					a.getLocation());
			send(u, "Cancelled appointment", msg);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
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
	
	public static void sendModifiedAppointmentNotification(int aid) {
		final String fmt = 
				"Dead %s %s\n" +
				"We are sad to inform you of a change of plans in regards to a\n" +
				"meeting of which you are scheduled to potentially attend.\n" +
				"New information about the appointment is included below:\n\n" +
				"Description: %s\nTime: %s to %s\nLocation: %s";
		try {
			Appointment a = new Appointment(aid);
			ArrayList<Integer> list = Invitation.getInvitationsForAppointment(aid);
			for(int uid : list) {
				try {
					User u = new User(uid);
					String msg = String.format(fmt, 
							u.getName(),
							u.getSurname(),
							a.getDescription(),
							a.getStart().toString(),
							a.getEnd().toString(),
							a.getLocation());
					send(u, "Changed appointment", msg);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
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
