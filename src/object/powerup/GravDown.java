package object.powerup;


import object.Ball;
import shared.Constants;
import shared.GameState;
import graphics.ui.Window;

public class GravDown extends Item {

	public GravDown(int y, int type) {
		super(y, type);
	}

	@Override
	public void performAction(GameState game) {
		Ball ball = game.getBall();
		if(ball.getGravity() > Constants.GRAVITY_DOWN_THRESH) {
			ball.setGravity(ball.getGravity() - Constants.GRAVITY_DOWN_STEP);
			if (ball.getGravity() < Constants.GRAVITY_DOWN_THRESH) {
				ball.setGravity(Constants.GRAVITY_DOWN_THRESH);
			}
		}
	}
	
	@Override
	public void paint(Window window, boolean opponent) {
		super.paint(window, opponent);
	}
}
