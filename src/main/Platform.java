package main;

import java.io.Serializable;

import static org.lwjgl.opengl.GL11.glColor4f;


class Platform extends CollidablePlatform implements Serializable {

    private int dy;
    private int width, height;
    private float x, y;
    private float highestPoint;
    public boolean isNull;

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
        dy = Constants.PLATFORM_START_DY;
        highestPoint = 200;
        this.isNull = false;
    }

    /**
     * Updates the position of the platform
     * @param game the game class object
     * @param timeStep The elapsed time in the last frame.
     */
    void update(GameState game, float timeStep) {
        if (timeStep == 0) return;
        float deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        Ball ball = game.getBall();

        // If platform is offscreen, move it back on!
        if (y > game.getWindowHeight()) {
            y = -300;
            x = game.random.nextInt(game.getWindowWidth() - 140);
            return;
        }

        // If we've got the flying power-up, don't bother with collision.
        if (ball.getCountFlyPower() > 0) {
            y += Constants.FLY_POWERUP_SPEED / deltaTime;
            game.score += Constants.FLY_POWERUP_SPEED / deltaTime;
            return;
        }

        // Otherwise, check for collision with the ball.
        checkForCollision(ball, deltaTime, x, y, width);

        // If the ball's height is locked, we need to compensate by moving
        // the platform down at the speed the ball's meant to be rising.
        if (ball.heightIsLocked()) {
            y -= ball.getDy() / deltaTime;
        }

        // Update platform Y position.
        y += dy / deltaTime;
        game.score += dy / deltaTime;
    }


    /*
     * Draws the platform
     */
    void paint(Window game, boolean opponent) {
        if(isNull) return;
        float scaledX = game.glScaleX(x, opponent, Screen.GAME);
        float scaledY = game.glScaleY(y);
        float widthGl = game.glScaleDistance(width);
        float heightGl = game.glScaleDistance(height);

        float[] verticesb = {scaledX, scaledY, 0.3f, scaledX, (scaledY - heightGl), 0.3f, (scaledX + widthGl), (scaledY - heightGl), 0.3f, (scaledX + widthGl), scaledY, 0.3f};
        glColor4f(1, 0, 0, 0);
        Rectangle.drawrectangle(verticesb);
    }
    
    public void setDx(int dx){
    	this.dy = dx;
    }
}
