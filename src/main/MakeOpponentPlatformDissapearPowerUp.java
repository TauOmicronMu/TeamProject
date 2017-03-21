package main;

import main.Ball;
import main.Item;

public class MakeOpponentPlatformDissapearPowerUp extends main.Item{

    MakeOpponentPlatformDissapearPowerUp(int y, int type) {
        super(y, type);
    }

    @Override
    public void performAction(GameState game) {
        game.makeClosestPlatformUnusable();
    }

    @Override
    public void paint(Window window, boolean opponent) {
        //colour
        super.paint(window, opponent);
    }
}
