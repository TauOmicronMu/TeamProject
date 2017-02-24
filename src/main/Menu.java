package main;

class Menu {

    private static void drawPlayButton() {
        double[] vertices = {-0.4f, 0, 0, 0.4f, 0, 0, 0.4f, -0.2f, 0, -0.4f, -0.2f, 0};
        Model rect1 = new Model(vertices);
        Texture2 texshader = new Texture2("src/res/Start.png");
        texshader.bind();
        //ModelTexture texture = new ModelTexture(rect1.loadTexture("Start"));
        //TexturedModel texturedModel = new TexturedModel(rect1,texture);
        rect1.render(vertices);
    }

    private static void drawSettingsButton() {
        double[] vertices = {-0.4f, -0.25f, 0, 0.4f, -0.25f, 0, 0.4f, -0.45f, 0, -0.4f, -0.45f, 0};
        Model rect2 = new Model(vertices);
        rect2.render(vertices);
    }

    private static void drawQuitButton() {
        double[] vertices = {-0.4f, -0.5f, 0, 0.4f, -0.5f, 0, 0.4f, -0.7f, 0, -0.4f, -0.7f, 0};
        Model rect3 = new Model(vertices);
        rect3.render(vertices);
    }

    static void drawBackToMenuButton() {
        double[] vertices = {-0.95f, 0.95f, 0, -0.85f, 0.95f, 0, -0.85f, 0.9f, 0, -0.95f, 0.9f, 0};
        Model rect4 = new Model(vertices);
        rect4.render(vertices);
    }

    static void drawAll() {
        drawPlayButton();
        drawSettingsButton();
        drawQuitButton();
    }

}