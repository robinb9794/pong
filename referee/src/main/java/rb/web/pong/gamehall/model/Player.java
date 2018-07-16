package rb.web.pong.gamehall.model;

import javax.websocket.Session;

import rb.web.pong.gamehall.model.racket.Racket;

public class Player {
	private String name;
	private Racket racket;
	private Session session;
	
	public Player(Session session) {
		this.session = session;
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
	
	public Session getSession() {
		return session;
	}
	
}
