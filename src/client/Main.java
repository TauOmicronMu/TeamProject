package client;

import audio.AudioEngine;
import graphics.screen.Menu;
import graphics.screen.Screen;
import graphics.ui.Window;
import networking.Message;
import networking.NetworkClient;
import shared.Constants;
import shared.GameState;
import shared.OpponentType;

import static java.lang.System.currentTimeMillis;

public class Main extends NetworkClient {

    private static final int windowHeight = Constants.WINDOW_HEIGHT;
    private static final int windowWidth = Constants.WINDOW_WIDTH;
    private static NetworkClient instance;

    public static NetworkClient getInstance() {
        return instance;
    }

    public Window getWindow() {
        return myWindow;
    }

    private Window myWindow;
    private GameState myGame, oppGame;

    private Main(String host, int port) {
        super(host, port);
        Main.instance = this;
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

            // Clear *all of the stuff* that gets
            // created by openGL that we can't remove
            // manually
            if(gcCounter % 20 == 0) System.gc();
            gcCounter++;

            if (myWindow.getScreen() == Screen.GAME) {
                handleMessages();
                myGame.updatePhysics(timeStep);
                oppGame.updatePhysics(timeStep);
            }

            myWindow.clear();
            myWindow.handleInput(myGame, this);
            myWindow.repaint(myGame, oppGame);
            long endTime = currentTimeMillis();
            timeStep = endTime - startTime;

            /*
            if (timeStep >= Constants.MAX_TIME_PER_FRAME ) {
                if (myWindow.getScreen() == Screen.GAME)
                    System.err.println("[WARN] Main.play : Exceeded target time per frame: " + timeStep);
                continue;
            }*/

/*            try {
                Thread.sleep(Constants.MAX_TIME_PER_FRAME - timeStep);
                endTime = currentTimeMillis();
                timeStep = endTime - startTime
            } catch (InterruptedException e) {
                System.err.println("[WARN] Main.play : main loop interrupted.");
                break;
            }*/
        }
        myWindow.end();
        System.out.println("[INFO] Main.play : Window closed.");
        AudioEngine.getInstance().destroy();
        System.exit(0);
    }

    public void startGame(OpponentType opponentType) {
        initialize();
        sendMessage(new Message(opponentType));

        new Thread(() -> {
            // Wait for the seed from the server, because we can't create the game states
            // without it.
            System.out.println("[INFO] Main.initializeGame : Waiting for seed...");
            Message msg = waitForMessage();
            int seed = (int) msg.getObject();
            System.out.println("[INFO] Main.initializeGame : Received seed => " + seed);

            myGame.setSeed(seed);
            myGame.generatePlatforms();
            myGame.generateItems();
            oppGame.setSeed(seed);
            oppGame.generatePlatforms();
            oppGame.generateItems();

            myWindow.setScreen(Screen.GAME);
        }).start();
    }

    public static void main(String[] args) {
        AudioEngine.isClient = true;
        Main main = new Main(Constants.HOST, Constants.PORT);
        main.play();
    }

    /**
     * This method is called whenever we receive a message from the Server.
     * @param someonesGame The message we've just received.
     */
    @Override
    public void handleMessage(Message someonesGame) {
        // Todo: This is probably really inefficient.
        // Nah fam I'm sure it's all gucci
        if (someonesGame.isMyGame()) {
            myGame = (GameState) someonesGame.getObject();
            if(myGame.gameOver()) {
                Window.getInstance().setWinner(false); // The other player won!
                Window.getInstance().setScreen(Screen.GAME_OVER);
            }
        }
        else {
            oppGame = (GameState) someonesGame.getObject();
            if(oppGame.gameOver()) {
                Window.getInstance().setWinner(true); // We won!
                Window.getInstance().setScreen(Screen.GAME_OVER);
            }
        }
    }
}
