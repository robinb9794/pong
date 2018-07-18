package rb.web.pong.gamehall.model;

public class Racket {
	private String type;
	private Position position;
	private final int WIDTH = Rules.RACKET_WIDTH;
	private final int HEIGHT = Rules.RACKET_HEIGHT;
	
	public Racket(String type) {
		this.type = type;
		this.position = new Position();
	}
	
	public int getX() {
		return this.position.getX();
	}
	
	public int getY() {
		return this.position.getY();
	}
	
	public int getWidth() {
		return this.WIDTH;
	}
	
	public int getHeight() {
		return this.HEIGHT;
	}
}
