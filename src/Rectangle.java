import java.util.Random;
/**
 * Class for creating all the shapes in the game
 * @author Ella
 *
 */
public class Rectangle {

	private static Random rand = new Random();
	
	
	
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
