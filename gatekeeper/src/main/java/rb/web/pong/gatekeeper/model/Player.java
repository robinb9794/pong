package rb.web.pong.gatekeeper.model;

import javax.websocket.Session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
	@JsonProperty
	private String name;
	@JsonProperty
	private String racket;
	@JsonIgnore
	private Session session;

	
	public Player(String name, String racket, Session session) {
		this.name = name;
		this.racket = racket;
		this.session = session;
	}
	
	public String getName() {
		return this.name;
	}

	public String getRacket() {
		return this.racket;
	}
	
	public Session getSession() {
		return this.session;
	}
}
