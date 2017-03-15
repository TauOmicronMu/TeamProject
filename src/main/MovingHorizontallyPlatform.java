package main;

import java.util.Random;

class MovingHorizontallyPlatform extends Platform{

    private int dx;
    private double x1, x2;

    MovingHorizontallyPlatform(double x, double y, int width, int height, double x1, double x2 ) {
        super(x, y, width, height);
        this.x1 = x1;
        this.x2 = x2;
        dx = -1;        
    }

//    public MovingHorizontallyPlatform(double xPosition, double yPosition, int platformWidth, int platformHeight, double v, double v1) {
//
//        dy = 2;
//        dx = -1;
//        x = 300;
//        y = 300;
//        width = 120;
//        height = 40;
//        highestPoint = 200;
//    }

    @Override
    void update(GameState game) {
      
        Ball ball = game.getBall();
        
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
                		y +=Math.abs(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1);
                		score+=ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 ;
                	} else{
                		y += dy;
                		score += dy;
                	}          	
                	//System.out.println((ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1));
                      
                checkForCollision(ball, game);
                }
//                if (y >= game.getWindowHeight()) {
//
//                    Random r = new Random();
//                    y = -700;
//                    x = 200 + r.nextInt(game.getWindowWidth()-400);
//                    x1 = x - 200;
//                    x2 = x + 200;
//
//                }
            } else {
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			score+=20;
                } else {
                    y += dy;
                    score+= dy;
                checkForCollision(ball, game);
                }
//                if (y >= game.getWindowHeight()) {
//
//                    Random r = new Random();
//                    y = -700;
//                    x = 100 + r.nextInt(game.getWindowWidth()-400);
//                    x1 = x - 200;
//                    x2 = x + 200;
//                    //x = game.getWindowWidth()/2;
//                }
            }
        } else {
        	if(y>-100){
        		y-=6;
        	}
        }
        
        if(x <= x1){
    		dx=-dx;
    		x += dx;
    	}else if(x >= x2){
    		dx=-dx;
    		x += dx;
    	}else {
    		x += dx;
    	}             
    }

    @Override
    void paint(Window game) {
        super.paint(game);
    }
}