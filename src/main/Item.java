package main;

import java.io.Serializable;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor4f;


class Item implements Serializable {

    private int x, y, dy, radius, type, highestPoint;

    /*
     * Constructor for item class(PowerUps)
     * @param y the y position of the powerUp
     * @param type, the type of the powerUp
     */
    Item(int x, int y, int type) {
        this.y = y;
        this.type=type;
        radius = Constants.ITEM_RADIUS;
        dy = Constants.ITEM_START_DY;
        highestPoint = 200;
    }

    /*
     * Return the type of powerUp
     * 1 for GraveDown
     * 2 for GraveUp
     * 3 for FlyUp
     */
    public int getType() {
		return type;
	}
    /*
     * Get method for Y
     */
    int getY() {
        return y;
    }

    /*
     * Get method for X
     */
    public int getX() {
        return x;
    }

    /*
     * Set method for x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /*
     * Updates the position of the PowerUp
     */
    void update(GameState game, double timeStep) {
        if (timeStep == 0) return;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        Ball ball = game.getBall();

        if (ball.gameOver()) {
            if (y > -100) y -= 6;
            return;
        }

        if (ball.getY() < highestPoint && ball.getDy() < 0) {
            double deltaY = ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1;
            if(deltaY < -3){
                y += Math.abs(deltaY);
            } else {
                y += dy * deltaTime;
            }
            if(ball.getCountFlyPower() == 0) checkForCollision(ball, game);
            else y += 20;
        } else {
            y += dy * deltaTime;
            if(ball.getCountFlyPower() == 0) checkForCollision(ball, game);
            else y += 20;
        }
    }
        

    /*
     * Checks for collision between the ball and the powerUp
     * @param ball
     */
    private void checkForCollision(Ball ball, GameState game) {
        double ballX = ball.getX();
        double ballY = ball.getY();
        int ballR = ball.getRadius();

        double a = x - ballX;
        double b = y - ballY;
        int collide = radius + ballR;
        //distance between object centers
        double c = Math.sqrt((double) (a * a) + (double) (b * b));
        if (c < collide) {
            performAction(ball);
            y = game.getWindowHeight() + 100;
        }

    }

    /*
     * Changes the behaviour of the ball depending on the powerUp
     * @param ball
     */
    public void performAction(Ball ball) {
    }

    /**
     * Paints the powerUps
     */
    public void paint(Window window, boolean opponent) {
        double[] vertices = Circle.createCircle(window.glScaleX(x, opponent, Screen.GAME), window.glScaleY(y), 0.2f, 0.02f, 4);
        Model circle1 = new Model(vertices);
        circle1.render(vertices, false);
    }
}
