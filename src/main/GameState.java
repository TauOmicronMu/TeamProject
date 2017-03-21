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
    private Platform platforms[] = new Platform[GameModes.getNrPlatforms(gameMode)];

    private Item items[] = new Item[GameModes.getNrItems(gameMode)];
    private static int gameMode;

    private static int PLATFORM_WIDTH = GameModes.getPlatformsWidth(gameMode);
    private static final int PLATFORM_HEIGHT = 20;
    private double platformsSpeed;
//    private int nrOfPlatforms;
//    private int nrOfItems;
    int score;
    int counter=0;


    GameState(int width, int height, int gameMode) {
        this.windowWidth = width;
        this.windowHeight = height;
        ball = new Ball(windowWidth / 2, 200);
        this.gameMode = gameMode;


        this.platformsSpeed = GameModes.getPlatformsSpeed(gameMode);
//        this.nrOfItems = GameModes.getNrItems(gameMode);
//        this.nrOfPlatforms = GameModes.getNrPlatforms(gameMode);
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
        PLATFORM_WIDTH = GameModes.getPlatformsWidth(gameMode);
        platforms[0] = new NormalPlatform(
                0,
                400,
                800,
                PLATFORM_HEIGHT,
                platformsSpeed
        );
        switch(gameMode) {
            case 1: generatePlatformsEasyMode();
            case 2: generatePlatformsNormalMode();
            case 3: generatePlatformsHardMode();
        }

    }

    void generatePlatformsEasyMode(){
        for (int i = 0; i < platforms.length; i=i+2) {

            if (i % 4 == 0 && i!=0) {
                int xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                int yPosition = 400 - 100 * i ;
                platforms[i] = new NormalPlatform(
                        xPosition,
                        yPosition,
                        PLATFORM_WIDTH,
                        PLATFORM_HEIGHT,
                        platformsSpeed
                );
                if(windowWidth - xPosition - PLATFORM_WIDTH >200) {
                    xPosition = xPosition + 200;

                }
                else xPosition = xPosition -200;
                if(i+1<platforms.length)
                    platforms[i+1] = new NormalPlatform(
                            xPosition,
                            yPosition,
                            PLATFORM_WIDTH,
                            PLATFORM_HEIGHT,
                            platformsSpeed
                    );
            } else if (i!= 0){
                switch (random.nextInt(3)) {
                    case 0:
                        int x2Position = Math.max(Math.min(random.nextInt(windowWidth - 2*PLATFORM_WIDTH), windowWidth - 400 - PLATFORM_WIDTH), 400 + PLATFORM_WIDTH);
                        int y2Position = 400 - 100 * i;
                        platforms[i] = new MovingHorizontallyPlatform(
                                x2Position,
                                y2Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed,
                                x2Position - 200,
                                x2Position + 200

                        );
                        break;
                    case 1:
                        int x3Position = random.nextInt((int) (windowWidth - 100 - 2*PLATFORM_WIDTH));
                        int y3Position = 400 - 100 * i;
                        platforms[i] = new TrapPlatform(
                                x3Position,
                                y3Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed
                        );
                        break;
                    case 2:
                        int xPosition = random.nextInt(windowWidth - 100 - 2*PLATFORM_WIDTH);
                        int yPosition = 400 - 100 * i;
                        platforms[i] = new JumpOncePlatform(
                                xPosition,
                                yPosition,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed
                        );
                        break;
                }
            }
        }
    }
    void generatePlatformsNormalMode(){
        for (int i = 1; i < platforms.length; i++) {

            if (i % 2 != 0) {
                int xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                int yPosition = 400 - 100 * i ;

                platforms[i] = new NormalPlatform(
                        xPosition,
                        yPosition,
                        PLATFORM_WIDTH,
                        PLATFORM_HEIGHT,
                        platformsSpeed
                );
            } else {
                switch (random.nextInt(3)) {
                    case 0:
                        int x2Position = PLATFORM_WIDTH/2 + 200 + random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                        int y2Position = 400 - 100 * i;
                        platforms[i] = new MovingHorizontallyPlatform(
                                x2Position,
                                y2Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed,
                                x2Position - 200,
                                x2Position + 200
                        );
                        break;
                    case 1:
                        int x3Position = random.nextInt((int) (windowWidth - 100 - 2*PLATFORM_WIDTH));
                        int y3Position = 400 - 100 * i;
                        platforms[i] = new TrapPlatform(
                                x3Position,
                                y3Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed
                        );

                        break;
                    case 2:
                        int xPosition = random.nextInt(windowWidth - 100 - 2*PLATFORM_WIDTH);
                        int yPosition = 400 - 100 * i;
                        platforms[i] = new JumpOncePlatform(
                                xPosition,
                                yPosition,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed
                        );
                        break;
                }
            }
        }
    }
    void generatePlatformsHardMode(){

        for (int i = 1; i < platforms.length; i++) {

                switch (random.nextInt(4)) {
                    case 0:
                        int x2Position = PLATFORM_WIDTH/2 + 200 + random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                        int y2Position = 400 - 100 * i;
                        platforms[i] = new MovingHorizontallyPlatform(
                                x2Position,
                                y2Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed,
                                x2Position - 200,
                                x2Position + 200
                        );
                        break;
                    case 1:
                        int x3Position = random.nextInt((int) (windowWidth - 100 - 2*PLATFORM_WIDTH));
                        int y3Position = 400 - 100 * i;
                        platforms[i] = new TrapPlatform(
                                x3Position,
                                y3Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed
                        );
                        break;
                    case 2:
                        int xPosition = random.nextInt(windowWidth - 100 - 2*PLATFORM_WIDTH);
                        int yPosition = 400 - 100 * i;
                        platforms[i] = new JumpOncePlatform(
                                xPosition,
                                yPosition,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed
                        );
                        break;
                    case 3:
                        int x4Position = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                        int y4Position = 400 - 100 * i ;
                        platforms[i] = new NormalPlatform(
                                x4Position,
                                y4Position,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed
                        );
                }
        }
    }

    /**
     * Populates the list of items in this main.GameState with a set of
     * power-ups. Currently, it's just using the GravUp power-up.
     */
    void generateItems() {
        for (int i = 0; i < items.length; i++) {
            switch (random.nextInt(4)) {
                case 0:
                    items[i]=new main.GravDown(- i * 1000 + random.nextInt(500), 1);
                    break;
                case 1:
                    items[i]=new main.GravUp(- i * 1000 + random.nextInt(500), 2);
                    break;
                case 2:
                    items[i]=new main.FlyUpPower(-i * 1000 + random.nextInt(500), 3);
                    break;
                case 3:
                    items[i]=new main.PointsItem(-i * 1000 + random.nextInt(500), 4);
                    break;
            }
        }
    }

    /**
     * Update the powerup items in the current game state
     * If item is offscreen, create a random new one!
     */
    private void updateItems() {
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
        switch(gameMode){
            case 1: updatePlatformsEasyMode();
            case 2: updatePlatformsNormalMode();
            default: updatePlatformsHardMode();
        }
    }

    private void updatePlatformsEasyMode(){
        double yPosition;
        double xPosition;

//        for(int i = 0; i<platforms.length; i++){
//            System.out.print(platforms[i].getY() + " ");
//        }
        for (int i = 0; i < platforms.length; i = i + 2 ) {
            if(platforms[i].getY() >= windowHeight) {
                if(i-1<0) {
                    yPosition = (platforms[platforms.length-1]).getY() - 100;
                }
                else yPosition = platforms[i-1].getY() - 100;
                //System.out.println(yPosition);
                if (i % 4 == 0) {
                    xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                    platforms[i] = new NormalPlatform(
                            xPosition,
                            yPosition,
                            PLATFORM_WIDTH,
                            PLATFORM_HEIGHT,
                            platformsSpeed
                    );

                    if(windowWidth - xPosition > 620) {
                        xPosition = xPosition + PLATFORM_WIDTH * 2 + 60 + random.nextInt((int)(windowWidth - xPosition - PLATFORM_WIDTH * 4 -60));

                    }
                    else xPosition = xPosition - PLATFORM_WIDTH * 2 - 60 - random.nextInt((int)(windowWidth - xPosition - PLATFORM_WIDTH * 2 + 60));

                    if(i+1<platforms.length) {
                        platforms[i + 1] = new NormalPlatform(
                                xPosition,
                                yPosition,
                                PLATFORM_WIDTH,
                                PLATFORM_HEIGHT,
                                platformsSpeed
                        );
                    }

                } else {
                    switch (random.nextInt(3)) {
                        case 0:
                            xPosition = Math.max(Math.min(random.nextInt(windowWidth - 2*PLATFORM_WIDTH), windowWidth - 400 - PLATFORM_WIDTH), 400 + PLATFORM_WIDTH);
                            platforms[i] = new MovingHorizontallyPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed,
                                    (int) xPosition - 200,
                                    (int) xPosition + 200
                            );
                            break;
                        case 1:
                            xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                            platforms[i] = new TrapPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed
                            );
                            break;
                        case 2:
                            xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                            platforms[i] = new JumpOncePlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed
                            );
                            break;
                    }
                }
            }
        }
    }
    private void updatePlatformsNormalMode(){
        double yPosition;
        double xPosition;
//        for(int i = 0; i<platforms.length; i++){
//            System.out.print(platforms[i].getY() + " ");
//        }
        for (int i = 0; i < platforms.length; i++) {
            if(platforms[i].getY() >= windowHeight) {
                if(i-1<0) {
                    yPosition = (platforms[platforms.length-1]).getY() - 100;
                }
                else yPosition = platforms[i-1].getY() - 100;
                //System.out.println(yPosition);
                if (i % 2 != 0) {
                    xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                    platforms[i] = new NormalPlatform(
                            xPosition,
                            yPosition,
                            PLATFORM_WIDTH,
                            PLATFORM_HEIGHT,
                            platformsSpeed
                    );
                } else {
                    switch (random.nextInt(3)) {
                        case 0:
                            xPosition = Math.max(Math.min(random.nextInt(windowWidth - 2*PLATFORM_WIDTH), windowWidth - 400 - PLATFORM_WIDTH), 400 + PLATFORM_WIDTH);
                            platforms[i] = new MovingHorizontallyPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed,
                                    (int) xPosition - 200,
                                    (int) xPosition + 200
                            );
                            break;
                        case 1:
                            xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                            platforms[i] = new TrapPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed
                            );
                            break;
                        case 2:
                            xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                            platforms[i] = new JumpOncePlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed
                            );
                            break;
                    }
                }
            }
        }
    }
    private void updatePlatformsHardMode(){
        double yPosition;
        double xPosition;
//        for(int i = 0; i<platforms.length; i++){
//            System.out.print(platforms[i].getY() + " ");
//        }
        for (int i = 0; i < platforms.length; i++) {
            if(platforms[i].getY() >= windowHeight) {
                if(i-1<0) {
                    yPosition = (platforms[platforms.length-1]).getY() - 100;
                }
                else yPosition = platforms[i-1].getY() - 100;
                //System.out.println(yPosition);

                    switch (random.nextInt(3)) {
                        case 0:
                            xPosition = Math.max(Math.min(random.nextInt(windowWidth - 2*PLATFORM_WIDTH), windowWidth - 400 - PLATFORM_WIDTH), 400 + PLATFORM_WIDTH);
                            platforms[i] = new MovingHorizontallyPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed,
                                    (int) xPosition - 200,
                                    (int) xPosition + 200
                            );
                            break;
                        case 1:
                            xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                            platforms[i] = new TrapPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed
                            );
                            break;
                        case 2:
                            xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                            platforms[i] = new JumpOncePlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed
                            );
                            break;
                        case 3:
                            xPosition = random.nextInt(windowWidth - 2*PLATFORM_WIDTH);
                            platforms[i] = new NormalPlatform(
                                    xPosition,
                                    yPosition,
                                    PLATFORM_WIDTH,
                                    PLATFORM_HEIGHT,
                                    platformsSpeed
                            );
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
        if(counter>0) counter--;
  
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
            case "Shift":
                this.makeClosestPlatformUnusable();
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
    /**
     * Returns the y position of the highest platform
     */
    public double getHighestPlatform(){
        double max = 0;
        for(int i = 0; i<platforms.length; i++){
            if(platforms[i].getY()<max)
                max = platforms[i].getY();
        }
        return max;
    }
    /**
     * Returns the index of the closest platform to the ball
     */
    public int getClosestPlatform(){

        double ballXPosition = ball.getX();
        double ballYPosition = ball.getY();

        double platformXPosition = platforms[0].getX();
        double platformYPosition = platforms[0].getY();

        //Compute the distance between the ball and the first platform
        double smallest =  Math.sqrt( Math.pow(ballXPosition - platformXPosition, 2) + Math.pow(ballYPosition - platformYPosition , 2));
        int index = 0;
        for(int i = 1; i<platforms.length; i++){

            platformXPosition = platforms[i].getX();
            platformYPosition = platforms[i].getY();

            //now we do the same thing for every platform and select the one with the smallest distance
            double distance = Math.sqrt( Math.pow(ballXPosition - platformXPosition, 2) + Math.pow(ballYPosition - platformYPosition , 2));
            if(distance < smallest) {
                smallest = distance;
                index = i;
            }
        }
        //return the index of the closest platform
        return index;
    }

    /**
     * Make the closest platform to the ball unusable
     */
    public  void makeClosestPlatformUnusable(){
        if(counter == 0){
            int index = getClosestPlatform();
            platforms[index].setNoDraw(true);
            //System.out.println("THIS WORKS");
            //System.out.println(index);
            counter = 100;
        }
    }

}
