package rb.web.pong.gamehall.model;

public class Ball {
	private final int SIZE = Rules.BALL_SIZE;
	private Position position;
	
	public Ball() {
		int x = Rules.CANVAS_WIDTH / 2 - SIZE;
		int y = Rules.CANVAS_HEIGHT / 2 - SIZE;
		position = new Position(x, y);
	}
	
	public int getSize() {
		return this.SIZE;
	}
	
	public int getX() {
		return this.position.getX();
	}
	
	public int getY() {
		return this.position.getY();
	}
}
