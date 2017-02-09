package networking;

public abstract class NetworkServer extends NetworkUser {
    private int port;

    public NetworkServer(int port) {
        this.port = port;
    }

    protected void initialize() {
        NetworkServerEngine netEngine = new NetworkServerEngine();
        netEngine.initialize(port);
        engine = netEngine;
    }
}