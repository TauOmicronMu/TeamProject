package main;



public class GravDown extends main.Item{

	public GravDown(int y, int type) {
		super(y, type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void performAction(Ball ball) {
		if(ball.getGravity()>7) {
		ball.setGravity(ball.getGravity() - 2);
			if(ball.getGravity() < 7){
				ball.setGravity(7);
			}
		}
		System.out.println("IT WORKS GRAV DOWN");
	}
	
	@Override
	public void paint(Window window) {
		
		//colour
		super.paint(window);
	}
}
