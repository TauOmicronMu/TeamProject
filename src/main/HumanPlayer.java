package main;


public class HumanPlayer implements Player {
    private final ServerSideClientHandler serverSideClientHandler;

    public HumanPlayer(ServerSideClientHandler serverSideClientHandler) {
        this.serverSideClientHandler = serverSideClientHandler;
    }
}
