package main;

import networking.Message;
import networking.NetworkClient;

public class Main extends NetworkClient {

    private static final int windowHeight = 800;
    private static final int windowWidth = 800;

    private Window window;
    private GameState game;

    private Main(String host, int port) {
        super(host, port);
    }

    private void initializeGame() {
        window = new Window(windowHeight, windowWidth);
        game = new GameState(windowWidth, windowHeight);
        game.setUp();
        window.init(game, this);
    }

    private void play() {
        initializeGame();
        Menu.drawAll();
        while (!window.shouldClose()) {
            if (game.getScreen() == Screen.GAME) {
                handleMessages();
                //game.updateLogic();
                //game.updatePhysics();
            }
            window.handleInput(game, this);
            window.repaint(game);
        }
        window.end();
    }

    public static void main(String[] args) {
        Main main = new Main("localhost", 8080);
        main.play();
    }

    @Override
    public void handleMessage(Message m) {
        // Todo: This is probably really inefficient.
        System.out.println("Updating with gamestate from server");
        game = (GameState) m.getObject();
    }
}
