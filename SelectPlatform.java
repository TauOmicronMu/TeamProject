package main;

import java.util.Arrays;

//Find the highest and reachable platform
public class SelectPlatform {
	
	private GameState game;
	private double vy = 0;//y-velocity
	//double min_vx = -5;//minimum x-velocity
	private double max_vx = 5;//maximum x-velocity
	private final double g = 15;//gravity
	private final double radius = 20;
	private Platform tempPlatform = new Platform(400,750,140,20);
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
//		if(game.selected != null){
//			if(!game.selected.isNull && Arrays.asList(ps).contains(game.selected)){
//				return game.selected;
//			}
//		}
		
//		for(int i = 0; i < ps.length; i++){
//			if(ps[i].checkCollisionAI(b, game)){
//				collision = true;
//				break;
//			}
//		}
//		if(!collision && game.selected != null){
//			return game.selected;
//		}
		
//		if(ps == null){
//			return tempPlatform;
//		}
		
		vy = b.getDy();
		//Find reachable
		//If the platform is not reachable, replace x,y by 0,800.
		Platform[] reachable = new Platform[ps.length];
		
		for(int i = 0; i < ps.length; i++){
			
			//Not showing platform
			if(ps[i].y < 0){
				reachable[i] = tempPlatform;
				continue;
			}
			
			if((ps[i] instanceof TrapPlatform)){//MovingHorizontallyPlatform
				reachable[i] = tempPlatform;
				continue;
			}
			
			double dx = ps[i].x - b.getX();
			double dy = ps[i].y - b.getY();
			double dt = vy*vy - 2*g*((-dy)+radius);
			
			//Platform too high
			if(dt < 0){
				reachable[i] = tempPlatform;
				continue;
			}
			
			//Ball cannot go to higher platform when it is going down
			if(vy>0 && ps[i].y<b.getY()){
				reachable[i] = tempPlatform;
				continue;
			}
			
			double t;
			if(vy<0){
				t = (vy + Math.sqrt(dt))/g;//total time of ball travel
			}else{
				t = (vy - Math.sqrt(dt))/g;
			}
			
			double max_x = Math.abs(t)*max_vx*10;
			
			if(dx >= 0){
				if(dx <= max_x){
					reachable[i] = ps[i];
				}else{
					reachable[i] = tempPlatform;
					continue;
				}
			}else{
				if(Math.abs(dx) <= max_x+140){
					reachable[i] = ps[i];
				}else{
					reachable[i] = tempPlatform;
					continue;
				}
					
			}
		}
		
		
		//Find highest
		//y is smaller, platform is higher
		Platform highest = tempPlatform;
		double middle = game.getWindowWidth()/2;
		
		for(int i = 0; i < reachable.length; i++){
			if(reachable[i].y < highest.y){
				highest = reachable[i];
			}else if(reachable[i].y == highest.y){
				if(Math.abs(reachable[i].x-middle) < Math.abs(highest.x-middle)){
					highest = reachable[i];
				}
			}
		}
		return highest;
	}
}
