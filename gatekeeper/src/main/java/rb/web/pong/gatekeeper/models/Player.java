package rb.web.pong.gatekeeper.models;

import org.springframework.web.socket.WebSocketSession;

public class Player {
	private String id;
	private String name;
	private String racket;
	private int score;
	private WebSocketSession session;
	
	public Player(String id, WebSocketSession session) {
		this.id = id;
		this.session = session;
	}
	
	public Player(String name, String racket) {
		this.name = name;
		this.racket = racket;
		this.score = 0;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRacket() {
		return this.racket;
	}
	
	public void setRacket(String racket) {
		this.racket = racket;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void incrScore() {
		this.score++;
	}
	
	public WebSocketSession getSession() {
		return this.session;
	}
	
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
}
