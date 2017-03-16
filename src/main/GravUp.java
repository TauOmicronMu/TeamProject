package main;


public class GravUp extends main.Item{

	public GravUp(int y, int type) {
		super(y, type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void performAction(GameState game) {
		Ball ball = game.getBall();
		ball.setGravity(ball.getGravity() + 2);
		//System.out.println("GRAVEUP");
	}
	
	
	@Override
	public void paint(Window window) {
		
		//colour
		super.paint(window);
	}
}
