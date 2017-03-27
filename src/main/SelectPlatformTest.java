package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class SelectPlatformTest {
	
	@Test
	public void test() {
		GameState game = new GameState(800,800);
		Ball ball = new Ball(400,400);
		ball.setDy(-100);
		Platform p1 = new Platform(200,500,140,20);
		Platform p2 = new Platform(250,300,140,20);
		Platform p3 = new Platform(400,250,140,20);
		Platform p4 = new Platform(300,50,140,20);
		Platform p5 = new Platform(100,600,140,20);
		Platform p6 = new Platform(250,700,140,20);
		Platform p7 = new Platform(400,650,140,20);
		Platform p8 = new Platform(300,850,140,20);
		Platform p9 = new Platform(200,300,140,20);
		Platform p10 = new Platform(250,200,140,20);
		Platform p11 = new Platform(400,550,140,20);
		Platform p12 = new Platform(100,150,140,20);
		Platform p13 = new Platform(600,180,140,20);
		Platform p14 = new Platform(90,150,140,20);
		Platform p15 = new Platform(400,250,140,20);
		Platform[] ps = new Platform[]{p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15};
		game.ball = ball;
		game.platforms = ps;
		SelectPlatform sp = new SelectPlatform(game);
		Platform selected = sp.select(ball,ps);
		assertEquals(p12,selected);
	}

}
