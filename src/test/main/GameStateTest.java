package main;

import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;


public class GameStateTest {

    public GameState gameState;
    @Before
    public void setUp() throws Exception {
        gameState = new GameState(1600, 800);
    }

    @Test
    public void setSeed() throws Exception {
        int seed = 1;

        gameState.setSeed(seed);
        assertTrue(gameState.random!=null);
    }

    @Test
    public void getWindowWidth() throws Exception {
        assertEquals(1600,gameState.getWindowWidth());
    }

    @Test
    public void getWindowHeight() throws Exception {
        assertEquals(800,gameState.getWindowHeight());
    }

    @Test
    public void generatePlatforms() throws Exception {
        gameState.setSeed(2);
        gameState.generatePlatforms();
        for(int i = 0; i < gameState.platforms.length; i++)
        {
            assertTrue(gameState.platforms[i] != null);
        }
    }

    @Test
    public void updatePhysics() throws Exception {
        if(gameState.gameOver())return;
        gameState.setSeed(2);
        gameState.items[0]  = new main.GravDown(800 , 1);
        gameState.items[1]  = new main.FlyUpPower(900, 3);
        gameState.items[2]  = new main.PointsItem(1000, 3);
        gameState.items[3]  = new main.MakeOpponentPlatformDissapearPowerUp(1100, 3);
        gameState.items[4]  = new main.GravUp( 1200, 2);

        gameState.updateItems();
        for(int i = 0; i<gameState.items.length; i++){
            assertTrue(gameState.items[i] != null);
        }

        gameState.setSeed(2);
        Platform theOne = new NormalPlatform(
                0,
                850,
                800,
                20
        );
        Platform theSecond = new NormalPlatform(
                0,
                200,
                800,
                20
        );

        for(int i = 1; i < gameState.platforms.length; i++){
            // assertTrue(gameState.platforms[i].getY() > platformsTest[i].getY() );
            //System.out.println(gameState.platforms[i].getY() + "asda" + platformsTest[i].getY());
            //assertNotEquals(gameState.platforms[i].getY(), platformsTest[i].getY());
            gameState.platforms[i] = theOne;
        }
        gameState.platforms[0] = theSecond;


        double y = gameState.getBall().getY();
        double timeStep = 20;

        gameState.updatePhysics(timeStep);


        assertNotEquals(y, gameState.getBall().getY());

        for(int i = 0; i<gameState.items.length; i++){
            assertTrue(gameState.items[i] != null);
        }
        for(int i = 0; i < gameState.platforms.length; i++){
            assertTrue(gameState.platforms[i] != null );
        }


    }

    @Test
    public void generateItems() throws Exception {
        gameState.setSeed(2);
        gameState.generateItems();
        for(int i = 0; i < gameState.items.length; i++)
        {
            assertTrue(gameState.items[i] != null);
        }

    }

    @Test
    public void getItems() throws Exception {
        gameState.setSeed(2);
        gameState.items[0]  = new main.GravDown(-500 , 1);
        gameState.items[1]  = new main.FlyUpPower(-700, 3);
        gameState.items[2]  = new main.GravUp( -900, 2);

        Item[] itemsTest = gameState.getItems();
        assertEquals(gameState.items[0], itemsTest[0]);
        assertEquals(gameState.items[1], itemsTest[1]);
        assertEquals(gameState.items[2], itemsTest[2]);

    }

    @Test
    public void getBall() throws Exception {
        Ball ball = new Ball(300, 309);
        gameState.ball = ball;
        assertEquals(ball, gameState.getBall());
    }

    @Test
    public void getBasicPlatforms() throws Exception {
        Platform theOne = new NormalPlatform(
                0,
                400,
                800,
                20
        );
        for(int i = 0; i< gameState.platforms.length; i++){
            gameState.platforms[i] = theOne;
        }

        Platform[] platformsTest = gameState.getBasicPlatforms();
        for(int i = 0; i< platformsTest.length; i++){
            assertEquals(theOne, platformsTest[i]);
        }
    }

    @Test
    public void getScore() throws Exception {
        int newScore = 400;
        gameState.score = newScore;
        assertEquals(newScore, gameState.getScore());

    }

    @Test
    public void gameOver() throws Exception {
        gameState.ball.gameOver = true;
        assertTrue(gameState.gameOver());
    }

