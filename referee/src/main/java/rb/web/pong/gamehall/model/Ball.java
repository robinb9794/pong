package rb.web.pong.gamehall.model;

public class Ball {
	private final int RADIUS = GameRules.BALL_RADIUS;
	private Coordinate coordinate;
	
	public Ball() {
		double x = GameRules.FIELD_WIDTH / 2;
		double y = GameRules.FIELD_HEIGHT / 2;
		coordinate = new Coordinate(x, y);
	}
	
	public synchronized int getRadius() {
		return this.RADIUS;
	}
	
	public synchronized double getX() {
		return this.coordinate.getX();
	}
	
	public synchronized void setX(double x) {
		this.coordinate.setX(x);
	}
	
	public synchronized double getY() {
		return this.coordinate.getY();
	}
	
	public synchronized void setY(double y) {
		this.coordinate.setY(y);
	}
	
	public String toString() {
		return "(" + this.getX() + " | " + this.getY() + ")";
	}
}
