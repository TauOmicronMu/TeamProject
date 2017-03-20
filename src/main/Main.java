package main;

import networking.Message;
import networking.NetworkClient;

import static java.lang.System.currentTimeMillis;

public class Main extends NetworkClient {

    private static final int windowHeight = Constants.WINDOW_HEIGHT;
    private static final int windowWidth = Constants.WINDOW_WIDTH;

    public Window getWindow() {
        return myWindow;
    }

    private Window myWindow;
    private GameState myGame, oppGame;

    private Main(String host, int port) {
        super(host, port);
    }


    /**
     * The play() method implements the main myGame loop.
     */
    private void play() {

        myGame = new GameState(windowWidth, windowHeight);
        oppGame = new GameState(windowWidth, windowHeight);

        myWindow = new Window(windowHeight, windowWidth);
        myWindow.init(myGame, this);

        Menu.drawAll();

        long gcCounter = 0;
        long timeStep = 0;

        while (!myWindow.shouldClose()) {
            long startTime = currentTimeMillis();

            // Force garbage collection of LWJGL stuff. Todo: use the LWJGL stack thing to allocate our buffers.
            if(gcCounter % 10 == 0) System.gc();
            gcCounter++;

            if (myWindow.getScreen() == Screen.GAME) {
                handleMessages();
                myGame.updatePhysics(timeStep);
                oppGame.updatePhysics(timeStep);
            }

            myWindow.handleInput(myGame, this);
            myWindow.repaint(myGame, oppGame);  // Todo: paint oppGame
            long endTime = currentTimeMillis();
            timeStep = endTime - startTime;

            // If we've overstepped our time, just continue and hope we catch up.
            if (timeStep >= Constants.TARGET_MS_PER_FRAME) {
                if (myWindow.getScreen() == Screen.GAME)
                    System.out.println("[INFO] Main.play : Overshot max timestep: " + timeStep);
                continue;
            }

            // Otherwise cap the frame-rate at TARGET_FPS.
            try {
                Thread.sleep(Constants.TARGET_MS_PER_FRAME-timeStep);
            } catch (InterruptedException e) {
                System.out.println("[WARN] Main.play : Main game loop was interrupted.");
                break;
            }

            // If we capped the frame-rate, our time step should include the thread sleep.
            timeStep = currentTimeMillis() - startTime;
        }

        myWindow.end();
        System.out.println("[INFO] Main.play : Window closed.");
        AudioEngine.getInstance().destroy();
        System.exit(0);
    }

    void startGame(OpponentType opponentType) {
        initialize();
        sendMessage(new Message(opponentType));

        // Wait for the seed from the server, because we can't create the game states
        // without it.
        System.out.println("[INFO] Main.initializeGame : Waiting for seed...");
        Message msg = waitForMessage();
        int seed = (int) msg.getObject();
        System.out.println("[INFO] Main.initializeGame : Received seed => " + seed);

        myGame.setSeed(seed);
        myGame.generateItems();
        myGame.generatePlatforms();
        oppGame.setSeed(seed);
        oppGame.generateItems();
        oppGame.generatePlatforms();

        myWindow.setScreen(Screen.GAME);
    }

    public static void main(String[] args) {
        Main main = new Main(Constants.HOST, Constants.PORT);
        main.play();
    }

    /**
     * This method is called whenever we receive a message from the Server.
     * @param messageFromServer The message we've just received.
     */
    @Override
    public void handleMessage(Message messageFromServer) {
        if (messageFromServer.getText() != null && messageFromServer.getText().equals(Constants.END_GAME)) {
            backToMenu();
            return;
        }
        if (messageFromServer.isMyGame()) myGame = (GameState) messageFromServer.getObject();
        else oppGame = (GameState) messageFromServer.getObject();
    }

    public void backToMenu() {
        myWindow.clear();
        System.out.println("[INFO] Main.backToMenu : Returning to main menu.");
        myWindow.setScreen(Screen.MAIN_MENU);
        Menu.drawAll();
    }
}
