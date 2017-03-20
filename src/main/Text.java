package main;

import org.lwjgl.opengl.GL11;

/**
 * 
 * Class to write any text during the game
 * It draws each character from a series of rectangles
 *
 */

class Text{

	/**
	 * Draws the string given - breaks it down into individual letters and draws each of them
	 * @param s the string 
	 * @param x the beginning x coordinate
	 * @param y the beginning y coordinate
	 * @param scale how big the letters should be
	 */
	public static void draw(String s, float x, float y, float scale) {
	      float startX = x;
	      scale = scale * 0.25f;
	      boolean lessspace = false;
	      boolean lessspace2 = false;
	      boolean space = false;
	      int charNum = 1, spaceNum = 0;
	      for (char c : s.toLowerCase().toCharArray()) {
	          switch(c){
	          case '<':
	              drawBack(scale, startX, y);
	              break;
	          case ' ':
	              space = true;
	              break;
	          case '\n':
	              y -= 2;
	              startX -= charNum * (scale * 5);
	              startX -= spaceNum * (scale * 3);
	              spaceNum = 0;
	              charNum = 0;
	              break;
	          case 'a':
	            drawA(scale, startX, y);
	              break;
	          case 'b':
	        	  drawB(scale, startX, y);
	              break;
	          case 'd':
	        	  drawD(scale, startX, y);
	          case 'c':
	        	  drawC(scale, startX, y);
	              break;
	          case 'e':
	        	  drawE(scale, startX, y);
	              break;
	          case 'f':
	        	  drawF(scale, startX, y);
	              break;
	          case 'g':
	        	  drawO(scale, startX, y);
	        	  drawG(scale, startX, y);
	              break;
	          case 'h':
	        	  drawH(scale, startX, y);
	              break;
	          case 'i':
	        	  startX = startX - (scale*3);
	        	  drawI(scale, startX, y);
	              break;
	          case 'j':
	        	  startX = startX - (scale*2);
	        	  drawJ(scale, startX, y);
	              break;
	          case 'k':
	        	  drawK(scale, startX, y);
	              break;
	          case 't':
	        	  drawT(scale, startX, y);
	        	  break;
	          case 'l':
	        	  startX = startX - (scale*3);
	        	  drawL(scale, startX, y);
	              break;
	          case 'm':
	        	  drawM(scale, startX, y);
	              break;
	          case 'p':
	        	  drawP(scale, startX, y);
	          case 'o':
	        	  drawO(scale, startX, y);
	        	  break;
	          case 'n':
	        	  drawN(scale, startX, y);
	              break;
	          case 'q':
	        	  drawO(scale,startX,y);
	        	  drawQ(scale, startX, y);
	              break;
	          case 'r':
	        	  drawR(scale, startX, y);
	              break;
	          case 's':
	        	  drawS(scale, startX, y);
	              break;
	          case 'u':
	        	  drawU(scale, startX, y);
	              break;
	          case 'w':
	        	  drawW(scale, startX, y);
	              break;
	          case 'v':
	        	  drawV(scale, startX, y);
	              break;
	          case 'x':
	        	  drawX(scale, startX, y);
	              break;
	          case 'y':
	        	  drawU(scale, startX, y);
	        	  drawY(scale, startX, y);
	              break;
	          case 'z':
	        	  drawZ(scale, startX, y);
	              break;
	          case '1':
	              draw1(scale,startX,y);
	              break;
	          case '2':
	        	  draw2(scale,startX,y);
	              break;
	          case '3':
	        	  draw3(scale,startX,y);
	              break;
	          case '4':
	        	  draw4(scale,startX,y);
	              break;
	          case '8':
	        	  draw8(scale,startX,y);
	        	  draw6(scale,startX,y);
	        	  draw5(scale,startX,y);
	        	  break;
	          case '6':
	        	  draw6(scale,startX,y);
	        	  draw5(scale,startX,y);
	        	  break;
	          case '5':
	        	  draw5(scale,startX,y);
	              break;
	          case '7':
	        	  draw7(scale,startX,y);
	              break;
	          case '9':
	        	  draw9(scale,startX,y);
	              break;
	          case '0':
	        	  draw0(scale,startX,y);
	              break;
	          }
	          if(!space){
	        	  startX += scale * 0.65/scale;
	              charNum++;
	          }else{
	              startX += scale * 3/scale;
	              spaceNum++;
	          }
	          space = false;
	          lessspace = false;
	          lessspace2 = false;
	      }
	   }

