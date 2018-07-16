package rb.web.pong.gatekeeper.waitingroom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import rb.web.pong.gatekeeper.model.Hall;
import rb.web.pong.gatekeeper.model.MessageType;
import rb.web.pong.gatekeeper.model.Player;
import rb.web.pong.gatekeeper.model.Recorder;

public abstract class WaitingRoom extends Endpoint implements IMessageHandling{
	protected static List<Hall> createdLobbies = new ArrayList<Hall>();
	
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
		Player playerOfSentMessage = getPlayerBySession(session, waitingPlayers);
		sendToPlayers(MessageType.valueOf("CONNECTED"), playerOfSentMessage.getName() + " has joined the party!", waitingPlayers);
	}
	
	@Override
	public synchronized void sendToPlayers(MessageType type, String message, Set<Player> playersWithSameRacket) {
		JSONObject objectToBeSend = createJson(type, message, playersWithSameRacket);
		Recorder.LOG.debug("SENDING MESSAGE TO CLIENTS: " + objectToBeSend.toString());
		for(Player p : playersWithSameRacket) {
			if(p.getSession().isOpen())
				p.getSession().getAsyncRemote().sendText(objectToBeSend.toString());
			else
				playersWithSameRacket.remove(p);
		}
	}	
	
	@Override
	public synchronized Player getPlayerBySession(Session session, Set<Player> waitingPlayers) {
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
	public synchronized void removePlayer(Session session, Set<Player> waitingPlayers) {
		for(Player p : waitingPlayers) {
			if(p.getSession().equals(session)) {
				waitingPlayers.remove(p);
				break;
			}
		}
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
	
	protected class Supervisor implements Runnable{	
		private Set<Player> waitingPlayers;
		private int countdown;
		
		public Supervisor(Set<Player> waitingPlayers) {
			this.waitingPlayers = waitingPlayers;
			countdown = 30;
			String message = "Enough players present. The match is about to start. Prepare yourselves!";
			sendToPlayers(MessageType.valueOf("INFO"), message, waitingPlayers);
		}
		@Override
		public void run() {
			try {
				while(waitingPlayers.size() > 1 && countdown >= 0) {
					Thread.sleep(1000);
					sendToPlayers(MessageType.valueOf("COUNTDOWN"), Integer.toString(countdown), waitingPlayers);
					if(countdown == 0) {
						openHallsAndSendInfoToPlayers();		
					}						
					countdown--;
				}
				if(createdLobbies.size() == 0)
					sendToPlayers(MessageType.valueOf("INFO"), "New matchmaking in the progress...", waitingPlayers);
			}catch(Exception e) {
				Recorder.LOG.error(e.toString());
			}
		}	
		
		private synchronized void openHallsAndSendInfoToPlayers() {
			int id = 0;
			Iterator<Player> iterator = waitingPlayers.iterator();
			for(int i = 0; i < waitingPlayers.size(); i += 4) {
				Hall hall = new Hall(id);				
				for(int j = 0; j < 4 && iterator.hasNext(); j++) {
					try {
						Recorder.LOG.debug("BRINGING PLAYER TO HALL " + (id));
						hall.addPlayer(iterator.next());
					}catch(Exception e) {
						Recorder.LOG.debug("CREATED HALLS");
					}
				}
				createdLobbies.add(hall);
				sendToPlayers(MessageType.valueOf("HALL"), Integer.toString(id++), hall.getRegisteredPlayers());
			}
		}
	}
}