package rb.web.pong.gatekeeper.waitingroom;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import rb.web.pong.gatekeeper.model.Player;
import rb.web.pong.gatekeeper.model.Recorder;

public class WaitingRoom extends Endpoint{
	private Set<Player> players;
	
	public WaitingRoom() {
		Recorder.LOG.debug("OPENED SYSTEM WAITING ROOM");
		players = new HashSet<Player>();
	}
	
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		String name = session.getRequestParameterMap().get("name").get(0);
		String racket = session.getRequestParameterMap().get("racket").get(0);
		Recorder.LOG.debug("CLIENT CONNTECTED: " + name + ", " + racket);
		players.add(new Player(name, racket, session));
	}
}
