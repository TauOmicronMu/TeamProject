package main;

import java.io.Serializable;
import java.util.Random;

public class MovingVerticallyPlatform extends Platform{

	private double dy2;
	private double dy3;
	private double distance;
	private double distanceFinalValue;
	
	public 	MovingVerticallyPlatform(int x, int y, int width, int height, double distance) {
		super(x,y,width, height);
		this.distance = distance;
		this.distanceFinalValue = distance;
		this.dy2 = 2;
		this.dy3 = -1;
	}
	
	@Override
	public void update(GameState game){
        Ball ball = game.getBall();
        
        if(distance>0){
        	if(dy2>0){
        		distance = distance - dy2;
        	}else
        		distance = distance + dy2;
        	
        }else{
        	dy2=-dy2;
        	distance = distanceFinalValue;
        }
        
        if(ball.gameOver() == false){
        	if(ball.getDy() >0)
            	ball.setPermission(false);
            
            if(ball.getY() < highestPoint && ball.getDy() < 0){
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			score+=20;
       
                } else {
                	ball.setPermission(true);
                	
                	if(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 < -3){	
                		y +=Math.abs(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1)+ dy2;
                		score+=ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 + dy;
                	} else{
                		y += dy2;
                		score += dy2;
                	}          	
                	//System.out.println((ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1));
                      
                checkForCollision(ball, game);
                }
                if (y > game.getWindowHeight()) {

                    Random r = new Random();
                    y = -300;
                    x = 100 + r.nextInt(game.getWindowWidth()-200);                   	                  
                }
            } else {
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			score+=20;
                } else {
                    y += dy2;
                    score+= dy2;
                checkForCollision(ball, game);
                }
                if (y > game.getWindowHeight()) {
                	
                    Random r = new Random();
                    y = -300;
                    x = 100 + r.nextInt(game.getWindowWidth()-200);
                    //x = game.getWindowWidth()/2;              
                }
            }
        } else {
        	if(y>-100){
        		y-=6;
        	}
        }      
	}	
	@Override
	public void paint(Window game) {
		super.paint(game);
	}
}
