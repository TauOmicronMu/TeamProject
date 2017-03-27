package main;

import java.io.IOException;
import java.util.Hashtable;

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
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    private void initializeGame() throws ClassNotFoundException, IOException {
        window = new Window(windowHeight, windowWidth);
        game = new GameState(windowWidth, windowHeight);
        
        System.out.println("Reading Hashtable...");		
        AI t = new AI(game);  //run this before the game start
        Hashtable<String, Double> database = t.getDB();   		
        AIThread ai = new AIThread(database, game);        		
        game.ai = ai;    		
        ai.start();
        
        game.setUp();
        game.generatePlatforms();
        game.generateItems();
        window.init(game, this);
    }
    /**
     * The play() method implements the main game loop.
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    private void play() throws ClassNotFoundException, IOException {
        initializeGame();
        Menu.drawAll();
        
        double gcCounter = 0.0;
        
        while (!window.shouldClose()) {
        	
        	if(gcCounter % 10000 == 0) System.gc();
        	gcCounter++;
        	
            if (window.getScreen() == Screen.GAME) {
                handleMessages();
                game.updateLogic();
                game.updatePhysics();
            }
            window.handleInput(game, this);
            window.repaint(game);

            try {
                Thread.sleep(1000/700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        window.end();
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
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
