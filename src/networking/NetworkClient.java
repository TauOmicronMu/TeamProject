package networking;


/**
 * A NetworkClient exists to abstract away some setup code for creating a
 * NetworkClientEngine and initializing it, then defaulting to NetworkUser
 * for everything else.
 */
abstract public class NetworkClient extends NetworkUser {
    private String host;
    private int port;

    public NetworkClient(String serverHost, int serverPort) {
        this.host = serverHost;
        this.port = serverPort;
    }

    /**
     * Setup a NetworkClientEngine, initialize it, then assign it to the
     * NetworkEngine of this particular NetworkUser.
     */
    protected void initialize() {
        NetworkClientEngine engine = new NetworkClientEngine();
        engine.initialize(host, port);
        this.engine = engine;
    }
}
