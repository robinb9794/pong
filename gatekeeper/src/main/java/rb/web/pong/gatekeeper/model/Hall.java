package rb.web.pong.gatekeeper.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hall {
	private Set<Player> registeredPlayers;
	private int id;
	
	public Hall(int id) {
		this.registeredPlayers = new HashSet<Player>();
		this.id = id;
	}
	
	public Set<Player> getRegisteredPlayers(){
		return this.registeredPlayers;
	}
	
	public synchronized List<Player> getRegisteredPlayersAsList(){
		List<Player> players = new ArrayList<Player>();
		players.addAll(registeredPlayers);
		return players;
	}
	
	public synchronized void addPlayer(Player player) {
		registeredPlayers.add(player);
	}
	
	public synchronized void removePlayer(Player player) {
		for(Player p : registeredPlayers) {
			if(player.equals(p)) {
				registeredPlayers.remove(p);
				break;
			}				
		}
	}
	
	public int getId() {
		return this.id;
	}
}
