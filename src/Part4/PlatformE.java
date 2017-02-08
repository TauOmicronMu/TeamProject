package Part4;

public class PlatformE {

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
			Rectangle.drawrectangle(verticesb);
		}
		if(rsigny==1 && rsignx ==0)
		{
			float[] verticesb = {-rnumx,rnumy,0.3f,-rnumx,(rnumy - 0.05f),0.3f,(-rnumx + 0.3f),(rnumy - 0.05f),0.3f,(-rnumx +0.3f),rnumy,0.3f};
			Rectangle.drawrectangle(verticesb);
		}
		if(rsigny==0 && rsignx ==1)
		{
			float[] verticesb = {rnumx,-rnumy,0.3f,rnumx,(-rnumy + 0.05f),0.3f,(rnumx - 0.3f),(-rnumy + 0.05f),0.3f,(rnumx -0.3f),-rnumy,0.3f};
			Rectangle.drawrectangle(verticesb);
		}
		if(rsigny==0 && rsignx ==0)
		{
			float[] verticesb = {-rnumx,-rnumy,0.3f,-rnumx,(-rnumy + 0.05f),0.3f,(-rnumx + 0.3f),(-rnumy + 0.05f),0.3f,(-rnumx + 0.3f),-rnumy,0.3f};
			Rectangle.drawrectangle(verticesb);
		}
			
	}
}
