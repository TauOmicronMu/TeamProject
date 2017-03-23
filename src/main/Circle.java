package main;

public class Circle {

    /**
     * Draws the pinball
     *
     * @param x the current x position of the centre of the pinball
     * @param y the current y position of the centre of the pinball
     */
    static void paintPinball(Window window, double x, double y, double radius, boolean opponent) {
        double radius2 = window.glScaleDistance(radius);
        double x2 = window.glScaleX(x, opponent, Screen.GAME);
        double y2 = window.glScaleY(y);
        double[] vertices = createCircle(x2, y2, 0.5f, radius2);
        Model circle1 = Menu.getPinballModel();
        circle1.render(vertices, false);
    }

    /**
     * Draws the powerUp(item class)
     *
     * @param x the current x position of the centre of the powerUp
     * @param y the current y position of the centre of the powerUp
     */
    static public void paintItem(Window window, int x, int y, int radius, boolean opponent) {
        double radius2 = window.glScaleDistance(radius);
        double x2 = window.glScaleX(x, opponent, Screen.GAME);
        double y2 = window.glScaleY(y);
        double[] vertices = createCircle(x2, y2, 0.3f, radius2);
        Model circle1 = Menu.getItemModel();
        circle1.render(vertices, false);
    }

    /**
     * Calculates all the points of the circumference of the circle
     *
     * @param posx the current x position of the centre of the pinball
     * @param posy the current y position of the centre of the pinball
     * @param posz the current z position of the centre of the pinball
     * @return all the points of the circle
     */
    public static double[] createCircle(double x, double y, double z, double radius, int sides) {
        double degToRad = Math.PI/180.0;
        int degPerSide = 360/sides;

        double[] vertices = new double[(sides + 1) * 3];

        int j = 0;
        for (int i = 0; i <= 360; i += degPerSide) {
            double rad = i * degToRad;
            vertices[j] = x + radius * Math.cos(rad);
            vertices[j + 1] = y + radius * Math.sin(rad);
            vertices[j + 2] = z;
            j+=3;
        }

        return vertices;
    }

    /**
     * Calculates all the points of the circumference of the circle
     *
     * @param posx the current x position of the centre of the pinball
     * @param posy the current y position of the centre of the pinball
     * @param posz the current z position of the centre of the pinball
     * @return all the points of the circle
     */
    public static double[] createCircle(double x, double y, double z, double radius) {
        return createCircle(x, y, z, radius, Constants.CIRCLE_SIDES);
    }

}
