package rb.web.pong.gamehall.hall;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.json.JSONObject;

import rb.web.pong.gamehall.hall.handler.BallHandler;
import rb.web.pong.gamehall.hall.handler.PlayerHandler;
import rb.web.pong.gamehall.hall.handler.message.InitMessageHandler;
import rb.web.pong.gamehall.hall.handler.message.UpdateMessageHandler;
import rb.web.pong.gamehall.model.Ball;
import rb.web.pong.gamehall.model.MessageType;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.player.Player;

public class Hall extends Endpoint {
	protected static Set<Player> players = new HashSet<Player>();
	protected static Ball ball = new Ball();
	protected static int registeredPlayers = -1;
	protected static int initializedPlayers = 0;
	protected static int hallId = -1;
	protected static boolean gameIsRunning;
	
	protected static InitMessageHandler initHandler;
	protected static UpdateMessageHandler updateHandler;
	protected static BallHandler ballHandler;
	
	@Override
	public synchronized void onOpen(Session session, EndpointConfig config) {
		if(registeredPlayers == -1 && hallId == -1) {
			registeredPlayers = getNumberOfRegisteredPlayersFromSession(session);
			hallId = getHallIdFromSession(session);
			initHandler = new InitMessageHandler();
			updateHandler = new UpdateMessageHandler();
			ballHandler = new BallHandler();
		}		
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
			public synchronized void onMessage(String message) {
				try {
					handleReceivedMessage(message, session);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    private synchronized void handleReceivedMessage(String message, Session session) {
		Player playerOfSentMessage = PlayerHandler.getPlayerBySession(session);
		JSONObject receivedJson = new JSONObject(message);
		handleReceivedMessage(receivedJson, playerOfSentMessage);
	} 
    
    @SuppressWarnings("incomplete-switch")
	private synchronized void handleReceivedMessage(JSONObject receivedJson, Player playerOfSentMessage) {
    	MessageType messageType = MessageType.valueOf(receivedJson.getString("type"));
    	switch(messageType) {
    	case INIT:
    		initHandler.handleMessage(receivedJson, playerOfSentMessage);
    		break;
    	case UPDATE:
    		updateHandler.handleMessage(receivedJson, playerOfSentMessage);
    		break;
    	}
    }    
    
    
    public synchronized void onClose(Session session, CloseReason closeReason) {
		for(Player p : players) {
			if(p.getSession().equals(session)) {
				Recorder.LOG.debug(hallId + " ; " + p.getName() + " HAS LEFT THE HALL");
				players.remove(p);
				break;
			}
		}
    }

    public synchronized void onError(Session session, Throwable throwable) {
    	Recorder.LOG.error(throwable.getMessage());
    }
}
