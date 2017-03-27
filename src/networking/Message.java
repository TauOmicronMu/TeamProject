package networking;

import java.awt.geom.Point2D;
import java.io.Serializable;
import main.GameState;

/**
 * A networking.Message is a simple wrapper around the data that will be sent between the
 * client and the server. For this simple demo it can be as simple as a String.
 */
public class Message implements Serializable {
    private String key = null;
    private Point2D.Float coords = null;
    private GameState gameState = null;

    public Message(String key) {
        this.key = key;
    }

    public Message(Point2D.Float coords) {
        this.coords = coords;
    }

    public Message(GameState gameState) {
        this.gameState = gameState;
    }

    public String getText() {
        // Todo: this is a quick hack to stop the "demo" package complaining.
        return this.key;
    }

    public Object getObject() {
        if (coords != null) return coords;
        if (key != null) return key;
        if (gameState != null) return gameState;
        return null;
    }
}
