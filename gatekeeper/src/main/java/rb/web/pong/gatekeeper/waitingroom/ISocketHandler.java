package rb.web.pong.gatekeeper.waitingroom;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public interface ISocketHandler {
	public void onOpen(Session session, EndpointConfig config);
	public void onClose(Session session, CloseReason closeReason);
}
