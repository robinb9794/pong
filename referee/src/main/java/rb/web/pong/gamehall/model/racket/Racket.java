package rb.web.pong.gamehall.model.racket;

public class Racket {
	private String type;
	private Point position;
	
	public Racket(String type) {
		this.type = type;
		this.position = new Point();
	}
}
