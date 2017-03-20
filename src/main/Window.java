package main;

import networking.Message;
import networking.NetworkClient;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.awt.geom.Point2D;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import static java.lang.System.currentTimeMillis;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * main.Main class for graphics
 *
 * @author Ella
 */
class Window {

    private Screen screen = Screen.MAIN_MENU;
    private static int cursorXPosition;
    private static int cursorYPosition;
    private static CircleShader cshader;
	private static RectangleShader rshader;
	private static PowerUpShader pshader1;
	private static PowerUpShader pshader2;
	private static PowerUpShader pshader3;
    private long window;
    private int windowHeight = 800;
    private int windowWidth = 800;
    private static boolean shouldChangeToGame = false;
    private static boolean shouldQuit = false;
    private static boolean shouldChangeToMenu = false;


    Window(int windowHeight, int windowWidth) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
    }

    int getHeight() {
        return windowHeight;
    }

    int getWidth() {
        return windowWidth;
    }


    void quit() {
        shouldQuit = true;
    }
    
    void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
    }


    /**
     * Initializes the GLFW library, creating a window and any necessary shaders.
     */
    void init(GameState gameState, Main client) {
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
        cshader = new CircleShader();
		rshader = new RectangleShader();
		pshader1 = new PowerUpShader("pshader");
		pshader2 = new PowerUpShader("pshader2");
		pshader3 = new PowerUpShader("pshader3");

		clear();

        registerInputCallbacks(gameState, client);
    }



    /**
     * Utility method to terminate any graphics-related services.
     */
    void end() {
        glfwTerminate();
    }


    /**
     * Has the user pressed the Close button?
     * @return Whether the window should close now.
     */
    boolean shouldClose() {
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
            	if(type == 1)
            	{
            		pshader1.bind();
            		item.paint(this, opponent);
            		pshader1.stop();
            	}
            	if(type == 2)
            	{
            		pshader2.bind();
            		item.paint(this, opponent);
            		pshader2.stop();
            	}
            	else
            	{
            		pshader3.bind();
            		item.paint(this, opponent);
            		pshader3.stop();
            	}
            }
        }
    }

    /**
     * Redraw the screen with data from the given main.GameState.
     * @param ourGameState Information about the position about each item.
     */
    void repaint(GameState ourGameState, GameState theirGameState) {
        final boolean debug = false;
        long startTime;

        if (screen == Screen.MAIN_MENU) {
           Menu.drawAll();
           glfwSwapBuffers(window);
           return;
        }

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

        cshader.stop();
        Menu.drawBackToMenuButton();
        Menu.printScore(ourGameState.score, ourGameState.getBall(), false);
        Menu.printScore(theirGameState.score, theirGameState.getBall(), true);

        glfwSwapBuffers(window);
    }


    /**
     * Utility method to delegate to main.Circle in order to render the ball.
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
        Platform[] platforms = gameState.getPlatforms();
        for (Platform platform : platforms) {
            if (platform != null) platform.paint(this, opponent);
        }
        MovingPlatform[] movingPlatforms = gameState.getMovingPlatforms();
        for (MovingPlatform movingPlatform : movingPlatforms) {
            if (movingPlatform != null) movingPlatform.paint(this, opponent);
        }
    }


    /**
     * Use GLFW to handle the current state of the keyboard.
     */
    private void handleKeyboardInput(GameState gameState, Main client) {
        //System.out.println("[INFO] Window.handleKeyboardInput : Handling input.");
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_TRUE) {
            // System.out.println("[INFO] Window.handleKeyboardInput : Key(A) pressed.");
            gameState.getBall().moveLeft();
            if (!client.sendMessage(new Message("a"))) client.backToMenu();
        } else if (glfwGetKey(window, GLFW_KEY_D) == GLFW_TRUE) {
            // System.out.println("[INFO] Window.handleKeyboardInput : Key(D) pressed.");
            gameState.getBall().moveRight();
            client.sendMessage(new Message("d"));
        }
    }


    /**
     * Utility/wrapper method to handle both types of input.
     */
    void handleInput(GameState gameState, Main client) {
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
    private boolean withinBounds(float x, float y, float xLower, float xUpper, float yLower, float yUpper) {
        return xLower <= x && x <= xUpper && yLower <= y && y <= yUpper;
    }

    /**
     * Test whether the coordinate (x, y) is within the Play Game button.
     * @param x The coordinate's X component.
     * @param y The coordinate's Y component.
     * @return Whether the coordinate is within the button.
     */
    private boolean onPlayGameButton(float x, float y) {
        return withinBounds(x, y, -0.4f, 0.4f, -0.2f, 0f);
    }

    /**
     * Test whether the coordinate (x, y) is within the Quit button.
     * @param x The coordinate's X component.
     * @param y The coordinate's Y component.
     * @return Whether the coordinate is within the button.
     */
    private boolean onQuitButton(float x, float y) {
        return withinBounds(x, y, -0.4f, 0.4f, -0.7f, -0.5f);
    }

    /**
     * Test whether the coordinate (x, y) is within the Back To main.Menu button.
     * @param x The coordinate's X component.
     * @param y The coordinate's Y component.
     * @return Whether the coordinate is within the button.
     */
    private boolean onBackToMenuButton(float x, float y) {
        return withinBounds(x, y, -0.95f, -0.85f, 0.85f, 0.95f);
    }

    /**
     * Handle the left mouse button being pressed.
     * @param gameState The game state, in case we need to retrieve or set any
     *                  information about the objects which it comprises.
     * @param client
     */
    private void handleMouseClick(GameState gameState, Main client, float x, float y) {
        x = glScaleX(x, false, client.getWindow().getScreen());
        y = glScaleY(y);
        System.out.printf("[INFO] Mouse press at (%.2f, %.2f).\n", x, y);

        // Handle mouse input depending on which screen we're on.
        switch(screen) {

            // If we're on the main menu:
            case MAIN_MENU: {
                if (onPlayGameButton(x, y)) {
                    client.startGame(OpponentType.HUMAN);
                } else if (onQuitButton(x, y)) {
                    quit();
                }
                break;
            }

            // If we're in the game:
            case GAME: {
                if (onBackToMenuButton(x, y)) {
                    System.out.println("Back to main menu.");
                    screen = Screen.MAIN_MENU;
                }
                break;
            }
            default:
                System.err.println("Game screen not initialized!");
        }
    }


    /**
     * Register callbacks for the left mouse button being released and
     * for the cursor position changing.
     * @param gameState The game state, which may be needed inside the callbacks.
     */
    private void registerInputCallbacks(GameState gameState, Main client) {
        // Callback for when the left mouse button is released:
        GLFWMouseButtonCallbackI mousecallback = (window1, button, action, mods) -> {
            // Check that we're handling a left-mouse-button click.
            if (button != GLFW_MOUSE_BUTTON_LEFT || action != GLFW_RELEASE) return;
            DoubleBuffer xposbuf = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer yposbuf = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, xposbuf, yposbuf);
            float x = (float) xposbuf.get();
            float y = (float) yposbuf.get();
            handleMouseClick(gameState, client, x, y);
        };
        glfwSetMouseButtonCallback(window, mousecallback);
    }


    /**
     * Converts an x-coordinate to float equivalent (needed for drawing with openGL)
     *
     * @param x The X coordinate to scale to the OpenGL viewport.
     * @return The converted coordinate.
     */
    float glScaleX(float x, boolean opponent, Screen screen) {
        if(screen == Screen.MAIN_MENU) return -1.0f + x / (float) (windowWidth/2);
        if (opponent) return x / (float) (windowWidth);
        return -1.0f + x / (float) (windowWidth);
    }

    /**
     * Converts an y-coordinate to float equivalent (needed for drawing with openGL)
     *
     * @param y The Y coordinate to scale to the OpenGL viewport.
     * @return The converted coordinate.
     */
    float glScaleY(float y) {
        return 1.0f - y / (float) (windowHeight / 2);
    }


    /**
     * Converts a distance onscreen to its OpenGL-scaled equivalent.
     * @param distance The onscreen distance.
     * @return The scaled distance.
     */
    float glScaleDistance(float distance) {
        return 1.0f * distance / (windowWidth / 2);
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
