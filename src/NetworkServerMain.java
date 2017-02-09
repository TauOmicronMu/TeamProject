import networking.Message;
import networking.NetworkServer;

class Server extends NetworkServer implements Runnable {

    private int gameState = 0;

    Server(int port) {
        super(port);
    }

    @Override
    public void run() {
        initialize();

        while (true) {
            try {

                Thread.sleep(5000);
                System.out.println("Server tick");

                // We must implement handleMessage (singular) below.
                handleMessages();
                sendMessage(new Message(String.valueOf(gameState)));

                // Todo: Time-based updates as well as input-based ones. This is important!
                // (Why is it important? Because physics happens without user input, that's why.)

            } catch (InterruptedException e) {
                System.out.println("Server thread interrupted:");
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void handleMessage(Message m) {
        System.out.println("Got input from client. Updating game state with server-side physics:");
        updateGameState(Integer.valueOf(m.getText()));
    }

    private void updateGameState(int input) {
        gameState += input;
        gameState %= 20;
    }
}

public class NetworkServerMain {
    public static void main(String... args) {
        Server server = new Server(8080);
        new Thread(server).run();
    }
}
