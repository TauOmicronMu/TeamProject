package main;

import java.util.Arrays;
import java.util.Hashtable;

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
		
//        int ballX = (int) b.getX();
//        int ballY = (int) b.getY();
//        int radius = b.getRadius();
//        
//        if(!Arrays.asList(ps).contains(game.selected)){
//        	game.selected = null;
//        }
//        
//        if(game.selected != null){
//            if (ballY + radius > game.selected.y && ballY + radius < game.selected.y + game.selected.height) {
//                if (ballX > game.selected.x && ballX < game.selected.x + game.selected.width) {
//                	System.out.println("Select platform");
//        			SelectPlatform sp = new SelectPlatform(game);
//        			game.selected = sp.select(b,ps);
//                }
//            }
//        }else{
//        	System.out.println("Select platform");
//			SelectPlatform sp = new SelectPlatform(game);
//			game.selected = sp.select(b,ps);
//        }
//        
//        p = game.selected;
		
		double dx;
		double dy;
		double vy;
		
			SelectPlatform sp = new SelectPlatform(game);
			p = sp.select(b,ps);
			
			dx = p.x - b.getX();//x-distance between ball and platform 
			if(dx < -(p.width)/2){
				right = false;
				dx = -(dx+p.width);
			}
			dy = Math.abs(b.getY() - p.getY());//y-distance between ball and platform 
			vy = b.getDy();
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
		
		
		//Score function is symmetric and domain connected.
		//Straight line between 2 points are inside the domain.
		//Therefore, we can learn int(10) case and interpolate non-int result(between two int) with convex convernation.		
		int dx_int = (int) (dx-(dx%10));
		double dx_dec = (dx%10)/10;
		double vector_x = (dx_dec-0.5)/Math.cos(45);
		int dy_int = (int) (dy-(dy%10));
		double dy_dec = (dy%10)/10;
		double vector_y = (dy_dec-0.5)/Math.cos(45);
		int vy_int = (int)Math.floor(vy);
		double vy_dec = (10*vy - 10*vy_int)/10;
		
		//Temp method
		if(vy_dec > 0.5) vy_int++;
		
		double result1 = 0.0;
		double result2 = 0.0;
		double result3 = 0.0;
		double result4 = 0.0;
		if(database.get(dx_int + "," + dy_int + "," + vy_int) != null)	result1 = (double) database.get(dx_int + "," + dy_int + "," + vy_int);
		if(database.get((dx_int+10) + "," + dy_int + "," + vy_int) != null)	result2 = (double) database.get((dx_int+10) + "," + dy_int + "," + vy_int);
		if(database.get(dx_int + "," + (dy_int+10) + "," + vy_int) != null)	result3 = (double) database.get(dx_int + "," + (dy_int+10) + "," + vy_int);
		if(database.get((dx_int+10) + "," + (dy_int+10) + "," + vy_int) != null)	result4 = (double) database.get((dx_int+10) + "," + (dy_int+10) + "," + vy_int);
		double result = ((vector_x+0.5)*result4 + (vector_y+0.5)*result2 + (0.5-vector_x)*result1 + (0.5-vector_y)*result3)/2;
		if(result==0 && dx>300) result = 5.0;
		if(!right) result = -result;
//		if(vy<0) result = -result;
		int isb = (int)Math.round(result);
		double currentDx = b.getDx();
		
		if(currentDx > isb){
			return -1;
		}else if(currentDx < isb){
			return 1;
		}else{
			return 0;
		}
			
//		return isb;
	}
}
