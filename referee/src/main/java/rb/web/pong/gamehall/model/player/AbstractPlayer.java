package rb.web.pong.gamehall.model.player;

abstract class AbstractPlayer {
	protected String name;	
	
	public synchronized String getName() {
		return name;
	}
	
	public synchronized void setName(String name) {
		this.name = name;
	}
}
