import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Main class for graphics
 *
 * @author Ella
 */
class Window {

    private static int cursorXPosition;
    private static int cursorYPosition;
    private static Shader shader;
    private long window;
    private int windowHeight = 800;
    private int windowWidth = 800;
    private static boolean shouldChangeToGame = false;
    private static boolean shouldQuit = false;
    private static boolean shouldChangeToMenu = false;
    private static boolean initial = true;

    private GLFWVidMode videoMode;

    private Menu menu = new Menu();


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

    /**
     * Initializes the GLFW library, creating a window and any necessary shaders.
     */
    void init(GameState gameState) {
        if (!glfwInit()) {
            System.err.println("Failed to initialise GLFW");
            System.exit(1);
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        window = glfwCreateWindow(windowWidth, windowHeight, "Pinball", 0, 0);
        if (window == 0) {
            System.err.println("Failed to create window.");
            System.exit(1);
        }

        videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int windowXPosition = (videoMode.width() - windowWidth) / 2;
        int windowYPosition = (videoMode.height() - windowHeight) / 2;
        glfwSetWindowPos(window, windowXPosition, windowYPosition);

        glfwShowWindow(window);
        glfwMakeContextCurrent(window);

        GL.createCapabilities();
        shader = new Shader("shader");

        registerInputCallbacks(gameState);
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
        return glfwWindowShouldClose(window);
    }


    /**
     * Draw each powerup item in the current game state.
     */
    private void drawAllItems(GameState gameState) {
        Item[] items = gameState.getItems();
        for (Item item : items) {
            item.paint(this);
        }
    }


    /**
     * Redraw the screen with data from the given GameState.
     * @param gameState Information about the position about each item.
     */
    void repaint(GameState gameState) {
        glClear(GL_COLOR_BUFFER_BIT);
        shader.bind();

        if (gameState.getScreen() == Screen.MAIN_MENU) {
            Menu.drawAll();
        } else {
            drawAllItems(gameState);
            drawAllPlatforms(gameState);
            drawBall(gameState);
            Menu.drawBackToMenuButton();
        }

        glfwSwapBuffers(window);
    }


    /**
     * Utility method to delegate to Circle in order to render the ball.
     * @param gameState The game-state containing the ball.
     */
    private void drawBall(GameState gameState) {
        Ball ball = gameState.getBall();
        Circle.paintPinball(this, ball.getX(), ball.getY(), ball.getRadius());
    }


    /**
     * Utility method to iterate through each platform and render it.
     * @param gameState The game-state containing each platform.
     */
    private void drawAllPlatforms(GameState gameState) {
        Platform[] platforms = gameState.getPlatforms();
        for (Platform platform : platforms) {
            platform.paint(this);
        }
    }


    /**
     * Use GLFW to handle the current state of the keyboard.
     */
    private void handleKeyboardInput(GameState gameState) {
        if (gameState.getInitial() && glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_TRUE) {
            gameState.setInitial(false);
        }

        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_TRUE) {
            gameState.getBall().moveLeft();
        } else if (glfwGetKey(window, GLFW_KEY_D) == GLFW_TRUE) {
            gameState.getBall().moveRight();
        }
    }


    /**
     * Utility/wrapper method to handle both types of input.
     */
    void handleInput(GameState gameState) {
        glfwPollEvents();
        handleMouseInput(gameState);
        handleKeyboardInput(gameState);
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
    private boolean onPlayGameButton(float x, float y) {
        return withinBounds(x, y, -0.4, 0.4, -0.2, 0);
    }

    /**
     * Test whether the coordinate (x, y) is within the Quit button.
     * @param x The coordinate's X component.
     * @param y The coordinate's Y component.
     * @return Whether the coordinate is within the button.
     */
    private boolean onQuitButton(float x, float y) {
        return withinBounds(x, y, -0.4, 0.4, -0.7, -0.5);
    }

    /**
     * Test whether the coordinate (x, y) is within the Back To Menu button.
     * @param x The coordinate's X component.
     * @param y The coordinate's Y component.
     * @return Whether the coordinate is within the button.
     */
    private boolean onBackToMenuButton(float x, float y) {
        return withinBounds(x, y, -0.95, -0.85, 0.9, 0.95);
    }

    /**
     * Handle the left mouse button being pressed.
     * @param gameState The game state, in case we need to retrieve or set any
     *                  information about the objects which it comprises.
     */
    private void handleMouseInput(GameState gameState) {

        // Only handle mouse input if the left mouse button has been pressed.
        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) != GLFW_PRESS) return;

        // Todo: can't we just get cursor position from GLFW here?
        float x = glScaleX(cursorXPosition);
        float y = glScaleY(cursorYPosition);

        // Handle mouse input depending on which screen we're on.
        switch(gameState.getScreen()) {

            // If we're on the main menu:
            case MAIN_MENU: {
                if (onPlayGameButton(x, y)) {
                    shouldChangeToGame = true;
                } else if (onQuitButton(x, y)) {
                    shouldQuit = true;
                }
            }

            // If we're in the game:
            case GAME: {
                if (onBackToMenuButton(x, y)) {
                    shouldChangeToMenu = true;
                } else {
                    gameState.getBall().setX(cursorXPosition);
                    gameState.getBall().setY(cursorYPosition);
                }
            }
        }
    }


    /**
     * Register callbacks for the left mouse button being released and
     * for the cursor position changing.
     * @param gameState The game state, which may be needed inside the callbacks.
     */
    private void registerInputCallbacks(GameState gameState) {
        // Callback for when the left mouse button is released:
        GLFWMouseButtonCallbackI mousecallback = (window1, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE) {
                if (shouldChangeToMenu) {
                    gameState.setScreen(Screen.MAIN_MENU);
                    shouldChangeToMenu = false;
                } else if (shouldChangeToGame) {
                    gameState.setScreen(Screen.GAME);
                    gameState.setInitial(true);
                    shouldChangeToGame = false;
                } else if (shouldQuit) {
                    glfwSetWindowShouldClose(window1, true);
                }
            }
        };
        glfwSetMouseButtonCallback(window, mousecallback);

        // Callback to save the current position of the cursor.
        // Todo: does this need to be a callback?
        GLFWCursorPosCallbackI cursorcallback = (window, xpos, ypos) -> {
            cursorXPosition = (int) xpos;
            cursorYPosition = (int) ypos;
        };
        glfwSetCursorPosCallback(window, cursorcallback);
    }


    /**
     * Converts an x-coordinate to float equivalent (needed for drawing with openGL)
     *
     * @param x The X coordinate to scale to the OpenGL viewport.
     * @return The converted coordinate.
     */
    float glScaleX(int x) {
        return -1.0f + (float) x / (float) (windowWidth / 2);
    }

    /**
     * Converts an y-coordinate to float equivalent (needed for drawing with openGL)
     *
     * @param y The Y coordinate to scale to the OpenGL viewport.
     * @return The converted coordinate.
     */
    float glScaleY(int y) {
        return 1.0f - (float) y / (float) (windowHeight / 2);
    }


    /**
     * Converts a distance onscreen to its OpenGL-scaled equivalent.
     * @param distance The onscreen distance.
     * @return The scaled distance.
     */
    float glScaleDistance(int distance) {
        return 1.0f * (float) distance / (windowWidth / 2);
    }


}
