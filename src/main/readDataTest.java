package main;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Hashtable;

import org.junit.Test;

public class readDataTest {

	@Test
	public void test() throws ClassNotFoundException, IOException {
		Ball ball = new Ball(400,400);
		ball.setDy(-100);
		ball.dx = 0;
		Platform p1 = new Platform(330,350,140,20);
		Platform p2 = new Platform(400,450,140,20);
		Platform[] ps = new Platform[]{p1};
		System.out.println("Reading Hashtable...");
		GameState game = new GameState(800,800);
		AI t = new AI(game);
		Hashtable<String, Double> database = t.getDB();
		readData rd = new readData();
		double move = rd.AI(ball, ps, database, game);
		int result = (int) Math.round(move);
		assertEquals(-1,result);
	}

}
