package main;

public class Constants {
    public static final int WINDOW_WIDTH = 800*2;
    public static final int WINDOW_HEIGHT = 800;

    public static final int PORT = 8080;
    public static final String HOST = "localhost";

    public static final int TARGET_FPS = 8;
    public static final int MAX_TIME_PER_FRAME = 1000/TARGET_FPS;

    public static final String TITLE = "Space Jump";

	public static final int PLATFORM_START_DY = 10;
	public static final int ITEM_START_DY = 10;

	public static final float GRAVITY = 6.81f;

    public static final int AGILITY = 20;
    public static final int MAX_SPEED = 80;
    public static final double TIME_STEP_COEFFICIENT = 0.01;
    public static final int FLY_POWERUP_SPEED = 40;
    public static final int MAX_BALL_HEIGHT = WINDOW_HEIGHT / 4;
    public static final int MOVING_PLATFORM_DX = 30;
}
