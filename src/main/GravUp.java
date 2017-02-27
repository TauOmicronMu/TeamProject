package main;


public class GravUp extends main.Item{

	public GravUp(int y, int type) {
		super(y, type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void performAction(Ball ball) {
		ball.setGravity(ball.getGravity() + 2);
		System.out.println("IT WORKS GRAV UP");
	}
	
	
	@Override
	public void paint(Window window) {
		
		//colour
		super.paint(window);
	}
}
