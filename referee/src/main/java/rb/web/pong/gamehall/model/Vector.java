package rb.web.pong.gamehall.model;

public class Vector {
	private double[] values;
	
	public Vector(double x, double y) {
		values = new double[2];
		values[0] = x;
		values[1] = y;
	}
	
	public synchronized double getX() {
		return this.values[0];
	}
	
	public synchronized void setX(double x) {
		values[0] = x;
	}
	
	public synchronized double getY() {
		return this.values[1];
	}
	
	public synchronized void setY(double y) {
		values[1] = y;
	}
	
	public synchronized double getValue(int i) {
		return this.values[i];
	}
	
	public synchronized String toString() {
		return "(" + values[0] + " | " + values[1] + ")";
	}
}
