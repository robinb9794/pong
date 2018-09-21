package rb.web.pong.gamehall.model;

public class GameRules {
	public static final int FIELD_WIDTH = 800;
	public static final int FIELD_HEIGHT = 600;
	
	public static final int INIT_LIFES = 2;
	
	public static final int RACKET_WIDTH = 150;
	public static final int RACKET_HEIGHT = 10;
	public static final int RACKET_SPEED = 15;
	public static final int RACKET_AREA_WIDTH = RACKET_WIDTH / 5;
	
	public static final int SPACE_HORIZONTAL = FIELD_WIDTH / 10;
	public static final int SPACE_VERTICAL = FIELD_HEIGHT / 10;
	
	public static final int BALL_RADIUS = 10;	
	public static final int BALL_SPEED = 2;
	
	public static final int INIT_COUNTDOWN = 10;
	public static final int RESET_COUNTDOWN = 3;
	
	public static final int SERVER_INIT_TICK = 10;
	public static int SERVER_TICK = SERVER_INIT_TICK;
}
