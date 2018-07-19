package rb.web.pong.gamehall.model;

public class Racket {
	private String type;
	private Position startPos;
	private final int WIDTH = Rules.RACKET_WIDTH;
	private final int HEIGHT = Rules.RACKET_HEIGHT;
	
	public Racket(String type, Position startPos) {
		this.type = type;
		this.startPos = startPos;
	}
	
	public Position getStartPos() {
		return this.startPos;
	}
	
	public int getWidth() {
		return this.WIDTH;
	}
	
	public int getHeight() {
		return this.HEIGHT;
	}
}
