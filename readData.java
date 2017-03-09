package main;

import java.util.Hashtable;

public class readData {
	
	public readData(){
		
	}
	
	/**
	 * Input a ball and a list of platform, then return a x-velocity
	 * @param b Ball
	 * @param ps Platform list
	 * @return x-velocity
	 */
	public double AI(Ball b, Platform[] ps, Hashtable database,GameState game){
		Platform p = new Platform();
		SelectPlatform sp = new SelectPlatform(game);
		boolean r = true;//platform on right hand side
		p = sp.select(b,ps);
		
		double dx = p.getX() - b.getX();//x-distance between ball and platform 
		if(dx < -70){
			r = false;
			dx = -(dx+140);
		}
		double dy = Math.abs(b.getY() - p.getY());//y-distance between ball and platform 
		double vy = b.getDy();
		
		
		//Score function is symmetric and domain connected.
		//Straight line between 2 points are inside the domain.
		//Therefore, we can learn int case and interpolate non-int result(between two int) with convex convernation.		
		int dx_int = (int)Math.floor(dx);
		double dx_dec = (10*dx - 10*dx_int)/10;
		double vector_x = (dx_dec-0.5)/Math.cos(45);
		int dy_int = (int)Math.floor(dy);
		double dy_dec = (10*dy - 10*dy_int)/10;
		double vector_y = (dy_dec-0.5)/Math.cos(45);
		int vy_int = (int)Math.floor(vy);
		double vy_dec = (10*vy - 10*vy_int)/10;
		
		//Temp method
		if(vy_dec > 0.5) vy_int++;
		
		double result1 = -1.0;
		double result2 = -1.0;
		double result3 = -1.0;
		double result4 = -1.0;
		if(database.get(dx_int + "," + dy_int + "," + vy_int) != null)	result1 = (double) database.get(dx_int + "," + dy_int + "," + vy_int);
		if(database.get((dx_int+1) + "," + dy_int + "," + vy_int) != null)	result2 = (double) database.get((dx_int+1) + "," + dy_int + "," + vy_int);
		if(database.get(dx_int + "," + (dy_int+1) + "," + vy_int) != null)	result3 = (double) database.get(dx_int + "," + (dy_int+1) + "," + vy_int);
		if(database.get(dx_int + "," + (dy_int+1) + "," + vy_int) != null)	result4 = (double) database.get(dx_int + "," + (dy_int+1) + "," + vy_int);
		double result = ((vector_x+0.5)*result4 + (vector_y+0.5)*result2 + (0.5-vector_x)*result1 + (0.5-vector_y)*result3)/2;
		if(!r) result = -result;
		return result;
	}
}
