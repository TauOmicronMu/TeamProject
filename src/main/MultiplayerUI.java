package main;

/**
 * Created by Jennifer on 08/03/2017.
 */
public class MultiplayerUI {
    static RectangleShader rshader = new RectangleShader();
    static TextShader tshader = new TextShader();
    static Text text = new Text();

    static void drawAIButton() {
        double[] vertices = {-0.8f, 0.1, 0, -0.3f, 0.1, 0, -0.3f, -0.1f, 0, -0.8f, -0.1f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices);
        rshader.stop();
        tshader.bind();
        text.draw("ai", -4.3f, 0.55f, 0.5f, 4f);
        tshader.stop();
    }

    static void  drawPVPButton(){
        double[] vertices = {0.3f, 0.1, 0, 0.8f, 0.1, 0, 0.8f, -0.1f, 0, 0.3f, -0.1f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices);
        rshader.stop();
        tshader.bind();
        text.draw("PvP", 4f, 0.6f, 0.5f, 4f);
        tshader.stop();
    }

    static void drawAll() {
        drawAIButton();
        drawPVPButton();
    }
}
