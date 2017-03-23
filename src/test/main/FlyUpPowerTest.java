package main;

import org.junit.Before;
import org.junit.Test;

import java.io.Flushable;

import static org.junit.Assert.*;



public class FlyUpPowerTest {

    public FlyUpPower itemTest;
    public GameState gameState;

    @Before
    public void setUp() throws Exception {
        itemTest = new FlyUpPower(400, 3);
        gameState = new GameState(1600, 800);
    }

    @Test
    public void performAction() throws Exception {

        itemTest.performAction(gameState);
        assertEquals(Constants.FLY_POWERUP_SPEED, gameState.getBall().getCountFlyPower());
    }

}