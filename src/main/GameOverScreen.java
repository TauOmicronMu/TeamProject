package main;

public class GameOverScreen {

    static boolean won;

    public GameOverScreen(boolean won){ this.won = won; }

    static ShaderProgram tshader = new ShaderProgram("shaders/tshader.vs","shaders/shader.fs");

    public static void drawLoadingWord(){
        tshader.bind();
        Text.draw(won ? Constants.GAME_WIN_TEXT : Constants.GAME_LOSE_TEXT , -3.0f, 0.5f, 0.6f, false);
        tshader.stop();
    }
}

