package gradual;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCreateShader;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.Random;
/**
 * Main class for graphics
 * @author Ella
 *
 */
public class Graphics {

		private static float x = 0f;
		private static float y = -0.615f;
		private static float invisiblex;
		private static float invisibley;
		private static boolean buttondown = false;
		private static boolean drawrec = true;
		private static float changey = -0.01f;
		private static float changex = 0;
		private static boolean started = false;
		private static Shader shader;
		private long window;
		private Ball3 b;
		private GLFWVidMode videoMode;
		private Platform p[] = new Platform[10];
		private Item item[] = new Item[3];
		private Random r = new Random();
		
		public Graphics() {
			// TODO Auto-generated constructor stub
		}
			
		public int getHeight() {
			return videoMode.height();
		}
		public int getWidth() {
			return videoMode.width();
		}
		
		/**
		 * Sets up the screen, placing the ball in the initial position and creating the obstacles
		 * @param window
		 */
		public long init()
		{		
			if(!glfwInit())
			{
				System.err.println("Failed to initialise GLFW");
				System.exit(1);
			}
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			window = glfwCreateWindow(600, 600, "Pinball", 0, 0);
			if(window == 0)
			{
				throw new IllegalStateException("Failed to create window");
				
			}

			videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (videoMode.width()- 600)/2, (videoMode.height() - 600)/2);
			
			glfwShowWindow(window);
			glfwMakeContextCurrent(window);
			
			GL.createCapabilities();
			
			
			shader = new Shader("shader");
			
			return window;
		}
		
		public void start() {
			b = new Ball3();
			for(int i = 0; i < p.length; i++){
				p[i] = new Platform(videoMode.width()- r.nextInt(700), videoMode.height() - 200 * i, 140, 20);
			}
			
			for(int i = 0; i < item.length; i++){
				item[i] = new GravUp( -1000 * i);
			}
			
		}
		
		/**
		 * Closes down the graphics
		 */
		public void end()
		{
			glfwTerminate();
		}
		
		/**
		 * Called every run of the main game loop to update 
		 * the position of the ball, create new obstacles, 
		 * move the window etc.
		 * @param window
		 */
		public void run()
		{
			while(true)
			{
				setup(shader);
			
				for(int i = 0; i< item.length; i++){
					if(item[i].getY() == videoMode.height() + 100){
						item[i]=null;
						switch (r.nextInt(4)){
						case 0:
							item[i]=new GravUp(- 10 * r.nextInt(500));
							break;
						case 1:
							item[i]=new GravDown(-10 * r.nextInt(500));
							break;
						case 2:
							item[i]=new AgilityUp(-10 * r.nextInt(500));
							break;
						case 3:
							item[i]=new AgilityDown(-10 * r.nextInt(500));
							break;
						}
					}
				}
				
				for(int i = 0; i < p.length; i++){
					p[i].update(this, b);
				}
				
				for(int i = 0; i < item.length; i++){
					item[i].update(this, b);
				}
				
				b.update(this);
			
				b.paint(this);
				
				for(int i = 0; i < p.length; i++){
					p[i].paint(this);
				}
				
				for(int i = 0; i < item.length; i++){
					item[i].paint();
				}
			
			if(drawrec)
			{
				float[] verticesr = {-1.0f,-0.6f,0.3f,-1.0f,-0.63f,0.3f,1.0f,-0.63f,0.3f,1.0f,-0.6f,0.3f};
				Rectangle.drawrectangle(verticesr);
			}
			//mousenavigation(window);
			glfwSwapBuffers(window);
			
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
		/**
		 * Clears the colour from the screen and binds the shader
		 * Necessary each time you draw
		 * @param shader
		 */
		public static void setup(Shader shader)
		{
			glfwPollEvents();
			glClear(GL_COLOR_BUFFER_BIT);
			shader.bind();
			
		}
		
		
		public float changexCoord(int x) {
			float newX = -1+(x/400); 
			return newX;
		}
		
		public float changeyCoord(int y) {
			float newY = 1 -(y/400);
			return newY;
		}
}
