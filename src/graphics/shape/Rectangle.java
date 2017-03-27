package graphics.shape;

/**
 * Class for creating all the rectangles in the game
 *
 * @author Ella
 */
public class Rectangle {


    /**
     * Draw the rectangle
     *
     * @param vertices the float points of the corners of the rectangle
     */
 
    public static void drawrectangle(double[] vertices, Model model, boolean outline)
    {
    	model.render(vertices, outline);
    }

}
