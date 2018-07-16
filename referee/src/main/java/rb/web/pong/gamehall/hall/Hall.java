package rb.web.pong.gamehall.hall;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import rb.web.pong.gamehall.model.MessageType;
import rb.web.pong.gamehall.model.Player;
import rb.web.pong.gamehall.model.Recorder;

public class Hall extends Endpoint{
	private Set<Player> players = new HashSet<Player>();
	private static int numberOfRegisteredPlayers;
	private static int hallId;
	
	public Hall() {
		Recorder.LOG.debug("JETZT");
	}
	
	@Override
	public synchronized void onOpen(Session session, EndpointConfig config) {
		numberOfRegisteredPlayers = getNumberOfRegisteredPlayersFromSession(session);
		hallId = getHallIdFromSession(session);
		players.add(new Player(session));
		addMessageHandler(session);
	}
	
	private synchronized int getNumberOfRegisteredPlayersFromSession(Session session) {
		return Integer.parseInt(session.getRequestParameterMap().get("numberOfRegisteredPlayers").get(0));
	}
	
	private synchronized int getHallIdFromSession(Session session) {
		return Integer.parseInt(session.getRequestParameterMap().get("hallId").get(0));
	}
	
	private synchronized void addMessageHandler(Session session) {
		session.addMessageHandler(new MessageHandler.Whole<String>(){
			@Override
			public void onMessage(String message) {
				try {
					handleMessage(message, session);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public synchronized void handleMessage(String message, Session session) {
		Recorder.LOG.debug("RECEIVED MESSAGE FROM CLIENT: " + message);
		Player playerOfSentMessage = getPlayerBySession(session);
		JSONObject receivedJson = new JSONObject(message);
	}
    
    private synchronized void sendToPlayers(JSONObject objectToBeSend) {
		Recorder.LOG.debug("SENDING MESSAGE TO CLIENTS: " + objectToBeSend.toString());
		for(Player p : players) {
			if(p.getSession().isOpen()) {
				p.getSession().getAsyncRemote().sendText(objectToBeSend.toString());
			}
		}
	}
    
    private synchronized Player getPlayerBySession(Session session) {
		for(Player p : players) {
			if(p.getSession().equals(session)) {
				return p;
			}
		}
		return null;
	}
    
    private synchronized JSONObject createJson(MessageType type) {
		JSONObject json = new JSONObject();
		json.put("type", type.toString());
		json.put("players", convertPlayersToJsonArray());
		return json;
	}
    
    private synchronized JSONArray convertPlayersToJsonArray() {
		JSONArray array = new JSONArray();
		for(Player p : players) {
			JSONObject obj = new JSONObject();
			obj.put("name", p.getName());
			obj.put("racket", p.getRacket());
			array.put(obj);
		}
		return array;
	}
    
    public synchronized void onClose(Session session, CloseReason closeReason) {
		Recorder.LOG.debug("CLIENT HAS LEFT HALL");
		for(Player p : players) {
			if(p.getSession().equals(session)) {
				players.remove(p);
				break;
			}
		}
    }

    public synchronized void onError(Session session, Throwable throwable) {

    }
}
