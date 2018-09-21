package rb.web.pong.gamehall.hall.handler;

import rb.web.pong.gamehall.model.Position;
import rb.web.pong.gamehall.model.player.Player;

import java.util.Iterator;

import javax.websocket.Session;

import rb.web.pong.gamehall.hall.Hall;
import rb.web.pong.gamehall.hall.coordinator.PositionCoordinator;
import rb.web.pong.gamehall.model.GameRules;

public class PlayerHandler extends Hall{
	public static synchronized Player getPlayerBySession(Session session) {
		for(Player p : players) {
			if(p.getSession().getId().equals(session.getId())) {
				return p;
			}
		}
		return null;
	}
	
	public static synchronized void swapWidthAndHeight(Player player) {
		int width = player.getRacket().getWidth();
		int height = player.getRacket().getHeight();
		player.getRacket().setWidth(height);
		player.getRacket().setHeight(width);
	}
	
	public static synchronized void handleDirection(String direction, Player movingPlayer) {
		double x = movingPlayer.getRacket().getCoordinate().getX();
		double y = movingPlayer.getRacket().getCoordinate().getY();
		switch(direction) {
		case "UP":
			if(playerCanMoveUp(movingPlayer))
				moveUp(movingPlayer, y - GameRules.RACKET_SPEED);
			break;
		case "RIGHT":
			if(playerCanMoveRight(movingPlayer))
				moveRight(movingPlayer, x + GameRules.RACKET_SPEED);
			break;
		case "DOWN":
			if(playerCanMoveDown(movingPlayer))
				moveDown(movingPlayer, y + GameRules.RACKET_SPEED);
			break;
		case "LEFT":
			if(playerCanMoveLeft(movingPlayer))
				moveLeft(movingPlayer, x - GameRules.RACKET_SPEED);
			break;
		}
	}
	
	private static synchronized boolean playerCanMoveUp(Player movingPlayer) {
		return movingPlayer.isVertical() && movingPlayer.getRacket().getY() > GameRules.SPACE_VERTICAL;
	}
	
	private static synchronized void moveUp(Player movingPlayer, double y) {
		if(y < GameRules.SPACE_VERTICAL)
			y = GameRules.SPACE_VERTICAL;
		movingPlayer.getRacket().setY(y);
	}
	
	private static synchronized boolean playerCanMoveRight(Player movingPlayer) {
		return movingPlayer.isHorizontal() && movingPlayer.getRacket().getX() + GameRules.RACKET_WIDTH < GameRules.FIELD_WIDTH - GameRules.SPACE_HORIZONTAL;
	}
	
	private static synchronized void moveRight(Player movingPlayer, double x) {
		if(x > GameRules.FIELD_WIDTH - GameRules.SPACE_HORIZONTAL)
			x = GameRules.FIELD_WIDTH - GameRules.SPACE_HORIZONTAL;
		movingPlayer.getRacket().setX(x);
	}
	
	private static synchronized boolean playerCanMoveDown(Player movingPlayer) {
		return movingPlayer.isVertical() && movingPlayer.getRacket().getY() + GameRules.RACKET_WIDTH < GameRules.FIELD_HEIGHT - GameRules.SPACE_VERTICAL;
	}
	
	private static synchronized void moveDown(Player movingPlayer, double y) {
		if(y > GameRules.FIELD_HEIGHT - GameRules.SPACE_VERTICAL)
			y = GameRules.FIELD_HEIGHT - GameRules.SPACE_VERTICAL;
		movingPlayer.getRacket().setY(y);
	}
	
	private static synchronized boolean playerCanMoveLeft(Player movingPlayer) {
		return movingPlayer.isHorizontal() && movingPlayer.getRacket().getX() > GameRules.SPACE_HORIZONTAL;
	}
	
	private static synchronized void moveLeft(Player movingPlayer, double x) {
		if(x < GameRules.SPACE_HORIZONTAL)
			x = GameRules.SPACE_HORIZONTAL;
		movingPlayer.getRacket().setX(x);
	}
	
	public static synchronized Player getPlayerByPosition(Position position) {
		for(Player player : players) {
			if(player.getPosition().equals(position))
				return player;
		}
		return null;
	}
	
	public static synchronized Player getPlayerByArea(int area) {
		Position position = null;
		switch(area) {
		case 1:
			position = Position.TOP;
			break;
		case 2:
			position = Position.RIGHT;
			break;
		case 3:
			position = Position.BOTTOM;
			break;
		case 4:
			position = Position.LEFT;
			break;
		}
		return getPlayerByPosition(position);
	}
	
	public static synchronized boolean playersFaceEachOther() {
		Iterator<Player> iterator = players.iterator();
		Position firstPosition = iterator.next().getPosition();
		Position secondPosition = iterator.next().getPosition();
		return PositionCoordinator.isOppositePosition(firstPosition, secondPosition);
	}
	
	public static synchronized void setNewPositions() {
		int i = 0;
		for(Player player: players) {
			if(i == 0) 
				PositionCoordinator.getAndSetLeftStartPos(player);				
			else
				PositionCoordinator.getAndSetRightStartPos(player);
			++i;
		}
	}
	
	public static synchronized void resetPositions() {
		for(Player player : players) {
			switch(player.getPosition()) {
			case TOP:
				player.getRacket().setCoordinate(PositionCoordinator.getAndSetTopStartPos(player));
				break;
			case RIGHT:
				player.getRacket().setCoordinate(PositionCoordinator.getAndSetRightStartPos(player));
				break;
			case BOTTOM:
				player.getRacket().setCoordinate(PositionCoordinator.getAndSetBottomStartPos(player));
				break;
			case LEFT:
				player.getRacket().setCoordinate(PositionCoordinator.getAndSetLeftStartPos(player));
				break;
			}
		}
	}
}
