package networking;


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
    public void initialize() {
        if (this.engine != null) {
            System.out.println("[INFO] NetworkClient.initialize : stopping previous engine.");
            try {
                this.engine.stop();
            } catch (InterruptedException ignored) {
                System.out.println("[INFO] NetworkClient.initialize : caught InterruptedException from stopping network client.");
            }
        }

        NetworkClientEngine engine = new NetworkClientEngine();
        engine.initialize(host, port);
        this.engine = engine;
    }
}
