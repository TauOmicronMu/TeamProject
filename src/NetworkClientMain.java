import networking.Message;


class Client extends NetworkClient implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
                System.out.println("Client tick");
                handleMessages(this);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted:");
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void handleMessage(Message m) {
        System.out.println("Handling message: " + m.getText());
    }
}


public class NetworkClientMain {
    public static void main(String... args) {
        Client client = new Client();
        new Thread(client).start();
    }
}
