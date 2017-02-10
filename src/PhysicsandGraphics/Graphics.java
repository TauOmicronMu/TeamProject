package PhysicsandGraphics;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;

import java.util.Random;
/**
 * Main class for graphics
 * @author Ella
 *
 */
public class Graphics {

		private static int invisiblex;
		private static int invisibley;
		private static boolean buttondown = false;
		private static boolean started = false;
		private static Shader shader;
		private long window;
		private Ball3 b;
		private GLFWVidMode videoMode;
		private Platform p[] = new Platform[10];
		private Item item[] = new Item[3];
		private Random r = new Random();
		private DrawCircle drawc = new DrawCircle();
		
		public Graphics() {
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
		public void init()
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
			
		}
		
		public void start() {
			b = new Ball3(videoMode.width()/2,25);
			for(int i = 0; i < p.length; i++){
				p[i] = new Platform(videoMode.width()- r.nextInt(videoMode.width()-50), videoMode.height() - 100 * i, 140, 20);
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
			while(!glfwWindowShouldClose(window))
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
				mousenavigation();
				drawc.paintPinball(this, b.getX(), b.getY());
				for(int i = 0; i < p.length; i++){
					p[i].paint(this);
				}
				
				for(int i = 0; i < item.length; i++){
					item[i].paint();
				}
			
			
			glfwSwapBuffers(window);
			
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
		}
		

		/**
		 * Monitors the mouse, checks for clicks and releases
		 * @param window
		 */
		private void mousenavigation() {

			//find current position of cursor
			GLFWCursorPosCallbackI cursorcallback = new GLFWCursorPosCallbackI(){

				public void invoke(long window, double xpos, double ypos)
				{
					invisiblex = (int) xpos;
					invisibley = (int) ypos;
				}
			};
			glfwSetCursorPosCallback(window,cursorcallback);
			
			//check if mouse has been released
			GLFWMouseButtonCallbackI mousecallback = new GLFWMouseButtonCallbackI()
			{
				public void invoke(long window, int button, int action, int mods)
				{
					if(button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE)
					{
						buttondown = false;
						started = true;
					}
				}
				
			};
			
			//check if mouse has been pressed
			glfwSetMouseButtonCallback(window,mousecallback);
			if(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS)
			{
				b.setX(invisiblex);
				b.setY(invisibley);
				b.update(this);
				buttondown = true;
			}
			
			//if the button hasn't been pressed keep moving ball
			if(!buttondown && started )
			{		
				b.update(this);
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
			float newx = (float)-1.0f + ((float)x/(float)(videoMode.width()/2));
			return newx;
		}
		
		public float changeyCoord(int y) {
			float newy = (float)1.0f - ((float)y/(float)(videoMode.height()/2));
			return newy;
		}
}
