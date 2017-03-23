package main;

public class Constants {
    public static final int WINDOW_WIDTH = 600*2;
    public static final int WINDOW_HEIGHT = 600;

    public static final int PORT = 8080;
    public static final String HOST = "localhost";

    public static final int TARGET_FPS = 60;
    public static final int MAX_TIME_PER_FRAME = 1000/TARGET_FPS;

    public static final String TITLE = "Space Jump";

	public static final int PLATFORM_START_DY = 10;
	public static final int ITEM_START_DY = 10;

	public static final float GRAVITY = 6.9f/1.337f;

    public static final int AGILITY = 20;

    public static final int MAX_SPEED = 60;
    public static final double GAME_SPEED = 50;

    public static final double TIME_STEP_COEFFICIENT = 1/GAME_SPEED;

    public static final int FLY_POWERUP_SPEED = 450;
    public static final int MAX_BALL_HEIGHT = WINDOW_HEIGHT / 4;
    public static final int MOVING_PLATFORM_DX = 30;
    public static final double MIN_TIME_PER_FRAME = 1000 / 240;

    public static final int ITEM_RADIUS = 10;

    public static final int CIRCLE_SIDES = 8;

    public static final int SRVR_MS_PER_UPDT = 6;

    public static final int PLATFORM_WIDTH = 200;
    public static final int PLATFORM_HEIGHT = 20;

    public static final double AI_MAX_DIST = 400.00;

    public static final int GRAVITY_UP_STEP = 2;
    public static final int GRAVITY_DOWN_STEP = 2;
    public static final int GRAVITY_DOWN_THRESH = 7;

    public static final int POINT_ITEM_STEP = 3000;

    public static final String LOADING_SCREEN_TEXT = "searching...";
    public static final String GAME_WIN_TEXT = "Victory!"; //"You VVin!";
    public static final String GAME_LOSE_TEXT = "Defeat..."; //"You Lose!";
}

