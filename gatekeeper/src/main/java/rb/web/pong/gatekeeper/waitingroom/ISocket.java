package rb.web.pong.gatekeeper.waitingroom;

import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import rb.web.pong.gatekeeper.model.Player;

public interface ISocket {
	public void onOpen(Session session, EndpointConfig config);
	public void onClose(Session session, CloseReason closeReason);
}
