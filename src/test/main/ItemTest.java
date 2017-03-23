package main;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    public Item item;
    public GameState gameState;
    @Before
    public void setUp() throws Exception {
        item = new Item(200, 3);
        gameState = new GameState(1600,800);
    }
    @Test
    public void getType() throws Exception {
        assertEquals(item.type, item.getType(), 0);
    }

    @Test
    public void update() throws Exception {
        Ball ball = gameState.getBall();
        ball.setCountFlyPower(200);
        double y  = item.getY();
        double timeStep = 4;
        double deltaTimeStep = timeStep * Constants.TIME_STEP_COEFFICIENT;
        item.update(gameState, timeStep);
        assertTrue(y + Constants.FLY_POWERUP_SPEED * deltaTimeStep == item.getY());

        ball.setCountFlyPower(0);
        item.update(gameState, timeStep);

        y= item.getY();
        double dy = ball.getDy();
        ball.setHeightLocked(true);
        ball.setY(100);
        ball.setDy(-20);
        item.update(gameState, timeStep);
        assertEquals(y - dy + deltaTimeStep, item.getY(),1);


    }


    @Test
    public void getY() throws Exception {

    }

}
