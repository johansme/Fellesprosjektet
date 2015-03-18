package server.httpserver;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import server.User;

public class HttpSessionManager extends Thread {
	private static final int SESSION_TIMEOUT = 60 * 60 * 5 * 1000;
	private static final int HARVEST_INTERVAL = 1000;
	
	private Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();
	private SecureRandom rand = new SecureRandom();
	private boolean running = true;
	
	public String getNewSession(int uid) {
		HttpSession s = new HttpSession(uid);
		String key;
		do {
			key = new BigInteger(130, rand).toString(32);
		} while(sessions.containsKey(key));
		sessions.put(key, s);
		return key;
	}
	
	public synchronized User getUserFromSession(String sessionId) {
		if(!sessions.containsKey(sessionId)) return null;
		else {
			HttpSession s = sessions.get(sessionId);
			s.touch();
			try { return new User(s.getUid()); }
			catch(SQLException e) { return null; }
		}
	}

	private Thread t;
	private String threadName;
	
	public HttpSessionManager() {
		threadName = "HttpSessionHarvester";
		t = new Thread(this, threadName);
	}
	
	private synchronized void harvest() {
		long now = System.currentTimeMillis();
		Iterator<Entry<String, HttpSession>> it = sessions.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, HttpSession> pair = (Map.Entry<String, HttpSession>)it.next();
			HttpSession s = (HttpSession) pair.getValue();
			if(now - s.getLastAccessed() > SESSION_TIMEOUT) {
				it.remove();
			}
		}
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				harvest();
				Thread.sleep(HARVEST_INTERVAL);
			} catch(InterruptedException e) {
				e.printStackTrace();
				return;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void start() {
		t.start();
	}
	
	public void end() {
		running = false;
	}
}
