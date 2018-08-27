package rb.web.pong.gamehall.hall.handler.message;

import org.json.JSONObject;

import rb.web.pong.gamehall.model.GameRules;
import rb.web.pong.gamehall.model.MessageType;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.player.Player;

public class ResetMessageHandler extends UpdateMessageHandler implements Runnable{
	private int countdown;
	private Player targetPlayer;
	
	public ResetMessageHandler(Player targetPlayer) {
		this.countdown = GameRules.RESET_COUNTDOWN;
		this.targetPlayer = targetPlayer;
	}
	
	@Override
	public void run() {
		try {
			JSONObject objectToBeSend;
			while(countdown >= 0) {
				objectToBeSend = new JSONObject();
				objectToBeSend.put("type", MessageType.RESET.toString());
				objectToBeSend.put("countdown", countdown--);
				objectToBeSend.put("players", getPlayerInfoAsJsonArray());
				objectToBeSend.put("playerName", targetPlayer.getName());
				objectToBeSend.put("playerLifes", targetPlayer.getLifes());
				sendToPlayers(objectToBeSend);
				Thread.sleep(1000);
			}
			gameIsRunning = true;
			new Thread(updateHandler).start();
		}catch(Exception e) {
			Recorder.LOG.error(e.toString());
		}
	}

}
