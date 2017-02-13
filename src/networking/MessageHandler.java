package networking;

/**
 * This MessageHandler interface exists to ensure that anything extending
 * NetworkUser (an *abstract* class) must define a handleMessage method.
 * This method is called whenever we receive a message on a socket, and
 * is essential for both the client and server to implement.
 */
interface MessageHandler {
    void handleMessage(Message m);
}
