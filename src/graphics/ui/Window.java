package graphics.ui;

import client.Main;
import graphics.screen.*;
import graphics.shader.Shader;
import graphics.shader.ShaderProgram;
import graphics.shape.Circle;
import networking.Message;
import networking.NetworkClient;
import object.Ball;
import object.powerup.Item;
import object.platform.Platform;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import shared.Constants;
import shared.GameState;
import shared.OpponentType;

import java.nio.DoubleBuffer;

import static java.lang.System.currentTimeMillis;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * client.Main class for graphics
 *
 * @author Ella
 */
public class Window {

    private Screen screen = Screen.MAIN_MENU;
    private static int cursorXPosition;
    private static int cursorYPosition;
    private static ShaderProgram cshader;
	private static ShaderProgram rshader;
	private static ShaderProgram pshader1;
	private static ShaderProgram pshader2;
	private static ShaderProgram pshader3;
	private static ShaderProgram starshader;
    private long window;
    private int windowHeight = Constants.WINDOW_HEIGHT;
    private int windowWidth = Constants.WINDOW_WIDTH;
    private static boolean shouldQuit = false;
    private boolean changeAudio;
    private static boolean finishedLoading;
    private static Window instance;
    private static boolean winner = false;


    public Window(int windowHeight, int windowWidth) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        Window.instance = this;
    }

    public static Window getInstance() {
        return instance;
    }

    public void quit() {
        shouldQuit = true;
    }
    
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void setWinner(boolean w) { this.winner = w; }

    public boolean getWinner() { return winner; }

    /**
     * Initializes the GLFW library, creating a window and any necessary shaders.
     */
    public void init(GameState gameState, Main client) {
        if (!glfwInit()) {
            System.err.println("Failed to initialise GLFW");
            System.exit(1);
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        window = glfwCreateWindow(windowWidth, windowHeight, Constants.TITLE, 0, 0);
        if (window == 0) {
            System.err.println("Failed to create window.");
            System.exit(1);
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int windowXPosition = (videoMode.width() - windowWidth) / 2;
        int windowYPosition = (videoMode.height() - windowHeight) / 2;
        glfwSetWindowPos(window, windowXPosition, windowYPosition);

        glfwShowWindow(window);
        glfwMakeContextCurrent(window);

        GL.createCapabilities();
        cshader = new ShaderProgram("shaders/cshader.vs","shaders/shader.fs");
		rshader = new ShaderProgram("shaders/rshader.vs","shaders/shader.fs");
		pshader1 = new ShaderProgram("shaders/pshader.vs","shaders/shader.fs");
		pshader2 = new ShaderProgram("shaders/pshader2.vs","shaders/shader.fs");
		pshader3 = new ShaderProgram("shaders/pshader3.vs","shaders/shader.fs");
		starshader = new ShaderProgram("shaders/starshader.vs","shaders/shader.fs");

        registerInputCallbacks(gameState, client);
    }



    /**
     * Utility method to terminate any graphics-related services.
     */
    public void end() {
        glfwTerminate();
    }


    /**
     * Has the user pressed the Close button?
     * @return Whether the window should close now.
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(window) || shouldQuit;
    }


    /**
     * Draw each powerup item in the current game state.
     */
    private void drawAllItems(GameState gameState, boolean opponent) {
        Item[] items = gameState.getItems();
        for (Item item : items) {
            if (item != null)
            {
            	int type = item.getType();
            	if(type == 2)
            	{
            		pshader1.bind();
            		item.paint(this, opponent);
            		pshader1.stop();
            	}
            	if(type == 1)
            	{
            		pshader2.bind();
            		item.paint(this, opponent);
            		pshader2.stop();
            	}
            	if(type == 3)
            	{
            		pshader3.bind();
            		item.paint(this, opponent);
            		pshader3.stop();
            	}
            	if(type == 4)
            	{
            		starshader.bind();
            		item.paint(this,opponent);
            		starshader.stop();
            	}
            	if (type == 5)
                {
                    cshader.bind();
                    item.paint(this, opponent);
                    cshader.stop();
                }
            }
        }
    }

    /**
     * Prints the score, given a ball and platform.
     * @param ball
     */
    public static void printScore(int score, Ball ball, boolean opponent) {
        if(!ball.gameOver())
        {
            String scoreText = String.valueOf(score / 1000);

            Shader tshader2 = new Shader("tshader2.vs");
            int length = scoreText.length();
            tshader2.bind();
            int xOffset = opponent ? Constants.WINDOW_WIDTH/2 : 0;
            Text.draw(scoreText, xOffset + 5.8f - (length * 0.3f), 6.8f, 0.6f,opponent);
            tshader2.stop();
        }
        else
        {
            System.out.println("Final score: "+score);
        }
    }

    /**
     * Redraw the screen with data from the given shared.GameState.
     * @param ourGameState Information about the position about each item.
     */
    public void repaint(GameState ourGameState, GameState theirGameState) {
        final boolean debug = false;
        long startTime;

        if (screen == Screen.MAIN_MENU) {
           Menu.drawAll();
        } else if (screen == Screen.SETTINGS){
            Menu.drawBackToMenuButton();
            Settings.drawAudioBar();
            Settings.drawSlider();
            Settings.drawInstructions();
        } else if(screen == Screen.LOADING){
            if(!finishedLoading){
                LoadingScreen.drawLoadingWord();
                Menu.drawStars();
            }
        } else if(screen == Screen.GAME_OVER) {
            Menu.drawStars();
            GameOverScreen.drawScreen(winner);
            Menu.drawBackToMenuButton();
        }
        else {

            Menu.drawStars();
            rshader.bind();

        	if(debug) System.out.println("draw platforms for our game state");
            if(debug) startTime = currentTimeMillis();

            drawAllPlatforms(ourGameState, false);

            if(debug) System.out.println((currentTimeMillis() - startTime) + "ms");
            if(debug) System.out.println("draw platforms for opponent game state");
            if(debug) startTime = currentTimeMillis();

            drawAllPlatforms(theirGameState, true);

        	if(debug) System.out.println((currentTimeMillis() - startTime) + "ms");

            rshader.stop();

        	if(debug) System.out.println("draw items for our game state");
            if(debug) startTime = currentTimeMillis();

            drawAllItems(ourGameState, false);

            if(debug) System.out.println((currentTimeMillis() - startTime) + "ms");
            if(debug) System.out.println("draw items for opponent game state");
            if(debug) startTime = currentTimeMillis();

            drawAllItems(theirGameState, true);

            if(debug) System.out.println((currentTimeMillis() - startTime) + "ms");

            cshader.bind();

            if(debug) System.out.println("draw ball for our game state");
            if(debug) startTime = currentTimeMillis();

            drawBall(ourGameState, false);

            if(debug) System.out.println((currentTimeMillis() - startTime) + "ms");
            if(debug) System.out.println("draw ball for opponent game state");
            if(debug) startTime = currentTimeMillis();

            drawBall(theirGameState, true);

            if(debug) System.out.println((currentTimeMillis() - startTime) + "ms");

            drawCenterLine();

            cshader.stop();
            Menu.drawBackToMenuButton();
            // Menu.printScore(ourGameState.score, ourGameState.getBall(), false);
            // Menu.printScore(theirGameState.score, ourGameState.getBall(), true);
        }

        glfwSwapBuffers(window);
    }

    private void drawCenterLine() {
        glBegin(GL_LINES);
            glLineWidth(3.0f);
            glColor3i(255,255,255);
            glVertex2f(0, -1);
            glColor3i(255, 255, 255);
            glVertex2f(0, 1);
        glEnd();
    }

    /**
     * Utility method to delegate to graphics.shape.Circle in order to render the ball.
     * @param gameState The game-state containing the ball.
     */
    private void drawBall(GameState gameState, boolean opponent) {
        Ball ball = gameState.getBall();
        Circle.paintPinball(this, ball.getX(), ball.getY(), ball.getRadius(), opponent);
    }


    /**
     * Utility method to iterate through each platform and render it.
     * @param gameState The game-state containing each platform.
     */
    private void drawAllPlatforms(GameState gameState, boolean opponent) {
        // System.out.println("DRAW ALL PLATFORMS :O :O :O :O :O");
        Platform[] platforms = gameState.getBasicPlatforms();
        for (Platform platform : platforms) {
            if (platform != null) platform.paint(this, opponent);
        }
    }


    /**
     * Use GLFW to handle the current state of the keyboard.
     */
    private void handleKeyboardInput(GameState gameState, NetworkClient client) {
        //System.out.println("[INFO] Window.handleKeyboardInput : Handling input.");
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_TRUE) {
            // System.out.println("[INFO] Window.handleKeyboardInput : Key(A) pressed.");
            gameState.getBall().moveLeft();
            client.sendMessage(new Message("a"));
        } else if (glfwGetKey(window, GLFW_KEY_D) == GLFW_TRUE) {
            // System.out.println("[INFO] Window.handleKeyboardInput : Key(D) pressed.");
            gameState.getBall().moveRight();
            client.sendMessage(new Message("d"));
        } else if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_TRUE) {
            // System.out.println("[INFO] Window.handleKeyboardInput : Key(D) pressed.");
            gameState.getBall().doubleJump();
            client.sendMessage(new Message("Space"));
        } else if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_TRUE) {
            // client.sendMessage(new Message("Shift"));
        }
    }


    /**
     * Utility/wrapper method to handle both types of input.
     */
    public void handleInput(GameState gameState, NetworkClient client) {
        glfwPollEvents();
        // We don't handle mouse input here as that's event-driven.
        handleKeyboardInput(gameState, client);
    }


    /**
     * Check whether values X and Y lie within the rectangle described by the parameters
     * xLower, yLower, xUpper and yUpper.
     * @param x The X component of the coordinate to test.
     * @param y The Y component of the coordinate to test.
     * @param xLower The X component of the bottom-left-hand corner.
     * @param yLower The Y component of the bottom-left-hand corner.
     * @param xUpper The X component of the top-right-hand corner.
     * @param yUpper The Y component of the top-right-hand corner.
     * @return Whether (x, y) lies in the rectangle described.
     */
    private boolean withinBounds(double x, double y, double xLower, double xUpper, double yLower, double yUpper) {
        return xLower <= x && x <= xUpper && yLower <= y && y <= yUpper;
    }

    /**
     * Test whether the coordinate (x, y) is within the Play Game button.
     * @param x The coordinate's X component.
     * @param y The coordinate's Y component.
     * @return Whether the coordinate is within the button.
     */
    private boolean onSinglePlayerButton(double x, double y) {
        return withinBounds(x, y, -0.4, 0.4, -0.26, -0.06);
    }

    private boolean onMultiplayerButton(double x, double y) {
        return withinBounds(x, y, -0.4, 0.4, -0.48, -0.28);
    }

    private boolean onSettingsButton(double x, double y){
        return withinBounds(x, y, -0.4, 0.4, -0.7, -0.5);
    }

    private boolean onAudioBar(double x, double y) {
        double newX = ((1+x)*2)-1;
        return withinBounds(newX, y, -0.2, 0.6, -0.42, -0.37);
    }

    /**
     * Test whether the coordinate (x, y) is within the Quit button.
     * @param x The coordinate's X component.
     * @param y The coordinate's Y component.
     * @return Whether the coordinate is within the button.
     */
    private boolean onQuitButton(double x, double y) {
        return withinBounds(x, y, -0.4, 0.4, -0.92, -0.72);
    }

    /**
     * Test whether the coordinate (x, y) is within the Back To graphics.screen.Menu button.
     * @param x The coordinate's X component.
     * @param y The coordinate's Y component.
     * @return Whether the coordinate is within the button.
     */
    private boolean onBackToMenuButton(double x, double y) {
        return withinBounds(x, y, /*-0.95*/ -0.97, -0.85, 0.845f, 0.95f);
    }

    /**
     * Handle the left mouse button being pressed.
     * @param gameState The game state, in case we need to retrieve or set any
     *                  information about the object which it comprises.
     * @param client
     */
    private void handleMouseClick(GameState gameState, Main client, double x, double y) {
        x = glScaleX(x, false, client.getWindow().getScreen());
        y = glScaleY(y);
        System.out.printf("[INFO] Mouse press at (%.2f, %.2f).\n", x, y);

        // Handle mouse input depending on which screen we're on.
        switch(screen) {
            // If we're on the main menu:
            case MAIN_MENU: {
                if (onMultiplayerButton(x, y)) {
                    System.out.println("Multiplayer button clicked");
                    finishedLoading = false;
                    screen = Screen.LOADING;
                    client.startGame(OpponentType.HUMAN);
                } else if (onQuitButton(x, y)) {
                    quit();
                } else if (onSinglePlayerButton(x, y)) {
                    System.out.println("Single player button clicked");
                    client.startGame(OpponentType.AI);
                } else if (onSettingsButton(x, y)) {
                    System.out.println("Settings button clicked");
                    screen = Screen.SETTINGS;
                }
                break;
            }

            // If we're in the game:
            case GAME: {
                if (onBackToMenuButton(x, y)) {
                    System.out.println("Back to main menu.");
                    screen = Screen.MAIN_MENU;
                } else {
                    // Todo: N.B. This is for demonstrating the server-client sync.
                    gameState.getBall().setX(cursorXPosition);
                    gameState.getBall().setY(cursorYPosition);
                }
                break;
            }

            case SETTINGS: {
                if (onBackToMenuButton(x, y)) {
                    screen = Screen.MAIN_MENU;
                    changeAudio = false;
                    // break;
                } else if (onAudioBar(x, y)){
                    System.out.println("On audio bar.");
                    changeAudio = true;
                    double newX = ((1+x)*2)-1;
                    Settings.setXLower(newX - 0.025);
                    Settings.drawSlider();
                }
                if(onAudioBar(x, y)){
                    changeAudio = true;
                }
                if(!onAudioBar(x, y)){
                    changeAudio = false;
                }
                break;
            }

            case GAME_OVER: {
                if (onBackToMenuButton(x, y)) {
                    System.out.println("Back to main menu.");
                    screen = Screen.MAIN_MENU;
                    break;
                }
            }
            case LOADING: {
                // Do nothing
                break;
            }
            default:
                System.err.println("[WARN] Window.handleMouseClick : Game screen not initialized!");
        }
    }


    /**
     * Register callbacks for the left mouse button being released and
     * for the cursor position changing.
     * @param gameState The game state, which may be needed inside the callbacks.
     */
    private void registerInputCallbacks(GameState gameState, Main client) {
        // Callback for when the left mouse button is released:
        GLFWMouseButtonCallbackI mousecallback = (window, button, action, mods) -> {
            // Check that we're handling a left-mouse-button click.
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE) {
                DoubleBuffer xposbuf = BufferUtils.createDoubleBuffer(1);
                DoubleBuffer yposbuf = BufferUtils.createDoubleBuffer(1);
                glfwGetCursorPos(window, xposbuf, yposbuf);
                double x = xposbuf.get();
                double y = yposbuf.get();
                handleMouseClick(gameState, client, x, y);
            }
        };
        glfwSetMouseButtonCallback(window, mousecallback);
        GLFWCursorPosCallbackI cursorcallback = (window, xpos, ypos) -> {
            cursorXPosition = (int) xpos;
            cursorYPosition = (int) ypos;
            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS && screen == Screen.SETTINGS) {
                double x = glScaleX(cursorXPosition);
                double y = glScaleY(cursorYPosition);
                if(changeAudio) {
                    double newX = ((1+x)/0.5)*2+-1;
                    Settings.setXLower(newX - 0.025);
                    Settings.drawSlider();
                    System.out.println("Volume Percentage: " + Settings.volumePercentage());
                }
            }
        };
        glfwSetCursorPosCallback(window, cursorcallback);
    }


    /**
     * Converts an x-coordinate to float equivalent (needed for drawing with openGL)
     *
     * @param x The X coordinate to scale to the OpenGL viewport.
     * @return The converted coordinate.
     */
    public double glScaleX(double x, boolean opponent, Screen screen) {
        if(screen == Screen.MAIN_MENU) return -1.0f + x / (float) (windowWidth/2);
        if (opponent) return x / (float) (windowWidth);
        return -1.0f + x / (float) (windowWidth);
    }

    double glScaleX(double x) { return -1.0f + x / (float) windowWidth/2; }

    /**
     * Converts an y-coordinate to float equivalent (needed for drawing with openGL)
     *
     * @param y The Y coordinate to scale to the OpenGL viewport.
     * @return The converted coordinate.
     */
    public double glScaleY(double y) {
        return 1.0f - y / (float) (windowHeight / 2);
    }


    /**
     * Converts a distance onscreen to its OpenGL-scaled equivalent.
     * @param distance The onscreen distance.
     * @return The scaled distance.
     */
    public double glScaleDistance(double distance) {
        return 1.0f * (double) distance / (windowWidth / 2);
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
