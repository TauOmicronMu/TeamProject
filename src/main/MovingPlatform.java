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
        dy = Constants.PLATFORM_START_DY; // was 3
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
    void update(GameState game, double timeStep) {
        // Todo: Investigate magic numbers here.

        double timeStepPixels = timeStep * Constants.TIME_STEP_COEFFICIENT;

        Ball ball = game.getBall();

        if (y <= game.getWindowHeight() && ball.getCountFlyPower() == 0) {
            checkForCollision(ball);
        }

        if(!ball.gameOver()){
        	if(ball.getDy() >0)
            	ball.setPermission(false);
            
            if(ball.getY() < highestPoint && ball.getDy() < 0){
            	if(ball.getCountFlyPower() >0){
        			y+=20;
        			score+=20;
                } else {
                    ball.setPermission(true);
                    double eqn = dy * timeStepPixels + .5 * ball.getGravity() * timeStepPixels * timeStepPixels;

                	if(eqn < -3){
                		y +=Math.abs(eqn);
                		score += eqn;
                	} else{
                		y += dy * timeStepPixels;
                		score += dy * timeStepPixels;
                	}          	

                //checkForCollision(ball);
                }
                if (y > game.getWindowHeight()) {

                    Random r = game.random;
                    y = -300;
                    x = 100 + r.nextInt(game.getWindowWidth()-200);
                    x1 = x - 200;
                    x2 = x + 200;	
                    
                }
            } else {
            	if(ball.getCountFlyPower() >0){
        			y += 20;
        			score += 20;
                } else {
                    y += dy * timeStepPixels;
                    score += dy * timeStepPixels;
                //checkForCollision(ball);
                }
                if (y > game.getWindowHeight()) {
                	
                    Random r = game.random;
                    y = -300;
                    x = 100 + r.nextInt(game.getWindowWidth()-200);
                    x1 = x - 200;
                    x2 = x + 200;
                    //x = game.getWindowWidth()/2;              
                }
            }
        } else {
        	if(y>-100){
        		y -= 6;
        	}
        }
        
        if (x <= x1) {
    		dx =- dx;
    		x += dx;
    	} else if(x >= x2) {
    		dx = -dx;
    		x += dx;
    	} else {
    		x += dx;
    	}
    }

    /*
     * Checks if any ball has collided with the platform
     * @param ball the ball object
     */
    private void checkForCollision(Ball ball) {
        double ballX = ball.getX();
        double ballY = ball.getY();
        int radius = ball.getRadius();

        double ballBottom = ballY + radius;
        double rectTop = y;
        double rectLeft = x;
        double rectRight = x + width;

        // Check the ball's height is colliding with the top of the platform.
        double tolerance = Math.abs(ball.getDy());  // pixels
        if (ballBottom < rectTop - tolerance) return;
        if (ballBottom > rectTop + tolerance) return;

        // Check the ball is aligned with the top of the platform.
        if (ballX < rectLeft) return;
        if (ballX > rectRight) return;

        // Check that the ball is moving downwards.
        if (ball.getDy() < 0) return;


        // If the ball has collided with the top of the platform ~Tom
        // AudioEngine.getInstance().playTrack(AudioEngine.BOING); // Play the boing sound
        System.out.println("[INFO] Platform.checkForCollision : Collision! Setting ball's new dY.");
        ball.setY(rectTop - radius);
        ball.setDy(-ball.getMaxSpeed());
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