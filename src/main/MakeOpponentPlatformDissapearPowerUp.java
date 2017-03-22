package main;

import networking.Message;

public class MakeOpponentPlatformDissapearPowerUp extends main.Item{

    MakeOpponentPlatformDissapearPowerUp(int y, int type) {
        super(y, type);
    }

    @Override
    public void performAction(GameState game) {
        if (AudioEngine.isClient) {
            Main.getInstance().sendMessage(
                    new Message("PlatformDelete")
            );
        }
    }

    @Override
    public void paint(Window window, boolean opponent) {
        super.paint(window, opponent);
    }
}
