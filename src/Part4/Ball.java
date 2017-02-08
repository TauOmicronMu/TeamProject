package Part4;
import static org.lwjgl.opengl.GL11.glColor4f;

public class Ball {
	
	/**
	 * Draws the pinball
	 * @param posx the current x position of the centre of the pinball
	 * @param posy the current y position of the centre of the pinball
	 */
	public static void drawCircle(float posx, float posy, double radius)
	{
		float[] vertices = createCircle(posx,posy,400,radius);
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
			
			glColor4f(0,1,0,0);
			
			vertices[j] = (float) ( x + ((radius/1.3) * Math.cos(i * doublePI / noSides)));
			vertices[j+1] = (float) ( y + (radius * Math.sin(i * doublePI / noSides)));
			vertices[j+2] = z;
			i++;
		}

		return vertices;
	}
	
}
