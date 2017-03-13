package main;



public class GravDown extends main.Item{

	public GravDown(int y, int type) {
		super(y, type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void performAction(GameState game) {
		Ball ball = game.getBall();

		if(ball.getGravity()>3) {
		ball.setGravity(ball.getGravity() - 2);
			if(ball.getGravity() < 3){
				ball.setGravity(3);
			}
		}

		//System.out.println("GRAVEDOWN");
	}
	
	@Override
	public void paint(Window window) {
		
		//colour
		super.paint(window);
	}
}
