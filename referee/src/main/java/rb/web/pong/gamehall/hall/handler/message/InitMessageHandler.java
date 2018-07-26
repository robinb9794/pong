package rb.web.pong.gamehall.hall.handler.message;

import java.awt.Color;
import java.util.Random;

import org.json.JSONObject;

import rb.web.pong.gamehall.model.Player;
import rb.web.pong.gamehall.model.Position;
import rb.web.pong.gamehall.hall.handler.DirectionHandler;
import rb.web.pong.gamehall.hall.handler.StartPositionHandler;
import rb.web.pong.gamehall.model.Coordinate;
import rb.web.pong.gamehall.model.MessageType;
import rb.web.pong.gamehall.model.Racket;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.Rules;

public class InitMessageHandler extends MessageHandler {
	@Override
	public synchronized void handleMessage(JSONObject receivedJson, Player playerOfSentMessage) {
		String name = receivedJson.getString("name");
		String racketType = receivedJson.getString("racket");		
		initPlayer(playerOfSentMessage, name, racketType);
		if (canSendInitMessageToPlayers()) {
			createInitMessageForPlayers();	
			new Thread(new CountdownMessageHandler()).start();
		}				
	}	
	
	private synchronized void initPlayer(Player playerOfSentMessage, String name, String racketType) {		
		Color color = getRandomColor();
		Coordinate startPos = StartPositionHandler.getStartPos(playerOfSentMessage);
		Racket racket = new Racket(startPos);
    	playerOfSentMessage.setName(name);    	
    	playerOfSentMessage.setRacket(racket);
    	playerOfSentMessage.setColor(color);
    	initializedPlayers++;
    	if(shouldSwapWidthAndHeight(playerOfSentMessage))
    		DirectionHandler.swapWidthAndHeight(playerOfSentMessage);
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
    	fieldObj.put("width", Rules.CANVAS_WIDTH);
    	fieldObj.put("height", Rules.CANVAS_HEIGHT);
    	return fieldObj;
    }    
	
	private synchronized boolean shouldSwapWidthAndHeight(Player lastConnectedPlayer) {
		return lastConnectedPlayer.getPosition() == Position.LEFT || lastConnectedPlayer.getPosition() == Position.RIGHT;
	}
	
	class CountdownMessageHandler implements Runnable{
		private int countdown;
		
		public CountdownMessageHandler() {
			this.countdown = 10;
		}
		
		@Override
		public void run() {
			JSONObject objectToBeSend;
			try {
				while(countdown >= 0) {
					Thread.sleep(1000);
					objectToBeSend = new JSONObject();
					objectToBeSend.put("type", MessageType.COUNTDOWN.toString());
					objectToBeSend.put("countdown", countdown--);
					sendToPlayers(objectToBeSend);
				}
				new Thread(updateHandler).start();
			}catch(Exception e) {
				Recorder.LOG.error(e.toString());
			}		
		}
	}
}
