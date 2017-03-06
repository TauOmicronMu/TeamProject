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
        tshader2.bind();
        //Text.draw("kvwxz", -6f, 1.5f, 0.5f, 3.5f);
        tshader2.stop();
        tshader.bind();
        Text.draw("start",-0.9f, -0.2f, 0.5f, 3.5f);
        tshader.stop();
    }

    private static void drawSettingsButton() {
        double[] vertices = {-0.4f, -0.25f, 0, 0.4f, -0.25f, 0, 0.4f, -0.45f, 0, -0.4f, -0.45f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices);
        rshader.stop();
        tshader.bind();
        Text.draw("settings", -1.9f, -2.2f, 0.5f, 3.5f);
        tshader.stop();
    }

    private static void drawQuitButton() {
        double[] vertices = {-0.4f, -0.5f, 0, 0.4f, -0.5f, 0, 0.4f, -0.7f, 0, -0.4f, -0.7f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices);
        rshader.stop();
        tshader.bind();
        Text.draw("quit", -0.6f, -4.1f, 0.5f, 3.5f);
        tshader.stop();     
    }

    static void drawBackToMenuButton() {
        double[] vertices = {-0.95f, 0.95f, 0, -0.85f, 0.95f, 0, -0.85f, 0.845f, 0, -0.95f, 0.845f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices);
        rshader.stop();
        tshader.bind();
        Text.draw("<", -5.8f, 6.3f, 0.6f, 3f);
        tshader.stop();
    }

    static void drawAll() {
        drawPlayButton();
        drawSettingsButton();
        drawQuitButton();
    }


    /**
     * Prints the score, given a ball and platform.
     * @param ball
     */
    public static void printScore(int score, Ball ball) {
        if(!ball.gameOver())
        {
            String scoreText = String.valueOf(score / 1000);

            TextShader2 tshader2 = new TextShader2();
            int length = scoreText.length();
            tshader2.bind();
            Text.draw(scoreText, 5.8f - (length * 0.3f), 6.8f, 0.6f, 3f);
            tshader2.stop();
        }
        else
        {
            System.out.println("Final score: "+score);
        }
    }

}