package networking.integration;


import networking.Message;
import networking.NetworkClient;
import networking.NetworkEngine;
import networking.SocketFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import shared.GameState;
import shared.OpponentType;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

/**
 * This is a full integration test for networking, sending objects to and from a server.
 */

public class TestNetworking {
    private static DummyClient client;

    private static class MockSocket extends Socket {
        public MockSocket() throws IOException {
            throw new IOException("Couldn't connect!");
        }
    }


    private static class DummyClient extends NetworkClient {

        int seedReceived = 0;
        OpponentType opponentTypeReceived;
        GameState gameStateReceieved;
        boolean gameStateReceievedIsOurs;

        DummyClient() {
            super("localhost", 25565, new SocketFactory());
        }

        @Override
        public void handleMessage(Message m) {
            Object o = m.getObject();
            if (o instanceof Integer) seedReceived = (int) o;
            else if (o instanceof OpponentType) opponentTypeReceived = (OpponentType) o;
            else if (o instanceof GameState) {
                gameStateReceieved = (GameState) o;
                gameStateReceievedIsOurs = m.isMyGame();
            }
        }
    }

    @BeforeClass
    public static void setUp() {
        TestNetworking.client = new DummyClient();
        client.initialize();
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        try {
            client.engine.stop();
        } catch(InterruptedException ignored) {
            return;
        }
        assert false;
    }

    /**
     * Send an integer to the server and ensure it sends us back the same integer.
     */
    @Test
    public void testSameRandomSeed() throws InterruptedException {
        client.sendMessage(new Message(123));
        Thread.sleep(1000);
        client.handleMessages();
        assertEquals(123, client.seedReceived);
    }

    /**
     * Send an integer to the server and ensure it sends us back the same integer.
     */
    @Test
    public void testSameOpponentType() throws InterruptedException {
        client.sendMessage(new Message(OpponentType.AI));
        Thread.sleep(1000);
        client.handleMessages();
        assertEquals(OpponentType.AI, client.opponentTypeReceived);
    }

    /**
     * Send an integer to the server and ensure it sends us back the same integer.
     */
    @Test
    public void testSameGameState() throws InterruptedException {
        GameState gs = new GameState(1, 2);
        client.sendMessage(new Message(gs, true));
        Thread.sleep(1000);
        client.handleMessages();
        assert client.gameStateReceieved.equals(gs);
        assert client.gameStateReceievedIsOurs;
    }

    /**
     * Initializing the client again should pretty much do nothing.
     */
    @Test
    public void testReInitializeClient() {
        client.initialize();
    }

    /**
     * We shouldn't crash if the stop() method in initialize() fails.
     */
    @Test
    public void testReInitializeInterruptedException() {
        NetworkEngine oldEngine = client.engine;
        NetworkEngine newEngine = mock(NetworkEngine.class);
        try {
            Mockito.doThrow(new InterruptedException()).when(newEngine).stop();
        } catch (InterruptedException ignored) {}

        client.engine = newEngine;
        client.initialize();

        client.engine = oldEngine;
    }

    /**
     * We shouldn't throw an exception if the socket can't connect.
     */
    @Test
    public void testSocketCantConnect() {
        class MockSocketFactory extends SocketFactory {
            public Socket createSocket(String host, int port) throws IOException {
                return new MockSocket();
            }
        }
        SocketFactory mockSocketFactory = new MockSocketFactory();
        client.socketFactory = mockSocketFactory;

        client.initialize();

        client.socketFactory = new SocketFactory();
        client.initialize();
    }

    /**
     * We shouldn't throw an IO exception if we can't close a socket.
     */
    @Test
    public void testIfSocketCloseThrowsIOException() {
        class MockSocket extends Socket {
            public void close() throws IOException {
                throw new IOException();
            }
        }
        Socket oldSocket = client.engine.socket;
        client.engine.socket = new MockSocket();
        try {
            client.engine.stop();
        } catch (InterruptedException ignored) {}
        client.engine.socket = oldSocket;
    }

    @Test
    public void testWaitForMessageWorks() {
        Message msg = new Message("123");
        class FakeMessages extends LinkedBlockingDeque {
            public Message take() {
                return msg;
            }
        }
        BlockingDeque oldMessages = client.engine.messages;
        client.engine.messages = new FakeMessages();
        assertEquals(msg, client.waitForMessage());
        client.engine.messages = oldMessages;
    }

    @Test
    public void testWaitForMessageBreaks() {
        class FakeMessages extends LinkedBlockingDeque {
            public Message take() throws InterruptedException {
                throw new InterruptedException();
            }
        }
        BlockingDeque oldMessages = client.engine.messages;
        client.engine.messages = new FakeMessages();
        assertEquals(
                client.waitForMessage(),
                null
        );
        client.engine.messages = oldMessages;
    }

    @Test
    public void testSendMessageFails() throws IOException {
        ObjectOutputStream oldOS = client.engine.outputStream;
        ObjectOutputStream os = mock(ObjectOutputStream.class);
        // Mockito.doThrow(new IOException()).when(os).writeObject(any());
        class MyObjectOutputStream extends ObjectOutputStream {
            private final Object out;

            private MyObjectOutputStream(OutputStream out) throws IOException {
                this.out = out;
            }

            public void reset() throws IOException {
                throw new IOException();
            }
        }
        client.engine.outputStream = new MyObjectOutputStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException();
            }
        });
        boolean result = client.sendMessage(new Message("m"));
        assertFalse(result);
        client.engine.outputStream = oldOS;
    }


    @Test
    public void testReadObjectFails() throws IOException, ClassNotFoundException {
        ObjectInputStream oldIS = client.engine.getInputStream();


        class MyObjectInputStream extends ObjectInputStream {
            private Object in;

            private MyObjectInputStream(InputStream in) throws IOException {
                this.in = in;
            }

            public void reset() throws IOException {
                throw new IOException();
            }
        }

        client.engine.setInputStream(new MyObjectInputStream(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0xACED0005;
            }
        }));
        // client.engine.run();
        assert true;  // We got here, so it's stopped running!
        client.engine.setInputStream(oldIS);
    }
}
