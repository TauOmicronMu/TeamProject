package main;

import java.io.Serializable;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor4f;


class MovingPlatform implements Serializable {

    private int dy;
    private int dx;
    private int width, height;
    private double x, y, x1, x2;
    private int score = 0;
    private double highestPoint;


    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y position of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    MovingPlatform(int x, int y, int width, int height, int x1, int x2 ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.x1 = x1;
        this.x2 = x2;
        dy = 3;
        dx = -1;
        highestPoint = 200;
    }

    public MovingPlatform() {
        dy = 3;
        dx = -1;
        x = 300;
        y = 300;
        width = 120;
        height = 40;
        highestPoint = 200;
    }

    /*
     *Updates the position of the platform
     *@param game the game class object
     *@param ball the ball class object
     */
    void update(GameState game) {
        // Todo: Investigate magic numbers here.

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
                      
                checkForCollision(ball);
                }
                if (y > game.getWindowHeight()) {

                    Random r = new Random();
                    y = -300;
                    x = 100 + r.nextInt(game.getWindowWidth()-200);
                    x1 = x - 200;
                    x2 = x + 200;	
                    
                }
            } else {
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			score+=20;
                } else {
                    y += dy;
                    score+= dy;
                checkForCollision(ball);
                }
                if (y > game.getWindowHeight()) {
                	
                    Random r = new Random();
                    y = -300;
                    x = 100 + r.nextInt(game.getWindowWidth()-200);
                    x1 = x - 200;
                    x2 = x + 200;
                    //x = game.getWindowWidth()/2;              
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

    /*
     * Returns the score of the player
     */
    public int getScore() {
		return score;
	}
    /*
     * Checks if any ball has collided with the platform
     * @param ball the ball object
     */
    private void checkForCollision(Ball ball) {
        int ballX = (int) ball.getX();
        int ballY = (int) ball.getY();
        int radius = ball.getRadius();

        if (ballY + radius > y && ballY + radius < y + height) {
            //System.out.println("Y true");
            if (ballX > x && ballX < x + width) {

                //System.out.println("Collision");

                double newDy = ball.getGameDy();
                if(ball.getDy()>0){
                ball.setDy(newDy);
                }
                ball.setY(y - radius);
            }
        }
    }

    /*
     * Draws the platform
     */
    void paint(Window game, boolean opponent) {
        double scaledX = game.glScaleX(x, opponent, Screen.GAME);
        double scaledY = game.glScaleY(y);
        double widthGl = game.glScaleDistance(width);
        double heightGl = game.glScaleDistance(height);

        double[] verticesb = {scaledX, scaledY, 0.3f, scaledX, (scaledY - heightGl), 0.3f, (scaledX + widthGl), (scaledY - heightGl), 0.3f, (scaledX + widthGl), scaledY, 0.3f};
        glColor4f(1, 0, 0, 0);
        Rectangle.drawrectangle(verticesb);
    }
    
    public void setDx(int dx){
    	this.dy = dx;
    }
}