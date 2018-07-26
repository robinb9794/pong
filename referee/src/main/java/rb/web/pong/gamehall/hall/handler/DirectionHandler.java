package rb.web.pong.gamehall.hall.handler;

import rb.web.pong.gamehall.model.Player;
import rb.web.pong.gamehall.model.Rules;

public class DirectionHandler {
	public static synchronized void swapWidthAndHeight(Player lastConnectedPlayer) {
		int width = lastConnectedPlayer.getRacket().getWidth();
		int height = lastConnectedPlayer.getRacket().getHeight();
		lastConnectedPlayer.getRacket().setWidth(height);
		lastConnectedPlayer.getRacket().setHeight(width);
	}
	
	public static synchronized void handleDirection(String direction, Player playerOfSentMessage) {
		int x = playerOfSentMessage.getRacket().getCoordinate().getX();
		int y = playerOfSentMessage.getRacket().getCoordinate().getY();
		switch(direction) {
		case "UP":
			if(playerCanMoveUp(playerOfSentMessage))
				playerOfSentMessage.getRacket().setY(y - Rules.RACKET_SPEED);
			break;
		case "RIGHT":
			if(playerCanMoveRight(playerOfSentMessage))
				playerOfSentMessage.getRacket().setX(x + Rules.RACKET_SPEED);
			break;
		case "DOWN":
			if(playerCanMoveDown(playerOfSentMessage))
				playerOfSentMessage.getRacket().setY(y + Rules.RACKET_SPEED);
			break;
		case "LEFT":
			if(playerCanMoveLeft(playerOfSentMessage))
				playerOfSentMessage.getRacket().setX(x - Rules.RACKET_SPEED);
			break;
		}
	}
	
	private static synchronized boolean playerCanMoveUp(Player playerOfSentMessage) {
		return playerOfSentMessage.isVertical() && playerOfSentMessage.getRacket().getY() > Rules.SPACE_TOP;
	}
	
	private static synchronized boolean playerCanMoveRight(Player playerOfSentMessage) {
		return playerOfSentMessage.isHorizontal() && playerOfSentMessage.getRacket().getX() + Rules.RACKET_WIDTH < Rules.CANVAS_WIDTH - Rules.SPACE_RIGHT;
	}
	
	private static synchronized boolean playerCanMoveDown(Player playerOfSentMessage) {
		return playerOfSentMessage.isVertical() && playerOfSentMessage.getRacket().getY() + Rules.RACKET_WIDTH < Rules.CANVAS_HEIGHT - Rules.SPACE_BOTTOM;
	}
	
	private static synchronized boolean playerCanMoveLeft(Player playerOfSentMessage) {
		return playerOfSentMessage.isHorizontal() && playerOfSentMessage.getRacket().getX() > Rules.SPACE_LEFT;
	}
}
