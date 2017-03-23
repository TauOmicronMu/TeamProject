package main;

public class FlyUpPower extends main.Item{

	FlyUpPower(int y, int type) {
		super(y, type);
	}

	@Override
	public void performAction(GameState game) {
		if (AudioEngine.isClient)
			AudioEngine.getInstance().loopTrack(AudioEngine.WHOOSH);
		Ball ball = game.getBall();
		ball.setCountFlyPower(Constants.FLY_POWERUP_SPEED);
		ball.setX(Constants.WINDOW_WIDTH/4 + Constants.PLATFORM_WIDTH/2);
		ball.setY(Constants.WINDOW_HEIGHT/2 + Constants.PLATFORM_HEIGHT/2);
	}
	
	@Override
	public void paint(Window window, boolean opponent) {
		super.paint(window, opponent);
	}
}
