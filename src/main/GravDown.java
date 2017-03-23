package main;



public class GravDown extends main.Item{

	GravDown(int y, int type) {
		super(y, type);
	}

	@Override
	public void performAction(GameState game) {
		Ball ball = game.getBall();
		if(ball.getGravity() > Constants.GRAVITY_DOWN_THRESH) {
		ball.setGravity(ball.getGravity() - Constants.GRAVITY_DOWN_STEP);
			if(ball.getGravity() < Constants.GRAVITY_DOWN_THRESH){
				ball.setGravity(Constants.GRAVITY_DOWN_THRESH);
			}
		}
	}
	
	@Override
	public void paint(Window window, boolean opponent) {
		
		//colour
		super.paint(window, opponent);
	}
}
