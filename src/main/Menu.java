package main;

class Menu {

    static TextShader tshader = new TextShader();
    static TextShader2 tshader2 = new TextShader2();
    static RectangleShader rshader = new RectangleShader();
    static Text text = new Text();
    static double[] verticesr = new double[] {0,0,0,0,0,0,0,0,0,0,0,0};
	static Model rmodel = new Model(verticesr);
	static double[] verticest = new double[] {0,0,0,0,0,0,0,0,0};
	static Model tmodel = new Model(verticest);
	static double[] verticesc = new double[362*3];
	static Model cmodel = new Model(verticesc);

    private static void drawPlayButton() {
        double[] vertices = {-0.4f, -0.04f, 0, 0.4f, -0.04f, 0, 0.4f, -0.24f, 0, -0.4f, -0.24f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices,rmodel);
        rshader.stop();
        tshader2.bind();
        tshader2.stop();
        tshader.bind();
        Text.draw("start",-1.1f, -0.5f, 0.5f);
        tshader.stop();
    }

    private static void drawSettingsButton() {
        double[] vertices = {-0.4f, -0.27f, 0, 0.4f, -0.27f, 0, 0.4f, -0.47f, 0, -0.4f, -0.47f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices,rmodel);
        rshader.stop();
        tshader.bind();
        Text.draw("settings", -1.9f, -2.4f, 0.5f);
        tshader.stop();
    }

    private static void drawQuitButton() {
        double[] vertices = {-0.4f, -0.5f, 0, 0.4f, -0.5f, 0, 0.4f, -0.7f, 0, -0.4f, -0.7f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices,rmodel);
        rshader.stop();
        tshader.bind();
        Text.draw("quit", -0.6f, -4.1f, 0.5f);
        tshader.stop();     
    }

    static void drawBackToMenuButton() {
        double[] vertices = {-0.95f, 0.95f, 0, -0.85f, 0.95f, 0, -0.85f, 0.845f, 0, -0.95f, 0.845f, 0};
        rshader.bind();
        Rectangle.drawrectangle(vertices,rmodel);
        rshader.stop();
        tshader.bind();
        Text.draw("<", -5.8f, 6.3f, 0.6f);
        tshader.stop();
    }

    static void drawAll() {
        drawPlayButton();
        drawSettingsButton();
        drawQuitButton();
    }

    static Model getRectangleModel()
    {
    	return rmodel;
    }
    
    static Model getTriangleModel()
    {
    	return tmodel;
    }
    
    static Model getCircleModel()
    {
    	return cmodel;
    }

}