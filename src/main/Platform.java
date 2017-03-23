package main;

import java.io.Serializable;
import static org.lwjgl.opengl.GL11.glColor4f;


public class Platform implements Serializable {

    public int dy;
    public int width, height;
    public double x, y;
    private double highestPoint;
    public boolean noDraw;

    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y position of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    public Platform(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dy = Constants.PLATFORM_START_DY;
        highestPoint = 200;
        this.noDraw = false;
    }

    /**
     * Updates the position of the platform
     * @param game the game class object
     * @param timeStep The elapsed time in the last frame.
     */


    void update(GameState game, double timeStep) {
        //Every type of platforms has its own implementation of this method
    }

    public void checkForCollision(Ball ball, GameState game, double deltaTime) {
        if(noDraw) return;
            double ballX = ball.getX();
            double ballY = ball.getY();
            int radius = ball.getRadius();


            double ballBottom = ballY + radius;
            double rectTop = y;
            double rectLeft = x;


            // Todo: How is only half the platform colliding?
            double rectRight = x + width*2;

            // Check if the ball is above the platform *and* will be below
            // it after exactly one tick at the current framerate.
            if (ballBottom >= rectTop) return;
            double newBallBottom = ballBottom + ball.getDy() * deltaTime;
            if (newBallBottom <= rectTop) return;

            // Check the ball is aligned with the top of the platform.
            if (ballX+radius < rectLeft) return;
            if (ballX-radius > rectRight) return;

            // If the ball has collided with the top of the platform ~Tom
            //if (AudioEngine.isClient) AudioEngine.getInstance().playTrack(AudioEngine.BLOP); // Play the boing sound
            ball.setY(rectTop - radius);
            ball.setDy(-ball.getMaxSpeed());

    }
    /*
     * Draws the platform
     */
    void paint(Window game, boolean opponent) {
        if(noDraw) return;
        double scaledX = game.glScaleX(x, opponent, Screen.GAME);
        double scaledY = game.glScaleY(y);
        double widthGl = game.glScaleDistance(width);
        double heightGl = game.glScaleDistance(height);

        double[] verticesb = {scaledX, scaledY, 0.3f, scaledX, (scaledY - heightGl), 0.3f, (scaledX + widthGl), (scaledY - heightGl), 0.3f, (scaledX + widthGl), scaledY, 0.3f};
        glColor4f(1, 0, 0, 0);
        Rectangle.drawrectangle(verticesb, Menu.getRectangleModel(), true);
    }
    public void setDx(int dx){
        this.dy = dx;
    }
    public double getY() {
        return y;
    }
    public double getX(){ return x; }
    public void setNoDraw(boolean x){noDraw = x;}
}
