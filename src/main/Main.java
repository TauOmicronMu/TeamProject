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

        while (!myWindow.shouldClose()) {

            long startTime = currentTimeMillis();
            if (myWindow.getScreen() == Screen.GAME) {
                handleMessages();
                myGame.updateLogic();
                myGame.updatePhysics();
                oppGame.updateLogic();
                oppGame.updatePhysics();
            }

            myWindow.handleInput(myGame, this);
            myWindow.repaint(myGame, oppGame);  // Todo: paint oppGame

            long endTime = currentTimeMillis();
            //System.out.println("[INFO] Main.play : Loop took " + (endTime-startTime) + "ms");
            try {
                Thread.sleep(Math.max(Constants.FPS_SLEEP - (endTime-startTime), 0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        oppGame.setSeed(seed);

        myWindow.setScreen(Screen.GAME);
    }

    public static void main(String[] args) {
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
        if(someonesGame.isMyGame()) myGame = (GameState) someonesGame.getObject();
        else oppGame = (GameState) someonesGame.getObject();
    }
}
