package rb.web.pong.gamehall.model;

import javax.websocket.Session;

public class Player {
	private String name;
	private Racket racket;
	private int score;
	private Session session;
	
	public Player(Session session) {
		this.session = session;
		this.score = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Racket getRacket() {
		return racket;
	}
	
	public void setRacket(String type) {
		this.racket = new Racket(type);
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void incrScore() {
		this.score++;
	}
	
	public Session getSession() {
		return session;
	}
	
}
