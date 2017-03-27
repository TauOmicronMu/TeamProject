package main;

import java.util.Arrays;
import java.util.Hashtable;

/**
 * Input game state and output move
 * @author ava
 *
 */
public class readData {
	
	public readData(){
		
	}
	
	private Platform p;
	private boolean right = true;//platform on right hand side
	
	/**
	 * Input a ball and a list of platform, then return a x-velocity
	 * @param b Ball
	 * @param ps Platform list
	 * @return x-velocity
	 */
	public double AI(Ball b, Platform[] ps, Hashtable database,GameState game){
		
		double dx;//x-distance between ball and platform 
		double dy;//y-distance between ball and platform
		double vy;//y-velocity of ball
		
		//Select the best platform
		SelectPlatform sp = new SelectPlatform(game);
		p = sp.select(b,ps);
		
		//Handle the platform on left
		dx = p.x - b.getX();
		if(dx < -(p.width)/2){
			right = false;
			dx = -(dx+p.width);
		}
		
		dy = Math.abs(b.getY() - p.getY()); 
		vy = b.getDy();
		
		//If the ball is on the platform currently, stop to adjust the x-velocity anymore
		//Because the AI will always hit the center of platform, which is unnecessary.
		if((!(p instanceof MovingHorizontallyPlatform)) && vy>=-10 && b.getX()>p.x+10 && b.getX()<p.x+p.width-10 && b.getY()<p.y){
			double currentDx = b.getDx();
			double isb = 0.0;
			if(currentDx > isb){
				return -1;
			}else if(currentDx < isb){
				return 1;
			}else{
				return 0;
			}
		}
		
		
		//Score function of EDA is symmetric and domain connected.
		//Straight line between 2 points are inside the domain.
		//Therefore, we can learn int case and interpolate non-int result(between two int) with convex interpolation.
		//Also, because we only pre-train the +10 in valued of x and y distance, it can be deal with convex interpolation too.
		//And for the method of the convex interpolation, welcome to ask me or some further detail is on the report.
		int dx_int = (int) (dx-(dx%10));
		double dx_dec = (dx%10)/10;
		int dy_int = (int) (dy-(dy%10));
		double dy_dec = (dy%10)/10;
		int vy_int = (int)Math.floor(vy);
		double vy_dec = (10*vy - 10*vy_int)/10;
		
		double result1 = 0.0;
		double result2 = 0.0;
		double result3 = 0.0;
		double result4 = 0.0;
		double result5 = 0.0;
		double result6 = 0.0;
		double result7 = 0.0;
		double result8 = 0.0;

		if(database.get(dx_int + "," + dy_int + "," + vy_int) != null) result1 = (double) database.get(dx_int + "," + dy_int + "," + vy_int);
		if(database.get((dx_int+10) + "," + dy_int + "," + vy_int) != null)	result2 = (double) database.get((dx_int+10) + "," + dy_int + "," + vy_int);
		if(database.get((dx_int+10) + "," + (dy_int+10) + "," + vy_int) != null) result3 = (double) database.get((dx_int+10) + "," + (dy_int+10) + "," + vy_int);
		if(database.get(dx_int + "," + (dy_int+10) + "," + vy_int) != null)	result4 = (double) database.get(dx_int + "," + (dy_int+10) + "," + vy_int);
		if(database.get(dx_int + "," + dy_int + "," + (vy_int+1)) != null) result5 = (double) database.get(dx_int + "," + dy_int + "," + (vy_int+1));
		if(database.get((dx_int+10) + "," + dy_int + "," + (vy_int+1)) != null)	result6 = (double) database.get((dx_int+10) + "," + dy_int + "," + (vy_int+1));
		if(database.get((dx_int+10) + "," + (dy_int+10) + "," + (vy_int+1)) != null) result7 = (double) database.get((dx_int+10) + "," + (dy_int+10) + "," + (vy_int+1));
		if(database.get(dx_int + "," + (dy_int+10) + "," + (vy_int+1)) != null)	result8 = (double) database.get(dx_int + "," + (dy_int+10) + "," + (vy_int+1));
			
		double result = ((100-10*10*vy_dec/3)*(result1+result2+result3+result4)/4 +
						(100-10*10*(1-vy_dec)/3)*(result5+result6+result7+result8)/4 +
						(100-1*10*dy_dec/3)*(result1+result2+result5+result6)/4 +
						(100-1*10*(10-dy_dec)/3)*(result3+result4+result7+result8)/4 +
						(100-10*1*dx_dec/3)*(result1+result4+result5+result8)/4 +
						(100-10*1*(10-dx_dec)/3)*(result2+result3+result6+result7)/4)/(600-100);

		
		if(result==0 && dx>300) result = 5.0;//For safety only,the result will not be 0 normally
		
		if(!right) result = -result;
		
		//The x-velocity of ball can be set directly, and it will be more accurate
		//However, the move is changed by 1
		//Thus, if the current x-velocity is bigger than the result, -1(Left);
		//smaller than the result, +1(Right);
		//equal to the result, 0(No move).
		int isb = (int)Math.round(result);
		int currentDx = (int)Math.round(b.getDx());
		if(currentDx > isb){
			return -1;
		}else if(currentDx < isb){
			return 1;
		}else{
			return 0;
		}
	}
}
