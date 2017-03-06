package networking;


import main.OpponentType;

/**
 * A NetworkClient exists to abstract away some setup code for creating a
 * NetworkClientEngine and initializing it, then defaulting to NetworkUser
 * for everything else.
 */
abstract public class NetworkClient extends NetworkUser {
    private final String host;
    private final int port;

    public NetworkClient(String serverHost, int serverPort) {
        this.host = serverHost;
        this.port = serverPort;
    }

    /**
     * Setup a NetworkClientEngine, initialize it, then assign it to the
     * NetworkEngine of this particular NetworkUser.
     */
    public void initialize(OpponentType opponentType) {
        if (this.engine != null) {
            this.engine.stop();
        }

        NetworkClientEngine engine = new NetworkClientEngine();
        engine.initialize(host, port);
        engine.sendMessage(new Message(opponentType));
        this.engine = engine;
    }

    public void stop() {
        this.engine.stop();
    }
}
