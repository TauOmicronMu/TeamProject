public class GravUp extends Item{

    public GravUp(int x) {
        super(x);
        // TODO Auto-generated constructor stub
    }

    public void performActio(Ball ball) {
        ball.setGravity(ball.getGravity() + 3);
    }

    @Override
    public void paint() {

        //colour
        super.paint();
    }
}
