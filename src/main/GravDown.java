package main;



public class GravDown extends main.Item{

	GravDown(int y, int type) {
		super(y, type);
	}

	@Override
	public void performAction(GameState game) {
		Ball ball = game.getBall();
		if(ball.getGravity()>7) {
		ball.setGravity(ball.getGravity() - 2);
			if(ball.getGravity() < 7){
				ball.setGravity(7);
			}
		}
	}
	
	@Override
	public void paint(Window window, boolean opponent) {
		
		//colour
		super.paint(window, opponent);
	}
}
