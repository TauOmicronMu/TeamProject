package main;

import java.io.Serializable;

import static org.lwjgl.opengl.GL11.glColor4f;


class PlatformOld implements Serializable {

    private int dy;
    private int width, height;
    private double x, y;
    private double highestPoint;
    public boolean isNull;

    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y position of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    PlatformOld(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dy = Constants.PLATFORM_START_DY;
        highestPoint = 200;
        this.isNull = false;
    }

    /*
     *Updates the position of the platform
     *@param game the game class object
     *@param ball the ball class object
     */
    void update(GameState game, double timeStep) {
        // Todo: Investigate magic numbers here.

        double timeStepPixels = 1/timeStep * Constants.TIME_STEP_COEFFICIENT;

        Ball ball = game.getBall();
        if (ball.gameOver()) return;
        if(ball.getDy() > 0) ball.setHeightLocked(false);

        if(ball.getY() < highestPoint && ball.getDy() < 0){
            if(ball.getCountFlyPower() > 0) {
                y += 20;
                game.score += 20;
            } else {
                ball.setHeightLocked(true);
                double val = dy * timeStepPixels + .5 * ball.getGravity() * timeStepPixels * timeStepPixels;
                if(val < -3){
                    y += Math.abs(val);
                    game.score += val;
                }
                else y += dy * timeStepPixels;
                game.score += dy * timeStepPixels;

                checkForCollision(ball);
            }

        } else {
            if(ball.getCountFlyPower() > 0){
                y += 20;
                game.score += 20;
            } else {
                y += dy * timeStepPixels;
                game.score += dy;
            checkForCollision(ball);
            }
        }
    }


    /*
     * Checks if any ball has collided with the platform
     * @param ball the ball object
     */
    private void checkForCollision(Ball ball) {
        if (isNull) return;

        double ballX = ball.getX();
        double ballY = ball.getY();
        int radius = ball.getRadius();

        double ballBottom = ballY + radius;
        double rectTop = y;
        double rectBottom = y + height;
        double rectLeft = x;
        double rectRight = x + width;

        if (ballBottom > rectBottom) return;
        if (ballY < rectTop) return;
        System.out.println("At correct height");
        if (ballX < rectLeft) return;
        if (ballX > rectRight) return;
        System.out.println("At correct width");
        if (ball.getDy() <= 0) return;
        System.out.println("At correct velocity!");

        // If the ball has collided with the top of the platform ~Tom
        // AudioEngine.getInstance().playTrack(AudioEngine.BOING); // Play the boing sound
        System.out.println("[INFO] Platform.checkForCollision : Collision! Setting ball's new dY.");
        // ball.setY(ball.getY() - radius);
        // ball.setDy(ball.getMaxSpeed());
    }

    /*
     * Draws the platform
     */
    void paint(Window game, boolean opponent) {
        if(isNull) return;
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
