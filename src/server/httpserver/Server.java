package server.httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class Server {
	private HttpSessionManager sessionMgr;
	private HttpServer server;
	boolean running = true;

	private Server() throws IOException {
		sessionMgr = new HttpSessionManager();
		sessionMgr.start();
		server = HttpServer.create(new InetSocketAddress(8000), 200);
		server.setExecutor(null);
	}

	public HttpSessionManager getSessionManager() {
		return sessionMgr;
	}

	private void run() {
		server.start();
		while(running);
		sessionMgr.end();
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.run();
	}
}
