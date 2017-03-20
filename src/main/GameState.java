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
    private Platform platforms[] = new Platform[15];

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
                    case 1:
                        int x3Position = random.nextInt(windowWidth - 100);
                        int y3Position = 400 - 100 * i;
                        platforms[i] = new TrapPlatform(
                                x3Position,
                                y3Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT
                        );
                        break;
                    case 2:
                        int xPosition = random.nextInt(windowWidth - 100);
                        int yPosition = 400 - 100 * i;
                        platforms[i] = new JumpOncePlatform(
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

    /**
     * Update the powerup items in the current game state
     * If item is offscreen, create a random new one!
     */
    private void updateItems() {
//        for(int i = 0; i<items.length; i++){
//            System.out.print(items[i].getY() + " ");
//        }
            System.out.println();
            for (int i = 0; i < items.length; i++) {
                if (items[i].getY() >= windowHeight) {
                    //items[i] = null;
                    int y = (int)this.getHighestItem();
                    switch (random.nextInt(4)) {
                        case 0:
                            items[i]=new main.GravDown( -1000 + y - random.nextInt(500), 1);
                            break;
                        case 1:
                            items[i]=new main.GravUp(  - 1000 + y - random.nextInt(500), 2);
                            break;
                        case 2:
                            items[i]=new main.FlyUpPower(  - 1000 + y - random.nextInt(500), 3);
                            break;
                        case 3:
                            items[i]=new main.PointsItem(-1000 + y - random.nextInt(500), 4);
                            break;
                    }
                }
            }
    }

    /**
     * Update the platforms in the current game state.
     * If platform is offscreen, create a random new one!
     */
    private void updatePlatforms() {
        double yPosition;
        double xPosition;
//        for(int i = 0; i<platforms.length; i++){
//            System.out.print(platforms[i].getY() + " ");
//        }
        System.out.println();
        for (int i = 0; i < platforms.length; i++) {
            if(platforms[i].getY() >= windowHeight) {
                if(i-1<0) {
                    yPosition = (platforms[platforms.length-1]).getY() - 100;

                }
                else yPosition = platforms[i-1].getY() - 100;
                //System.out.println(yPosition);
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
                            xPosition = 200 + random.nextInt(windowWidth - 400);
                            platforms[i] = new MovingHorizontallyPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    (int) xPosition - 200,
                                    (int) xPosition + 200
                            );
                            break;
                        case 1:
                            xPosition = random.nextInt(windowWidth - 100);
                            platforms[i] = new TrapPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT
                            );
                            break;
                        case 2:
                            xPosition = random.nextInt(windowWidth - 100);
                            platforms[i] = new JumpOncePlatform(
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


    void updatePhysics(double timeStep) {
    	updateItems();
    	updatePlatforms();
    	ball.update(this, timeStep);
        for (Platform platform : platforms) {
            if (platform != null) platform.update(this, timeStep);
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
            switch (random.nextInt(4)) {
                case 0:
                    items[i]=new main.GravDown(- i * 500 + random.nextInt(500), 1);
                    break;
                case 1:
                    items[i]=new main.GravUp(- i * 500 + random.nextInt(500), 2);
                    break;
                case 2:
                    items[i]=new main.FlyUpPower(-i * 500 + random.nextInt(500), 3);
                    break;
                case 3:
                    items[i]=new main.PointsItem(-i * 500 + random.nextInt(500), 4);
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
    Platform[] getBasicPlatforms() {
        return platforms;
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

    public void handleInput(String move) {
        switch(move) {
            case "a":
                getBall().moveLeft();
                break;
            case "d":
                getBall().moveRight();
                break;
            case "Space":
                getBall().doubleJump();
                break;
            default:
                System.err.println("[WARN] GameState.handleInput : Bad move => " + move);
        }
    }
    /**
     * Returns the y position of the highest item
     */
    public double getHighestItem(){
        double max = 0;
        for(int i = 0; i<items.length; i++){
            if(items[i].getY()<max)
                max = items[i].getY();
        }
        return max;
    }
}
