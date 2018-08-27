package rb.web.pong.gamehall.hall.handler.message;

import org.json.JSONObject;

import rb.web.pong.gamehall.hall.handler.BallHandler;
import rb.web.pong.gamehall.hall.handler.PlayerHandler;
import rb.web.pong.gamehall.model.MessageType;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.player.Player;
import rb.web.pong.gamehall.model.GameRules;

public class UpdateMessageHandler extends MessageHandler implements Runnable{
	@Override
	public void run() {
		try {
			Recorder.LOG.debug(hallId + " ; UPDATE MESSAGE HANDLER HAS STARTED");
			BallHandler.startInRandomDirection();
			while(gameIsRunning) {
				BallHandler.handleBall();
				JSONObject objectToBeSend = getObjectToBeSend();
				sendToPlayers(objectToBeSend);
				Thread.sleep(GameRules.SERVER_TICK);
			}
		}catch(Exception e) {
			Recorder.LOG.error(e.toString());
		}
	}
	
	private synchronized JSONObject getObjectToBeSend() {
		JSONObject objectToBeSend = new JSONObject();
		objectToBeSend.put("type", MessageType.UPDATE.toString());
		objectToBeSend.put("ball", getBallInfoAsJson());
		objectToBeSend.put("players", getPlayerInfoAsJsonArray());
		return objectToBeSend;
	}
	
	@Override
	public synchronized void handleMessage(JSONObject receivedJson, Player playerOfSentMessage) {
		String direction = receivedJson.getString("direction");
		PlayerHandler.handleDirection(direction, playerOfSentMessage);
	}	
}
