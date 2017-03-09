package main;
//Find the highest and reachable platform
public class SelectPlatform {
	
	private GameState game;
	private double vy = 90;//y-velocity
	//double min_vx = -5;//minimum x-velocity
	private double max_vx = 50;//maximum x-velocity
	private final double g = 15;//gravity
	private final double radius = 20;
	private Platform tempPlatform = new Platform(0,800,120,40);
	
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
		Platform[] reachable = new Platform[ps.length];
		
		for(int i = 0; i < ps.length; i++){
			double dx = ps[i].getX() - b.getX();
			double dy = Math.abs(b.getY() - ps[i].getY());
			if(dy > 250){
				reachable[i] = tempPlatform;
				System.out.println("higher 250");
				continue;
			}
			
			double t = (vy + Math.sqrt(vy*vy - 2*g*(dy+radius)))/g;//total time of ball travel
			double max_x = t*max_vx;

			if(Math.abs(dx) <= max_x + 120){
				reachable[i] = ps[i];
			}else{
				reachable[i] = tempPlatform;
				System.out.println("More than x-max");
			}
		}
		
		
		//Find highest
		//y is smaller, platform is higher
		Platform highest = reachable[0];
		int counter2 = 0;
		double middle = game.getWindowWidth()/2;
		
		while(reachable != null && counter2 < reachable.length){
			System.out.println("Current y: " + reachable[counter2].getY());
			if(reachable[counter2].getY() < highest.getY()){
				highest = reachable[counter2];
			}else if(reachable[counter2].getY() == highest.getY()){
				
				if(Math.abs(reachable[counter2].getX()-middle) < Math.abs(highest.getX()-middle)){
					highest = reachable[counter2];
				}
			}
			counter2++;
		}
		
		System.out.println("Platform: " + highest.getX() + "," + highest.getY());
		return highest;
	}
}
