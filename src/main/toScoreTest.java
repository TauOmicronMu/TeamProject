package main;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class toScoreTest {

	@Test
	public void test() {
		Ball ball = new Ball(400,400);
		ball.setDy(100);
		Platform p = new Platform(330,350,140,20);
		double dx = p.x - ball.getX();
		double dy = p.y - ball.getY();
		toScore f = new toScore((int)dx,(int)dy,(int)ball.getDy(),p.width);
		ArrayList<Double> xvSample = new ArrayList<Double>();
		xvSample.add(0.0);
		xvSample.add(10.0);
		int score1 = (int)Math.floor(f.apply(xvSample).get(0));
		int score2 = (int)Math.floor(f.apply(xvSample).get(1));
		assertEquals(70,score1);
		assertEquals(-1,score2);
		
		
	}

}
