package rb.web.pong.gamehall.model;

public class Coordinate {
	private double x, y;
	
	public Coordinate() {
		this(0, 0);
	}
	
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public synchronized double getX() {
		return x;
	}

	public synchronized void setX(double x) {
		this.x = x;
	}

	public synchronized double getY() {
		return y;
	}

	public synchronized void setY(double y) {
		this.y = y;
	}
	
	public synchronized void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
