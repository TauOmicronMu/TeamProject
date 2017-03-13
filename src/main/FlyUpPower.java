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
		ball.setCountFlyPower(300);
		//System.out.println("FLYUPPOWER");
	}
	
	@Override
	public void paint(Window window) {
		//colour
		super.paint(window);
	}
}
