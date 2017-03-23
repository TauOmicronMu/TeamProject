package object.powerup;


import audio.AudioEngine;
import shared.Constants;
import shared.GameState;
import graphics.ui.Window;

public class PointsItem extends Item {

    public PointsItem(int y, int type) {
        super(y, type);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void performAction(GameState game) {
        // game.score = game.score + Constants.POINT_ITEM_STEP;
        if (AudioEngine.isClient) AudioEngine.getInstance().playTrack(AudioEngine.POINTS);
    }


    @Override
    public void paint(Window window, boolean opponent) {
        //colour
        super.paint(window, opponent);
    }
}
