package main;

import java.io.Serializable;
import java.util.Random;


/**
 * A GameState stores all the information about the current level,
 * the ball position, the positions of each platform etc. It also
 * provides utility methods for generating these positions at the
 * start, and update methods which delegate to the update methods
 * provided by the Items and Platforms themselves.
 */
public class GameState implements Serializable {

    private final int windowWidth;
    private final int windowHeight;
    Random random;
    private Ball ball;
    private Platform platforms[] = new Platform[8];
    private MovingPlatform movingPlatform[] = new MovingPlatform[4]; 
    private Item items[] = new Item[3];

    private static final int PLATFORM_WIDTH = 140;
    private static final int PLATFORM_HEIGHT = 20;
    int score;


    GameState(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
        ball = new Ball(windowWidth / 2, 200);
    }

    void setSeed(int seed) {
        this.random = new Random(seed);
    }


    int getWindowWidth() {
        return windowWidth;
    }

    int getWindowHeight() {
        return windowHeight;
    }

    /**
     * Populates the list of platforms in this game state with a set
     * of randomly generated platforms. They should be spaced evenly
     * in the Y direction, have a random position in the X direction,
     * and be of a uniform width and height.
     */
    void generatePlatforms() {
        for (int i = 0; i < platforms.length; i++) {
            // Todo: understand and refactor these "magic numbers".
            int xPosition = random.nextInt(windowWidth - 220);
            int yPosition = windowHeight - 200 * i;

            platforms[i] = new Platform(
                    xPosition,
                    yPosition,
                    PLATFORM_WIDTH,
                    PLATFORM_HEIGHT
            );
        }

        int[] platformYValues = new int[]{-800, -1000, -100, -300};

        for (int i=0; i<4; i++) {
            int x = PLATFORM_WIDTH + random.nextInt((getWindowWidth() - PLATFORM_WIDTH)/2);
            int x1 = x - PLATFORM_WIDTH;
            int x2 = x + PLATFORM_WIDTH;
            int y = platformYValues[i];
            movingPlatform[0] = new MovingPlatform(
                    x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT, x1, x2
            );
        }
    }

    /**
     * Update the powerup items in the current game state.
     */
    private void updateItems() {
        Item[] items = this.getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null || items[i].getY() > windowHeight ) {
                items[i] = null;
                int x = random.nextInt(700) + Constants.ITEM_RADIUS + 100;
                switch (random.nextInt(3)) {
                    case 0:
						items[i]=new main.GravDown(x, - 10 * random.nextInt(500), 1);
						break;
					case 1:
						items[i]=new main.GravUp(x, -10 * random.nextInt(500), 2);
						break;
					case 2:
						items[i]=new main.FlyUpPower(x, -10 * random.nextInt(500), 3);
						break;
                }
            }
        }
    }


    void updatePhysics(double timeStep) {
    	updateItems();
    	ball.update(this, timeStep);
        for (Platform platform : platforms) {
            if (platform != null) platform.update(this, timeStep);
        }
        for (MovingPlatform movingPlatform : movingPlatform) {
            if (movingPlatform != null) movingPlatform.update(this, timeStep);
        }
        for (Item item : items) {
            if (item != null) item.update(this, timeStep);
        }
  
    }


    /**
     * Populates the list of items in this main.GameState with a set of
     * power-ups. Currently, it's just using the GravUp power-up.
     */
    void generateItems() {
        for (int i = 0; i < items.length; i++) {
            // Todo: understand and refactor this "magic number".
            int x = random.nextInt(700) + Constants.ITEM_RADIUS + 100;
        	switch (random.nextInt(3)) {
            case 0:
				items[i]=new main.GravUp(x, -i * 1000, 1);
				break;
			case 1:
				items[i]=new main.GravDown(x, -i * 1000, 2);
				break;
			case 2:
				items[i]=new main.FlyUpPower(x, -i * 1000, 3);
				break;
        	}
        }
    }


    /**
     * Retrieve each powerup item currently on-screen.
     */
    Item[] getItems() {
        return items;
    }


    /**
     * Retrieve the ball that's currently on-screen.
     */
    Ball getBall() {
        return ball;
    }


    /**
     * Retrieve each platform currently on-screen.
     */
    Platform[] getPlatforms() {
        return platforms;
    }
    
    MovingPlatform[] getMovingPlatforms() {
        return movingPlatform;
    }


    /**
     * Returns the score of the player
     */
    public int getScore() {
        return score;
    }

    public boolean gameOver(){
    	return ball.gameOver();
    }

    public void handleInput(String move) {
        switch(move) {
            case "a":
                getBall().moveLeft();
                break;
            case "d":
                getBall().moveRight();
                break;
            default:
                System.err.println("[WARN] GameState.handleInput : Bad move => " + move);
        }
    }
}
