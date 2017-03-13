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
    private Random random = new Random();
    private Ball ball;
    private Platform platforms[] = new Platform[15];
    private Item items[] = new Item[3];
    private int[] Level1 = {
            400, -300, 400,
            -300, 400, -300,
            400, -300, 400, -300
    };
    private int counter = 0;


    private static final int PLATFORM_WIDTH = 100;
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
     * Populates the list of platforms in this game state with a set
     * of randomly generated platforms. They should be spaced evenly
     * in the Y direction, have a random position in the X direction,
     * and be of a uniform width and height.
     */
    void generatePlatforms() {

        platforms[0] = new NormalPlatform(
                0,
                400,
                800,
                PLATFORM_HEIGHT
        );

        for (int i = 1; i < platforms.length; i++) {
            // Todo: understand and refactor these "magic numbers".


            if (i % 2 != 0) {
                int xPosition = random.nextInt(windowWidth - 100);
                int yPosition = 400 - 100 * i ;

                platforms[i] = new NormalPlatform(
                        xPosition,
                        yPosition,
                        PLATFORM_WIDTH,
                        PLATFORM_HEIGHT
                );
            } else {
                switch (random.nextInt(3)) {
                    case 0:
                        int xPosition = random.nextInt(windowWidth - 100);
                        int yPosition = 400 - 100 * i - 20;
                        platforms[i] = new JumpOncePlatform(
                                xPosition,
                                yPosition,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT
                        );
                        break;

                    case 1:
                        int x2Position = 200 + random.nextInt(windowWidth - 400);
                        int y2Position = 400 - 100 * i;
                        platforms[i] = new MovingHorizontallyPlatform(
                                x2Position,
                                y2Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                x2Position - 200,
                                x2Position + 200
                        );
                        break;
                    case 2:
                        int x3Position = random.nextInt(windowWidth - 100);
                        int y3Position = 400 - 100 * i - 20;
                        platforms[i] = new TrapPlatform(
                                x3Position,
                                y3Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT
                        );
                        break;
                    }
                }
            }
        }

    /**
     * Update the powerup items in the current game state.
     */
    private void updateItems() {
        //Item[] items = this.getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null || items[i].getY() > windowHeight || items[i].getNull()) {
                //items[i] = null;
                switch (random.nextInt(4)) {
                    case 0:
						items[i]=new main.GravDown(- 10 * random.nextInt(500), 1);
						items[i].setNull(false);
						break;
					case 1:
						items[i]=new main.GravUp(-10 * random.nextInt(500), 2);
                        items[i].setNull(false);
						break;
					case 2:
                        items[i]=new main.FlyUpPower(-10 * random.nextInt(500), 3);
                        items[i].setNull(false);
                        break;
                    case 3:
                        items[i]=new main.PointsItem(-10 * random.nextInt(500), 4);
                        items[i].setNull(false);
                        break;
                }
            }
        }
    }

    private void updatePlatforms() {
        double yPosition;
        double xPosition;

    //Platform[] platforms = this.getPlatforms();
        for (int i = 0; i < platforms.length; i++) {
            if(platforms[i].getY() >= windowHeight || platforms[i].getNull()) {

                if(i-1<0) {
                    yPosition = (platforms[platforms.length-1]).getY() - 100;
                    System.out.println(yPosition);
                }
                else yPosition = platforms[i-1].getY() - 100;

                if (i % 2 != 0) {

                    xPosition = random.nextInt(windowWidth - 100);
                    platforms[i] = new NormalPlatform(
                            xPosition,
                            yPosition,
                            PLATFORM_WIDTH,
                            PLATFORM_HEIGHT
                    );
                } else {
                    switch (random.nextInt(3)) {
                        case 0:
                            xPosition = random.nextInt(windowWidth - 100);
                            platforms[i] = new JumpOncePlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT
                            );
                            break;

                        case 1:
                            xPosition = 200 + random.nextInt(windowWidth - 400);
                            platforms[i] = new MovingHorizontallyPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    xPosition - 200,
                                    xPosition + 200
                            );
                            break;
                        case 2:
                            xPosition = random.nextInt(windowWidth - 100);
                            platforms[i] = new TrapPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT
                            );
                            break;
                    }
                }
            }
        }
    }

    void updateLogic() {
        updateItems();
        updatePlatforms();
    }

    void updatePhysics() {
        ball.update(this);
        for (Platform platform : platforms) {
            if (platform != null) platform.update(this);
        }
        for (Item item : items) {
            if (item != null) item.update(this);
        }
    }


    /**
     * Populates the list of items in this main.GameState with a set of
     * power-ups.
     */
    void generateItems() {
        for (int i = 0; i < items.length; i++) {
            // Todo: understand and refactor this "magic number".
        	switch (random.nextInt(4)) {
                case 0:
                    items[i]=new main.GravDown(- 10 * random.nextInt(500), 1);
                    break;
                case 1:
                    items[i]=new main.GravUp(-10 * random.nextInt(500), 2);
                    break;
                case 2:
                    items[i]=new main.FlyUpPower(-10 * random.nextInt(500), 3);
                    break;
                case 3:
                    items[i]=new main.PointsItem(-10 * random.nextInt(500), 4);
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

    void printScore() {
        System.out.println(score);
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

    int getCounter(){
        return counter;
    }

    int[] getLevel(){
        return Level1;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public double getYPlatform(int i){
        return platforms[i].getY();
    }
}

