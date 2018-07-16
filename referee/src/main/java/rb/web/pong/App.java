package rb.web.pong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

import rb.web.pong.gamehall.hall.Hall;
import rb.web.pong.gamehall.model.Recorder;

@EnableDiscoveryClient
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Bean
	public ServerEndpointRegistration systemEndpointRegistration() {
		Recorder.LOG.debug("OPENED SPORTSHALL FOR PLAYERS");
		final String PATTERN = "/pong/sportshall/hall/{hallId}/{numberOfRegisteredPlayers}";
		return new ServerEndpointRegistration(PATTERN, Hall.class);
	}
	
	@Bean
	public ServerEndpointExporter endpointExporterNotWorking() {
		return new ServerEndpointExporter();
	}
}
