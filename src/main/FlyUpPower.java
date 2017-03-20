package main;

import main.Ball;
import main.Item;

public class FlyUpPower extends main.Item{

	FlyUpPower(int x, int y, int type) {
		super(x, y, type);
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
