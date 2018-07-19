package rb.web.pong.gamehall.hall;

import org.json.JSONArray;
import org.json.JSONObject;

import rb.web.pong.gamehall.model.Player;
import rb.web.pong.gamehall.model.Recorder;

abstract class MessageHandler extends Hall{
	abstract void handleMessage(JSONObject receivedJson, Player playerOfSentMessage);
	
	protected synchronized static void sendToPlayers(JSONObject objectToBeSend) {
		Recorder.LOG.debug(hallId + " ; SENDING MESSAGE TO CLIENTS: " + objectToBeSend.toString());
		for(Player p : players) {
			if(p.getSession().isOpen()) {
				p.getSession().getAsyncRemote().sendText(objectToBeSend.toString());
			}
		}
	}
	
	protected synchronized static JSONObject getBallInfoAsJson() {
    	JSONObject ballObj = new JSONObject();
    	JSONObject positionObj = new JSONObject();
    	positionObj.put("x", ball.getX());
    	positionObj.put("y", ball.getY());
    	ballObj.put("position", positionObj);
    	ballObj.put("size", ball.getSize());
    	return ballObj;
    }
    
	protected synchronized static JSONArray getPlayerInfoAsJsonArray() {
		JSONArray array = new JSONArray();
		for(Player p : players) {
			JSONObject playerObj = new JSONObject();
			playerObj.put("name", p.getName());
			playerObj.put("lifes", p.getLifes());
			playerObj.put("racket", getRacketInfoFromPlayerAsJson(p));
			array.put(playerObj);
		}
		return array;
	}
    
	protected synchronized static JSONObject getRacketInfoFromPlayerAsJson(Player player) {
    	JSONObject racketObj = new JSONObject();
    	racketObj.put("width", player.getRacket().getWidth());
    	racketObj.put("height",  player.getRacket().getHeight());    	
    	racketObj.put("startPos", getStartPosAsJson(player));    	
    	return racketObj;
    }   
	
	protected synchronized static JSONObject getStartPosAsJson(Player player) {
		JSONObject startPosObj = new JSONObject();
    	startPosObj.put("x", player.getRacket().getStartPos().getX());
    	startPosObj.put("y", player.getRacket().getStartPos().getY());
    	return startPosObj;
	}
}
