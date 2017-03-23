package tests.networking;

import networking.Message;
import networking.NetworkClientEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


@RunWith(MockitoJUnitRunner.class)
public class NetworkClientEngineTest {

    @InjectMocks
    private networking.NetworkClientEngine nce;

    public void setUp() {
        nce = new NetworkClientEngine();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setErr(new PrintStream(baos));
        nce = new NetworkClientEngine();
    }

    /**
     * The initialize() method should create a new Socket, then call the
     * superclass's initialize method with that socket.
      */
    @Test
    public void initialize() throws Exception {

        try {
            nce.initialize("localhost", 80);
        } catch (Exception ignored) {}
    }

    @Test
    public void stop() throws Exception {
        try {
            nce.stop();
        } catch (Exception ignored) {}
    }

    @Test
    public void nextMessage() throws Exception {
        try {
            nce.nextMessage();
        } catch (Exception ignored) {}
    }

    @Test
    public void waitForMessage() throws Exception {
        Thread x = new Thread(() -> {
            try {
                nce.waitForMessage();
            } catch (Exception ignored) {}
        });
        x.start();
        Thread.sleep(500);
        x.interrupt();
    }

    @Test
    public void sendMessage() throws Exception {
        try {
            nce.sendMessage(new Message("123"));
        } catch (Exception ignored) {}
    }

    @Test
    public void run() throws Exception {
        try {nce.run();} catch(Exception ignored) {}
    }

    @Test
    public void isRunning() throws Exception {
        try {nce.isRunning();} catch(Exception ignored){}
    }

}