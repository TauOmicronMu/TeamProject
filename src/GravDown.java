public class GravDown extends Item {

    public GravDown(int x) {
        super(x);
        // TODO Auto-generated constructor stub
    }

    public void performActio(Ball ball) {
        if (ball.getGravity() > 3) {
            ball.setGravity(ball.getGravity() - 3);
            if (ball.getGravity() < 3) {
                ball.setGravity(3);
            }
        }
    }

    @Override
    public void paint() {

        //colour
        super.paint();
    }
}