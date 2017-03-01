package main;

import java.io.Serializable;

public class Ball implements Serializable {

    private double x;
    private double y;
    private double dx = 0;
    private double dy = 0;
    private int radius = 20;
    private double gravity = 15;
    private static final double energyloss = 1;
    private static final double dt = 0.1;
    private static final double xFriction = 0.9;
    private double gameDy = -90;
    private int agility = 1;
    private int maxSpeed = 5;
    private int countFlyPower = 0;
    private int score;
    private boolean permission = false;
    private boolean gameOver = false;


    Ball(double i, double j) {
        x = i;
        y = j;
    }


    void moveRight() {
    	if (gameOver == false){
    		if (dx + agility < maxSpeed) {
                dx += agility;

            }
    	}
    }

    void moveLeft() {
    	if (gameOver == false){
    		if (dx - agility > -maxSpeed) {
                dx -= agility;
            }
    	}     
    }


    void update(GameState game) {

        int height = game.getWindowHeight();
        int width = game.getWindowWidth();

        if(gameOver == false){
        	
        	if (x + dx > width - radius - 1) {
                x = width - radius - 1;
                dx = -dx;
            } else if (x + dx < radius) {
                x = radius;
                dx = -dx;
            } else {
                x += dx;
            }
            
            if(countFlyPower == 0){
            	if (y == height - radius - 1) {
                    dx *= xFriction;
                    if (Math.abs(dx) < 0.8) {
                        dx = 0;
                    }
                }

                if (y > height - radius - 1) {
                    y = height - radius - 1;
                    dy *= energyloss;
                    //if you dont want the game to end when the ball touches the ground,
                    //just comment the next line of code("gameOver = true;")
                    // gameOver = true;
                    dy = -dy;
                } else {
                		// Calculate new velocity in Y direction:
                        dy += gravity * dt;
                        // Calculate new Y position:
                        if(permission == false){
                        y += dy * dt + .5 * gravity * dt * dt;
                        if(dy > 100){
        					dy = 100;
        				}
        				if(dy < -100){
        					dy = -100;
        				}
        				if(dy<0){
        					score += -dy;
        				} else {
        					score += 3;
        				}
                	}
                }
            }
            else countFlyPower --;           	      
        }               
    }
    double getGameDy() {
        return gameDy;
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

    int getAgility() {
        return agility;
    }
    
    public int getCountFlyPower() {
		return countFlyPower;
	}
    
    public void setCountFlyPower(int countFlyPower) {
		this.countFlyPower = countFlyPower;
	}
    
    public int getScore() {
    	return score;
    }
    
    public double getDx() {
		return dx;
	}
    
    public double getDy() {
		return dy;
	}
    
    public static double getDt() {
		return dt;
	}
    
    public void setPermission(boolean permition) {
		this.permission = permition;
	}
    
    public boolean gameOver(){
    	return gameOver;
    }
    
}