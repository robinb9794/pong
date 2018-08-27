package rb.web.pong.gamehall.hall.handler;

import java.util.concurrent.ThreadLocalRandom;
import rb.web.pong.gamehall.model.GameRules;
import rb.web.pong.gamehall.model.Position;
import rb.web.pong.gamehall.model.Recorder;
import rb.web.pong.gamehall.model.Vector;
import rb.web.pong.gamehall.model.player.Player;

public class VectorHandler extends BallHandler{
	public static synchronized double getRandomValue(double min, double max) {
		double randomValue = ThreadLocalRandom.current().nextDouble(min, max + 1);
		return getRoundedValue(randomValue);
	}
	
	public static synchronized double getSecondVectorValue(double firstValue, boolean shouldBeNegative) {
		double secondVectorValue = Math.sqrt(Math.pow(GameRules.BALL_SPEED, 2) - Math.pow(firstValue, 2));
		secondVectorValue = getRoundedValue(secondVectorValue);
		return shouldBeNegative ? -secondVectorValue : secondVectorValue;
	}
	
	public static synchronized double getRoundedValue(double value) {
		long factor = (long) Math.pow(10,  2);
		value *= factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	public synchronized static void calculateollision(Player player) {	
		Recorder.LOG.info(hallId + " ; COLLISION WITH RACKET: " + player.getPosition().toString());
		double vectorX = v.getX();
		double vectorY = v.getY();
		int collisionArea = CollisionCoordinator.getRacketCollisionArea(player);
		Vector collisionVector;
		if(PositionCoordinator.racketIsHorizontal(player.getPosition()))
			collisionVector = getHorizontalCollisionVector(player.getPosition(), collisionArea, vectorX, vectorY);
		else
			collisionVector = getVerticalCollisionVector(player.getPosition(), collisionArea, vectorX, vectorY);
		updateVector(collisionVector);
	}
	
	private static synchronized Vector getHorizontalCollisionVector(Position playerPosition, int collisionArea, double vectorX, double vectorY) {
		switch(collisionArea) {
		case 0:
			if(playerPosition == Position.TOP)
				vectorY = 1;
			else
				vectorY = -1;
			vectorX = -1;
			break;
		case 1:
			if(playerPosition == Position.TOP)
				vectorY = GameRules.BALL_SPEED;
			else
				vectorY = -GameRules.BALL_SPEED;
			vectorX = 0;
			break;
		case 2:
			vectorY *= -1;
			break;
		case 3:
			if(playerPosition == Position.TOP)
				vectorY = GameRules.BALL_SPEED;
			else
				vectorY = -GameRules.BALL_SPEED;
			vectorX = 0;
			break;
		case 4:
			if(playerPosition == Position.TOP)
				vectorY = 1;
			else
				vectorY = -1;
			vectorX = 1;
			break;
		}
		return new Vector(vectorX, vectorY);
	}
	
	private static synchronized Vector getVerticalCollisionVector(Position playerPosition, int collisionArea, double vectorX, double vectorY) {
		switch(collisionArea) {
		case 0:
			if(playerPosition == Position.RIGHT)
				vectorX = -1;
			else
				vectorX = 1;
			vectorY = -1;
			break;
		case 1:
			if(playerPosition == Position.RIGHT)
				vectorX = -GameRules.BALL_SPEED;
			else
				vectorX = GameRules.BALL_SPEED;
			vectorY = 0;
			break;
		case 2:
			vectorX *= -1;
			break;
		case 3:
			if(playerPosition == Position.RIGHT)
				vectorX = -GameRules.BALL_SPEED;
			else
				vectorX = GameRules.BALL_SPEED;
			vectorY = 0;
			break;
		case 4:
			if(playerPosition == Position.RIGHT)
				vectorX = -1;
			else
				vectorX = 1;
			vectorY = 1;
			break;
		}
		return new Vector(vectorX, vectorY);
	}
	
	private static synchronized void updateVector(Vector tmp) {
		Recorder.LOG.debug(hallId + " ; TMP VECTOR: " + tmp.toString());
		double vectorX = getRoundedValue(tmp.getX());
		double vectorY = getRoundedValue(tmp.getY());
		v.setX(vectorX);
		v.setY(vectorY);
	}
	
	public static synchronized boolean ballIsComingFromBottom() {
		return v.getY() < 0;
	}
	
	public static synchronized boolean ballIsComingFromLeft() {
		return v.getX() > 0;
	}
	
	public static synchronized boolean ballIsComingFromTop() {
		return v.getY() > 0;
	}
	
	public static synchronized boolean ballIsComingFromRight() {
		return v.getX() < 0;
	}
	
	public static synchronized void setOppositeX() {
		double vectorX = v.getX();
		v.setX(vectorX *= -1);
	}
	
	public static synchronized void setOppositeY() {
		double vectorY = v.getY();
		v.setY(vectorY *= -1);
	}
	
	public static synchronized void setOppositeDirection() {
		double vectorX = v.getX();
		double vectorY = v.getY();
		v.setX(vectorX *= -1);
		v.setY(vectorY *= -1);
	}	
}
