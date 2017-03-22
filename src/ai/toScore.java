package ai;

import java.util.ArrayList;

public class toScore {
	
	private int dx;//x-distance between platform and ball
	private int dy;//y-distance between platform and ball
	private final double g = 15;//gravity(can be variable)
	private double vy = 0;//y-velocity
//	private final double xFriction = 0.9;
	private final double radius = 20;//Ball radius(can be variable)
	private final double platformW = 140;//Platform width(can be variable)
	
	/**
	 * 
	 * @param x x-distance between platform and ball
	 * @param y y-distance between platform and ball
	 * @param vy current y-velocity of the ball
	 */
	public toScore(int x, int y, int vy){
		dx = x;
		dy = y;
		this.vy = vy;
	}
	
	/**
	 * Convert where the ball hit to score, if the ball miss the platform, return -1; hit the middle of platform, return 70; etc.
	 * @param sample list of sample x-velocity generated by distribution
	 * @return a list of score
	 */
	public ArrayList<Double> apply(ArrayList<Double> sample){
		ArrayList<Double> result = new ArrayList<Double>();
		if(vy*vy - 2*g*(dy+radius) < 0){
			return result;
		}
		double t = (vy + Math.sqrt(vy*vy - 2*g*(dy+radius)))/g;//total time of ball travel
		for(int i = 0; i < sample.size(); i++){
			double x_land = sample.get(i)*t;
			if(x_land < dx||x_land > dx+platformW){
				result.add(-1.0);
			}else if(x_land <= dx+platformW/2){
				result.add((x_land-dx));
			}else{
				result.add(((dx+platformW)-x_land));
			}
		}
		return result; 
	}
}
