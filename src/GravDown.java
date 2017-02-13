class GravDown extends Item {

    GravDown(int x) {
        super(x);
        // TODO Auto-generated constructor stub
    }

    public void performAction(Ball ball) {
        if (ball.getGravity() > 3) {
            ball.setGravity(ball.getGravity() - 3);
            if (ball.getGravity() < 3) {
                ball.setGravity(3);
            }
        }
    }

}
