package Part4;

import java.awt.Graphics;
import java.util.Random;

public class Item {
	
	private int x, y, dy, width, height, radius;
	private GameClass game;
	
	public Item(int y) {
		this.y = y;
		Random r = new Random();
		x = r.nextInt(700) + radius + 100;
		radius = 10;
		dy = 2;
	}
	
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	public void update(GameClass game, Ball3 ball) {
		y += dy;
		this.game = game;
		 checkForCollision(ball);
		 if(y > game.getHeight() - radius ){
			 Random r = new Random();
			 y = - 700 - r.nextInt(300);
			 x = r.nextInt(700)+ radius+ 100;
			 
		 }
		
	}
	private void checkForCollision(Ball3 ball) {
		int ballX = ball.getX();
		int ballY = ball.getY();
		int ballR = ball.getRadius();
		
		int a = x - ballX;
		int b = y - ballY;
		int collide = radius + ballR;
		//distance between object centers
		double c = Math.sqrt((double) (a*a) + (double) (b*b)); 
		if(c < collide){
			performAction(ball);
			x = 0;
			y = game.getHeight() + 100;
		}
		
	}
	private void performAction(Ball3 ball) {
		// TODO Auto-generated method stub
		
	}

	public void paint(Graphics g){
		//g.setColor(Color.GREEN);
		g.fillOval(x-radius, y-radius, radius*2, radius*2);
	}
}
		