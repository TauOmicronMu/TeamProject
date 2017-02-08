import networking.Message;

class Server extends NetworkClient implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
                System.out.println("Server tick");
                handleMessages(this);
            } catch (InterruptedException e) {
                System.out.println("Server thread interrupted:");
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void handleMessage(Message m) {
        System.out.println(m.getText());
    }
}

public class NetworkServerMain {
    public static void main(String... args) {
        Server server = new Server();
        new Thread(server).run();
    }
}
