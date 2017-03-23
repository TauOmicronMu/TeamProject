package networking;

import java.awt.geom.Point2D;
import java.io.Serializable;
import main.GameState;
import main.OpponentType;

/**
 * A Networking Message is a wrapper around several different object types
 * which may be sent between the client and the server. It provides several
 * constructors for each of the allowed types, and some small utility
 * methods for accessing certain metadata about the message.
 */
public class Message implements Serializable {
    private OpponentType opponentType = null;
    private String key = null;
    private Point2D.Float coords = null;
    private GameState gameState = null;
    private Integer seed = null;
    private Boolean isMyGame = null;

    /**
     * Instantiate the message with data about a "keypress".
     * @param key The key the user has pressed.
     */
    public Message(String key) {
        this.key = key;
    }

    /**
     * Instantiate the message with the type of opponent -- either human or AI
     * -- that a player has requested.
     * @param opponentType Either HUMAN or AI; a type of opponent.
     */
    public Message(OpponentType opponentType) {
        this.opponentType = opponentType;
    }

    /**
     * Instantiate the message with a 2D coordinate.
     * @param coords The coordinate X, Y.
     */
    public Message(Point2D.Float coords) {
        this.coords = coords;
    }

    /**
     * Instantiate the message with a Game State, and describe whether it should be
     * treated as the recipient's own game state, or the recipient's opponent's.
     * @param gameState The game state object itself: info about platforms, ball etc.
     * @param isMyGame True if the game state is the recipient's own, false otherwise.
     */
    public Message(GameState gameState, Boolean isMyGame) {
        this.gameState = gameState;
        this.isMyGame = isMyGame;
    }

    /**
     * Instantiate the message with an integer Seed, intended to be used to initialise
     * a Random number generator.
     * @param seed The initial seed to use; an Integer.
     */
    public Message(Integer seed) { this.seed = seed; }


    /**
     * Assuming the Message contains a String, returns that string.
     * @return The String contained within the message.
     */
    public String getText() {
        return this.key;
    }

    /**
     * Determine whether the Game State assumed to be contained in this
     * message is to be interpreted as the message recipient's own.
     * @return True if the Game State is the recipient's, False otherwise.
     */
    public boolean isMyGame() { return isMyGame != null && isMyGame; }


    /**
     * General function to return the contained object, no matter its type.
     * @return The main message payload, as an Object.
     */
    public Object getObject() {
        if (coords != null) return coords;
        if (key != null) return key;
        if (gameState != null) return gameState;
        if (opponentType != null) return opponentType;
        if (seed != null) return seed;
        return null;
    }
}
