package rb.web.pong.gamehall.hall.handler;

import rb.web.pong.gamehall.hall.Hall;
import rb.web.pong.gamehall.model.GameRules;
import rb.web.pong.gamehall.model.Racket;
import rb.web.pong.gamehall.model.player.Player;

public class CollisionCoordinator extends Hall{	
	public static synchronized boolean ballIsHittingTopRacket(Racket racket) {
		double racketStartX = racket.getX();
		double racketEndX = racketStartX + GameRules.RACKET_WIDTH;
		double racketCollisionLine = racket.getY() + GameRules.RACKET_HEIGHT;
		double ballCollisionLineToCheck = ball.getY() - GameRules.BALL_RADIUS;
		if(ballIsAtHeightOfRacket("Less", ballCollisionLineToCheck, racketCollisionLine) && ballIsInRacketRange(ball.getX(), racketStartX, racketEndX) && VectorHandler.ballIsComingFromBottom())
			return true;
		return false;
	}
	
	public static synchronized boolean ballIsHittingRightRacket(Racket racket) {
		double racketStartY = racket.getY();
		double racketEndY = racketStartY + GameRules.RACKET_WIDTH;
		double racketCollisionLine = racket.getX();
		double ballCollisionLineToCheck = ball.getX() + GameRules.BALL_RADIUS;
		if(ballIsAtHeightOfRacket("Greater", ballCollisionLineToCheck, racketCollisionLine) && ballIsInRacketRange(ball.getY(), racketStartY, racketEndY) && VectorHandler.ballIsComingFromLeft())
			return true;
		return false;
	}
	
	public static synchronized boolean ballIsHittingBottomRacket(Racket racket) {
		double racketStartX = racket.getX();
		double racketEndX = racketStartX + GameRules.RACKET_WIDTH;
		double racketCollisionLine = racket.getY();
		double ballCollisionLineToCheck = ball.getY() + GameRules.BALL_RADIUS;
		if(ballIsAtHeightOfRacket("Greater", ballCollisionLineToCheck, racketCollisionLine) && ballIsInRacketRange(ball.getX(), racketStartX, racketEndX) && VectorHandler.ballIsComingFromTop())
			return true;
		return false;
	}
	
	public static synchronized boolean ballIsHittingLeftRacket(Racket racket) {
		double racketStartY = racket.getY();
		double racketEndY = racketStartY + GameRules.RACKET_WIDTH;
		double racketCollisionLine = racket.getX() + GameRules.RACKET_HEIGHT;
		double ballCollisionLineToCheck = ball.getX() - GameRules.BALL_RADIUS;
		if(ballIsAtHeightOfRacket("Less", ballCollisionLineToCheck, racketCollisionLine) && ballIsInRacketRange(ball.getY(), racketStartY, racketEndY) && VectorHandler.ballIsComingFromRight())
			return true;
		return false;
	}
	
	private static synchronized boolean ballIsAtHeightOfRacket(String operation, double valueToCheck, double collisionLine) {
		valueToCheck = (int) valueToCheck;
		collisionLine = (int) collisionLine;
		switch(operation) {
		case "Greater":
			if(valueToCheck + GameRules.BALL_SPEED >= collisionLine && valueToCheck - GameRules.BALL_SPEED >= collisionLine)
				return true;
			break;
		case "Less":
			if(valueToCheck + GameRules.BALL_SPEED <= collisionLine && valueToCheck - GameRules.BALL_SPEED <= collisionLine)
				return true;
			break;
		}		
		return false;
	}
	
	private static synchronized boolean ballIsInRacketRange(double ballValue, double start, double end) {
		return ballValue + GameRules.BALL_RADIUS >= start && ballValue + GameRules.BALL_RADIUS <= end;
	}
	
	public static synchronized boolean ballIsHittingTopRightCorner() {
		return ball.getY() - GameRules.BALL_RADIUS <= 0 && ball.getX() + GameRules.BALL_RADIUS >= GameRules.FIELD_WIDTH;
	}
	
	public static synchronized boolean ballIsHittingBottomRightCorner() {
		return ball.getY() + GameRules.BALL_RADIUS >= GameRules.FIELD_HEIGHT && ball.getX() + GameRules.BALL_RADIUS >= GameRules.FIELD_WIDTH;
	}
	
	public static synchronized boolean ballIsHittingBottomLeftCorner() {
		return ball.getY() + GameRules.BALL_RADIUS >= GameRules.FIELD_HEIGHT && ball.getX() - GameRules.BALL_RADIUS <= 0;
	}
	
	public static synchronized boolean ballIsHittingTopLeftCorner() {
		return ball.getY() - GameRules.BALL_RADIUS <= 0 && ball.getX() - GameRules.BALL_RADIUS <= 0;
	}
	
	public static synchronized boolean ballIsHittingTopWall() {
		return ball.getY() - GameRules.BALL_RADIUS <= 0;
	}	
	
	public static synchronized boolean ballIsHittingRightWall() {
		return ball.getX() + GameRules.BALL_RADIUS >= GameRules.FIELD_WIDTH;
	}
	
	public static synchronized boolean ballIsHittingBottomWall() {
		return ball.getY() + GameRules.BALL_RADIUS >= GameRules.FIELD_HEIGHT;
	}
	
	public static synchronized boolean ballIsHittingLeftWall() {
		return ball.getX() - GameRules.BALL_RADIUS <= 0;
	}	
	
	public static synchronized int getRacketCollisionArea(Player player) {
		double racketStart = PositionCoordinator.racketIsHorizontal(player.getPosition()) ? player.getRacket().getX() : player.getRacket().getY();
		double ballValue = PositionCoordinator.racketIsHorizontal(player.getPosition()) ? ball.getX() : ball.getY();
		double dx = ballValue - racketStart;
		int collisionArea = (int) (dx / GameRules.RACKET_AREA_WIDTH);
		return collisionArea;
	}
}
