package networking;

import java.util.Optional;


abstract class NetworkUser implements MessageHandler {
    NetworkEngine engine;

    protected void sendMessage(Message m) {
        engine.sendMessage(m);
    }

    protected void handleMessages() {
        Optional<Message> maybeMessage;
        while ((maybeMessage = engine.nextMessage()).isPresent()) {
            Message m = maybeMessage.get();
            this.accept(m);
        }
    }

    @Override
    public void accept(Message message) {
        this.handleMessage(message);
    }
}