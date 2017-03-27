package shared;
/**
 * 
 *
 * @param <X> generic type X
 * @param <Y> generic type Y
 */
public class Pair<X,Y> {
	
	private X x;
	private Y y;
	
	/**
	 * 
	 * @param x the object of type X
	 * @param y the object of type Y
	 */
	public Pair(X x,Y y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Retrieves the X part of the pair
	 * @return the x value
	 */
	public X getX()
	{
		return this.x;
	}
	
	/**
	 * Retrieves the Y part of the pair
	 * @return the y value
	 */
	public Y getY()
	{
		return this.y;
	}
	
	/**
	 * Sets the X part of the pair
	 * @param x the new x value
	 */
	public void setX(X x)
	{
		this.x = x;
	}
	
	/**
	 * Sets the X part of the pair
	 * @param x the new x value
	 */
	public void setY(Y y)
	{
		this.y = y;
	}

}
