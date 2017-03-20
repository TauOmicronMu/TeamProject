package main;


public class PointsItem extends Item{

    public PointsItem(int y, int type) {
        super(y, type);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void performAction(GameState game) {
        game.score = game.score + 3000  ;
        //System.out.println("POINTs");
    }


    @Override
    public void paint(Window window, boolean opponent) {
        //colour
        super.paint(window, opponent);
    }
}
