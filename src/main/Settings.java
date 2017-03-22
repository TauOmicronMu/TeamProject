package main;

/**
 * Created by Jennifer on 08/03/2017.
 */
public class Settings {
    static double xLower = 0.18;
    static double xUpper = xLower + 0.05;
    static ShaderProgram rshader = Menu.rshader;
    static ShaderProgram tshader = Menu.tshader;
    static Text text = new Text();

    static void drawAudioBar() {
        double[] vertices = {
                -0.2f, 0, 0,
                0.6f, 0, 0,
                0.6f, -0.05f, 0,
                -0.2f, -0.05f, 0
        };
        rshader.bind();
        Rectangle.drawrectangle(vertices, Menu.getRectangleModel(), false);
        rshader.stop();
        // tshader.bind();
        Text.draw("Volume", -6f, 0.5f, 0.5f, false);
        // tshader.stop();
    }

    static void drawSlider(){
        double[] vertices = {
                getXLower(), 0.01, 0,
                0.8+getXUpper(), 0.01, 0,
                0.8+getXUpper(), -0.06f, 0,
                0.8+getXLower(), -0.06f, 0
        };
        rshader.bind();
        Rectangle.drawrectangle(vertices, Menu.getRectangleModel(), false);
        rshader.stop();
    }

    static void setXLower(double x){
        if(x<=-0.6){
            xLower = -0.6;
        } else if(x>=-0.2){
            xLower = -0.6;
        } else{
            xLower = x;
        }
        xUpper = xLower + 0.05;
    }

    static double getXLower(){
        return xLower;
    }

    static double getXUpper(){
        return xUpper;
    }

    static double volumePercentage(){
        return ((getXLower() - (-0.425))/0.8)*100;
    }
}