    @Test
    public void handleInput() throws Exception {

        if(gameState.gameOver())return;
        Ball ball2 = gameState.getBall();
        ball2.moveLeft();

        gameState.handleInput("a");
        assertEquals(ball2.getX(), gameState.getBall().getX(), 0);

        ball2.moveRight();
        gameState.handleInput("d");
        assertEquals(ball2.getX(), gameState.getBall().getX(), 0);

        ball2.doubleJump();
        gameState.handleInput("Space");
        assertEquals(ball2.getY(), gameState.getBall().getY(), 0);

        gameState.setSeed(1);
        gameState.generatePlatforms();
        gameState.handleInput("Shift");

        assertTrue(gameState.platforms[gameState.getClosestPlatform()].noDraw);

        gameState.handleInput("anythingElse");
    }

    @Test
    public void getHighestItem() throws Exception {
        gameState.items[0]  = new main.GravDown(-500 , 1);
        gameState.items[1]  = new main.FlyUpPower(-700, 3);
        gameState.items[2]  = new main.PointsItem(-700, 3);
        gameState.items[3]  = new main.MakeOpponentPlatformDissapearPowerUp(-700, 3);
        gameState.items[4]  = new main.GravUp( -900, 2);

        assertEquals(-900, gameState.getHighestItem(), 0);
    }

    @Test
    public void getHighestPlatform() throws Exception {
        Platform theOne = new NormalPlatform(
                0,
                600,
                800,
                20
        );
        Platform theHighestOne = new NormalPlatform(
                0,
                200,
                800,
                20
        );
        for(int i = 0; i< gameState.platforms.length- 1; i++){
            gameState.platforms[i] = theOne;
        }
        gameState.platforms[gameState.platforms.length-1] = theHighestOne;
        assertEquals(200, gameState.getHighestPlatform(), 0);

    }

    @Test
    public void getClosestPlatform() throws Exception {
        gameState.ball.setY(200);

        Platform theOne = new NormalPlatform(
                0,
                600,
                800,
                20
        );
        Platform theClosestOne = new NormalPlatform(
                0,
                300,
                800,
                20
        );
        for(int i=0; i< gameState.platforms.length-1; i++){
            gameState.platforms[i] = theOne;
        }
        int index = gameState.platforms.length-1;
        gameState.platforms[index] = theClosestOne;
        assertEquals(index, gameState.getClosestPlatform());
    }

    @Test
    public void makeClosestPlatformUnusable() throws Exception {
        gameState.ball.setY(200);

        Platform theOne = new NormalPlatform(
                0,
                600,
                800,
                20
        );
        Platform theClosestOne = new NormalPlatform(
                0,
                300,
                800,
                20
        );
        for(int i=0; i< gameState.platforms.length-1; i++){
            gameState.platforms[i] = theOne;
        }
        int index = gameState.platforms.length-1;
        gameState.platforms[index] = theClosestOne;
        gameState.makeClosestPlatformUnusable();
        assertTrue(gameState.platforms[index].noDraw);
    }

    @Test
    public void getOppscore() throws Exception {
        assertEquals(0, gameState.getOppscore());
    }

    @Test
    public void setOppscore() throws Exception {
        gameState.setOppscore(400);
        assertEquals(400, gameState.oppscore, 0);
    }

    @Test
    public void updateItems(){
        gameState.setSeed(2);
        gameState.items[0]  = new main.GravDown(800 , 1);
        gameState.items[1]  = new main.FlyUpPower(900, 3);
        gameState.items[2]  = new main.PointsItem(1000, 3);
        gameState.items[3]  = new main.MakeOpponentPlatformDissapearPowerUp(1100, 3);
        gameState.items[4]  = new main.GravUp( 1200, 2);

        gameState.updateItems();
        for(int i = 0; i<gameState.items.length; i++){
            assertTrue(gameState.items[i] != null);
        }
    }

    @Test
    public void updatePlatforms() {
        gameState.setSeed(2);
        Platform theOne = new NormalPlatform(
                0,
                850,
                800,
                20
        );
        Platform theSecond = new NormalPlatform(
                0,
                200,
                800,
                20
        );

        for(int i = 1; i < gameState.platforms.length; i++){
           // assertTrue(gameState.platforms[i].getY() > platformsTest[i].getY() );
            //System.out.println(gameState.platforms[i].getY() + "asda" + platformsTest[i].getY());
            //assertNotEquals(gameState.platforms[i].getY(), platformsTest[i].getY());
            gameState.platforms[i] = theOne;
        }
        gameState.platforms[0] = theSecond;
        gameState.updatePlatforms();

        for(int i = 0; i < gameState.platforms.length; i++){
            assertTrue(gameState.platforms[i] != null );
        }
    }

}