	/**
	 * Draws the back button within the main game
	 * @param scale passed from draw method
	 * @param startX the position that this letter should start at
	 * @param y the y coordinate
	 */
	private static void drawBack(float scale, float startX, float y)
	{
        convert(scale * startX, scale * (y-0.33f),scale * (startX - 0.5f), scale * (y-0.33f));
        
        GL11.glLineWidth(3.5f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glEnable(GL11.GL_LINE_WIDTH);
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.2f), scale * (y - 0.1f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.3f));
        GL11.glVertex2f(scale * (startX - 0.2f), scale * (y - 0.5f));
        GL11.glEnd();
	}
	
	/*
	 * Drawing the numbers
	 * 
	 */
	private static void draw1(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.25f), scale * (y - 0.3f),scale * (startX - 0.25f), scale * (y - 1));
        convert(scale * (startX - 0.25f), scale * (y - 0.3f),scale * (startX - 0.5f), scale * (y - 0.3f));
        convert(scale * (startX - 0.5f), scale * (y - 1),scale * (startX), scale * (y - 1));
	}
	
	private static void draw2(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y-0.3f),scale * (startX - 0.4f), scale * (y-0.3f));
        convert(scale * (startX), scale * (y-0.3f),scale * (startX), scale * (y - 0.65f));
        convert(scale * (startX - 0.4f), scale * (y - 0.65f),scale * (startX), scale * (y - 0.65f));
        convert(scale * (startX - 0.4f), scale * (y - 0.65f),scale * (startX - 0.4f), scale * (y - 1));
        convert(scale * (startX), scale * (y - 1),scale * (startX - 0.4f), scale * (y - 1));
	}
	private static void draw3(float scale, float startX, float y)
	{
		convert(scale * (startX - 0.4f), scale * (y - 0.3f),scale * (startX), scale * (y - 0.3f));
		convert(scale * (startX), scale * (y - 0.3f),scale * (startX), scale * (y -0.65f));
		convert(scale * (startX), scale * (y - 0.65f),scale * (startX - 0.5f), scale * (y - 0.65f));
		convert(scale * (startX), scale * (y - 0.65f),scale * (startX), scale * (y - 1));
		convert(scale * (startX), scale * (y - 1),scale * (startX - 0.5f), scale * (y - 1));
	}
	
	private static void draw4(float scale, float startX, float y)
	{
	    convert(scale * startX, scale * (y - 0.25f),scale * startX, scale * (y - 1.1f));
	    convert(scale * (startX - 0.4f), scale * (y - 0.25f),scale * (startX - 0.4f), scale * (y - 0.7f));
	    convert(scale * (startX - 0.4f), scale * (y - 0.7f),scale * (startX), scale * (y - 0.7f));
	}
	
	private static void draw5(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y - 0.3f),scale * (startX - 0.5f), scale * (y - 0.3f));
        convert(scale * (startX - 0.5f), scale * (y - 0.3f),scale * (startX - 0.5f), scale * (y - 0.65f));
        convert(scale * (startX - 0.5f), scale * (y - 0.65f),scale * (startX), scale * (y - 0.65f));
        convert(scale * (startX), scale * (y - 0.65f),scale * (startX), scale * (y - 1));
        convert(scale * (startX), scale * (y - 1),scale * (startX - 0.5f), scale * (y - 1));
	}
	
	private static void draw6(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.5f), scale * (y - 0.65f),scale * (startX - 0.5f), scale * (y - 1));
	}
	
	private static void draw7(float scale, float startX, float y)
	{
        convert(scale * (startX-0.1f), scale * (y - 0.3f),scale * (startX-0.1f), scale * (y - 1.1f));
        convert(scale * (startX - 0.5f), scale * (y - 0.3f),scale * (startX-0.1f), scale * (y - 0.3f));
	}
	
	private static void draw8(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y - 0.65f),scale * (startX), scale * (y - 0.3f));
	}
	
	private static void draw9(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y - 0.65f),scale * (startX), scale * (y - 0.3f));
        convert(scale * (startX), scale * (y - 0.3f),scale * (startX - 0.5f), scale * (y - 0.3f));
        convert(scale * (startX - 0.5f), scale * (y - 0.3f),scale * (startX - 0.5f), scale * (y - 0.65f));
        convert(scale * (startX - 0.5f), scale * (y - 0.65f),scale * (startX), scale * (y - 0.65f));
        convert(scale * (startX), scale * (y - 0.65f),scale * (startX), scale * (y - 1));
        convert(scale * (startX), scale * (y - 1),scale * (startX - 0.5f), scale * (y - 1));
	}
	
	private static void draw0(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y - 0.3f),scale * (startX), scale * (y - 1));
        convert(scale * (startX), scale * (y - 1),scale * (startX - 0.5f), scale * (y - 1));
        convert(scale * (startX - 0.5f), scale * (y - 1),scale * (startX - 0.5f), scale * (y - 0.3f));
        convert(scale * (startX - 0.5f), scale * (y - 0.3f),scale * (startX), scale * (y - 0.3f));
	}
	
	
	/*
	 * Drawing the letters
	 */
	private static void drawA(float scale, float startX, float y)
	{
		convert((scale * (startX - 0.45f)),(scale * (y-0.64f)),(scale * (startX - 0.45f)), (scale * (y-1)));
		convert((scale * (startX - 0.45f)),(scale * (y-1)),(scale * (startX - 0f)), (scale * (y-1)));
        convert((scale * (startX - 0f)),(scale * (y-1)),(scale * (startX - 0f)), (scale * (y-0.7f)));
        convert((scale * (startX - 0f)),(scale * (y-0.7f)),(scale * (startX - 0.45f)), (scale * (y-0.7f)));
        convert((scale * (startX - 0f)),(scale * (y-0.7f)),(scale * (startX - 0f)), (scale * (y-0.4f)));
        convert((scale * (startX - 0f)),(scale * (y-0.4f)),(scale * (startX - 0.45f)), (scale * (y-0.4f)));
	}
	
	private static void drawB(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.5f), scale * (y + 0.2f),scale * (startX - 0.5f), scale * (y - 1));
        convert(scale * (startX - 0.5f), scale * (y - 1),scale * (startX), scale * (y - 1));
        convert(scale * (startX), scale * (y - 1),scale * (startX), scale * (y-0.5f));
        convert(scale * (startX), scale * (y-0.5f),scale * (startX - 0.5f), scale * (y-0.5f));
	}
	
	private static void drawC(float scale, float startX, float y)
	{
        convert(scale * startX, scale * (y-0.5f),scale * (startX - 0.5f), scale * (y-0.5f));
        convert(scale * (startX - 0.5f), scale * (y-0.45f),scale * (startX - 0.5f), scale * (y - 1));
        convert(scale * (startX - 0.5f), scale * (y - 1),scale * (startX-0.05f), scale * (y - 1));
	}
	
	private static void drawD(float scale, float startX, float y)
	{
        convert(scale * startX, scale * (y - 1),scale * startX, scale * (y + 0.2f));
	}
	
	private static void drawE(float scale, float startX, float y)
	{
        convert(scale * startX, scale * (y-0.42f),scale * (startX - 0.4f), scale * (y-0.42f));
        convert(scale * startX, scale * (y-0.4f),scale * startX, scale * (y - 0.85f));
        convert(scale * startX, scale * (y - 0.8f),scale * (startX - 0.4f), scale * (y - 0.8f));
        convert(scale * (startX - 0.4f), scale * (y-0.4f),scale * (startX - 0.4f), scale * (y - 0.9f));
        convert(scale * (startX - 0.4f), scale * (y - 0.8f),scale * (startX - 0.4f), scale * (y - 1f));
        convert(scale * (startX - 0.4f), scale * (y - 1f),scale * (startX), scale * (y - 1f));
	}
	
	private static void drawF(float scale, float startX, float y)
	{
        convert(scale * (startX + 0.15f), scale * (y - 0.1f),scale * (startX - 0.25f), scale * (y - 0.1f));
        convert(scale * (startX - 0.25f), scale * (y - 0.1f),scale * (startX - 0.25f), scale * (y - 1.1f));
        convert(scale * (startX - 0.4f), scale * (y - 0.45f),scale * (startX), scale * (y - 0.45f));
	}
	
	private static void drawG(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y-1f),scale * (startX), scale * (y - 1.2f));
        convert(scale * (startX), scale * (y - 1.2f),scale * (startX - 0.4f), scale * (y - 1.2f));
	}
	
	private static void drawH(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.5f), scale * (y + 0.3f),scale * (startX - 0.5f), scale * (y - 1.1f));
        convert(scale * (startX - 0.5f), scale * (y-0.5f),scale * (startX), scale * (y-0.5f));
        convert(scale * (startX), scale * (y-0.5f),scale * (startX), scale * (y - 1.1f));
	}
	
	private static void drawI(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y-0.4f),scale * (startX), scale * (y - 1.1f));
        convert(scale * (startX), scale * (y - 0.15f),scale * (startX), scale * (y - 0.15f));
        
	}
	
	private static void drawJ(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y-0.4f),scale * (startX), scale * (y - 1.1f));
        convert(scale * (startX), scale * (y - 1.1f),scale * (startX - 0.25f), scale * (y - 1.1f));
        convert(scale * (startX), scale * (y - 0.15f),scale * (startX), scale * (y - 0.15f));
        
	}
	
	private static void drawK(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.5f), scale * (y),scale * (startX - 0.5f), scale * (y - 1.1f));

        GL11.glLineWidth(3.5f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glEnable(GL11.GL_LINE_WIDTH);
        GL11.glVertex2f(scale * (startX - 0.4f), scale * (y-0.45f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.4f), scale * (y-0.45f));
        GL11.glVertex2f(scale * (startX), scale * (y  - 0.1f));
        GL11.glEnd();
        
	}
	
	private static void drawL(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y + 0.3f),scale * (startX), scale * (y - 1.1f));
	}
	
	private static void drawM(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.5f), scale * (y-0.5f),scale * (startX - 0.5f), scale * (y - 1.1f));
        convert(scale * (startX - 0.5f), scale * (y-0.5f),scale * (startX - 0.25f), scale * (y-0.5f));
        convert(scale * (startX - 0.25f), scale * (y-0.5f),scale * (startX - 0.25f), scale * (y - 1.1f));
        convert(scale * (startX - 0.25f), scale * (y-0.5f),scale * (startX), scale * (y-0.5f));
        convert(scale * (startX), scale * (y-0.5f),scale * (startX), scale * (y - 1.1f));
	}
	
	private static void drawN(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.4f), scale * (y-0.5f),scale * (startX - 0.4f), scale * (y - 1.1f));
        convert(scale * (startX - 0.4f), scale * (y-0.5f),scale * (startX), scale * (y-0.5f));
        convert(scale * (startX), scale * (y-0.5f),scale * (startX), scale * (y - 1.1f));
	}
	
	private static void drawO(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.4f), scale * (y - 1f),scale * (startX), scale * (y - 1f));
        convert(scale * (startX - 0.4f), scale * (y-0.5f),scale * (startX - 0.4f), scale * (y - 1f));
        convert(scale * (startX - 0.4f), scale * (y-0.5f),scale * (startX), scale * (y-0.5f));
        convert(scale * (startX), scale * (y-0.5f),scale * (startX), scale * (y - 1f));
        
	}
	
	private static void drawP(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.4f), scale * (y - 1f),scale * (startX - 0.4f), scale * (y - 1.4f));
	}
	
	private static void drawQ(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y - 1f),scale * (startX), scale * (y - 1.4f));
	}
	
	private static void drawR(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.35f), scale * (y-0.5f), scale * (startX - 0.35f), scale * (y - 1.1f));
        convert(scale * (startX - 0.35f), scale * (y - 0.5f),scale * (startX), scale * (y-0.5f));
	}
	
	private static void drawS(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y-0.4f),scale * (startX - 0.4f), scale * (y-0.4f));
        convert(scale * (startX - 0.4f),scale * (y-0.4f),scale * (startX - 0.4f),scale * (y - 0.75f));
        convert(scale * (startX - 0.4f), scale * (y - 0.75f), scale * (startX), scale * (y - 0.75f));
        convert(scale * (startX),scale * (y - 0.75f),scale * (startX),scale * (y - 1));
        convert(scale * (startX), scale * (y - 1), scale * (startX - 0.4f), scale * (y - 1));
	}
	
	private static void drawT(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.4f),scale * (y - 0.55f),scale * (startX),scale * (y - 0.55f));
        convert(scale * (startX - 0.2f), scale * (y - 0.2f),scale * (startX - 0.2f), scale * (y - 1.1f));
	}
	
	private static void drawU(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.4f), scale * (y-0.45f),scale * (startX - 0.4f), scale * (y - 1f));
        convert(scale * (startX - 0.4f), scale * (y - 1f),scale * (startX), scale * (y - 1f));
        convert(scale * (startX), scale * (y-0.45f),scale * (startX), scale * (y - 1f));
	}
	
	private static void drawV(float scale, float startX, float y)
	{        
        GL11.glLineWidth(3.5f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glEnable(GL11.GL_LINE_WIDTH);
        GL11.glVertex2f(scale * (startX - 0.3f), scale * (y-0.4f));
        GL11.glVertex2f(scale * (startX - 0.15f), scale * (y - 1));
        GL11.glVertex2f(scale*(startX - 0.15f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y-0.4f));
        GL11.glEnd();
	}
	
	private static void drawW(float scale, float startX, float y)
	{
        
        GL11.glLineWidth(3.5f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glEnable(GL11.GL_LINE_WIDTH);
        GL11.glVertex2f(scale * (startX - 0.52f), scale * (y-0.4f));
        GL11.glVertex2f(scale * (startX - 0.39f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.39f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.26f), scale * (y-0.4f));
        
        GL11.glVertex2f(scale * (startX - 0.26f), scale * (y-0.4f));
        GL11.glVertex2f(scale * (startX - 0.13f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.13f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y-0.4f));
        GL11.glEnd();
        
	}
	
	private static void drawX(float scale, float startX, float y)
	{                
        GL11.glLineWidth(3.5f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glEnable(GL11.GL_LINE_WIDTH);
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.5f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glEnable(GL11.GL_LINE_WIDTH);
        GL11.glVertex2f(scale * (startX), scale * (y-0.5f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glEnd();
	}
	
	private static void drawY(float scale, float startX, float y)
	{
        convert(scale * (startX - 0.4f), scale * (y-1.3f),scale * (startX), scale * (y - 1.3f));
        convert(scale * (startX), scale * (y- 1f),scale * (startX), scale * (y - 1.3f));
	}
	
	private static void drawZ(float scale, float startX, float y)
	{
        convert(scale * (startX), scale * (y-0.5f),scale * (startX - 0.5f), scale * (y-0.5f));
        convert(scale * (startX), scale * (y - 1), scale * (startX - 0.5f), scale * (y - 1));
        
        GL11.glLineWidth(3.5f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glEnable(GL11.GL_LINE_WIDTH);
        GL11.glVertex2f(scale * (startX - 0.4f), scale * (y-1f));
        GL11.glVertex2f(scale * (startX), scale * (y - 0.5f));
        GL11.glEnd();
	}
	
	/**
	 * The original coordinates of each rectangle are given as two points, 
	 * so this method converts those two into four coordinates so that it can
	 * call the drawrectangle method
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	private static void convert(float x1, float y1, float x2, float y2)
	{
		double[] vertices = {x1,y1,0.9,x1,(y2+0.01),0.9,(x2+0.01),(y2+0.01),0.9,(x2+0.01),y1,0.9};
		Rectangle.drawrectangle(vertices, Menu.getRectangleModel(), false);
	}
	

}