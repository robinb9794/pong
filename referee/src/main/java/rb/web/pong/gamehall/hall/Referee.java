package rb.web.pong.gamehall.hall;

import org.springframework.web.client.RestTemplate;

import rb.web.pong.gamehall.hall.handler.BallHandler;
import rb.web.pong.gamehall.hall.handler.PlayerHandler;
import rb.web.pong.gamehall.hall.handler.message.ResetMessageHandler;
import rb.web.pong.gamehall.model.GameRules;
import rb.web.pong.gamehall.model.Position;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.player.Player;
import rb.web.pong.gamehall.model.player.Winner;

public class Referee extends BallHandler{
	public static synchronized boolean playerHasLostLife(String goalLine) {
		Position playerPosition = Position.valueOf(goalLine);
		Player player = PlayerHandler.getPlayerByPosition(playerPosition);
		if(player != null) {
			Recorder.LOG.info(hallId + " ; DECREMENTING LIFE OF PLAYER " + player.getName());
			player.decrLife();
			if(playerHasLost(player)) {
				Recorder.LOG.info(hallId + " ; REMOVING PLAYER " + player.getName());
				players.remove(player);
				if(players.size() == 2 && !PlayerHandler.playersFaceEachOther())
					PlayerHandler.setNewPositions();
			}				
			if(lastManStanding())
				endGame();
			else 
				resetGame(player);
			return true;
		}
		return false;
	}
	
	private static synchronized boolean playerHasLost(Player player) {
		return player.getLifes() == 0;
	}
	
	private static synchronized boolean lastManStanding() {
		return players.size() == 1;
	}
	
	private static synchronized void endGame() {
		gameIsRunning = false;
		Winner winner = new Winner(players.iterator().next().getName());
		Recorder.LOG.info(hallId + " ; THE END, " + winner.getName() + " HAS WON!");
		String statsUrl = "http://localhost:8082/pong/stats/insert/";
		RestTemplate template = new RestTemplate();		
		String res = template.postForObject(statsUrl, winner, String.class);
	}
	
	private static synchronized void resetGame(Player playerWhoHasLostLife) {
		Recorder.LOG.info(hallId + " ; RESET GAME");
		PlayerHandler.resetPositions();
		ball.setX(GameRules.FIELD_WIDTH / 2);
		ball.setY(GameRules.FIELD_HEIGHT / 2);
		gameIsRunning = false;
		GameRules.SERVER_TICK = GameRules.SERVER_INIT_TICK;
		new Thread(new ResetMessageHandler(playerWhoHasLostLife)).start();
	}	
}
