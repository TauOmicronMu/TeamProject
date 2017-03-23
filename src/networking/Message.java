package networking;

import java.awt.geom.Point2D;
import java.io.Serializable;
import shared.GameState;
import shared.OpponentType;

/**
 * A networking.Message is a simple wrapper around the data that will be sent between the
 * client and the server. For this simple demo it can be as simple as a String.
 */
public class Message implements Serializable {
    private OpponentType opponentType = null;
    private String key = null;
    private Point2D.Float coords = null;
    private GameState gameState = null;
    private Integer seed = null;
    private Boolean isMyGame = null;

    public Message(String key) {
        this.key = key;
    }

    public Message(OpponentType opponentType) {
        this.opponentType = opponentType;
    }

    public Message(Point2D.Float coords) {
        this.coords = coords;
    }

    public Message(GameState gameState, Boolean isMyGame) {
        this.gameState = gameState;
        this.isMyGame = isMyGame;
    }

    public Message(Integer seed) { this.seed = seed; }

    public Message(Boolean isMyGame) { this.isMyGame = isMyGame; }

    public String getText() {
        // Todo: this is a quick hack to stop the "demo" package complaining.
        return this.key;
    }

    public boolean isMyGame() { return isMyGame != null && isMyGame; }

    public Object getObject() {
        if (coords != null) return coords;
        if (key != null) return key;
        if (gameState != null) return gameState;
        if (opponentType != null) return opponentType;
        if (seed != null) return seed;
        return null;
    }
}
