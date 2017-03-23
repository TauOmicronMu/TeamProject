package networking;


/**
 * A NetworkClient exists to abstract away some setup code for creating a
 * NetworkClientEngine and initializing it, then defaulting to NetworkUser
 * for everything else.
 */
abstract public class NetworkClient extends NetworkUser {
    private final String host;
    private final int port;

    /**
     * To start a NetworkClient, we need to be able to address the Server.
     * @param serverHost The hostname of the server, as a String.
     * @param serverPort The port to which we should connect to the server on.
     */
    public NetworkClient(String serverHost, int serverPort) {
        this.host = serverHost;
        this.port = serverPort;
    }

    /**
     * After creating a NetworkClient, we should start up a NetworkClientEngine.
     * Making sure we haven't got a previous engine (and if we have, stopping it),
     * we create and initialise a new NetworkClientEngine. We also make sure to
     * keep a reference to this new engine in our `engine` field, provided by
     * NetworkUser.
     */
    protected void initialize() {
        // Handle the case where we've already initialized a NetworkClient.
        if (this.engine != null) {
            System.out.println("[INFO] NetworkClient.engineInitialize : stopping previous engine.");
            try {
                this.engine.stop();
            } catch (InterruptedException ignored) {
                System.out.println("[INFO] NetworkClient.engineInitialize : caught InterruptedException from stopping network client.");
                return;
            }
        }

        // Create, engineInitialize and store a reference to a NetworkClientEngine.
        NetworkClientEngine engine = new NetworkClientEngine();
        engine.initialize(host, port);
        this.engine = engine;
    }
}
