package networking;

import java.io.IOException;
import java.net.Socket;

public class SocketFactory {
    public Socket createSocket(String hostname, int port) throws IOException {
        return new Socket(hostname, port);
    }
}
