package main;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BallTest {

    public Ball ball;
    public GameState gameState;
    @Before
    public void setUp(){
        ball = new Ball(400,400);
        gameState = new GameState(1600, 800);
    }

    @Test
    public void moveRight() throws Exception {
        double agility = ball.agility;
        double dx = ball.getDx();
        double maxSpeed = ball.getMaxSpeed();
        boolean gameOver = ball.gameOver();

        if(gameOver)return;
        ball.moveRight();
        if (dx + agility < maxSpeed) {
            dx += agility;
            assertEquals(dx, ball.getDx(), 0);
        }
    }

    @Test
    public void moveLeft() throws Exception {
        double agility = ball.agility;
        double dx = ball.getDx();
        double maxSpeed = ball.getMaxSpeed();
        boolean gameOver = ball.gameOver();

        if(gameOver)return;
        if (dx - agility > -maxSpeed) {
            dx -= agility;
            ball.moveLeft();
            assertEquals(dx, ball.getDx(), 0);
        }

    }
//
    @Test
    public void doubleJump() throws Exception {
        int doubleJump = ball.doubleJump;
        double dy = ball.getDy();
        double maxSpeed = ball.getMaxSpeed();
        if(doubleJump == 0){
            dy = -maxSpeed;
            doubleJump = 100;
        }
        ball.doubleJump();
        assertEquals(dy, ball.getDy(), 0);
    }
    @Test
    public void getX() throws Exception {
        assertEquals(400, ball.getX(), 0);
    }

    @Test
    public void setX() throws Exception {
        double newX = 500;
        ball.setX(newX);
        assertEquals(newX, ball.getX(), 0);
    }

    @Test
    public void getY() throws Exception {
        assertEquals(400, ball.getY(), 0);
    }

    @Test
    public void setY() throws Exception {
        double newY = 500;
        ball.setY(newY);
        assertEquals(newY, ball.getY(), 0);
    }

    @Test
    public void setDy() throws Exception {
        double oldDy = ball.getDy();
        double newDy = 20;
        ball.setDy(newDy);
        assertEquals(newDy, ball.getDy(), 0);
    }

    @Test
    public void getGravity() throws Exception {
        assertEquals(Constants.GRAVITY, ball.getGravity(), 0);
    }

    @Test
    public void setGravity() throws Exception {
        double oldGravity = Constants.GRAVITY;
        double newGravity = 40;
        ball.setGravity(newGravity);
        assertEquals(newGravity, ball.getGravity(), 0);
    }

    @Test
    public void getRadius() throws Exception {
        int radious = 20;
        assertEquals(radious, ball.getRadius(), 0);
    }

    @Test
    public void getCountFlyPower() throws Exception {
        int counterFlyPower = 0;
        assertEquals(counterFlyPower, ball.getCountFlyPower(), 0);
    }

    @Test
    public void setCountFlyPower() throws Exception {
        int oldCounterFlyPower = 0;
        int newCounterFlyPower = 20;
        ball.setCountFlyPower(newCounterFlyPower);
        assertEquals(newCounterFlyPower, ball.getCountFlyPower(), 0);
    }

    @Test
    public void getDx() throws Exception {
        assertEquals(0, ball.getDx(), 0);
    }

    @Test
    public void getDy() throws Exception {
        assertEquals(0, ball.getDy(), 0);
    }

    @Test
    public void setHeightLocked() throws Exception {
        ball.setHeightLocked(true);
        assertTrue(ball.heightIsLocked());
    }

    @Test
    public void gameOver() throws Exception {
        assertFalse(ball.gameOver());
    }

    @Test
    public void getMaxSpeed() throws Exception {
        assertEquals(Constants.MAX_SPEED, ball.getMaxSpeed(), 0);
    }

    @Test
    public void heightIsLocked() throws Exception {
        assertFalse(ball.heightIsLocked());
    }
    @Test
    public void update(){
        double timeStep = 4;
        double x = gameState.getWindowWidth()- ball.getRadius();
        ball.setX(x);
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        double changeX = ball.getDx() * deltaTime;
        double nextX = x + changeX;
        ball.update(gameState,timeStep);
        assertNotEquals(x, ball.getX());

        x= ball.getX();
        ball.setCountFlyPower(200);
        ball.update(gameState, timeStep);
        assertTrue(ball.getCountFlyPower()<200);
        //assertTrue(ball.getX() != x);

        ball.setX(0);
        ball.setY(gameState.getWindowHeight());
        ball.update(gameState, timeStep);
        System.out.println(ball.gameOver());
        assertNotEquals(0, ball.getX());
        assertTrue( !ball.gameOver());


    }
}