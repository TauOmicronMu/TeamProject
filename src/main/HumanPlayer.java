package main;

import networking.Message;
import networking.NetworkClient;

class HumanPlayer implements Player {
    private final NetworkClient networkClient;

    HumanPlayer(NetworkClient client) {
        this.networkClient = client;
    }

    @Override
    public Message waitForMessage() {
        return networkClient.waitForMessage();
    }
}
