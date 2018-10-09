package rb.web.pong.gamehall.hall.handler;

import rb.web.pong.gamehall.hall.Hall;
import rb.web.pong.gamehall.hall.Referee;
import rb.web.pong.gamehall.hall.coordinator.CollisionCoordinator;
import rb.web.pong.gamehall.hall.coordinator.FieldCoordinator;
import rb.web.pong.gamehall.model.GameRules;
import rb.web.pong.gamehall.model.Racket;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.Vector;
import rb.web.pong.gamehall.model.player.Player;

public class BallHandler extends Hall {
	protected static Vector v;

	public static synchronized void handleBall() {
		updateBallPosition();
		int currentArea = getCurrentArea();
		if (ballIsNearRacket(currentArea)) {
			if (ballIsHittingRacket(currentArea))
				incrSpeed();
		}
		checkForCollisionWithWall(currentArea);
	}

	public static synchronized void startInRandomDirection() {
		double x = 0, y = 0;
		while (x == 0 && players.size() == 2 || (x == 0 && y == 0)) {
			x = VectorHandler.getRandomValue(-GameRules.BALL_SPEED, GameRules.BALL_SPEED);
			y = VectorHandler.getSecondVectorValue(x, Math.random() > 0.5);
		}
		v = new Vector(x, y);
		Recorder.LOG.debug(hallId + " ; INIT VECTOR: " + v.toString());
	}

	private static synchronized void updateBallPosition() {
		double currentX = ball.getX();
		double currentY = ball.getY();
		ball.setX(currentX + v.getX());
		ball.setY(currentY + v.getY());
	}

	private static synchronized int getCurrentArea() {
		double currentBallX = ball.getX();
		double currentBallY = ball.getY();
		if (FieldCoordinator.ballIsInTopArea(currentBallX, currentBallY))
			return 1;
		else if (FieldCoordinator.ballIsInRightArea(currentBallX, currentBallY))
			return 2;
		else if (FieldCoordinator.ballIsInBottomArea(currentBallX, currentBallY))
			return 3;
		else if (FieldCoordinator.ballIsInLeftArea(currentBallX, currentBallY))
			return 4;
		return 0;
	}

	private static synchronized boolean ballIsNearRacket(int currentArea) {
		Player player = PlayerHandler.getPlayerByArea(currentArea);
		return player != null;
	}

	private static synchronized void checkForCollisionWithWall(int currentArea) {
		if (CollisionCoordinator.ballIsHittingTopWall()) {
			Recorder.LOG.info(hallId + " ; COLLISION WITH WALL: TOP");
			if (!Referee.playerHasLostLife("TOP"))
				VectorHandler.setOppositeY();
		} else if (CollisionCoordinator.ballIsHittingRightWall()) {
			Recorder.LOG.info(hallId + " ; COLLISION WITH WALL: RIGHT");
			if (!Referee.playerHasLostLife("RIGHT"))
				VectorHandler.setOppositeX();
		} else if (CollisionCoordinator.ballIsHittingBottomWall()) {
			Recorder.LOG.info(hallId + " ; COLLISION WITH WALL: BOTTOM");
			if (!Referee.playerHasLostLife("BOTTOM"))
				VectorHandler.setOppositeY();
		} else if (CollisionCoordinator.ballIsHittingLeftWall()) {
			Recorder.LOG.info(hallId + " ; COLLISION WITH WALL: LEFT");
			if (!Referee.playerHasLostLife("LEFT"))
				VectorHandler.setOppositeX();
		}
	}

	private synchronized static boolean ballIsHittingRacket(int currentArea) {
		Player player = PlayerHandler.getPlayerByArea(currentArea);
		Racket racket = player.getRacket();
		switch (player.getPosition()) {
		case TOP:
			if (CollisionCoordinator.ballIsHittingTopRacket(racket)) {
				VectorHandler.calculateCollision(player);
				return true;
			}
			break;
		case RIGHT:
			if (CollisionCoordinator.ballIsHittingRightRacket(racket)) {
				VectorHandler.calculateCollision(player);
				return true;
			}
			break;
		case BOTTOM:
			if (CollisionCoordinator.ballIsHittingBottomRacket(racket)) {
				VectorHandler.calculateCollision(player);
				return true;
			}
			break;
		case LEFT:
			if (CollisionCoordinator.ballIsHittingLeftRacket(racket)) {
				VectorHandler.calculateCollision(player);
				return true;
			}
			break;
		}
		return false;
	}

	private static synchronized void incrSpeed() {
		if (GameRules.SERVER_TICK > 2)
			GameRules.SERVER_TICK -= 1;
		Recorder.LOG.debug(hallId + " ; BALL TICKER IS NOW: " + GameRules.SERVER_TICK);
	}
}
