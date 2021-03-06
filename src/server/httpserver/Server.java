package server.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

import server.User;

public class Server {
	private HttpSessionManager sessionMgr;
	private AlertManager alertMgr;
	private HttpServer server;
	boolean running = true;

	private Server() throws IOException {
		sessionMgr = new HttpSessionManager();
		sessionMgr.start();
		alertMgr = new AlertManager();
		alertMgr.start();
		server = HttpServer.create(new InetSocketAddress(8000), 200);
		server.createContext("/login", new HttpLogin(this));
		server.createContext("/rooms", new HttpRooms(this));
		server.createContext("/user", new HttpUser(this));
		server.createContext("/appointment", new HttpAppointment(this));
		server.createContext("/invitation", new HttpInvitation(this));
		server.createContext("/group", new HttpGroup(this));
		server.setExecutor(null);
	}

	public HttpSessionManager getSessionManager() {
		return sessionMgr;
	}
	
	public User getUserFromExchange(HttpExchange t) {
		Headers h = t.getRequestHeaders();		
		return sessionMgr.getUserFromSession(h.getFirst("X-FP-Session"));
	}

	private void run() {
		server.start();
		while(running) {
			// Should wait for server thread to end, don't know how
			// Just sleep a bit to save CPU time
			try { Thread.sleep(1000); } catch(Exception e) {}
		}
		sessionMgr.end();
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.run();
	}
}
