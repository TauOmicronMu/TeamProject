package ai;

import object.Ball;
import object.platform.Platform;
import org.junit.Test;
import shared.Constants;
import shared.GameState;

import static junit.framework.TestCase.assertEquals;


public class AITest {
    @Test
    public void dist() throws Exception {
        Platform p = new Platform(4, 3, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT);
        Ball b = new Ball(0.0, 0.0);
        double dist = AI.dist(p, b);
        assertEquals(5.0, dist);

        Ball b2 = new Ball(Constants.PLATFORM_WIDTH + 5, 0);
        Platform p2 = new Platform(0, 12, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT);
        assertEquals(AI.dist(p2, b2), 13.0);
    }

    @Test
    public void getMove() throws Exception {
        Platform[] ps = {new Platform(4, 3, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT)};
        Ball b = new Ball(0.0, 0.0);
        GameState gs = new GameState(800, 800, b, ps);
        AI.Move move = AI.getMove(gs);
        assertEquals(AI.Move.RIGHT, move);

        Ball b2 = new Ball(Constants.PLATFORM_WIDTH + 5, 0);
        Platform[] ps2 = {new Platform(0, 12, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT)};
        GameState gs2 = new GameState(800, 800, b2, ps2);
        AI.Move move2 = AI.getMove(gs2);
        assertEquals(AI.Move.LEFT, move2);
    }

}