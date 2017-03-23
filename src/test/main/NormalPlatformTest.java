package main;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NormalPlatformTest {

    public NormalPlatform platform;
    public GameState gameState;
    @Before
    public void setUp(){
        platform = new NormalPlatform(400,400, 100, 40);
        gameState = new GameState(800, 800);
    }


    @Test
    public void update() throws Exception {
        double dy = Constants.PLATFORM_START_DY;
        double timeStep = 4;
        if (timeStep < Constants.MIN_TIME_PER_FRAME) return;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        double y = platform.y;
        y += dy * deltaTime;
        platform.update(gameState, timeStep);
        assertEquals(y, platform.getY(), 0);

        gameState.ball.setCountFlyPower(200);
        y = platform.getY() + Constants.FLY_POWERUP_SPEED * deltaTime;
        platform.update(gameState, timeStep);
        assertEquals(y, platform    .getY(), 0);

    }


}