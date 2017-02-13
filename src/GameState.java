import java.util.Random;

class GameState {

    private Screen screen = Screen.MAIN_MENU;
    private final int windowWidth;
    private final int windowHeight;
    private Random random = new Random();
    private Ball ball;
    private Platform platforms[] = new Platform[10];
    private Item items[] = new Item[3];

    private static final int PLATFORM_WIDTH = 120;
    private static final int PLATFORM_HEIGHT = 10;
    private boolean initial;


    GameState(Window window) {
        this.windowWidth = window.getWidth();
        this.windowHeight = window.getHeight();
    }


    int getWindowWidth() {
        return windowWidth;
    }

    int getWindowHeight() {
        return windowHeight;
    }


    /**
     * Sets up a GameState by creating a ball, platforms, and items.
     */
    public void setUp() {
        ball = new Ball(windowWidth / 2, windowHeight / 2);
        generatePlatforms();
        generateItems();
    }


    /**
     * Retrieve the current screen: either the Main Menu or the Game.
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
    private void generatePlatforms() {
        for (int i = 0; i < platforms.length; i++) {
            // Todo: understand and refactor these "magic numbers".
            int xPosition = random.nextInt(windowWidth - 220) - 100;
            int yPosition = windowHeight - 200 * i;
            platforms[i] = new Platform(
                    xPosition,
                    yPosition,
                    PLATFORM_WIDTH,
                    PLATFORM_HEIGHT
            );
        }
    }

    /**
     * Update the powerup items in the current game state.
     */
    private void updateItems() {
        Item[] items = this.getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i].getY() == windowHeight + 100) {
                items[i] = null;
                switch (random.nextInt(4)) {
                    case 0:
                        items[i] = new GravDown(-10 * random.nextInt(500));
                        break;
                    case 1:
                        items[i] = new GravDown(-10 * random.nextInt(500));
                        break;
                    case 2:
                        items[i] = new GravDown(-10 * random.nextInt(500));
                        break;
                    case 3:
                        items[i] = new GravDown(-10 * random.nextInt(500));
                        break;
                }
            }
        }
    }


    void updateLogic() {
        updateItems();
    }


    void updatePhysics() {
        for (Platform platform : platforms) {
            platform.update(this);
        }

        for (Item item : items) {
            item.update(this);
        }

        ball.update(this);
    }


    /**
     * Populates the list of items in this GameState with a set of
     * power-ups. Currently, it's just using the GravUp power-up.
     */
    private void generateItems() {
        for (int i = 0; i < items.length; i++) {
            // Todo: understand and refactor this "magic number".
            items[i] = new GravDown(-1000 * i);
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


    /**
     * Set the flag indicating the current screen.
     */
    void setScreen(Screen screen) {
        this.screen = screen;
    }

    boolean getInitial() {
        return initial;
    }

    void setInitial(boolean initial) {
        this.initial = initial;
    }
}
