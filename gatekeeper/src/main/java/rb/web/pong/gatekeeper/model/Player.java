package rb.web.pong.gatekeeper.model;

import javax.websocket.Session;

public class Player {
	private String name;
	private String racket;
	private int score;
	private Session session;

	
	public Player(String name, String racket, Session session) {
		this.name = name;
		this.racket = racket;
		this.session = session;
		this.score = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public String getRacket() {
		return this.racket;
	}
	
	
	public int getScore() {
		return this.score;
	}
	
	public void incrScore() {
		this.score++;
	}
	
	public Session getSession() {
		return this.session;
	}
}
