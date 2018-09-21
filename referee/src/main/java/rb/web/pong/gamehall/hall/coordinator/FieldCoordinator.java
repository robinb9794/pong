package rb.web.pong.gamehall.hall.coordinator;

import rb.web.pong.gamehall.model.GameRules;

public class FieldCoordinator {
	public static synchronized boolean ballIsInTopArea(double ballX, double ballY) {
		if(ballX >= GameRules.SPACE_HORIZONTAL && ballX <= GameRules.FIELD_WIDTH - GameRules.SPACE_HORIZONTAL) {
			if(ballY <= GameRules.FIELD_HEIGHT / 3)
				return true;
		}
		return false;
	}
	
	public static synchronized boolean ballIsInRightArea(double ballX, double ballY) {
		if(ballY >= GameRules.SPACE_VERTICAL && ballY <= GameRules.FIELD_HEIGHT - GameRules.SPACE_VERTICAL) {
			if(ballX >= GameRules.FIELD_WIDTH - GameRules.FIELD_WIDTH / 3)
				return true;
		}
		return false;
	}
	
	public static synchronized boolean ballIsInBottomArea(double ballX, double ballY) {
		if(ballX >= GameRules.SPACE_HORIZONTAL && ballX <= GameRules.FIELD_WIDTH - GameRules.SPACE_HORIZONTAL) {
			if(ballY >= GameRules.FIELD_HEIGHT - GameRules.FIELD_HEIGHT / 3)
				return true;
		}
		return false;
	}
	
	public static synchronized boolean ballIsInLeftArea(double ballX, double ballY) {
		if(ballY >= GameRules.SPACE_VERTICAL && ballY <= GameRules.FIELD_HEIGHT - GameRules.SPACE_VERTICAL) {
			if(ballX <= GameRules.FIELD_WIDTH / 3)
				return true;
		}
		return false;
	}
}
