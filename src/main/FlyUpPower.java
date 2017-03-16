package main;

import main.Ball;
import main.Item;

public class FlyUpPower extends main.Item{

	public FlyUpPower(int x, int type) {
		super(x, type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void performAction(Ball ball) {
		
		ball.setCountFlyPower(300);
		
	}
	
	@Override
	public void paint(Window window, boolean opponent) {
		//colour
		super.paint(window, opponent);
	}
}
