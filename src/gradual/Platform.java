package gradual;
import java.util.Random;

public class Platform {

	private int dx;
	private int x, y, width, height;
	
	public Platform(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		dx = 3;
	}
	
	public Platform() {
		dx = 3;
		x = 300;
		y = 300;
		width = 120;
		height = 40;
	}
	public void update(Graphics game, Ball3 ball) {
		//System.out.println(ball.getDy());
		if(ball.getAgility()>3){
			y += ball.getAgility();
		}else{
			y+=dx;
		}
		 checkForCollision(ball);
		 if(y > game.getHeight() ){
			 
			 Random r = new Random();
			 y = 0 + 40;
			 x = game.getWidth() - r.nextInt(700)-100;
			 
		 }
		
	}
	private void checkForCollision(Ball3 ball) {
		int ballX = ball.getX();
		int ballY = ball.getY();
		int radius = ball.getRadius();
		
		if (ballY + radius > y && ballY+radius < y + height){
			if(ballX > x && ballX < x + width){
			
				double newDy= ball.getGameDy();
				ball.setDy(newDy);
				ball.setY(y - radius);
			}
		}
	}
	public void paint(Graphics game){
		float floatx = game.changexCoord(x);
		float floaty = game.changeyCoord(y);
		float[] verticesb = {floatx,floaty,0.3f,floatx,(floaty - 0.05f),0.3f,(floatx - 0.3f),(floaty - 0.05f),0.3f,(floatx -0.3f),floaty,0.3f};
		Rectangle.drawrectangle(verticesb);
	}
}
