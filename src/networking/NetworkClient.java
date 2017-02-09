package networking;


abstract public class NetworkClient extends NetworkUser {
    private String host;
    private int port;

    public NetworkClient(String serverHost, int serverPort) {
        this.host = serverHost;
        this.port = serverPort;
    }

    protected void initialize() {
        NetworkClientEngine engine = new NetworkClientEngine();
        engine.initialize(host, port);
        this.engine = engine;
    }
}
