package main;

public class Constants {
    public static final int WINDOW_WIDTH = 800*2;
    public static final int WINDOW_HEIGHT = 800;

    public static final int PORT = 8080;
    public static final String HOST = "localhost";

    public static final int MAX_FPS = 120;
    public static final int FPS_SLEEP = 1000/MAX_FPS;

    public static final String TITLE = "Space Jump";
    
    // TODO: Don't have these as a constant - work them out
    // based on our current FPS.
    // 3 for 120 fps
    // 20 for 25 fps (don't ask)
	public static final int PLATFORM_START_DY = 10;
	public static final int ITEM_START_DY = 10;

	public static final float GRAVITY = 6.81f;

    public static final int AGILITY = 20;
    public static final int MAX_SPEED = 80;
    public static final double TIME_STEP_COEFFICIENT = 0.01;
    public static final int FLY_POWERUP_SPEED = 20;
}
