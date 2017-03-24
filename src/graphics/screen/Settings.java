package graphics.screen;

import graphics.shader.ShaderProgram;
import graphics.ui.Text;
import graphics.shape.Rectangle;

import static org.lwjgl.opengl.GL11.glColor3f;

/**
 * Created by Jennifer on 08/03/2017.
 */
public class Settings {
    static double xLower = 0.18;
    static double xUpper = xLower + 0.05;
    static ShaderProgram rshader = Menu.rshader;
    static ShaderProgram tshader = Menu.tshader;
    static Text text = new Text();

    public static void drawAudioBar() {
        double[] vertices = {
                -0.2f, -0.37, 0,
                0.6f, -0.37, 0,
                0.6f, -0.42f, 0,
                -0.2f, -0.42f, 0
        };
        rshader.bind();
        Rectangle.drawrectangle(vertices, Menu.getRectangleModel(), false);
        rshader.stop();
        //tshader.bind();
        glColor3f(255, 255, 255);
        Text.draw("Volume", -6f, -2.5f, 0.5f, false);
        glColor3f(255, 255, 255);
        //tshader.stop();
    }

    public static void drawSlider(){
        double[] vertices = {
                getXLower(), -0.36, 0,
                getXUpper(), -0.36, 0,
                getXUpper(), -0.43, 0,
                getXLower(), -0.43f, 0
        };
        rshader.bind();
        Rectangle.drawrectangle(vertices, Menu.getRectangleModel(), false);
        rshader.stop();
    }

    public static void setXLower(double x){
        if(x<=-0.225){
            xLower = -0.225;
        } else if(x>=0.575){
            xLower = 0.575;
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

    public static double volumePercentage(){
        return (((getXLower()+0.025) - (-0.2))/0.8)*100;
    }

    public static void drawInstructions(){
        glColor3f(255, 255, 255);
        Text.draw("Instructions", -6f, 5f, 0.5f, false);
        Text.draw("To move left press a", -10.5f, 6,0.3f, false );
        Text.draw("To move right press d", -10.5f,4.5f, 0.3f,false);
        Text.draw("To double jump press the space bar", -10.5f, 3,0.3f,false );
    }

}