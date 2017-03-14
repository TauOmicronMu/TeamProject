package main;

public class Match implements Runnable {

    private Player playerOne;
    private Player playerTwo;

    public Match(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    @Override
    public void run() {
        System.out.println("[INFO] Match.run: Starting match...");

        System.out.println("[INFO] Match.run: Match concluded.");
    }
}
