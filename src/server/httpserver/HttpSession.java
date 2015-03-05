package server.httpserver;

class HttpSession {
	private int uid;
	private long lastAccessed;
	
	public HttpSession(int uid) {
		this.uid = uid;
		touch();
	}
	
	public void touch() {
		lastAccessed = System.currentTimeMillis();
	}

	public int getUid() {
		return uid;
	}
	
	public long getLastAccessed() {
		return lastAccessed;
	}
}
