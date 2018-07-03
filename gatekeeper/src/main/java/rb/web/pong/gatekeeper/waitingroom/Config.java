package rb.web.pong.gatekeeper.waitingroom;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class Config implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new SystemWaitingRoom(), "/pong/waitingroom/system");
		registry.addHandler(new IndividualWaitingRoom(), "/pong/waitingroom/individual");
	}
	
}
