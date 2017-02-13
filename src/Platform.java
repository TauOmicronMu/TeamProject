import static org.lwjgl.opengl.GL11.glColor4f;

import java.util.Random;


public class Platform {

    private int dx;
    private int x, y, width, height;

    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y position of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    public Platform(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dx = 3;
    }

    public Platform() {
        dx = 3;
        x = 300;
        y = 300;
        width = 120;
        height = 40;
    }
    /*
     *Updates the position of the platform
     *@param game the game class object
     *@param ball the ball class object
     */
    public void update(Graphics game, Ball3 ball) {
        //System.out.println(ball.getDy());
        if(ball.getAgility()>3){
            y += ball.getAgility();
        }else{
            y+=dx;
        }
         checkForCollision(ball);
         if(y > game.getHeight() ){

             Random r = new Random();
             y = 0 + 40;
             x = game.getWidth() - r.nextInt(game.getWidth()-100);

         }

    }
    /*
     * Checks if any ball has collided with the platform
     * @param ball the ball object
     */
    private void checkForCollision(Ball3 ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int radius = ball.getRadius();

        if (ballY + radius > y && ballY+radius < y + height){
            //System.out.println("Y true");
            if(ballX > x && ballX < x + width){

                //System.out.println("Collision");

                double newDy= ball.getGameDy();
                ball.setDy(newDy);
                ball.setY(y - radius);
            }
        }
    }
    /*
     * Draws the platform
     */
    public void paint(Graphics game){
        float floatx = game.changexCoord(x);
        float floaty = game.changeyCoord(y);
        float widthGl = game.changeDistance(width);
        float heightGl = game.changeDistance(height);

        float[] verticesb = {floatx,floaty,0.3f,floatx,(floaty - heightGl),0.3f,(floatx + widthGl),(floaty - heightGl),0.3f,(floatx + widthGl),floaty,0.3f};
        glColor4f(1,0,0,0);
        Rectangle.drawrectangle(verticesb);
    }
}
