package PhysicsandGraphics;

import static org.lwjgl.opengl.GL11.glColor4f;

import java.util.Random;

public class Item {
	
	private int x, y, dy, radius;
	private Graphics game;
	
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
	
	public void update(Graphics game, Ball3 ball) {
		y += dy;
		this.game = game;
		 checkForCollision(ball);
		 if(y > game.getHeight() - radius ){
			 Random r = new Random();
			 y = - game.getHeight()-100 - r.nextInt(300);
			 x = r.nextInt(game.getWidth()-100)+ radius+ 100;
			 
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
	}

	public void paint(){
		float[] vertices = createCircle(game.changexCoord(x),game.changeyCoord(y),0.2f,0.02f);
		Model circle1 = new Model(vertices);
	
		circle1.render(vertices);
	}
	
	private static float[] createCircle(float posx, float posy, float posz, double radius)
	{
		int noSides = 360;
		int noVertices = noSides + 2;
		float doublePI = (float) Math.PI * 2;
		
		int i = 1;
		float[] vertices = new float[noVertices*3];
		float x = posx;
		float y = posy;
		float z = posz;
		vertices[0] = x;
		vertices[1] = y;
		vertices[2] = z;
		for(int j= 3; j < (noVertices * 3); j = j + 3)
		{
			
			vertices[j] = (float) ( x + (radius * Math.cos(i * doublePI / noSides)));
			vertices[j+1] = (float) ( y + (radius * Math.sin(i * doublePI / noSides)));
			vertices[j+2] = z;
			i++;
		}

		return vertices;
	}
}
		