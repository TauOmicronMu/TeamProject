package object.platform;

import org.junit.Before;
import org.junit.Test;
import shared.Constants;
import shared.GameState;

import static org.junit.Assert.assertEquals;


public class MovingHorizontallyPlatformTest {

    public MovingHorizontallyPlatform MovingHorizontallyPlatform;
    public GameState gameState;
    @Before
    public void setUp(){

        MovingHorizontallyPlatform = new MovingHorizontallyPlatform(400,400, 100, 40, 100, 100);
        gameState = new GameState(800, 800);
    }
    @Test
    public void getDx() throws Exception {
        assertEquals(15, MovingHorizontallyPlatform.getDx(), 0);
    }

    @Test
    public void update() throws Exception {
        double dy = MovingHorizontallyPlatform.dy;
        double dx = MovingHorizontallyPlatform.getDx();
        double timeStep = 6;
        //if (timeStep < Constants.MIN_TIME_PER_FRAME) return;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        double y = MovingHorizontallyPlatform.y;
        double x = MovingHorizontallyPlatform.x;
        y += dy * deltaTime;
        if (x <= MovingHorizontallyPlatform.x1 || x >= MovingHorizontallyPlatform.x2) dx = -dx;
        x += dx * deltaTime;
        MovingHorizontallyPlatform.update(gameState, timeStep);
        assertEquals(y, MovingHorizontallyPlatform.getY(), 0);
        assertEquals(x, MovingHorizontallyPlatform.getX(),0);
    }

}