package tests;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import main.Constants;
import org.junit.Before;
import org.junit.Test;
import main.Ball;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BallTest {

    public Ball ball;
    @Before
    public void setUp(){
        ball = new Ball(400,400);
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
        int doubleJump = ball.getDoubleJump();
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

}