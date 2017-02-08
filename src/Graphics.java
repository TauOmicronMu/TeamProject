import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
/**
 * Main class for graphics
 * @author Ella
 *
 */
public class Graphics{

		private static float x = 0f;
		private static float y = -0.615f;
		private static float invisiblex;
		private static float invisibley;
		private static boolean buttondown = false;
		private static boolean drawrec = true;
		private static float changey = -0.01f;
		private static float changex = 0;
		private static boolean started = false;
		private static ArrayList<ArrayList<Float>> obstacles = new ArrayList<ArrayList<Float>>();
		private static Shader shader = new Shader("shader");
		
		/**
		 * Sets up the screen, placing the ball in the initial position and creating the obstacles
		 * @param window
		 */
		public static void init(long window)
		{						
			setscreen(window,4);
		}
		
		/**
		 * Closes down the graphics
		 */
		public static void end()
		{
			glfwTerminate();
		}
		
		/**
		 * Called every run of the main game loop to update 
		 * the position of the ball, create new obstacles, 
		 * move the window etc.
		 * @param window
		 */
		public static void update(long window)
		{
			setup(shader);
			Shapes.drawCircle(x, y);
			
			for(int i = 0; i <obstacles.size(); i++)
			{
				ArrayList <Float> vert = obstacles.get(i);		
				Shapes.drawobstacle(vert.get(0),vert.get(1),vert.get(2),vert.get(3));
			}
			
			
			if(drawrec)
			{
				float[] verticesr = {-1.0f,-0.6f,0.3f,-1.0f,-0.63f,0.3f,1.0f,-0.63f,0.3f,1.0f,-0.6f,0.3f};
				Shapes.drawrectangle(verticesr);
			}
			mousenavigation(window);
			
			glfwSwapBuffers(window);
		}
		
		/**
		 * Called at first to create the initial screen with the obstacles
		 * @param window
		 * @param numobs the number of obstacles wanted
		 */
		private static void setscreen(long window, int numobs) 
		{
			int i = 0;
			while(i < numobs)
			{
				float[] verticeso = Shapes.getvertices();
				if(! tooclose(verticeso))
				{
					Shapes.drawobstacle(verticeso[0],verticeso[1],verticeso[2],verticeso[3]);
					glfwSwapBuffers(window);
					Shapes.drawobstacle(verticeso[0],verticeso[1],verticeso[2],verticeso[3]);
					ArrayList<Float> vert = new ArrayList<Float>();
					vert.add(verticeso[0]);
					vert.add(verticeso[1]);
					vert.add(verticeso[2]);
					vert.add(verticeso[3]);
					obstacles.add(vert);
					i++;
				}
			}
			
		}
		
		/**
		 * Checks if the proposed obstacle would be too close to any existing obstacles
		 * @param verticeso
		 * @return true if the proposed obstacle is too close
		 */
		private static boolean tooclose(float[] verticeso) {
			
			for(int i = 0; i < 2; i++)
			{
				int count = 0;
				for(int j = 0; j < obstacles.size(); j++)
				{
					ArrayList<Float> current = new ArrayList<Float>(obstacles.get(j));
					if((current.get(i) - verticeso[i] < 0.3f && current.get(i) - verticeso[i] > 0f) | (current.get(i) - verticeso[i] > -0.3f && current.get(i) - verticeso[i] < 0f))
					{
						count++;
					}
				}
				if(count > 1)
				{
					return true;
				}
			}
			return false;
		}

		

		/**
		 * Monitors the mouse, checks for clicks and releases
		 * @param window
		 */
		private static void mousenavigation(long window) {
		
			float[] pos;
			
			//find current position of cursor
			GLFWCursorPosCallbackI cursorcallback = new GLFWCursorPosCallbackI(){

				public void invoke(long window, double xpos, double ypos)
				{
					invisiblex = (float) xpos;
					invisibley = (float) ypos;
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
						drawrec = false;
						buttondown = false;
						started = true;
					}
				}
				
			};
			
			//check if mouse has been pressed
			glfwSetMouseButtonCallback(window,mousecallback);
			if(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS)
			{
				x = (float)-1+(invisiblex/320);
				y = (float)1-(invisibley/240);
				pos = update(x,y,0,0);
				buttondown = true;
			}
			
			//if the button hasn't been pressed keep moving ball
			if(!buttondown && started )
			{
				
				pos = update(x,y,changex,changey);
				
				if(pos[1] > 0.89 | pos[1] < -0.89)
				{
					changey = 0 - changey;
				}
				x = pos[0];
				y = pos[1];
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
		 * Calculates the new centre positions the pinball
		 * @param posx the current x position of the centre of the pinball
		 * @param posy the current y position of the centre of the pinball
		 * @param changex how much the x coordinate should change by
		 * @param changey how much the y coordinate should change by
		 * @return the new centre coordinates of the pinball
		 */
		public static float[] update(float posx, float posy, float changex, float changey)
		{				
				posx = posx + changex;
				posy = posy + changey;
			
				float [] positions = {posx,posy};
				return(positions);
		}
		
		public static void collision(float posx, float posy, float changex, float changey)
		{
			float[] newpoints = update(posx,posy,changex,changey);
			Shapes.drawCircle(newpoints[0],newpoints[1]);
		}
		
		/*
		 * Moving the screen?????
		 */
}
