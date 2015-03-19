package server.httpserver;

import java.util.ArrayList;

import server.Invitation;
import server.Notification;

public class AlertManager extends Thread {
	private final int PUSH_INTERVAL = 1000 * 2;
	boolean running = true;
	
	private void pushAlerts() {
		ArrayList<Invitation.Alert> alerts = Invitation.getDueAlerts();
		for(Invitation.Alert a : alerts) {
			if(Notification.sendAlertNotification(a.aid, a.uid)) {
				Invitation.markSent(a.aid, a.uid);
			}
			
		}
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				pushAlerts();
				Thread.sleep(PUSH_INTERVAL);
			} catch(InterruptedException e) {
				e.printStackTrace();
				return;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Thread t;
	private String threadName;
	
	public AlertManager() {
		threadName = "AlertPusher";
		t = new Thread(this, threadName);
	}
	
	public void start() {
		t.start();
	}
	
	public void end() {
		running = false;
	}
}
