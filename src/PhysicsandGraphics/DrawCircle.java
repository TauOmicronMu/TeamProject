package PhysicsandGraphics;

import static org.lwjgl.opengl.GL11.glColor4f;

public class DrawCircle {
	
	public DrawCircle(){
		
	}
	
	/**
	 * Draws the pinball
	 * @param posx the current x position of the centre of the pinball
	 * @param posy the current y position of the centre of the pinball
	 */
	public void paintPinball(Graphics game, int x, int y)
	{
		System.out.println("Old X: " + x);
		System.out.println("New X: " + game.changexCoord(x));
		float[] vertices = createCircle(game.changexCoord(x),game.changeyCoord(y),0.5f,0.08f);
		Model circle1 = new Model(vertices);
		circle1.render(vertices);
	}
	
	public void paintItem(Graphics game, int x, int y)
	{
		float[] vertices = createCircle(game.changexCoord(x),game.changeyCoord(y),0.3f,0.03f);
		Model circle1 = new Model(vertices);
		circle1.render(vertices);
	}
	
	/**
	 * Calculates all the points of the circumference of the circle
	 * @param posx the current x position of the centre of the pinball
	 * @param posy the current y position of the centre of the pinball
	 * @param posz the current z position of the centre of the pinball
	 * @return all the points of the circle
	 */
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
