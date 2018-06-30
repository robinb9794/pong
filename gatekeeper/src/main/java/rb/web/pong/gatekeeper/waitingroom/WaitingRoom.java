package rb.web.pong.gatekeeper.waitingroom;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

@Component
public class WaitingRoom extends TextWebSocketHandler{
	private Set<WebSocketSession> sessions;
	
	public WaitingRoom() {
		sessions = new HashSet<WebSocketSession>();
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		sessions.add(session);
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException{
		for(WebSocketSession s : sessions) {
			if(s.isOpen()) {
				Map value = new Gson().fromJson(message.getPayload(), Map.class);
				s.sendMessage(new TextMessage("Hello " + value.get("name") + "!")); 
			}else
				sessions.remove(s);
		}
	}
}
