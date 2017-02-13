package main;

import java.io.Serializable;

class Ball implements Serializable {


    private double x = 400;
    private double y = 25;
    private double dx = 0;
    private double dy = 0;
    private int radius = 20;
    private double gravity = 15;
    private static final double energyloss = 1;
    private static final double dt = 0.2;
    private static final double xFriction = 0.9;
    private static final double gameDy = -75;
    private int agility = 3;
    private int maxSpeed = 10;


    Ball(double i, double j) {
        x = i;
        y = j;
    }


    void moveRight() {
        if (dx + agility < maxSpeed) {
            dx += agility;

        }
    }

    void moveLeft() {
        if (dx - agility > -maxSpeed) {
            dx -= agility;
        }
    }


    void update(GameState game) {

        int height = game.getWindowHeight();
        int width = game.getWindowWidth();

        if (x + dx > width - radius - 1) {
            x = width - radius - 1;
            dx = -dx;
        } else if (x + dx < radius) {
            x = radius;
            dx = -dx;
        } else {
            x += dx;
        }
        if (y == height - radius - 1) {
            dx *= xFriction;
            if (Math.abs(dx) < 0.8) {
                dx = 0;
            }
        }

        if (y > height - radius - 1) {
            y = height - radius - 1;
            dy *= energyloss;
            dy = -dy;
        } else {
            // Calculate new velocity in Y direction:
            dy += gravity * dt;
            // Calculate new Y position:
            y += dy * dt + .5 * gravity * dt * dt;
        }
    }

    double getGameDy() {
        return gameDy;
    }

    public double getX() {
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

}