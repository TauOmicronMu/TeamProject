package main;

import java.io.Serializable;
import java.util.Random;

import static org.lwjgl.opengl.GL11.glColor4f;


class Item implements Serializable {

    private int x, y, dy, radius, type, highestPoint;
    private boolean isNull=false;


    /*
     * Constructor for item class(PowerUps)
     * @param y the y position of the powerUp
     * @param type, the type of the powerUp
     */
    Item(int y, int type) {
        this.y = y;
        this.type=type;
        Random r = new Random();
        x = r.nextInt(700) + radius + 100;
        radius = 10;
        dy = 3;
        highestPoint = 200;
    }

    public boolean getNull() {
        return isNull;
    }
    public void setNull(boolean x) {
        isNull = x;
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
    void update(GameState game) {
        
        Ball ball = game.getBall();
        
        if(ball.gameOver() == false) {
        	if(ball.getY() < highestPoint && ball.getDy() < 0){
            	
            	if(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1 < -3){
            		
            		y +=Math.abs(ball.getDy() * 0.1 + 0.5 * ball.getGravity() * 0.1 * 0.1);
            	} else {
            		y += dy;
            	}
            	
         		if(ball.getCountFlyPower() == 0)
         		 checkForCollision(game);
         		else y += 20;         
            	
           } else {
        	   y += dy;
        		if(ball.getCountFlyPower() == 0)
        		 checkForCollision(game);
        		else y += 20;
                }
            
        } else {
        	if(y>-100){
        		y-=6;
        	}
        }
       }
        

    /*
     * Checks for collision between the ball and the powerUp
     * @param ball
     */
    private void checkForCollision(GameState game) {
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
            isNull=true;
        }

    }

    /*
     * Changes the behaviour of the ball depending on the powerUp
     * @param ball
     */
    public void performAction(GameState game) {
    }

    /*
     * Paints the powerUps
     */
    public void paint(Window window) {
        if(isNull == false){
            double[] vertices = createCircle(window.glScaleX(x), window.glScaleY(y), 0.2f, 0.02f);
            Model circle1 = new Model(vertices);

            circle1.render(vertices);
        }

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
