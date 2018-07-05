package rb.web.pong.gatekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

import rb.web.pong.gatekeeper.waitingroom.WaitingRoom;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Bean
	public ServerEndpointRegistration endpointRegistration() {
		final String PATTERN = "/waitingroom/{name}/{racket}";
		return new ServerEndpointRegistration(PATTERN, WaitingRoom.class);
	}
	
	@Bean
	public ServerEndpointExporter endpointExporterNotWorking() {
		return new ServerEndpointExporter();
	}
}