package main;

import com.sun.org.apache.xpath.internal.SourceTree;
import networking.Message;
import networking.NetworkClient;

public class Main extends NetworkClient {

    private static final int windowHeight = Constants.WINDOW_HEIGHT;
    private static final int windowWidth = Constants.WINDOW_WIDTH;

    public Window getWindow() {
        return myWindow;
    }

    private Window myWindow, oppWindow;
    private GameState myGame, oppGame;

    private Main(String host, int port) {
        super(host, port);
    }

    /**
     * Setup the myGame by creating a Window and a GameState,
     * and initializing both of them.
     */

    private void initializeGame() {



    }

    /**
     * The play() method implements the main myGame loop.
     */
    private void play() {
        initializeGame();
        Menu.drawAll();
        while (!myWindow.shouldClose()) {
            if (myWindow.getScreen() == Screen.GAME) {
                handleMessages();
                myGame.updateLogic();
                myGame.updatePhysics();
                oppGame.updateLogic();
                oppGame.updatePhysics();
            }
            myWindow.handleInput(myGame, this);
            myWindow.repaint(myGame);
            oppWindow.repaint(oppGame);

            try {
                Thread.sleep(Constants.FPS_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        myWindow.end();
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

        myWindow = new Window(windowHeight, windowWidth);
        myGame = new GameState(seed, windowWidth, windowHeight);
        myGame.setUp();
        myWindow.init(myGame, this);

        oppWindow = new Window(windowHeight, windowWidth);
        oppGame = new GameState(seed, windowWidth, windowHeight);
        oppGame.setUp();
        oppWindow.init(oppGame, this);

        myWindow.setScreen(Screen.GAME);
    }

    public static void main(String[] args) {
        Main main = new Main(Constants.HOST, Constants.PORT);
        main.play();
    }

    /**
     * This method is called whenever we receive a message from the Server.
     * @param message The message we've just received.
     */
    @Override
    public void handleMessage(Message someonesGame) {
        // Todo: This is probably really inefficient.
        // Nah fam I'm sure it's all gucci
        if(someonesGame.isMyGame()) myGame = (GameState) someonesGame.getObject();
        else oppGame = (GameState) someonesGame.getObject();
    }
}
