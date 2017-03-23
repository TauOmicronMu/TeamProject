package tests.networking;

import main.GameState;
import main.OpponentType;
import networking.Message;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;


public class MessageTest {

    /**
     * Retrieving text from a Message should give the same text it was created with.
     */
    @Test
    public void getText() throws Exception {
        Message m = new Message("Hello, world!");
        assertEquals(
                m.getText(), "Hello, world!"
        );
    }

    /**
     * It should be possible to check whether a message concerns the recipient's
     * game state.
     */
    @Test
    public void isMyGame() throws Exception {
        Message m = new Message(new GameState(1, 2), false);
        assertFalse(m.isMyGame());
        Message m2 = new Message(new GameState(1, 2), true);
        assertTrue(m2.isMyGame());
    }

    /**
     * It should be possible to retrieve an object of any type from a Message.
     */
    @Test
    public void getObject() throws Exception {
        Point2D.Float coords = new Point2D.Float(1.0f, 2.0f);
        assertEquals(
                coords,
                new Message(coords).getObject()
        );

        assertEquals(
                "Hello, world!",
                new Message("Hello, world!").getObject()
        );

        GameState gs = new GameState(1, 2);
        assertEquals(
                gs,
                new Message(gs, true).getObject()
        );

        assertEquals(
                OpponentType.AI,
                new Message(OpponentType.AI).getObject()
        );

        assertEquals(
                123,
                new Message(123).getObject()
        );
    }
}