/**
 * Class for creating all the shapes in the game
 *
 * @author Ella
 */
public class Rectangle {


    /**
     * Draw the rectangle
     *
     * @param vertices the float points of the corners of the rectangle
     */
    public static void drawrectangle(float[] vertices) {
        Model rectangle1 = new Model(vertices);
        rectangle1.render(vertices);
    }


}