package networking;


/**
 * A NetworkServer exists to abstract away some setup code for creating a
 * NetworkServerEngine and initializing it, then defaulting to NetworkUser
 * for everything else.
 */
public abstract class NetworkServer extends NetworkUser {
    private final int port;

    public NetworkServer(int port) {
        this.port = port;
    }

    /**
     * Setup the NetworkServerEngine, then assign it to the `engine`
     * field of this NetworkUser (allowing us to default to NetworkUser
     * behaviour for things like message sending).
     */
    protected void initialize() {
        NetworkServerEngine netEngine = new NetworkServerEngine();
        netEngine.initialize(port);
        engine = netEngine;
    }
}