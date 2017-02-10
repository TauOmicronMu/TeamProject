package networking;

import java.io.Serializable;


/**
 * A Message is a simple wrapper around the data that will be sent between the
 * client and the server. For this simple demo it can be as simple as a String.
 */
public class Message implements Serializable {
    private final String text;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
