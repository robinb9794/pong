package rb.web.pong.gamehall.model;

import java.awt.Color;

import javax.websocket.Session;

public class Player {
	private String name;
	private Racket racket;
	private int lifes;
	private Color color;
	private Position position;
	private Session session;
	
	public Player(Session session) {
		this.session = session;
		this.lifes = 5;
	}
	
	public synchronized String getName() {
		return name;
	}
	
	public synchronized void setName(String name) {
		this.name = name;
	}

	public synchronized Racket getRacket() {
		return racket;
	}
	
	public synchronized void setRacket(Racket racket) {
		this.racket = racket;
	}
	
	public synchronized int getLifes() {
		return this.lifes;
	}
	
	public synchronized void decrLife() {
		this.lifes--;
	}
	
	public synchronized void setColor(Color color) {
		this.color = color;
	}
	
	public synchronized String getColorAsHexString() {
		return "#" + Integer.toHexString(this.color.getRGB() & 0xffffff);
	}
	
	public synchronized Position getPosition() {
		return this.position;
	}
	
	public synchronized void setPosition(Position position) {
		this.position = position;
	}
	
	public synchronized Session getSession() {
		return session;
	}
	
	public synchronized boolean isHorizontal() {
		return this.position.equals(Position.TOP) || this.position.equals(Position.BOTTOM);
	}
	
	public synchronized boolean isVertical() {
		return this.position.equals(Position.LEFT) || this.position.equals(Position.RIGHT);
	}
}
