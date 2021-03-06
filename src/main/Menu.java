package main;

import java.util.ArrayList;
import java.util.Random;

class Menu {

    static ShaderProgram tshader = new ShaderProgram("shaders/tshader.vs","shaders/shader.fs");
    static ShaderProgram tshader2 = new ShaderProgram("shaders/tshader2.vs","shaders/shader.fs");
    static ShaderProgram tshader3 = new ShaderProgram("shaders/tshader3.vs","shaders/shader.fs");
    static ShaderProgram tshader4 = new ShaderProgram("shaders/tshader4.vs","shaders/shader.fs");
    static ShaderProgram rshader = new ShaderProgram("shaders/rshader.vs","shaders/shader.fs");
    static ShaderProgram rshader2 = new ShaderProgram("shaders/rshader2.vs","shaders/shader.fs");
    static ShaderProgram starshader = new ShaderProgram("shaders/starshader.vs", "shaders/shader.fs");
    static Text text = new Text();
    static boolean stars = false;
    static ArrayList<Pair> starpoints = new ArrayList<Pair>();

    
    private static void drawBackground()
    {
    	int height = 800;
    	int width = 800;
    	double[] vertices = {-width, -height, 0, width, -height, 0, width, height, 0, -width, height, 0};
        rshader2.bind();
        Rectangle.drawrectangle(vertices, Menu.getRectangleModel(), false);
        rshader2.stop();
    }
    
    
    static void drawStars()
    {
    	starshader.bind();
    	Random rand = new Random();
    	if(!stars)
    	{
    		//stars = true;
    		for(int i = 0; i < 800; i++)
    		{
    			float x = rand.nextInt(800);
    			float y = rand.nextInt(800);
    			boolean x1 = rand.nextBoolean();
    			boolean y1 = rand.nextBoolean();
    			if(x1 && y1)
    			{
    				Text.draw(".",x/100, y/100, 5.5f,false);
    			}
    			if(x1 && !y1)
    			{
    				Text.draw(".",x/100, -(y/100), 5.5f,false);
    			}
    			if(!x1 && y1)
    			{
    				Text.draw(".",-(x/100), y/100, 5.5f,false);
    			}
    			if(!x1 && !y1)
    			{
    				Text.draw(".",-(x/100), -(y/100), 5.5f,false);
    			}
    		}
    	}
    	starshader.stop();
    }
    private static void drawTitle()
    {
    	tshader4.bind();
     	Text.draw("Space",-1.14f, 2.46f, 1.5f,false);
     	Text.draw("Jump",-1.14f, 1.46f, 1.5f,false);
        tshader4.stop();
    	tshader3.bind();
    	Text.draw("Space",-1.12f, 2.48f, 1.5f,false);
    	Text.draw("Jump",-1.12f, 1.48f, 1.5f,false);
        tshader3.stop();
    	tshader2.bind();
    	Text.draw("Space",-1.1f, 2.5f, 1.5f,false);
    	Text.draw("Jump",-1.1f, 1.5f, 1.5f,false);
        tshader2.stop();
    }
  static double[] verticesr = new double[] {0,0,0,0,0,0,0,0,0,0,0,0};
	static Model rmodel = new Model(verticesr);
	static double[] verticest = new double[] {0,0,0,0,0,0,0,0,0};
	static Model tmodel = new Model(verticest);
	static double[] verticesi = new double[18];
	static Model imodel = new Model(verticesi);
	static double[] verticesp = new double[27];
	static Model pmodel = new Model(verticesp);
	static double[] verticesPlatformPowerup = new double[25];
	static Model platformPowerupModel = new Model(verticesPlatformPowerup);

    private static void drawSettingsButton() {
        double[] vertices = {
                -0.4f, -0.5f, 0.1f,
                0.4f, -0.5f, 0.1f,
                0.4f, -0.7f, 0.1f,
                -0.4f, -0.7f, 0.1f
        };
        rshader.bind();
        Rectangle.drawrectangle(vertices,rmodel, true);
        rshader.stop();
        tshader.bind();
        Text.draw("settings", -1.9f, -4.2f, 0.5f,false);
        tshader.stop();
    }

    private static void drawQuitButton() {
        double[] vertices = {
                -0.4f, -0.72f, 0.1f,
                0.4f, -0.72f, 0.1f,
                0.4f, -0.92f, 0.1f,
                -0.4f, -0.92f, 0.1f
        };
        rshader.bind();
        Rectangle.drawrectangle(vertices,rmodel, true);
        rshader.stop();
        tshader.bind();
        Text.draw("quit", -0.6f, -5.9f, 0.5f,false);
        tshader.stop();     
    }

    static void drawBackToMenuButton() {
        double[] vertices = {
                -0.95f, 0.95f, 0.1f,
                -0.85f, 0.95f, 0.1f,
                -0.85f, 0.845f, 0.1f,
                -0.95f, 0.845f, 0.1f
        };
        rshader.bind();
        Rectangle.drawrectangle(vertices,rmodel, true);
        rshader.stop();
        tshader.bind();
        Text.draw("<", -5.8f, 6.3f, 0.6f,false);
        tshader.stop();
    }

    static void drawAll() {
    	drawBackground();
    	drawStars();
    	drawTitle();
        drawSinglePlayerButton();
        drawMultiplayerButton();
        drawSettingsButton();
        drawQuitButton();
        
    }

    /**
     * Prints the score, given a ball and platform.
     * @param ball
     */
    public static void printScore(int score, Ball ball) {
        if (!ball.gameOver()) {
            String scoreText = String.valueOf(score / 1000);

            ShaderProgram tshader2 = new ShaderProgram("shaders/tshader2.vs", "shaders/shader.fs");
            int length = scoreText.length();
            tshader2.bind();
            Text.draw(scoreText, 5.8f - (length * 0.3f), 6.8f, 0.6f,false);
            tshader2.stop();
        } else {
            System.out.println("Final score: " + score);
        }
    }

    private static void drawSinglePlayerButton(){
        double[] vertices = {
                -0.4f, -0.06f, 0.1f,
                0.4f, -0.06f, 0.1f,
                0.4f, -0.26f, 0.1f,
                -0.4f, -0.26f, 0.1f
        };
        rshader.bind();
        Rectangle.drawrectangle(vertices,rmodel, true);
        rshader.stop();
        tshader.bind();
        Text.draw("singleplayer", -2.8f, -0.8f, 0.45f, false);
        tshader.stop();
    }

    private static void drawMultiplayerButton() {
        double[] vertices = {
                -0.4f, -0.28f, 0.1f,
                0.4f, -0.28f, 0.1f,
                0.4f, -0.48f, 0.1f,
                -0.4f, -0.48f, 0.1f
        };
        rshader.bind();
        Rectangle.drawrectangle(vertices,rmodel, true);
        rshader.stop();
        tshader.bind();
        Text.draw("multiplayer", -2.5f, -2.8f, 0.45f, false);
        tshader.stop();

    }

    static Model getRectangleModel()
    {
    	return rmodel;
    }
    
    static Model getTriangleModel()
    {
    	return tmodel;
    }
    
    static Model getPinballModel()
    {
    	return pmodel;
    }

    static Model getPlatformPowerupModel() {
        return platformPowerupModel;
    }

    static Model getItemModel()
    {
    	return imodel;
    }

}