package main;

import java.io.Serializable;

public class Ball implements Serializable {

    private double x;
    private double y;
    private double dx = 0;
    private double dy = 0;
    private int radius = 20;
    private double gravity = Constants.GRAVITY; // was 15
    private static final double energyloss = 1;
    private static final double xFriction = 0.9;
    private double gameDy = -90;
    private int agility = Constants.AGILITY; // was 1
    private int maxSpeed = Constants.MAX_SPEED; // was 5
    private int countFlyPower = 0;
    private int score;
    private boolean permission = false;
    private boolean gameOver = false;


    Ball(double i, double j) {
        x = i;
        y = j;
    }


    void moveRight() {
    	if (gameOver) return;
        if (dx + agility < maxSpeed) {
            dx += agility;
        }
    }


    void moveLeft() {
    	if (gameOver) return;
        if (dx - agility > -maxSpeed) {
            dx -= agility;
        }
    }


    void update(GameState game, double timeStep) {
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;

        int height = Constants.WINDOW_HEIGHT;
        int width = Constants.WINDOW_WIDTH;

        if (gameOver) return;

        // Check for collisions with the left/right walls.
        if (x + (dx*deltaTime) >= width - radius) {
            x = width - radius;
            dx = -dx;
        } else if (x + dx < radius) {
            x = radius;
            dx = -dx;
        } else x += dx * deltaTime;

        // Check if we're inside the Fly powerup.
        if (countFlyPower > 0) {
            countFlyPower--;
            return;
        }

        // If the ball touches the ground...
        if (y >= height - radius) {
            // If we hit the floor, the game is over!
            // gameOver = true;
            dy = -maxSpeed;
            y += dy * deltaTime;
        } else {
            // Calculate new velocity in Y direction:
            dy += gravity * deltaTime;
            // Calculate new y coordinate.
            y += dy  * deltaTime;
        }

        dy = Math.min(maxSpeed, dy);
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    void setY(double y) {
        this.y = y;
    }

    void setDy(double dy) {
        this.dy = dy;
    }

    double getGravity() {
        return gravity;
    }

    void setGravity(double gravity) {
        this.gravity = gravity;
    }

    int getRadius() {
        return radius;
    }

    public int getCountFlyPower() {
		return countFlyPower;
	}
    
    public void setCountFlyPower(int countFlyPower) {
		this.countFlyPower = countFlyPower;
	}

    public double getDx() {
		return dx;
	}
    
    public double getDy() {
		return dy;
	}
    
    public void setPermission(boolean permission) {
		this.permission = permission;
	}
    
    public boolean gameOver(){
    	return gameOver;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }
}