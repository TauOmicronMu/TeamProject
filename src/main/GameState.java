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

    private Screen screen = Screen.MAIN_MENU;
    private final int windowWidth;
    private final int windowHeight;
    private Random random = new Random();
    private Ball ball;
    private Platform platforms[] = new Platform[8];
    private MovingPlatform movingPlatform[] = new MovingPlatform[4]; 
    private Item items[] = new Item[3];

    private static final int PLATFORM_WIDTH = 140;
    private static final int PLATFORM_HEIGHT = 20;

    int score;
    int mouseXPosition;
    int mouseYPosition;


    GameState(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
    }


    int getWindowWidth() {
        return windowWidth;
    }

    int getWindowHeight() {
        return windowHeight;
    }


    /**
     * Sets up a main.GameState by creating a ball, platforms, and items.
     */
    public void setUp() {
        ball = new Ball(windowWidth / 2, 200);
    }


    /**
     * Retrieve the current screen: either the main.Main main.Menu or the Game.
     */
    Screen getScreen() {
        return screen;
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
        int xPosition = 100 + random.nextInt(windowWidth - 300);
        int yPosition = -800;
        int y2Position = -1000;
        int y3Position = -100;
        int y4Position = -300;
        
        movingPlatform[0] = new MovingPlatform(
                xPosition,
                yPosition,
                PLATFORM_WIDTH,
                PLATFORM_HEIGHT,
                xPosition -200,
                xPosition +200
        );
        movingPlatform[1] = new MovingPlatform(
                xPosition,
                y2Position,
                PLATFORM_WIDTH,
                PLATFORM_HEIGHT,
                xPosition -200,
                xPosition + 200
        );
        
        movingPlatform[2] = new MovingPlatform(
                xPosition,
                y3Position,
                PLATFORM_WIDTH,
                PLATFORM_HEIGHT,
                xPosition -200,
                xPosition + 200
        );
        
        movingPlatform[3] = new MovingPlatform(
                xPosition,
                y4Position,
                PLATFORM_WIDTH,
                PLATFORM_HEIGHT,
                xPosition -200,
                xPosition + 200
        );
        
    }

    /**
     * Update the powerup items in the current game state.
     */
    private void updateItems() {
        Item[] items = this.getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null || items[i].getY() > windowHeight ) {
                items[i] = null;
                switch (random.nextInt(3)) {
                    case 0:
						items[i]=new main.GravDown(- 10 * random.nextInt(500), 1);
						break;
					case 1:
						items[i]=new main.GravUp(-10 * random.nextInt(500), 2);
						break;
					case 2:
						items[i]=new main.FlyUpPower(-10 * random.nextInt(500), 3);
						break;
                }
            }
        }
    }


    void updateLogic() {
        updateItems();
    }


    void updatePhysics() {
    	
    	ball.update(this);
        for (Platform platform : platforms) {
            if (platform != null) platform.update(this);
        }
        for (MovingPlatform movingPlatform : movingPlatform) {
            if (movingPlatform != null) movingPlatform.update(this);
        }
        for (Item item : items) {
            if (item != null) item.update(this);
        }
  
    }


    /**
     * Populates the list of items in this main.GameState with a set of
     * power-ups. Currently, it's just using the GravUp power-up.
     */
    void generateItems() {
        for (int i = 0; i < items.length; i++) {
            // Todo: understand and refactor this "magic number".
        	switch (random.nextInt(3)) {
            case 0:
				items[i]=new main.GravUp(-i * 1000, 1);
				break;
			case 1:
				items[i]=new main.GravDown(-i * 1000, 2);
				break;
			case 2:
				items[i]=new main.FlyUpPower(-i * 1000, 3);
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
     * Set the flag indicating the current screen.
     */
    void setScreen(Screen screen) {
        this.screen = screen;
    }
    
    void printScore() {
        if (gameOver())
            System.out.println("Your final score is = " + score);
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
}
