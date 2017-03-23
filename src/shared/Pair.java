package shared;

public class Pair<X,Y> {
	
	private X x;
	private Y y;
	
	public Pair(X x,Y y)
	{
		this.x = x;
		this.y = y;
	}
	
	public X getX()
	{
		return this.x;
	}
	
	public Y getY()
	{
		return this.y;
	}
	
	public void setX(X x)
	{
		this.x = x;
	}
	
	public void setY(Y y)
	{
		this.y = y;
	}

}
