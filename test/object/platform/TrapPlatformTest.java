package object.platform;

import object.Ball;
import org.junit.Before;
import org.junit.Test;
import shared.Constants;
import shared.GameState;

import static org.junit.Assert.*;


public class TrapPlatformTest {

    public TrapPlatform trapPlatform;
    public GameState gameState;
    @Before
    public void setUp(){
        trapPlatform = new TrapPlatform(400,400, 100, 40);
        gameState = new GameState(800, 800);
    }
    @Test
    public void update() throws Exception {
       double dy = trapPlatform.getDy();
        double timeStep = 4;
        if (timeStep < Constants.MIN_TIME_PER_FRAME) return;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;
        double y = trapPlatform.y;
        y += dy * deltaTime;
        trapPlatform.update(gameState, timeStep);
        assertEquals(y, trapPlatform.getY(), 0);
    }

    @Test
    public void checkForCollision() throws Exception {
        Ball ball = gameState.getBall();
        ball.setX(450);
        ball.setY(410);
        double ballX = ball.getX();
        double ballY = ball.getY();
        int radius = ball.getRadius();

        double ballBottom = ballY + radius;
        double rectTop = ball.getY();
        double rectLeft = ball.getX();

        double timeStep = 4;
        double deltaTime = timeStep * Constants.TIME_STEP_COEFFICIENT;

        double rectRight = trapPlatform.x + trapPlatform.width*2;
        if (ballBottom >= rectTop) return;
        double newBallBottom = ballBottom + ball.getDy() * deltaTime;
        if (newBallBottom <= rectTop) return;

        if (ballX+radius < rectLeft) return;
        if (ballX-radius > rectRight) return;

        trapPlatform.checkForCollision(ball, gameState, deltaTime);
        assertTrue(trapPlatform.noDraw);

    }

}