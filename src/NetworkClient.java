import networking.Message;
import networking.NetworkEngine;

import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("Duplicates")
abstract class NetworkClient implements Consumer<Message>, INetworkClient {
    private NetworkEngine engine;

    NetworkClient() {
        engine = new NetworkEngine();
        engine.initialize();
    }



    void handleMessages(Consumer<Message> consumer) {
        Optional<Message> maybeMessage;
        while ((maybeMessage = engine.nextMessage()).isPresent()) {
            Message m = maybeMessage.get();
            consumer.accept(m);
        }
    }

    @Override
    public void accept(Message message) {
        this.handleMessage(message);
    }
}