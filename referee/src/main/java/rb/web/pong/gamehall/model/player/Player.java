package rb.web.pong.gamehall.model.player;

import java.awt.Color;

import javax.websocket.Session;

import rb.web.pong.gamehall.model.GameRules;
import rb.web.pong.gamehall.model.Position;
import rb.web.pong.gamehall.model.Racket;

public class Player extends AbstractPlayer{
	private Racket racket;
	private int lifes;
	private Color color;
	private Position position;
	private Session session;
	
	public Player(Session session) {
		this.session = session;
		this.lifes = GameRules.INIT_LIFES;
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
