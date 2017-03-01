package main;

class Menu {
	
	static TextShader tshader = new TextShader();
	static TextShader2 tshader2 = new TextShader2();
	static RectangleShader rshader = new RectangleShader();
	static Text text = new Text();
	

    private static void drawPlayButton() {
        double[] vertices = {-0.4f, 0, 0, 0.4f, 0, 0, 0.4f, -0.2f, 0, -0.4f, -0.2f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices);
        rshader.stop();
        tshader.bind();
        text.draw("start", -0.9f, -0.1f, 0.6f, 3.5f);
        tshader.stop();
    }

    private static void drawSettingsButton() {
        double[] vertices = {-0.4f, -0.25f, 0, 0.4f, -0.25f, 0, 0.4f, -0.45f, 0, -0.4f, -0.45f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices);
        rshader.stop();
        tshader.bind();
        text.draw("settings", -1.9f, -1.9f, 0.55f, 3.5f);
        tshader.stop();
    }

    private static void drawQuitButton() {
        double[] vertices = {-0.4f, -0.5f, 0, 0.4f, -0.5f, 0, 0.4f, -0.7f, 0, -0.4f, -0.7f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices);
        rshader.stop();
        tshader.bind();
        text.draw("quit", -0.6f, -3.3f, 0.6f, 3.5f);
        tshader.stop();        
    }

    static void drawBackToMenuButton() {
        double[] vertices = {-0.95f, 0.95f, 0, -0.85f, 0.95f, 0, -0.85f, 0.845f, 0, -0.95f, 0.845f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices);
        rshader.stop();
        tshader.bind();
        text.draw("<", -5.8f, 6.3f, 0.6f, 3f);
        tshader.stop();
    }

    static void drawAll() {
        drawPlayButton();
        drawSettingsButton();
        drawQuitButton();
    }

}