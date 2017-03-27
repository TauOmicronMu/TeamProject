package main;


class TrapPlatform extends Platform {
	
	TrapPlatform(double x, double y, int width, int height) {
        super(x, y, width, height);
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
                checkForCollision(ball, game);
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
            if (ballX > x && ballX < x + width) {

                double newDy = ball.getGameDy();
                if(ball.getDy()>0){
                    isNull = true;
                }            
            }
        }
    }

    void paint(Window game) {
        super.paint(game);
    }
}
