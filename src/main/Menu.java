package main;

class Menu {

    private static void drawPlayButton() {
        double[] vertices = {-0.4f, 0, 0, 0.4f, 0, 0, 0.4f, -0.2f, 0, -0.4f, -0.2f, 0};
        Rectangle.drawrectangle(vertices);
    }

    private static void drawSettingsButton() {
        double[] vertices = {-0.4f, -0.25f, 0, 0.4f, -0.25f, 0, 0.4f, -0.45f, 0, -0.4f, -0.45f, 0};
        Rectangle.drawrectangle(vertices);
    }

    private static void drawQuitButton() {
        double[] vertices = {-0.4f, -0.5f, 0, 0.4f, -0.5f, 0, 0.4f, -0.7f, 0, -0.4f, -0.7f, 0};
        Rectangle.drawrectangle(vertices);
    }

    static void drawBackToMenuButton() {
        double[] vertices = {-0.95f, 0.95f, 0, -0.85f, 0.95f, 0, -0.85f, 0.9f, 0, -0.95f, 0.9f, 0};
        Rectangle.drawrectangle(vertices);
    }

    static void drawAll() {
        drawPlayButton();
        drawSettingsButton();
        drawQuitButton();
    }

}