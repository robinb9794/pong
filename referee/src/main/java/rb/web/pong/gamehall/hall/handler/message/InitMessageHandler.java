package rb.web.pong.gamehall.hall.handler.message;

import java.awt.Color;
import java.util.Random;

import org.json.JSONObject;

import rb.web.pong.gamehall.model.Position;
import rb.web.pong.gamehall.hall.coordinator.PositionCoordinator;
import rb.web.pong.gamehall.hall.handler.PlayerHandler;
import rb.web.pong.gamehall.model.Coordinate;
import rb.web.pong.gamehall.model.MessageType;
import rb.web.pong.gamehall.model.Racket;
import rb.web.pong.gamehall.model.player.Player;
import rb.web.pong.gamehall.model.GameRules;

public class InitMessageHandler extends MessageHandler {
	@Override
	public synchronized void handleMessage(JSONObject receivedJson, Player playerOfSentMessage) {
		String name = receivedJson.getString("name");
		String racketType = receivedJson.getString("racket");		
		initPlayer(playerOfSentMessage, name, racketType);
		if (canSendInitMessageToPlayers()) {
			createInitMessageForPlayers();	
			new Thread(new CountdownMessageHandler(GameRules.INIT_COUNTDOWN)).start();
		}				
	}	
	
	private synchronized void initPlayer(Player playerOfSentMessage, String name, String racketType) {		
		Color color = getRandomColor();
		Coordinate startPos = PositionCoordinator.getStartPos(playerOfSentMessage);
		Racket racket = new Racket(startPos);
    	playerOfSentMessage.setName(name);    	
    	playerOfSentMessage.setRacket(racket);
    	playerOfSentMessage.setColor(color);
    	initializedPlayers++;
    	if(shouldSwapWidthAndHeight(playerOfSentMessage))
    		PlayerHandler.swapWidthAndHeight(playerOfSentMessage);
    }	
	
	private synchronized Color getRandomColor() {
		Random random = new Random();
		float r = random.nextFloat();
		float g = random.nextFloat();
		float b = random.nextFloat();
		return new Color(r, g, b);
	}
    
	private synchronized boolean canSendInitMessageToPlayers() {
    	return initializedPlayers == registeredPlayers;
    }
	
	private synchronized void createInitMessageForPlayers() {
    	JSONObject objectToBeSend = new JSONObject();
    	objectToBeSend.put("type", MessageType.INIT.toString());
    	objectToBeSend.put("field", getFieldInfoAsJson());
    	objectToBeSend.put("ball", getBallInfoAsJson());
    	objectToBeSend.put("players", getPlayerInfoAsJsonArray());
    	sendToPlayers(objectToBeSend);
    } 
	
	private synchronized JSONObject getFieldInfoAsJson() {
    	JSONObject fieldObj = new JSONObject();
    	fieldObj.put("width", GameRules.FIELD_WIDTH);
    	fieldObj.put("height", GameRules.FIELD_HEIGHT);
    	fieldObj.put("spaceHorizontal", GameRules.SPACE_HORIZONTAL);
    	fieldObj.put("spaceVertical",  GameRules.SPACE_VERTICAL);
    	return fieldObj;
    }    
	
	private synchronized boolean shouldSwapWidthAndHeight(Player lastConnectedPlayer) {
		return lastConnectedPlayer.getPosition() == Position.LEFT || lastConnectedPlayer.getPosition() == Position.RIGHT;
	}
}
