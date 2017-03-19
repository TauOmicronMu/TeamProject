package main;

import java.io.Serializable;
import static org.lwjgl.opengl.GL11.glColor4f;

class Platform implements Serializable {

    public int dy;
    public int width, height;
    public double x;
	public double y;
    public int score = 0;
    public double highestPoint;
    public boolean isNull;

    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y position of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    Platform(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dy = 2;
        highestPoint = 200;
        this.isNull= false;
    }

    /*
     *Updates the position of the platform
     *@param game the game class object
     *@param ball the ball class object
     */
    void update(GameState game) {
        //Every type of platforms has its own implementation of this method
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
    public void checkForCollision(Ball ball, GameState game) {
        if(isNull == false) {
            int ballX = (int) ball.getX();
            int ballY = (int) ball.getY();
            int radius = ball.getRadius();

            if (ballY + radius > y && ballY + radius < y + height) {
                if (ballX > x && ballX < x + width) {
                    double newDy = ball.getGameDy();
                    if(ball.getDy()>0){
                        AudioEngine.getInstance().playTrack(AudioEngine.BOING);
                        ball.setDy(newDy);
                    }
                    ball.setY(y - radius);
                }
            }
        }
    }

    /*
     * Draws the platform
     */
    void paint(Window game) {
        if(isNull == false) {
            double scaledX = game.glScaleX(x);
            double scaledY = game.glScaleY(y);
            double widthGl = game.glScaleDistance(width);
            double heightGl = game.glScaleDistance(height);

            double[] verticesb = {scaledX, scaledY, 0.3f, scaledX, (scaledY - heightGl), 0.3f, (scaledX + widthGl), (scaledY - heightGl), 0.3f, (scaledX + widthGl), scaledY, 0.3f};
            glColor4f(1, 0, 0, 0);
            Rectangle.drawrectangle(verticesb,Menu.getRectangleModel());
        }
    }

    public void setDx(int dx){
    	this.dy = dx;
    }
    public double getY() {
        return y;
    }
    public boolean getNull() {
        return isNull;
    }
    public void setNull(boolean x) {
        isNull = x;
    }
}

