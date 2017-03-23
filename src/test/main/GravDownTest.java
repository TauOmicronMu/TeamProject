package main;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class GravDownTest {

    public GravDown itemTest;
    public GameState gameState;

    @Before
    public void setUp() throws Exception {
        itemTest = new GravDown(400, 1);
        gameState = new GameState(1600,800);
    }

    @Test
    public void performAction() throws Exception {
        Ball ball = gameState.getBall();
        //gameState.ball.setGravity();
        System.out.println(ball.getGravity());
        double gravity = ball.getGravity();
        itemTest.performAction(gameState);

        assertEquals(gravity - Constants.GRAVITY_DOWN_STEP, ball.getGravity(), 0);
        System.out.println(ball.getGravity());
        ball.setGravity(2.3);
        itemTest.performAction(gameState);

        assertEquals(Constants.GRAVITY_DOWN_THRESH, ball.getGravity(), 0);


    }

}