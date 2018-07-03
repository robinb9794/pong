package rb.web.pong.gatekeeper.waitingroom;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import rb.web.pong.gatekeeper.models.JsonMessage;
import rb.web.pong.gatekeeper.models.Player;
import rb.web.pong.gatekeeper.models.Recorder;

@Component
public class IndividualWaitingRoom extends TextWebSocketHandler{
	private Set<Player> players;
	
	public IndividualWaitingRoom() {
		Recorder.LOG.debug("OPENED INDIVIDUAL WAITING ROOM");
		players = new HashSet<Player>();
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		Recorder.LOG.debug("CLIENT CONNECTED TO SERVER: " + session.getId());
		addPlayerToList(session);
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException{
		Recorder.LOG.debug("SERVER RECEIVED MESSAGE FROM CLIENT: " + message.getPayload());
		Map<?, ?> json = new Gson().fromJson(message.getPayload(), Map.class);
		String name = (String) json.get("name");
		String racket = (String) json.get("racket");
		editLastConnectedPlayer(name, racket);
		for(Player p : players) {
			if(p.getSession().isOpen())			
				p.getSession().sendMessage(new TextMessage(createJsonString(name + " has joined the party!"))); 
			else
				players.remove(p);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		Recorder.LOG.debug("CLIENT DISCONNECTED FROM SERVER: " + session.getId());
		Player player = getPlayerFromId(session.getId());
		removePlayer(session);
		for(Player p : players) {
			if(p.getSession().isOpen())
				p.getSession().sendMessage(new TextMessage(createJsonString(player.getName() + " has left the party."))); 
			else
				players.remove(p);
		}
	}
	
	private synchronized void addPlayerToList(WebSocketSession session) {
		Recorder.LOG.debug("ADDED PLAYER TO WAITING LIST");
		players.add(new Player(session.getId(), session));
	}
	
	private synchronized void editLastConnectedPlayer(String name, String racket) {
		Player p = null;
		Iterator<Player> itr = players.iterator();
		while(itr.hasNext())
			p = itr.next();
		p.setName(name);
		p.setRacket(racket);
	}
	
	private synchronized String createJsonString(String message) {
		return new Gson().toJson(new JsonMessage(message, players.size()));		
	}
	
	private synchronized Player getPlayerFromId(String id) {
		for(Player p : players) {
			if(p.getId().equals(id))
				return p;
		}
		return null;
	}
	
	private synchronized void removePlayer(WebSocketSession session) {
		for(Player p : players) {
			if(p.getId().equals(session.getId())) {
				Recorder.LOG.debug("REMOVED " + p.getName());
				players.remove(p);
				break;
			}
		}
	}
}
