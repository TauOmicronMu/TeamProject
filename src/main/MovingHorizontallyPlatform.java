package main;


class MovingHorizontallyPlatform extends Platform{

    private int dx;
    private double x1, x2;

    MovingHorizontallyPlatform(double x, double y, int width, int height, double x1, double x2 ) {
        super(x, y, width, height);
        this.x1 = x1;
        this.x2 = x2;
        dx = -1;        
    }

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