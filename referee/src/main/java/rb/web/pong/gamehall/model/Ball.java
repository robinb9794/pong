package rb.web.pong.gamehall.model;

public class Ball {
	private final int SIZE = Rules.BALL_SIZE;
	private Coordinate coordinate;
	
	public Ball() {
		int x = Rules.CANVAS_WIDTH / 2;
		int y = Rules.CANVAS_HEIGHT / 2;
		coordinate = new Coordinate(x, y);
	}
	
	public synchronized int getSize() {
		return this.SIZE;
	}
	
	public synchronized int getX() {
		return this.coordinate.getX();
	}
	
	public synchronized void setX(int x) {
		this.coordinate.setX(x);
	}
	
	public synchronized int getY() {
		return this.coordinate.getY();
	}
	
	public synchronized void setY(int y) {
		this.coordinate.setY(y);
	}
}
