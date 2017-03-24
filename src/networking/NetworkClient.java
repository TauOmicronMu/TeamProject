package networking;


/**
 * A NetworkClient exists to abstract away some setup code for creating a
 * NetworkClientEngine and initializing it, then defaulting to NetworkUser
 * for everything else.
 */
abstract public class NetworkClient extends NetworkUser {
    private final String host;
    private final int port;
    public SocketFactory socketFactory;

    public NetworkClient(String serverHost, int serverPort, SocketFactory socketFactory) {
        this.host = serverHost;
        this.port = serverPort;
        this.socketFactory = socketFactory;
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
            } catch (InterruptedException e) {
                System.out.println("[INFO] NetworkClient.initialize : caught InterruptedException from stopping network client.");}
        }

        NetworkClientEngine engine = new NetworkClientEngine(socketFactory);
        engine.initialize(host, port);
        this.engine = engine;
    }
}
