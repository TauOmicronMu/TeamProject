package main;

import java.util.ArrayList;

/**
 * Objective function for EDA
 * defined to be symmetric about the center of targeting platform
 * @author Ava
 *
 */
public class toScore {
	
	private int dx;//x-distance between platform and ball
	private int dy;//y-distance between platform and ball
	private final double g = 15;//gravity(can be variable)
	private double vy = 0;//initial y-velocity
	private final double radius = 20;//Ball radius(can be variable)
	private double platformW = 140;//Platform width(can be variable)
	
	/**
	 * Constructor
	 * @param x x-distance between platform and ball
	 * @param y y-distance between platform and ball
	 * @param vy current y-velocity of the ball
	 * @param platformW width of platform
	 */
	public toScore(int x, int y, int vy, double platformW){
		dx = x;
		dy = y;
		this.vy = vy;
	}
	
	/**
	 * Convert where the ball hit to score
	 * For example, if the ball miss the platform, return -1; hit the middle of platform, return 70; etc.
	 * @param sample list of sample x-velocity
	 * @return a list of score
	 */
	public ArrayList<Double> apply(ArrayList<Double> sample){
		ArrayList<Double> result = new ArrayList<Double>();
		// if the distance between ball and platform is too large
		if(vy*vy - 2*g*(dy+radius) < 0){
			return result;
		}
		
		//Calculate the total time of ball travel
		double t = 0;
		if(dy>=0){
			t = (vy + Math.sqrt(vy*vy - 2*g*(dy+radius)))/g;
		}else{
			t = (vy + Math.sqrt(vy*vy + 2*g*(dy+radius)))/g;
		}
		
		for(int i = 0; i < sample.size(); i++){
			double x_land = sample.get(i)*t;
			if(x_land < dx||x_land > dx+platformW){//miss the platform
				result.add(-1.0);
			}else if(x_land <= dx+platformW/2){//closer to center of platform get higher score
				result.add((x_land-dx));
			}else{
				result.add(((dx+platformW)-x_land));
			}
		}
		return result; 
	}
}
