package main;


public class GravUp extends main.Item{

	public GravUp(int x, int y, int type) {
		super(x, y, type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void performAction(Ball ball) {
		ball.setGravity(ball.getGravity() + 2);
	}
	
	
	@Override
	public void paint(Window window, boolean opponent) {
		
		//colour
		super.paint(window, opponent);
	}
}
