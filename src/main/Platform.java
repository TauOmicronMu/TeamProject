package main;

import java.io.Serializable;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor4f;


class Platform implements Serializable {

    private int dy;
    private int width, height;
    private double x, y;
    private double highestPoint;

    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y position of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dy = 3;
        highestPoint = 200;
    }

    public Platform() {
        dy = 3;
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
       
        if(!ball.gameOver()){
        	
        	if(ball.getDy() > 0)
        		ball.setPermission(false);
            
            if(ball.getY() < highestPoint && ball.getDy() < 0){
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			game.score+=20;
                } else {
                	
                	ball.setPermission(true);
                	double newDx = ball.getDy() + ball.getGravity() + 0.1;
                	
                	if(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 < -3){
                		
                		y +=Math.abs(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1);
                		game.score+=ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 ;
                	}
                	else y += dy;
                	game.score += dy;
                	//System.out.println((ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1));
                     
                    
                checkForCollision(ball);
                }
                if (y > game.getWindowHeight()) {

                    Random r = new Random();
                    y = -300;
                    x = r.nextInt(game.getWindowWidth()-140);
                    //x = game.getWindowWidth()/2;
                }
            } else {
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			game.score+=20;
                } else {
                    y += dy;
                    game.score+= dy;
                checkForCollision(ball);
                }
                if (y > game.getWindowHeight()) {

                    Random r = new Random();
                    y = -300;
                    x = r.nextInt(game.getWindowWidth()-100);
                    //x = game.getWindowWidth()/2;  
                }
            }
        }  else {
        	if(y>-100){
        		y-=6;
        	}
        }
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

                // If the ball has collided with the top of the platform ~Tom
                if(ball.getDy()>0){
                    // AudioEngine.getInstance().playTrack(AudioEngine.BOING); // Play the boing sound
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
