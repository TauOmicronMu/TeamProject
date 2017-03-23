package object.powerup;

import audio.AudioEngine;
import client.Main;
import shared.GameState;
import graphics.ui.Window;
import networking.Message;

public class MakeOpponentPlatformDissapearPowerUp extends Item {

    public MakeOpponentPlatformDissapearPowerUp(int y, int type) {
        super(y, type);
    }

    @Override
    public void performAction(GameState game) {
        if (AudioEngine.isClient) {
            AudioEngine.getInstance().playTrack(AudioEngine.DELETE_PLATFORM);
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
