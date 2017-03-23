package main;

public class GameOverScreen {

    private static ShaderProgram tshader = new ShaderProgram("shaders/tshader.vs","shaders/shader.fs");

    static void drawScreen(boolean won){
        tshader.bind();
        Text.draw(won ? Constants.GAME_WIN_TEXT : Constants.GAME_LOSE_TEXT , -3.0f, 0.5f, 0.6f, false);
        tshader.stop();
    }
}

