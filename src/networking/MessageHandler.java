package networking;

import java.util.function.Consumer;

interface MessageHandler extends Consumer<Message> {
    void handleMessage(Message m);
}
