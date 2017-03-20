package main;

import main.Ball;
import main.Item;

public class FlyUpPower extends main.Item{

	public FlyUpPower(int x, int type) {
		super(x, type);
		// TODO Auto-generated constructor stub
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
