package networking;

import java.awt.geom.Point2D;
import java.net.Socket;

/**
 * Represents each individual player connected to the server.
 */
public class Client extends NetworkUser {
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }


    /**
     * This method is called whenever we receive a message from a client.
     * We should check whether the client has sent keyboard or mouse input
     * and act accordingly.
     * @param message The message we've been sent by the Client.
     */
    @Override
    public void handleMessage(Message message) {
        Object o = message.getObject();
        if (o.getClass() == Point2D.Float.class) {
            handleMouseClick((Point2D.Float) o);
        } else if (o.getClass() == String.class) {
            handleKeyPress((String) o);
        } else {
            System.out.println("Message didn't translate properly");
        }
    }

    private void handleKeyPress(String o) {

    }

    private void handleMouseClick(Point2D.Float o) {

    }
}
