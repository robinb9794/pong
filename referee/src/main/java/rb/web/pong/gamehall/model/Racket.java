package rb.web.pong.gamehall.model;

public class Racket {
	private Coordinate coordinate;
	private int width = GameRules.RACKET_WIDTH;
	private int height = GameRules.RACKET_HEIGHT;
	
	public Racket(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	public synchronized Coordinate getCoordinate() {
		return this.coordinate;
	}
	
	public synchronized void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	public double getX() {
		return this.coordinate.getX();
	}
	
	public synchronized void setX(double x) {
		this.coordinate.setX(x);
	}
	
	public synchronized double getY() {
		return this.coordinate.getY();
	}
	
	public void setY(double y) {
		this.coordinate.setY(y);
	}
	
	public synchronized int getWidth() {
		return this.width;
	}
	
	public synchronized void setWidth(int width) {
		this.width = width;
	}
	
	public synchronized int getHeight() {
		return this.height;
	}
	
	public synchronized void setHeight(int height) {
		this.height = height;
	}
}
