package rb.web.pong.gatekeeper.model;

import com.google.gson.annotations.Expose;

public class JsonMessage {
	@Expose
	private String message;
	@Expose
	private int waitingPlayers;
	
	public JsonMessage(String message, int waitingPlayers) {
		this.message = message;
		this.waitingPlayers = waitingPlayers;
	}
}
