package rb.web.pong.gatekeeper.stats;

public class StatsEntry {
	private String name;
	private int score;
	
	public StatsEntry() {
		this("John Doe", 100);
	}
	
	public StatsEntry(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
