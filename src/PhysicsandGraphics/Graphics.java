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
		private static Shader shader;
		private long window;
		private int windowHeight = 800;
		private int windowWidth = 800;
		private static boolean changetogame = false;
		private static boolean changetoquit = false;
		private static boolean changetomenu = false;
		private static boolean initial = true;
		private enum STATE{
			MENU,
			GAME
		};
		private static STATE state = STATE.MENU;
		private Ball3 b;
		private GLFWVidMode videoMode;
		private Platform p[] = new Platform[10];
		private Item item[] = new Item[3];
		private Random r = new Random();
		private DrawCircle drawc = new DrawCircle();
		private Menu menu = new Menu();
		
		public Graphics() {
		}
			
		public int getHeight() {
			return windowHeight;
		}
		public int getWidth() {
			return windowWidth;
		}
		
		/**
		 * Sets up the screen
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
			window = glfwCreateWindow(windowWidth, windowHeight, "Pinball", 0, 0);
			if(window == 0)
			{
				throw new IllegalStateException("Failed to create window");
			}

			videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (videoMode.width()- windowWidth)/2, (videoMode.height() - windowHeight)/2);
			
			glfwShowWindow(window);
			glfwMakeContextCurrent(window);
			
			GL.createCapabilities();
			shader = new Shader("shader");
			
		}
		/**
		 * Creates the ball object
		 * Creates the platforms
		 * Creates the power-ups
		 */
		public void start() {
			if(state==STATE.MENU){
				menu.drawall();
			}
			
			b = new Ball3(windowWidth/2,windowHeight/2);
			for(int i = 0; i < p.length; i++){
				p[i] = new Platform(r.nextInt(windowWidth-220) -100, windowHeight - 200 * i, 120, 10);
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
		 * move the obstacles, power-ups etc.
		 */
		public void run()
		{
			while(!glfwWindowShouldClose(window))
			{
				setup(shader);
				mousenavigation();
				if(state==STATE.MENU){
					menu.drawall();
					glfwSwapBuffers(window);
				}else if(initial){
					drawc.paintPinball(this, b.getX(), b.getY(), b.getRadius());
					if(glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_TRUE){
						initial = false;
					}glfwSwapBuffers(window);
				}else if(!initial){
					for(int i = 0; i< item.length; i++){
						if(item[i].getY() == windowHeight + 100){
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
					
					if(glfwGetKey(window, GLFW_KEY_A) == GLFW_TRUE){
						b.moveLeft();
					}else if(glfwGetKey(window, GLFW_KEY_D) == GLFW_TRUE){
						b.moveRight();
					}
					
					b.update(this);
					drawc.paintPinball(this, b.getX(), b.getY(), b.getRadius());
					for(int i = 0; i < p.length; i++){
						p[i].paint(this);
					}
					
					for(int i = 0; i < item.length; i++){
						item[i].paint();
					}
					Menu.drawbacktomenu();
				
				glfwSwapBuffers(window);
				
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
						if(changetomenu==true){
							state=STATE.MENU;
							changetomenu=false;
							changetogame=false;
							initial=true;
							start();
						}else if(changetogame==true){
							state=STATE.GAME;
						}else if(changetoquit==true){
							glfwSetWindowShouldClose(window, true);
						}
					}
				}
				
			};
			
			//check if mouse has been pressed
			glfwSetMouseButtonCallback(window,mousecallback);
			if(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS)
			{
				float x = changexCoord(invisiblex);
				float y = changeyCoord(invisibley);
				if(state==STATE.MENU && -0.4<=x && x<=0.4 && -0.2<=y && y<=0){
					changetogame = true;
					menu.drawall();
				}else if(state==STATE.MENU && -0.4<=x && x<=0.4 && -0.7<=y && y<=-0.5){
					changetoquit=true;
					menu.drawall();
				}else if(state==STATE.GAME && -0.95<=x && x<=-0.85 && 0.9<=y && y<=0.95){
					changetomenu = true;
				}else if(state==STATE.GAME){
					b.setX(invisiblex);
					b.setY(invisibley);
					//b.update(this);
					drawc.paintPinball(this, invisiblex, invisibley, b.getRadius());
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
		/**
		 * Converts an x-coordinate to float equivalent (needed for drawing with openGL)
		 * @param x
		 * @return the converted coordinate
		 */
		public float changexCoord(int x) {
			float newx = (float)(-1.0f) + ((float)x/(float)(windowWidth/2));
			return newx;
		}
		
		/**
		 * Converts an x-coordinate to float equivalent (needed for drawing with openGL)
		 * @param x
		 * @return the converted coordinate
		 */
		public float changexCoord2(int x) {
			float newx = 0f;
			if(x > windowWidth/2)
			{
				newx = (float)(float)x/(float)(windowWidth/2);
			}
			//float newx = (float)-1.0f + ((float)x/(float)(windowwidth/2));
			return newx;
		}
		
		/**
		 * Converts an y-coordinate to float equivalent (needed for drawing with openGL)
		 * @param y
		 * @return the converted coordinate
		 */
		public float changeyCoord(int y) {
			float newy = (float)1.0f - ((float)y/(float)(windowHeight/2));
			return newy;
		}
		
		public float changeDistance(int distance){
			float newDistance = (1.0f * (float)distance)/(windowWidth/2);
			return newDistance;
		}
		
		
}
