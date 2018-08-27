package rb.web.pong.statistician;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import rb.web.pong.statistician.model.Player;

@Component
public class StatsService {
	public static List<Player> stats;
	
	public StatsService() {
		stats = new ArrayList<Player>();
	}
	
	public Player findByName(String name) {
		for(Player player : stats) {
			if(player.getName().equals(name))
				return player;
		}
		return null;
	}
	
	public synchronized void insert(String name) {
		Player player = new Player(name);
		stats.add(player);
	}
	
	public synchronized void insert(Player player) {
		stats.add(player);
	}
	
	public synchronized boolean exists(String name) {
		for(Player player : stats) {
			if(player.getName().equals(name))
				return true;
		}
		return false;
	}
	
	public synchronized boolean isEmpty() {
		return stats.isEmpty();
	}
	
	public synchronized void update(String name) {
		Player player = findByName(name);
		int currentWins = player.getWins();
		player.setWins(currentWins + 1);
	}
}
