import static org.lwjgl.opengl.GL11.glColor4f;

import java.util.Random;
/**
 * Class for creating all the shapes in the game
 * @author Ella
 *
 */
public class Shapes {

	private static double radius = 0.15;
	private static Random rand = new Random();
	
	/**
	 * Once the vertices have been created, the obstacle is drawn
	 * @param rnumx the top left x coordinate
	 * @param rnumy the top left y coordinate
	 * @param rsign which half (top or bottom) of the screen the obstacle should be in
	 */
	public static void drawobstacle(float rnumx, float rnumy, float rsigny, float rsignx) 
	{	
		if(rsigny==1 && rsignx ==1)
		{					
			float[] verticesb = {rnumx,rnumy,0.3f,rnumx,(rnumy - 0.05f),0.3f,(rnumx - 0.3f),(rnumy - 0.05f),0.3f,(rnumx -0.3f),rnumy,0.3f};
			drawrectangle(verticesb);
		}
		if(rsigny==1 && rsignx ==0)
		{
			float[] verticesb = {-rnumx,rnumy,0.3f,-rnumx,(rnumy - 0.05f),0.3f,(-rnumx + 0.3f),(rnumy - 0.05f),0.3f,(-rnumx +0.3f),rnumy,0.3f};
			drawrectangle(verticesb);
		}
		if(rsigny==0 && rsignx ==1)
		{
			float[] verticesb = {rnumx,-rnumy,0.3f,rnumx,(-rnumy + 0.05f),0.3f,(rnumx - 0.3f),(-rnumy + 0.05f),0.3f,(rnumx -0.3f),-rnumy,0.3f};
			drawrectangle(verticesb);
		}
		if(rsigny==0 && rsignx ==0)
		{
			float[] verticesb = {-rnumx,-rnumy,0.3f,-rnumx,(-rnumy + 0.05f),0.3f,(-rnumx + 0.3f),(-rnumy + 0.05f),0.3f,(-rnumx + 0.3f),-rnumy,0.3f};
			drawrectangle(verticesb);
		}
			
	}
	
	/**
	 * Draw the rectangle
	 * @param vertices the float points of the corners of the rectangle
	 */
	public static void drawrectangle(float[] vertices)
	{
		Model rectangle1 = new Model(vertices);
		rectangle1.render(vertices);
	}
	/**
	 * Draws the pinball
	 * @param posx the current x position of the centre of the pinball
	 * @param posy the current y position of the centre of the pinball
	 */
	public static void drawCircle(float posx, float posy)
	{
		float[] vertices = createCircle(posx,posy,0.5f);
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
	private static float[] createCircle(float posx, float posy, float posz)
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
	
	
	/**
	 * creates the corners of the obstacles
	 * @return a 2d Arraylist, where each index contains the four corners of an obstacle
	 */
	public static float[] getvertices() 
	{

		float rnumx = rand.nextFloat();
		float rnumy = rand.nextFloat();
		float rsigny = rand.nextFloat();
		float rsignx = rand.nextFloat();
		while(rnumy > 0.3f && rsigny < 0.5f)
		{
			rnumy = rand.nextFloat();
		}
		if(rsigny < 0.5f)
		{
			rsigny = 0f;
		}
		else
		{
			rsigny = 1.0f;
		}
		if(rsignx < 0.5f)
		{
			rsignx = 0f;
		}
		else
		{
			rsignx = 1.0f;
		}

		return new float[]{rnumx,rnumy,rsigny,rsignx};
		
	}
}
