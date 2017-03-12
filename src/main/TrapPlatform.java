package main;

import java.util.Random;


class TrapPlatform extends Platform {
	
	TrapPlatform(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public TrapPlatform() {
        dy = 3;
        x = 300;
        y = 300;
        width = 140;
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
        			score+=20;
                } else {
                	
                	ball.setPermission(true);
                	double newDx = ball.getDy() + ball.getGravity() + 0.1;
                	
                	if(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 < -3){
                		
                		y +=Math.abs(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1);
                		score+=ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 ;
                	}
                	else y += dy;
                	score += dy;
                	//System.out.println((ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1));
                     
                    
                checkForCollision(ball, game);
                }
                if (y > game.getWindowHeight()) {

                    Random r = new Random();
                    
                    /*x = Level[counter];
                    y = Level[counter+1];
                    counter = counter + 2;*/
                    
                    y = -300;
                    x = r.nextInt(game.getWindowWidth()-140);
                    
                    
                    width = 140;
                    height = 20;
                    
                    
                }
            } else {
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			score+=20;
                } else {
                    y += dy;
                    score+= dy;
                checkForCollision(ball, game);
                }
                if (y > game.getWindowHeight()) {

                    Random r = new Random();
                    
                    /*x = Level[counter];
                    y = Level[counter+1];
                    counter = counter + 2;*/
                    
                    y = -300;
                    x = r.nextInt(game.getWindowWidth()-100);
                    
                    width = 140;
                    height = 20;                
                }
            }
        }  else {
        	if(y>-100){
        		y-=6;
        	}
        }
    }
    
    @Override
    public void checkForCollision(Ball ball,GameState game) {
        int ballX = (int) ball.getX();
        int ballY = (int) ball.getY();
        int radius = ball.getRadius();

        
        
        if (ballY + radius > y && ballY + radius < y + height) {
            //System.out.println("Y true");
            if (ballX > x && ballX < x + width) {

                //System.out.println("Collision");
            	  Random r = new Random();
                  
            		
                double newDy = ball.getGameDy();
                if(ball.getDy()>0){               	
                	y = -300;
                    x = r.nextInt(game.getWindowWidth()-140);
                                        
                    width = 140;
                    height = 20;
                }            
            }
        }
    }

    void paint(Window game) {
        super.paint(game);
    }
}
