package rb.web.pong.gamehall.hall;

import org.json.JSONObject;

import rb.web.pong.gamehall.model.Player;
import rb.web.pong.gamehall.model.Position;
import rb.web.pong.gamehall.model.Racket;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.Rules;

public class InitMessageHandler extends MessageHandler {
	@Override
	public synchronized void handleMessage(JSONObject receivedJson, Player playerOfSentMessage) {
		String name = receivedJson.getString("name");
		String racket = receivedJson.getString("racket");
		initPlayer(playerOfSentMessage, name, racket);
		Recorder.LOG.debug(hallId + " ; HANDLING INIT MESSAGE FROM " + playerOfSentMessage.getName());
		if (canSendInitMessageToPlayers())
			createInitMessageForPlayers();
	}
	
	private synchronized void initPlayer(Player playerOfSentMessage, String name, String racketType) {
		Position startPos = getStartPos();
		Racket racket = new Racket(racketType, startPos);
    	playerOfSentMessage.setName(name);    	
    	playerOfSentMessage.setRacket(racket);
    	Recorder.LOG.debug(hallId + " ; INITIALIZED PLAYER: " + name);
    }
	
	private synchronized Position getStartPos() {
		int x = Rules.CANVAS_WIDTH / 2 - Rules.RACKET_WIDTH / 2;
		int y = Rules.CANVAS_HEIGHT - 3 * Rules.RACKET_HEIGHT;
		return new Position(x, y);
	}
    
	private synchronized boolean canSendInitMessageToPlayers() {
    	return players.size() == numberOfRegisteredPlayers;
    }
	
	private synchronized void createInitMessageForPlayers() {
    	Recorder.LOG.debug(hallId + " ; CREATING INIT MESSAGE FOR PLAYERS");
    	JSONObject objectToBeSend = new JSONObject();
    	objectToBeSend.put("type", "INIT");
    	objectToBeSend.put("field", getFieldInfoAsJson());
    	objectToBeSend.put("ball", getBallInfoAsJson());
    	objectToBeSend.put("players", getPlayerInfoAsJsonArray());
    	sendToPlayers(objectToBeSend);
    } 
	
	private synchronized JSONObject getFieldInfoAsJson() {
    	JSONObject fieldObj = new JSONObject();
    	fieldObj.put("width", Rules.CANVAS_WIDTH);
    	fieldObj.put("height", Rules.CANVAS_HEIGHT);
    	return fieldObj;
    }    
}
