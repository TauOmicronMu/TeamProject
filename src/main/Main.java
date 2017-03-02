package main;

import networking.Message;
import networking.NetworkClient;

public class Main extends NetworkClient {

    private static final int windowHeight = 800;
    private static final int windowWidth = 800;

    public Window getWindow() {
        return window;
    }

    private Window window;
    private GameState game;

    private Main(String host, int port) {
        super(host, port);
    }

    /**
     * Setup the game by creating a Window and a GameState,
     * and initializing both of them.
     */
    private void initializeGame() {
        window = new Window(windowHeight, windowWidth);
        game = new GameState(windowWidth, windowHeight);
        game.setUp();
        window.init(game, this);
    }

    /**
     * The play() method implements the main game loop.
     */
    private void play() {
        initializeGame();
        Menu.drawAll();
        while (!window.shouldClose()) {
            if (window.getScreen() == Screen.GAME) {
                handleMessages();
                game.updateLogic();
                game.updatePhysics();
            }
            window.handleInput(game, this);
            window.repaint(game);

            try {
                Thread.sleep(1000/120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        window.end();
    }

    public static void main(String[] args) {
        Main main = new Main("localhost", 8080);
        main.play();
    }

    /**
     * This method is called whenever we receive a message from the Server.
     * @param message The message we've just received.
     */
    @Override
    public void handleMessage(Message message) {
        // Todo: This is probably really inefficient.
        game = (GameState) message.getObject();
    }
}
