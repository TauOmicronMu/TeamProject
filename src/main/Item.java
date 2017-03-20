package main;

import java.io.Serializable;
import java.util.Random;


class Item implements Serializable {

    private int x, y, dy, radius, type, highestPoint;
    private boolean noDraw;

    /*
     * Constructor for item class(PowerUps)
     * @param y the y position of the powerUp
     * @param type, the type of the powerUp
     */
    Item(int y, int type) {
        this.y = y;
        this.type = type;
        Random r = new Random();
        x = r.nextInt(600) + radius + 100;
        radius = 10;
        dy = Constants.ITEM_START_DY;
        highestPoint = 200;
        this.noDraw = false;
    }

    /*
     * Updates the position of the PowerUp
     */
    void update(GameState game, double timeStep) {
        if (timeStep == 0) return;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        Ball ball = game.getBall();

        // If we've got the flying power-up, don't bother with collision.
        if (ball.getCountFlyPower() > 0) {
            y += Constants.FLY_POWERUP_SPEED / deltaTime;
            game.score += Constants.FLY_POWERUP_SPEED / deltaTime;
            return;
        }

        // Otherwise, check for collision with the ball.
        checkForCollision(game);

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
     * Checks for collision between the ball and the powerUp
     * @param ball
     */
    private void checkForCollision(GameState game) {
        if(noDraw)return;

        Ball ball = game.getBall();
        double ballX = ball.getX();
        double ballY = ball.getY();
        int ballR = ball.getRadius();

        double a = x - ballX;
        double b = y - ballY;
        int collide = radius + ballR;
        //distance between object centers
        double c = Math.sqrt((double) (a * a) + (double) (b * b));
        if (c < collide) {
            performAction(game);
            noDraw = true;
        }

    }

    /*
     * Changes the behaviour of the ball depending on the powerUp
     * @param ball
     */
    public void performAction(GameState game) {
    }

    /**
     * Paints the powerUps
     */
    public void paint(Window window, boolean opponent) {
        if(noDraw) return;
        double[] vertices = createCircle(window.glScaleX(x, opponent, Screen.GAME), window.glScaleY(y), 0.2f, 0.02f);
        Model circle1 = new Model(vertices);
        circle1.render(vertices);
    }

    /**
     * Calculates all the points of the circumference of the circle
     *
     * @param posx the current x position of the centre of the powerUp
     * @param posy the current y position of the centre of the powerUp
     * @param posz the current z position of the centre of the powerUp
     * @return all the points of the circle
     */
    private static double[] createCircle(double posx, double posy, double posz, double radius) {
        int noSides = 360;
        int noVertices = noSides + 2;
        double doublePI = Math.PI * 2;

        int i = 1;
        double[] vertices = new double[noVertices * 3];
        vertices[0] = posx;
        vertices[1] = posy;
        vertices[2] = posz;
        for (int j = 3; j < (noVertices * 3); j = j + 3) {
            vertices[j] = posx + (radius * Math.cos(i * doublePI / noSides));
            vertices[j + 1] = posy + (radius * Math.sin(i * doublePI / noSides));
            vertices[j + 2] = posz;
            i++;
        }

        return vertices;
    }
    /*
     * Return the type of powerUp
     * 1 for GraveDown
     * 2 for GraveUp
     * 3 for FlyUp
     * 4 for Points
     */
    public int getType() {
        return type;
    }
    public void setDraw(boolean noDraw) {
        this.noDraw = noDraw;
    }
    public boolean getDraw() {
        return this.noDraw;
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
}
