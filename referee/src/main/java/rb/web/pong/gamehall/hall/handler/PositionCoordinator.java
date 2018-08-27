package rb.web.pong.gamehall.hall.handler;

import rb.web.pong.gamehall.hall.Hall;
import rb.web.pong.gamehall.model.Coordinate;
import rb.web.pong.gamehall.model.Position;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.player.Player;
import rb.web.pong.gamehall.model.GameRules;

public class PositionCoordinator extends Hall{
	private static volatile int positionCounter = 1;
	
	public static synchronized Coordinate getStartPos(Player player) {
		Coordinate startPos = new Coordinate();
		switch(positionCounter++) {
		case 1:
			startPos = getAndSetBottomStartPos(player);
			break;
		case 2:
			startPos = getAndSetTopStartPos(player);				
			break;
		case 3:
			startPos = getAndSetLeftStartPos(player);				
			break;
		case 4:
			startPos = getAndSetRightStartPos(player);
			break;
				
		}		
		return startPos;
	}
	
	public static synchronized Coordinate getAndSetTopStartPos(Player player) {
		Recorder.LOG.debug(hallId + " ; SET START POS: TOP");
		int x = GameRules.FIELD_WIDTH / 2 - GameRules.RACKET_WIDTH / 2;
		int y = GameRules.SPACE_VERTICAL - GameRules.RACKET_HEIGHT;
		player.setPosition(Position.TOP);
		return new Coordinate(x, y);
	}
	
	public static synchronized Coordinate getAndSetRightStartPos(Player player) {
		Recorder.LOG.debug(hallId + " ; SET START POS: RIGHT");
		int x = GameRules.FIELD_WIDTH - GameRules.SPACE_HORIZONTAL;
		int y = GameRules.FIELD_HEIGHT / 2 - GameRules.RACKET_WIDTH / 2;
		player.setPosition(Position.RIGHT);
		return new Coordinate(x, y);
	}
	
	public static synchronized Coordinate getAndSetBottomStartPos(Player player) {
		Recorder.LOG.debug(hallId + " ; SET START POS: BOTTOM");
		int x = GameRules.FIELD_WIDTH / 2 - GameRules.RACKET_WIDTH / 2;
		int y = GameRules.FIELD_HEIGHT - GameRules.SPACE_VERTICAL;
		player.setPosition(Position.BOTTOM);
		return new Coordinate(x, y);
	}	
	
	public static synchronized Coordinate getAndSetLeftStartPos(Player player) {
		Recorder.LOG.debug(hallId + " ; SET START POS: LEFT");
		int x = GameRules.SPACE_HORIZONTAL - GameRules.RACKET_HEIGHT;
		int y = GameRules.FIELD_HEIGHT / 2 - GameRules.RACKET_WIDTH / 2;
		player.setPosition(Position.LEFT);
		return new Coordinate(x, y);
	}	
	
	public static synchronized boolean racketIsHorizontal(Position racketPosition) {
		return racketPosition == Position.TOP || racketPosition == Position.BOTTOM;
	}
	
	public static synchronized boolean isOppositePosition(Position firstPosition, Position secondPosition) {
		switch(firstPosition) {
		case TOP:
			return secondPosition == Position.BOTTOM;
		case RIGHT:
			return secondPosition == Position.LEFT;
		case BOTTOM:
			return secondPosition == Position.TOP;
		case LEFT:
			return secondPosition == Position.RIGHT;
		}
		return false;
	}
}
