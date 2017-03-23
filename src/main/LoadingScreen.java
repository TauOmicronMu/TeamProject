package main;

import java.util.Random;

/**
 * Created by Jennifer on 22/03/2017.
 */
public class LoadingScreen {
    static ShaderProgram tshader = new ShaderProgram("shaders/tshader.vs","shaders/shader.fs");

    public static void drawLoadingWord(){
        tshader.bind();
        Text.draw("loading", -1.9f, 0f, 0.5f, false);
        tshader.stop();
    }

   public static void drawLoadingDot(){
       Random rand = new Random();
       tshader.bind();

       Text.draw(".", 1.4f, -1, 0.5f,false);
       Text.draw(".", 1.6f, -1, 0.5f,false);
       Text.draw(".", 1.8f, -1, 0.5f,false);


       tshader.stop();
   }
}
