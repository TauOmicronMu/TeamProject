package ai;

import main.Ball;
import main.GameState;
import main.Platform;

import java.io.IOException;

public class Testing {
	public static void main(String args[]) throws ClassNotFoundException, IOException{
		GameState game = new GameState(600,800);
		//AIEngine ai = new AIEngine(game);
		Ball ball = new Ball(300,750);
		ball.setDy(90);
		Platform p1 = new Platform(600,200,120,40);
		Platform p2 = new Platform(230,650,120,40);
		Platform p3 = new Platform(80,650,120,40);
		Platform[] ps = new Platform[]{p1,p2,p3};
		AI ai = new AI();
		double result = ai.apply(ball, ps);
		System.out.println("Result: " + result);
	}
}
