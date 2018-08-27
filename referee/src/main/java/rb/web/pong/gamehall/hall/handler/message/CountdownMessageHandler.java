package rb.web.pong.gamehall.hall.handler.message;

import org.json.JSONObject;

import rb.web.pong.gamehall.model.MessageType;
import rb.web.pong.gamehall.model.Recorder;

public class CountdownMessageHandler extends InitMessageHandler implements Runnable{
	private int countdown;
	
	public CountdownMessageHandler(int countdown) {
		this.countdown = countdown;
	}
	
	@Override
	public void run() {
		JSONObject objectToBeSend;
		try {
			while(countdown >= 0) {
				objectToBeSend = new JSONObject();
				objectToBeSend.put("type", MessageType.COUNTDOWN.toString());
				objectToBeSend.put("countdown", countdown--);
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
