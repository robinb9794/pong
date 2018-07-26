package rb.web.pong.gamehall.hall.handler;

import rb.web.pong.gamehall.hall.Hall;
import rb.web.pong.gamehall.model.Coordinate;
import rb.web.pong.gamehall.model.Player;
import rb.web.pong.gamehall.model.Position;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.Rules;

public class StartPositionHandler extends Hall{
	private static int positionCounter = 1;
	
	public static synchronized Coordinate getStartPos(Player lastConnectedPlayer) {
		Coordinate startPos = new Coordinate();
		switch(positionCounter) {
		case 1:
			startPos = getBottomStartPos(lastConnectedPlayer);
			break;
		case 2:
			startPos = getTopStartPos(lastConnectedPlayer);				
			break;
		case 3:
			startPos = getLeftStartPos(lastConnectedPlayer);				
			break;
		case 4:
			startPos = getRightStartPos(lastConnectedPlayer);
			break;
				
		}		
		positionCounter++;
		return startPos;
	}
	
	private static synchronized Coordinate getBottomStartPos(Player lastConnectedPlayer) {
		Recorder.LOG.debug(hallId + " ; SET START POS: BOTTOM");
		int x = Rules.CANVAS_WIDTH / 2 - Rules.RACKET_WIDTH / 2;
		int y = Rules.CANVAS_HEIGHT - Rules.SPACE_BOTTOM;
		lastConnectedPlayer.setPosition(Position.BOTTOM);
		return new Coordinate(x, y);
	}
	
	private static synchronized Coordinate getTopStartPos(Player lastConnectedPlayer) {
		Recorder.LOG.debug(hallId + " ; SET START POS: TOP");
		int x = Rules.CANVAS_WIDTH / 2 - Rules.RACKET_WIDTH / 2;
		int y = Rules.SPACE_TOP;
		lastConnectedPlayer.setPosition(Position.TOP);
		return new Coordinate(x, y);
	}
	
	private static synchronized Coordinate getLeftStartPos(Player lastConnectedPlayer) {
		Recorder.LOG.debug(hallId + " ; SET START POS: LEFT");
		int x = Rules.SPACE_LEFT;
		int y = Rules.CANVAS_HEIGHT / 2 - Rules.RACKET_WIDTH / 2;
		lastConnectedPlayer.setPosition(Position.LEFT);
		return new Coordinate(x, y);
	}
	
	private static synchronized Coordinate getRightStartPos(Player lastConnectedPlayer) {
		Recorder.LOG.debug(hallId + " ; SET START POS: RIGHT");
		int x = Rules.CANVAS_WIDTH - Rules.SPACE_RIGHT;
		int y = Rules.CANVAS_HEIGHT / 2 - Rules.RACKET_WIDTH / 2;
		lastConnectedPlayer.setPosition(Position.RIGHT);
		return new Coordinate(x, y);
	}
}
