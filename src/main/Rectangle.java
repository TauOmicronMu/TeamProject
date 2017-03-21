package main;

/**
 * Class for creating all the shapes in the game
 *
 * @author Ella
 */
class Rectangle {


    /**
     * Draw the rectangle
     *
     * @param vertices the float points of the corners of the rectangle
     */
 
    static void drawrectangle(double[] vertices, Model model, boolean outline)
    {
    	model.render(vertices, outline);
    }

}
