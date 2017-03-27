package main;

import java.util.Arrays;
/**
 * Find the highest and reachable platform using GameState
 * @author ava
 *
 */

public class SelectPlatform {
	
	private GameState game;
	private double vy = 0;//initial y-velocity
	private double max_vx = 5;//maximum x-velocity
	private final double g = 15;//gravity
	private final double radius = 20;//radius of ball
	private Platform tempPlatform = new Platform(330,750,140,20);//If the platform is unreachable, instead of tempPlatform, so if the ball cannot find any reachable platform, it will go to middle.
	
	/**
	 * Select a platform, which the ball should land, from a list of platform
	 * @param game Game State
	 */
	public SelectPlatform(GameState game){
		this.game = game;
	}
	
	/**
	 * Filter out which platform is unreachable, and then choose the highest one
	 * @param b Ball
	 * @param ps Platform list
	 * @return a platform the ball should land
	 */
	public Platform select(Ball b, Platform[] ps){
		//Finding reachable platform
		vy = b.getDy();
		Platform[] reachable = new Platform[ps.length];
		
		for(int i = 0; i < ps.length; i++){
			
			//Filter out the platform which is too high and outside the window, don't know why it will happened
			//But it does happen at the beginning.
			if(ps[i].y < 0){
				reachable[i] = tempPlatform;
				continue;
			}
			
			//Filter out the TrapPlatform
			if((ps[i] instanceof TrapPlatform)){
				reachable[i] = tempPlatform;
				ps[i].height = 10;//According to the trap platform and other platform look the same in the old version, I change it size if AI detect it is trapPlatform
				continue;
			}
			
			double dx = ps[i].x - b.getX();//x-distance between ball and platform
			double dy = ps[i].y - b.getY();//y-distance between ball and platform
			double dt = vy*vy - 2*g*((-dy)+radius);
			
			//Platform too high to reach
			if(dt < 0){
				reachable[i] = tempPlatform;
				continue;
			}
			
			//When the ball is going down, filter the platform which is higher than the ball
			if(vy>0 && ps[i].y<b.getY()){
				reachable[i] = tempPlatform;
				continue;
			}
			
			double t;//total time of ball travel
			if(vy<0){
				t = (vy + Math.sqrt(dt))/g;
			}else{
				t = (vy - Math.sqrt(dt))/g;
			}
			
			//max_x is the far of x-distance it can go
			//if the dx is bigger that the max_x which means the platform is unreachable
			double max_x = Math.abs(t)*max_vx*15;
			if(dx >= 0){
				if(dx <= max_x){
					reachable[i] = ps[i];
				}else{
					reachable[i] = tempPlatform;
					continue;
				}
			}else{//handle the platform on left
				if(Math.abs(dx) <= max_x+140){
					reachable[i] = ps[i];
				}else{
					reachable[i] = tempPlatform;
					continue;
				}
					
			}
		}
		
		
		//Find highest one from the list of reachable platforms
		Platform highest = tempPlatform;
		double middle = game.getWindowWidth()/2;
		
		for(int i = 0; i < reachable.length; i++){
			if(reachable[i].y < highest.y){
				highest = reachable[i];
			}else if(reachable[i].y == highest.y){
				//if the y-position of two platforms are the same, choose the closer to the middle one.
				if(Math.abs(reachable[i].x-middle) < Math.abs(highest.x-middle)){
					highest = reachable[i];
				}
			}
		}
		return highest;
	}
}
