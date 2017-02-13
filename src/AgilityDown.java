public class AgilityDown extends Item {

    public AgilityDown(int x) {
        super(x);
        // TODO Auto-generated constructor stub
    }

    public void performActio(Ball ball) {
        if (ball.getAgility() >= 2) {
            ball.setAgility(ball.getAgility() - 1);
        }
    }

    @Override
    public void paint() {
        //colour
        super.paint();
    }
}
