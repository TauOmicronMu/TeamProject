package main;

import main.Ball;
import main.Item;

public class FlyUpPower extends main.Item{

	FlyUpPower(int y, int type) {
		super(y, type);
	}

	@Override
	public void performAction(GameState game) {
		//if (AudioEngine.isClient)
			//AudioEngine.getInstance().loopTrack(AudioEngine.WHOOSH);
		Ball ball = game.getBall();
		ball.setCountFlyPower(Constants.FLY_POWERUP_SPEED * 5);
	}
	
	@Override
	public void paint(Window window, boolean opponent) {
		//colour
		super.paint(window, opponent);
	}
}
