package main;

import main.Ball;
import main.Item;

public class FlyUpPower extends main.Item{

	FlyUpPower(int y, int type) {
		super(y, type);
	}

	@Override
	public void performAction(GameState game) {
		Ball ball = game.getBall();
		ball.setCountFlyPower(100);
		
	}
	
	@Override
	public void paint(Window window, boolean opponent) {
		//colour
		super.paint(window, opponent);
	}
}
