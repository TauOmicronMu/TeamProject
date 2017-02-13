package networking;

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
    protected void sendMessage(Message m) {
        engine.sendMessage(m);
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
