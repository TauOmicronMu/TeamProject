package main;

import java.io.Serializable;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor4f;


class Item implements Serializable {

    private int x, y, dy, radius;
    private PowerUpShader pshader;

    /*
     * Constructor for item class(PowerUps)
     * @param y the y position of the powerUp
     */
    Item(int y) {
        this.y = y;
        Random r = new Random();
        x = r.nextInt(700) + radius + 100;
        radius = 10;
        dy = 3;
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
    void update(GameState game) {
        int height = game.getWindowHeight();
        int width = game.getWindowWidth();
        Ball ball = game.getBall();

        y += dy;

        checkForCollision(ball, game);
        if (y > height - radius) {
            Random r = new Random();
            y = -height - 100 - r.nextInt(300);
            x = r.nextInt(width - 100) + radius + 100;

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
            x = 0;
            y = game.getWindowHeight() + 100;
        }

    }

    /*
     * Changes the behaviour of the ball depending on the powerUp
     * @param ball
     */
    private void performAction(Ball ball) {
    }

    
    /*
     * Sets the shader file to use depending on the type of powerup
     */
    private String checkAction()
    {
    	return "shaders/pshader.vs";
    }
    /*
     * Paints the powerUps
     */
    void paint(Window window) {
    	String filename = checkAction();
    	pshader = new PowerUpShader(filename);
    	pshader.bind();
        double[] vertices = createCircle(window.glScaleX(x), window.glScaleY(y), 0.2f, 0.02f);
        Model circle1 = new Model(vertices);

        circle1.render(vertices);
        pshader.stop();
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

            glColor4f(0, 0, 1, 0);

            vertices[j] = posx + (radius * Math.cos(i * doublePI / noSides));
            vertices[j + 1] = posy + (radius * Math.sin(i * doublePI / noSides));
            vertices[j + 2] = posz;
            i++;
        }

        return vertices;
    }
}
