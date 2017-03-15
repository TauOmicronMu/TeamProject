package main;


public class NormalPlatform extends Platform{

    NormalPlatform(double x, double y, int width, int height) {
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
                checkForCollision(ball, game);
                }
            } else {
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			game.score+=20;
                } else {
                    y += dy;
                    game.score+= dy;
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
    void paint(Window game) {
        super.paint(game);
    }    
}
