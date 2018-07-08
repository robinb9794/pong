package rb.web.pong.gatekeeper.waitingroom;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import rb.web.pong.gatekeeper.model.MessageType;
import rb.web.pong.gatekeeper.model.Player;
import rb.web.pong.gatekeeper.model.Recorder;

public class IndividualCorner extends WaitingRoom implements ISocket{
	protected static Set<Player> waitingPlayers = new HashSet<Player>();
	
	@OnOpen
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		String name = session.getRequestParameterMap().get("name").get(0);
		String racket = "INDIVIDUAL";
		Recorder.LOG.debug("CLIENT ENTERED THE INDIVIDUAL CORNER: " + name + ", " + racket);
		waitingPlayers.add(new Player(name, racket, session));		
		addMessageHandler(session, waitingPlayers);		
		if(waitingPlayers.size() == 2)
			new Thread(new Referee(waitingPlayers)).start();
	}
	
	@OnClose
	@Override
	public void onClose(Session session, CloseReason closeReason) {
		Player disconnectedPlayer = getPlayerFromSession(session, waitingPlayers);
		Recorder.LOG.debug(disconnectedPlayer.getName() + " LEFT THE INDIVIDUAL CORNER");
		removePlayer(session, waitingPlayers);
		sendToPlayers(MessageType.valueOf("DISCONNECTED"), disconnectedPlayer.getName() + " has left the party!", waitingPlayers);
	}	
}

