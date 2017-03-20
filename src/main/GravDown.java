package main;



public class GravDown extends main.Item{

	GravDown(int x, int y, int type) {
		super(x, y, type);
	}

	@Override
	public void performAction(Ball ball) {
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
