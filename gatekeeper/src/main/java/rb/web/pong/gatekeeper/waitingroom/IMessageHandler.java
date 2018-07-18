package rb.web.pong.gatekeeper.waitingroom;

import java.util.Set;

import javax.websocket.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import rb.web.pong.gatekeeper.model.MessageType;
import rb.web.pong.gatekeeper.model.Player;

public interface IMessageHandler {
	public void addMessageHandler(Session session, Set<Player> waitingPlayers);
	public void handleReceivedMessage(String message, Session session, Set<Player> waitingPlayers);
	public void sendToPlayers(MessageType type, String message, Set<Player> playersWithSameRacket);
	public Player getPlayerBySession(Session session, Set<Player> waitingPlayers);
	public JSONObject getJson(MessageType type, String message, Set<Player> waitingPlayers);
	public JSONArray getPlayerInfoAsJsonArray(Set<Player> players);
	public void removePlayer(Session session, Set<Player> waitingPlayers);
}
