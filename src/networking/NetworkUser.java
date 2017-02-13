package networking;

import main.GameState;

import java.util.Optional;

/**
 * A NetworkUser is a handy way of reusing message-routing code between
 * both NetworkServer and NetworkClient.
 */
abstract class NetworkUser implements MessageHandler {
    NetworkEngine engine;

    /**
     * Wrapper method around the NetworkEngine's sendMessage method.
     * @param m The message to send to our connected device.
     */
    public boolean sendMessage(Message m) {
        if (m.getObject().getClass() == GameState.class) {
            GameState g = (GameState) m.getObject();
            System.out.println("Sending game state with ball: " + g.getBall());
        }
        return engine.sendMessage(m);
    }

    /**
     * Call handleMessage for each message returned by the NetworkEngine.
     */
    protected void handleMessages() {
        Optional<Message> maybeMessage;
        while ((maybeMessage = engine.nextMessage()).isPresent()) {
            Message m = maybeMessage.get();
            this.handleMessage(m);
        }
    }
}
