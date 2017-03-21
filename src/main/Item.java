package main;

import java.io.Serializable;
import java.util.Random;

import static main.Circle.createCircle;


class Item implements Serializable {

    private int x, y, dy, radius, type, highestPoint;
    private static Text text = new Text();
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
     * Return the type of powerUp
     * 1 for GraveDown
     * 2 for GraveUp
     * 3 for FlyUp
     * 4 for add points
     */
    public int getType() {
        return type;
    }

    /*
     * Get method for Y
     * Updates the position of the PowerUp
     */
    void update(GameState game, double timeStep) {
        if (timeStep == 0) return;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        Ball ball = game.getBall();

        // If we've got the flying power-up, don't bother with collision.
        if (ball.getCountFlyPower() > 0) {
            y += Constants.FLY_POWERUP_SPEED * deltaTime;
            //game.score += Constants.FLY_POWERUP_SPEED * deltaTime;
            return;
        }


        // Otherwise, check for collision with the ball.
        checkForCollision(game);

        // If the ball's height is locked, we need to compensate by moving
        // the platform down at the speed the ball's meant to be rising.
        if (ball.heightIsLocked()) {
            y -= ball.getDy() * deltaTime;
        }

        // Update platform Y position.
        y += dy * deltaTime;
        game.score += dy * deltaTime;
    }
    /*
     * Checks for collision between the ball and the powerUp
     * @param ball
     */
    private void checkForCollision(GameState game) {
        if (noDraw) return;

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
    public void paint(Window window) {
        if (noDraw) return;
        double[] vertices = null;
        if (type == 1) {
            vertices = new double[]{window.glScaleX(this.x), window.glScaleY(this.y), 0.8f, (window.glScaleX(this.x) + 0.03f), (window.glScaleY(this.y) + 0.03f), 0.8f, (window.glScaleX(this.x) - 0.03f), (window.glScaleY(this.y) + 0.03f), 0.8f};
        }
        if (type == 2) {
            vertices = new double[]{window.glScaleX(this.x), window.glScaleY(this.y), 0.8f, (window.glScaleX(this.x) + 0.03f), (window.glScaleY(this.y) - 0.03f), 0.8f, (window.glScaleX(this.x) - 0.03f), (window.glScaleY(this.y) - 0.03f), 0.8f};
        }
        if (type == 4) {
            vertices = createCircle(window.glScaleX(this.x), window.glScaleY(this.y), 0.2f, 0.02f);
        }
        if (type == 3) {
            vertices = createCircle(window.glScaleX(this.x), window.glScaleY(this.y), 0.2f, 0.02f);
            Text.draw("+300", (float) (window.glScaleX(this.x)), (float) (window.glScaleY(this.y)), 0.3f);
        }

        if (type != 5) {
            Model triangles = new Model(vertices);

            triangles.render(vertices, false);
        }
    }

    public void paint(Window window, boolean opponent) {
        if (noDraw) return;
        double[] vertices = createCircle(window.glScaleX(x, opponent, Screen.GAME), window.glScaleY(y), 0.2f, 0.02f, 5);
        Model circle1 = new Model(vertices);
        circle1.render(vertices, false);

    }

    public int getY() {
        return y;
    }
}
