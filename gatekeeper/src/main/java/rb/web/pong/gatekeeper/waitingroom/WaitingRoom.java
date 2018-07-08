package rb.web.pong.gatekeeper.waitingroom;

import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import rb.web.pong.gatekeeper.model.MessageType;
import rb.web.pong.gatekeeper.model.Player;
import rb.web.pong.gatekeeper.model.Recorder;

public abstract class WaitingRoom extends Endpoint implements IMessageHandling{
	
	@Override
	public synchronized void addMessageHandler(Session session, Set<Player> waitingPlayers) {
		session.addMessageHandler(new MessageHandler.Whole<String>(){
			@Override
			public void onMessage(String message) {
				try {
					handleMessage(message, session, waitingPlayers);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public synchronized void handleMessage(String message, Session session, Set<Player> waitingPlayers) {
		Recorder.LOG.debug("RECEIVED MESSAGE FROM CLIENT: " + message);
		Player playerOfSentMessage = getPlayerFromSession(session, waitingPlayers);
		sendToPlayers(MessageType.valueOf("CONNECTED"), playerOfSentMessage.getName() + " has joined the party!", waitingPlayers);
	}
	
	@Override
	public synchronized void sendToPlayers(MessageType type, String message, Set<Player> playersWithSameRacket) {
		JSONObject objectToBeSend = createJson(type, message, playersWithSameRacket);
		Recorder.LOG.debug("SENDING MESSAGE TO CLIENTS: " + objectToBeSend.toString());
		for(Player p : playersWithSameRacket) {
			if(p.getSession().isOpen()) {
				p.getSession().getAsyncRemote().sendText(objectToBeSend.toString());
			}
		}
	}	
	
	@Override
	public synchronized Player getPlayerFromSession(Session session, Set<Player> waitingPlayers) {
		for(Player p : waitingPlayers) {
			if(p.getSession().equals(session)) {
				return p;
			}
		}
		return null;
	}	
	
	@Override
	public synchronized JSONObject createJson(MessageType type, String message, Set<Player> waitingPlayers) {
		JSONObject json = new JSONObject();
		json.put("type", type.toString());
		json.put("message", message);		
		json.put("waitingPlayers", convertSetToJsonArray(waitingPlayers));
		return json;
	}
	
	@Override
	public synchronized JSONArray convertSetToJsonArray(Set<Player> players) {
		JSONArray array = new JSONArray();
		for(Player p : players) {
			JSONObject obj = new JSONObject();
			obj.put("name", p.getName());
			obj.put("racket", p.getRacket());
			array.put(obj);
		}
		return array;
	}
	
	@Override
	public synchronized void removePlayer(Session session, Set<Player> waitingPlayers) {
		for(Player p : waitingPlayers) {
			if(p.getSession().equals(session)) {
				waitingPlayers.remove(p);
				break;
			}
		}
	}
	
	protected class Referee extends IndividualCorner implements Runnable{	
		private Set<Player> waitingPlayers;
		private int countdown;
		
		public Referee(Set<Player> waitingPlayers) {
			this.waitingPlayers = waitingPlayers;
			countdown = 30;
			String message = "Enough players present. The match is about to start. Prepare yourselves!";
			sendToPlayers(MessageType.valueOf("INFO"), message, waitingPlayers);
		}
		@Override
		public void run() {
			try {
				while(waitingPlayers.size() > 1) {
					Thread.sleep(1000);
					sendToPlayers(MessageType.valueOf("COUNTDOWN"), Integer.toString(countdown--), waitingPlayers);
					if(countdown <= 0) {
						countdown = 0;
						bringPlayersToGameHall();
					}
				}
				sendToPlayers(MessageType.valueOf("INFO"), "Matchmaking has been canceled.", waitingPlayers);
			}catch(Exception e) {
				Recorder.LOG.error(e.toString());
			}
		}	
		
		private void bringPlayersToGameHall() {
			Recorder.LOG.debug("BRINGING PLAYERS TO THE GAME HALL");
		}
	}
}