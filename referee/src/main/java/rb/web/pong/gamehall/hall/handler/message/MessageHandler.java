package rb.web.pong.gamehall.hall.handler.message;

import org.json.JSONArray;
import org.json.JSONObject;

import rb.web.pong.gamehall.hall.Hall;
import rb.web.pong.gamehall.model.Player;
import rb.web.pong.gamehall.model.Recorder;

abstract class MessageHandler extends Hall{
	abstract void handleMessage(JSONObject receivedJson, Player playerOfSentMessage);
	
	protected synchronized static void sendToPlayers(JSONObject objectToBeSend) {
		for(Player p : players) {
			if(p.getSession().isOpen()) {
				p.getSession().getAsyncRemote().sendText(objectToBeSend.toString());
			}
		}
	}
	
	protected synchronized static JSONObject getBallInfoAsJson() {
    	JSONObject ballObj = new JSONObject();
    	JSONObject coordinateObj = new JSONObject();
    	coordinateObj.put("x", ball.getX());
    	coordinateObj.put("y", ball.getY());
    	ballObj.put("coordinate", coordinateObj);
    	ballObj.put("size", ball.getSize());
    	return ballObj;
    }
    
	protected synchronized static JSONArray getPlayerInfoAsJsonArray() {
		JSONArray array = new JSONArray();
		for(Player p : players) {
			JSONObject playerObj = new JSONObject();
			playerObj.put("name", p.getName());
			playerObj.put("lifes", p.getLifes());
			playerObj.put("color", p.getColorAsHexString());
			playerObj.put("position", p.getPosition().toString());
			playerObj.put("racket", getRacketInfoFromPlayerAsJson(p));
			array.put(playerObj);
		}
		return array;
	}
    
	protected synchronized static JSONObject getRacketInfoFromPlayerAsJson(Player player) {
    	JSONObject racketObj = new JSONObject();
    	racketObj.put("width", player.getRacket().getWidth());
    	racketObj.put("height",  player.getRacket().getHeight());    	
    	racketObj.put("coordinate", getCoorAsJson(player));    	
    	return racketObj;
    }   
	
	protected synchronized static JSONObject getCoorAsJson(Player player) {
		JSONObject startPosObj = new JSONObject();
    	startPosObj.put("x", player.getRacket().getX());
    	startPosObj.put("y", player.getRacket().getY());
    	return startPosObj;
	}
}
