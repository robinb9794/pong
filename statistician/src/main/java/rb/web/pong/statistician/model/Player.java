package rb.web.pong.statistician.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "player")
public class Player {
	private String name;
	private int wins;
	
	public Player() {}
	
	public Player(String name, int wins) {
		this.name = name;
		this.wins = wins;
	}
	
	public Player(String name) {
		this.name = name;
		this.wins = 1;
	}
	
	@XmlElement
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement
	public int getWins() {
		return wins;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
}
