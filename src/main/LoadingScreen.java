package main;

public class LoadingScreen {
    static ShaderProgram tshader = new ShaderProgram("shaders/tshader.vs","shaders/shader.fs");

    public static void drawLoadingWord(){
        tshader.bind();
        Text.draw(Constants.LOADING_SCREEN_TEXT, -3.0f, 0.5f, 0.6f, false);
        tshader.stop();
    }
}
