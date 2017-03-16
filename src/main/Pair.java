package main;

public class Pair<X,Y> {
	
	private float X;
	private float Y;
	
	public Pair(float x,float y)
	{
		this.X = x;
		this.Y = y;
	}
	
	public float getX()
	{
		return this.X;
	}
	
	public float getY()
	{
		return this.Y;
	}
	
	public void setX(float x)
	{
		this.X = x;
	}
	
	public void setY(float y)
	{
		this.Y = y;
	}

}
