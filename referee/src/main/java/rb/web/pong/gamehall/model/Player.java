package rb.web.pong.gamehall.model;

import javax.websocket.Session;

public class Player {
	private String name;
	private Racket racket;
	private int lifes;
	private Session session;
	
	public Player(Session session) {
		this.session = session;
		this.lifes = 5;
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
	
	public void setRacket(Racket racket) {
		this.racket = racket;
	}
	
	public int getLifes() {
		return this.lifes;
	}
	
	public void decrLife() {
		this.lifes--;
	}
	
	public Session getSession() {
		return session;
	}
	
}
