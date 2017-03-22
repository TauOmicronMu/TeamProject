package ai;

import main.Ball;
import main.Constants;
import main.GameState;
import main.Platform;

import java.util.ArrayList;
import java.util.Arrays;

//Find the highest and reachable platform
public class SelectPlatform {
	
	private GameState game;
	private double vy = 0;//y-velocity
	//double min_vx = -5;//minimum x-velocity
	private double max_vx = Constants.MAX_SPEED;//maximum x-velocity
	private final double g = Constants.GRAVITY;//gravity
	private final double radius = Constants.ITEM_RADIUS;
	private boolean collision = false;
	
	/**
	 * Select a platform, which the ball should land, from a list of platform
	 * @param game Game State
	 */
	public SelectPlatform(GameState game){
		this.game = game;
	}
	
	/**
	 * Filter which platform is reachable, and then choose the highest one
	 * @param b Ball
	 * @param ps Platform list
	 * @return a platform the ball should land
	 */
	public Platform select(Ball b, Platform[] ps){
		vy = b.getDy();
		//Find reachable
		//If the platform is not reachable, replace x,y by 0,800.
		ArrayList<Platform> reachable = new ArrayList<>();
		
		for(int i = 0; i < ps.length; i++){
			
			//Not showing platform
			if(ps[i].y < 0){
				continue;
			}

			if (ps[i] instanceof main.TrapPlatform) {
			    continue;
            }

			double dx = ps[i].x - b.getX();
			double dy = ps[i].y - b.getY();
			double dt = vy*vy - 2*g*((-dy)+radius);
			
			//Platform too high
			if(dt < 0) continue;
			
			//Ball cannot go to higher platform when it is going down
			if(vy>0 && ps[i].y<b.getY()){ continue; }
			
			double t;
			if(vy<0){
				t = (vy + Math.sqrt(dt))/g;//total time of ball travel
			}else{
				t = (vy - Math.sqrt(dt))/g;
			}

			// The maximum distance the ball can travel in the given time, t
			double max_x = t*max_vx + b.getDx();

			if(dx >= 0){
				if(dx <= max_x) {
                    reachable.add(ps[i]);
                }
			}else{
				if(Math.abs(dx) <= max_x+140) {
                    reachable.add(ps[i]);
                }
			}
		}
		
		
		//Find highest
		//y is smaller, platform is higher
        /*if(reachable.isEmpty()) return new Platform(Constants.WINDOW_WIDTH/4 - Constants.PLATFORM_WIDTH,
                                                    Constants.WINDOW_HEIGHT/4,
                                                      Constants.PLATFORM_WIDTH,
                                                      Constants.PLATFORM_HEIGHT);
        */

        if(reachable.isEmpty()) return null;

        Platform highest = reachable.get(0);
		double middle = game.getWindowWidth()/2;
		
		for(int i = 0; i < reachable.size(); i++){
			if(reachable.get(i).y < highest.y){
				highest = reachable.get(i);
			}else if(reachable.get(i).y == highest.y){
				if(Math.abs(reachable.get(i).x-middle) < Math.abs(highest.x-middle)){
					highest = reachable.get(i);
				}
			}
		}
        return highest;
	}
}
