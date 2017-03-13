package main;

import java.util.Random;

public class NormalPlatform extends Platform{

    NormalPlatform(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    public NormalPlatform() {
        dy = 2;
        x = 300;
        y = 300;
        width = 100;
        height = 20;
        highestPoint = 200;
    }   
    @Override
    void update(GameState game) {
       
        Ball ball = game.getBall();
        
        int[] Level = game.getLevel();
        int counter = game.getCounter();
        
        if(counter == Level.length){
        	game.setCounter(0);
        	counter = 0;
        }
             
        if(ball.gameOver()==false){
        	
        	if(ball.getDy() >0)
        		ball.setPermission(false);
            
            if(ball.getY() < highestPoint && ball.getDy() < 0){
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			game.score+=20;
                } else {
                	
                	ball.setPermission(true);
                	double newDx = ball.getDy() + ball.getGravity() + 0.1;
                	
                	if(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 < -2){
                		
                		y +=Math.abs(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1);
                		game.score+=ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 ;
                	}
                	else y += dy;
                	game.score += dy;
                	//System.out.println((ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1));
                     
                    
                checkForCollision(ball, game);
                }
//                if (y >= game.getWindowHeight()) {
//
////                    Random r = new Random();
////
////                   /* x = Level[counter];
////                    y = Level[counter+1];
////                    counter = counter + 2;*/
////
////                    y = -700;
////                    x = r.nextInt(game.getWindowWidth()-100);
//
//                    width = 100;
//                    height = 20;
//
//                }
            } else {
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			game.score+=20;
                } else {
                    y += dy;
                    game.score+= dy;
                checkForCollision(ball, game);
                }
//                if (y >= game.getWindowHeight()) {
//
//                    Random r = new Random();
//
////                    x = Level[counter];
////                    y = Level[counter+1];
////                    counter = counter + 2;
//                    y = -700;
//                    x = r.nextInt(game.getWindowWidth()-100);
//
//                    width = 100;
//                    height = 20;
//                }
            }
        }  else {
        	if(y>-100){
        		y-=6;
        	}
        }
    }
    
    
    @Override
    void paint(Window game) {
        super.paint(game);
    }    
}
