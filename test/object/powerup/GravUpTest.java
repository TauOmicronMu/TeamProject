package object.powerup;

import object.Ball;
import org.junit.Before;
import org.junit.Test;
import shared.Constants;
import shared.GameState;

import static org.junit.Assert.*;


public class GravUpTest {

    public GravUp itemTest;
    public GameState gameState;
    @Before
    public void setUp() throws Exception {
        itemTest = new GravUp(400, 2);
        gameState = new GameState(1600,800);
    }

    @Test
    public void performAction() throws Exception {
        Ball ball = gameState.getBall();
        //gameState.ball.setGravity();
        System.out.println(ball.getGravity());
        double gravity = ball.getGravity();
        itemTest.performAction(gameState);

        assertEquals(gravity + Constants.GRAVITY_UP_STEP, ball.getGravity(), 0);
    }

}