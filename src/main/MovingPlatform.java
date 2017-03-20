package main;

import java.io.Serializable;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor4f;


class MovingPlatform extends CollidablePlatform implements Serializable {
	
    private int dy;
    private int dx;
    private int width, height;
    private double x, y, x1, x2;
    private int score = 0;

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
        dx = 15;
    }

    public MovingPlatform() {
        dy = 3;
        dx = -Constants.MOVING_PLATFORM_DX;
        x = 300;
        y = 300;
        width = 120;
        height = 40;
    }

    /*
     *Updates the position of the platform
     *@param game the game class object
     *@param ball the ball class object
     */
    void update(GameState game, double timeStep) {
        if (timeStep <= Constants.MIN_TIME_PER_FRAME) return;
        Ball ball = game.getBall();
        if (ball.gameOver()) return;

        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;

        // If the platform is offscreen, move it back on!
        if (y > game.getWindowHeight()) {
            Random r = game.random;
            y = -300;
            x = width + r.nextInt((game.getWindowWidth() - width)/2);
            x1 = x - width/2;
            x2 = x + width/2;
            return;
        }

        // If we've got the flying power-up, don't bother with collision.
        if (ball.getCountFlyPower() > 0f) {
            y += Constants.FLY_POWERUP_SPEED / deltaTime;
            game.score += Constants.FLY_POWERUP_SPEED / deltaTime;
            return;
        }

        // Otherwise, check for collision.
        checkForCollision(ball, deltaTime, x, y, width);

        if (ball.heightIsLocked()) {
            y -= ball.getDy() / deltaTime;
        }

        // Update platform Y position.
        y += dy / deltaTime;
        game.score += dy / deltaTime;

        // Update platform X position.
        if (x <= x1 || x >= x2) dx = -dx;
        x += dx / deltaTime;
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
        Rectangle.drawrectangle(verticesb, Menu.getRectangleModel());
    }
    
    public void setDx(int dx){
    	this.dy = dx;
    }
}