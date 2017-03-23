package main;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlatformTest {

    public Platform platform;
    @Before
    public void setUp(){
        platform = new Platform(400,400, 100, 40);
    }

    @Test
    public void checkForCollision() throws Exception {
        Ball ball = new Ball(400, 400);

    }

    @Test
    public void getY() throws Exception {
        assertEquals(400, platform.getY(), 0);
    }

    @Test
    public void getX() throws Exception {
        assertEquals(400, platform.getX(), 0);
    }

    @Test
    public void setNoDraw() throws Exception {
        boolean noDrowOld = platform.noDraw;
        platform.setNoDraw(true);
        assertTrue(platform.noDraw);
    }

}