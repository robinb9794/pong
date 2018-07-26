package rb.web.pong.gamehall.hall.handler.message;

import org.json.JSONObject;

import rb.web.pong.gamehall.hall.handler.DirectionHandler;
import rb.web.pong.gamehall.model.MessageType;
import rb.web.pong.gamehall.model.Player;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.Rules;

public class UpdateMessageHandler extends MessageHandler implements Runnable{
	@Override
	public void run() {
		try {
			Recorder.LOG.debug(hallId + " ; SERVER TICKER HAS STARTED");
			while(true) {
				JSONObject objectToBeSend = new JSONObject();
				objectToBeSend.put("type", MessageType.UPDATE.toString());
				objectToBeSend.put("ball", getBallInfoAsJson());
				objectToBeSend.put("players", getPlayerInfoAsJsonArray());
				sendToPlayers(objectToBeSend);
				Thread.sleep(Rules.SERVER_TICK);
			}
		}catch(Exception e) {
			Recorder.LOG.error(e.toString());
		}
	}
	
	@Override
	public synchronized void handleMessage(JSONObject receivedJson, Player playerOfSentMessage) {
		String direction = receivedJson.getString("direction");
		DirectionHandler.handleDirection(direction, playerOfSentMessage);
	}	
}
