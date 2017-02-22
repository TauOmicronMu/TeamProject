package main;

import java.io.Serializable;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor4f;


class Platform implements Serializable {

    private int dx;
    private int x, y, width, height;
    
    private boolean tracksSet = false;
    private AudioEngine audio = new AudioEngine();
    
    private final String bounceFP = "boing.wav";
   
    private int bounce; // Reference to the bounce sound.

    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y positiosn of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    Platform(int x, int y, int width, int height) {
    	if(!tracksSet) {
    		setTracks();
    		this.tracksSet = true;
    	}
    	
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dx = 3;    
    }

    public Platform() {
    	if(!tracksSet) {
    		setTracks();
    		this.tracksSet = true;
    	}
    	
        dx = 3;
        x = 300;
        y = 300;
        width = 120;
        height = 40;
    }
    
    void setTracks() {
    	this.bounce = audio.createTrack(this.bounceFP);
    }

    /*
     *Updates the position of the platform
     *@param game the game class object
     *@param ball the ball class object
     */
    void update(GameState game) {
        // Todo: Investigate magic numbers here.

        Ball ball = game.getBall();
        if (ball.getAgility() > 3) {
            y += ball.getAgility();
        } else {
            y += dx;
        }
        checkForCollision(ball);
        if (y > game.getWindowHeight()) {

            // Random r = new Random();
            y = 40;
            // x = game.getWindowWidth() - r.nextInt(game.getWindowWidth() - 100);
            // x = game.getWindowWidth()/2;

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

            	audio.playTrack(bounce);
            	
                double newDy = ball.getGameDy();
                ball.setDy(newDy);
                ball.setY(y - radius);
            }
        }
    }

    /*
     * Draws the platform
     */
    void paint(Window game) {
        double scaledX = game.glScaleX(x);
        double scaledY = game.glScaleY(y);
        double widthGl = game.glScaleDistance(width);
        double heightGl = game.glScaleDistance(height);

        double[] verticesb = {scaledX, scaledY, 0.3f, scaledX, (scaledY - heightGl), 0.3f, (scaledX + widthGl), (scaledY - heightGl), 0.3f, (scaledX + widthGl), scaledY, 0.3f};
        Rectangle.drawrectangle(verticesb);
    }
}
