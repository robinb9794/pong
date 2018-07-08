package rb.web.pong.gatekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

import rb.web.pong.gatekeeper.model.Recorder;
import rb.web.pong.gatekeeper.waitingroom.IndividualCorner;
import rb.web.pong.gatekeeper.waitingroom.SystemCorner;

@EnableDiscoveryClient
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Bean
	public ServerEndpointRegistration systemEndpointRegistration() {
		Recorder.LOG.debug("INITIALIZED WAITING ROOM: SYSTEM");
		final String PATTERN = "/pong/waitingroom/system/{name}";
		return new ServerEndpointRegistration(PATTERN, SystemCorner.class);
	}
	
	@Bean
	public ServerEndpointRegistration individualEndpointRegistration() {
		Recorder.LOG.debug("INITIALIZED WAITING ROOM: INDIVIDUAL");
		final String PATTERN = "/pong/waitingroom/individual/{name}";
		return new ServerEndpointRegistration(PATTERN, IndividualCorner.class);
	}
	
	@Bean
	public ServerEndpointExporter endpointExporterNotWorking() {
		return new ServerEndpointExporter();
	}
}