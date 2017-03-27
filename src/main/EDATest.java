package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class EDATest {

	@Test
	public void test() {
		Ball ball = new Ball(400,400);
		ball.setDy(100);
		Platform p = new Platform(331,350,140,20);
		double dx = p.x - ball.getX();
		double dy = p.y - ball.getY();
		toScore f = new toScore((int)dx,(int)dy,(int)ball.getDy(),p.width);
		EDA eda = new EDA();
		double[] xV = new double[20];
		int counter = 0;
		for(double i=-5; i<=5;i=i+0.5){//Is the maximum x velocity 5?
			xV[counter] = i;
		}
		double xv = eda.simpleEDA(f, xV, 10);
		int xv_d = (int)Math.floor(xv);
		assertEquals(0,xv_d);
	}

}
