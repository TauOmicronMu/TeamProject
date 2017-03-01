package main;
import java.io.Serializable;

import org.lwjgl.opengl.GL11;

public class Text implements Serializable{
	
	public Text()
	{
		
	}
	
	public void draw(String s, float x, float y, float scale, float width) {
	      float startX = x;
	      scale = scale * 0.25f;
	      boolean lessspace = false;
	      boolean lessspace2 = false;
	      GL11.glLineWidth(width);
	      GL11.glBegin(GL11.GL_LINES);
	      GL11.glEnable(GL11.GL_LINE_WIDTH);
	      GL11.glColor3f(1, 1, 1);
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
	          case 'c':
	        	  drawC(scale, startX, y);
	        	  break;
	          case 'd':
	        	  drawD(scale, startX, y);
	              break;
	          case 'e':
	        	  drawE(scale, startX, y);
	              break;
	          case 'f':
	        	  drawF(scale, startX, y);
	              break;
	          case 'g':
	        	  drawG(scale, startX, y);
	              break;
	          case 'h':
	        	  drawH(scale, startX, y);
	              break;
	          case 'i':
	        	  drawI(scale, startX, y,width);
	        	  lessspace2 = true;
	              break;
	          case 'j':
	        	  drawJ(scale, startX, y,width);
	              break;
	          case 'k':
	        	  drawK(scale, startX, y);
	              break;
	          case 't':
	        	  drawT(scale, startX, y);
	        	  lessspace = true;
	        	  break;
	          case 'l':
	        	  drawL(scale, startX, y);
	        	  lessspace2 = true;
	              break;
	          case 'm':
	        	  drawM(scale, startX, y);
	              break;
	          case 'p':
	        	  drawP(scale, startX, y);
	        	  break;
	          case 'o':
	        	  drawO(scale, startX, y);
	        	  break;
	          case 'n':
	        	  drawN(scale, startX, y);
	              break;
	          case 'q':
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
	        	  lessspace = true;
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
	        	  drawY(scale, startX, y);
	              break;
	          case 'z':
	        	  drawZ(scale, startX, y);
	              break;
	          case '1':
	              GL11.glVertex2f(scale * (startX - 0.25f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX - 0.25f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              break;
	          case '2':
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.65f));
	              GL11.glVertex2f(scale * (startX - 0.3f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.3f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.15f), scale * (y));
	              GL11.glVertex2f(scale * (startX - 0.15f), scale * (y));
	              GL11.glVertex2f(scale * (startX - 0.35f), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              break;
	          case '3':
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	              break;
	          case '4':
	              GL11.glVertex2f(scale * startX, scale * (y + 0.8f));
	              GL11.glVertex2f(scale * startX, scale * (y - 1));
	              GL11.glVertex2f(scale * startX, scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              break;
	          case '8':
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	          case '6':
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	          case '5':
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	              break;
	          case '7':
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              break;
	          case '9':
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	              break;
	          case '0':
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.8f));
	              GL11.glVertex2f(scale * (startX), scale * (y + 0.8f));
	              break;
	          }
	          if(!space){
	        	  if(lessspace)
	        	  {
	        		  startX += scale * 0.55/scale;
	        	  }
	        	  if(lessspace2)
	        	  {
	        		  startX += scale * 0.45/scale;
	        	  }
	        	  if(!lessspace && !lessspace2)
	        	  {
	        		  startX += scale * 0.65/scale;
	        	  }
	              charNum++;
	          }else{
	              startX += scale * 3/scale;
	              spaceNum++;
	          }
	          space = false;
	          lessspace = false;
	          lessspace2 = false;
	      }
	      GL11.glEnd();
	      GL11.glDisable(GL11.GL_LINE_WIDTH);
	   }
	
	private void drawBack(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * startX, scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.2f), scale * (y - 0.1f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.3f));
        GL11.glVertex2f(scale * (startX - 0.2f), scale * (y - 0.5f));
	}
	
	private void drawA(float scale, float startX, float y)
	{
		
		/*!1-2*/GL11.glVertex2f(scale * (startX - 0.55f), scale * (y-0.7f));
        /*1-!2*/GL11.glVertex2f(scale * (startX - 0.55f), scale * (y -1.0f));
        /*!2-3*/GL11.glVertex2f(scale * (startX - 0.55f), scale * (y -1.0f));
        /*2-!3*/GL11.glVertex2f(scale * (startX-0.05f), scale * (y -1.0f));
        /*!3-4*/GL11.glVertex2f(scale * (startX-0.05f), scale * (y -1.0f));
        /*3-!4*/GL11.glVertex2f(scale * (startX-0.05f), scale * (y-0.7f));
        /*!4-1*/GL11.glVertex2f(scale * (startX-0.05f), scale * (y-0.7f));
        /*4-!1*/GL11.glVertex2f(scale * (startX - 0.55f), scale * (y-0.7f));
        /*!4-5*/GL11.glVertex2f(scale * (startX-0.05f), scale * (y-0.7f));
        /*4-!5*/GL11.glVertex2f(scale * (startX-0.05f), (scale) * (y - 0.4f));
        /*!5-6*/GL11.glVertex2f(scale * (startX-0.05f), (scale) * (y - 0.4f));
        /*5-!6*/GL11.glVertex2f(scale * (startX - 0.55f), scale * (y - 0.4f));
	}
	
	private void drawB(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
	}
	
	private void drawC(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * startX, scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * startX, scale * (y - 1));
	}
	
	private void drawD(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * startX, scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * startX, scale * (y - 1));
		GL11.glVertex2f(scale * startX, scale * (y - 1));
        GL11.glVertex2f(scale * startX, scale * (y + 0.3f));
	}
	
	private void drawE(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * startX, scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * startX, scale * (y-0.3f));
        GL11.glVertex2f(scale * startX, scale * (y - 0.7f));
        GL11.glVertex2f(scale * startX, scale * (y - 0.7f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.7f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.7f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.7f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1f));
	}
	
	private void drawF(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX + 0.15f), scale * (y - 0.0f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 0.0f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 0.0f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.45f));
        GL11.glVertex2f(scale * (startX), scale * (y - 0.45f));
	}
	
	private void drawG(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1.2f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1.2f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1.2f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.9f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.9f));
        GL11.glVertex2f(scale * (startX), scale * (y - 0.9f));
	}
	
	private void drawH(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1f));
	}
	
	private void drawI(float scale, float startX, float y, float width)
	{
		GL11.glVertex2f(scale * (startX - 0.35f), scale * (y-0.5f));
        GL11.glVertex2f(scale * (startX - 0.35f), scale * (y - 1f));
        GL11.glEnd();
        GL11.glPointSize(width);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2f(scale * (startX - 0.35f), scale * (y - 0.25f));
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINES);
	}
	
	private void drawJ(float scale, float startX, float y, float width)
	{
		GL11.glVertex2f(scale * (startX - 0.25f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 1.0f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 1.0f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1.0f));
        GL11.glEnd();
        GL11.glPointSize(width);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 0.05f));
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINES);
	}
	
	private void drawK(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y + 0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.35f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.35f));
        GL11.glVertex2f(scale * (startX), scale * (y + 0.05f));
	}
	
	private void drawL(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.25f), scale * (y + 0.3f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
	}
	
	private void drawM(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
	}
	
	private void drawN(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.6f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.6f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.6f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.1f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.1f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.1f), scale * (y - 1));
	}
	
	private void drawO(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
	}
	
	private void drawP(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1.5f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
	}
	
	private void drawQ(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y - 1.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.4f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.4f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.4f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.4f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
	}
	
	private void drawR(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1.0f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.45f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y - 0.4f));
	}
	
	private void drawS(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.65f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 0.65f));
        GL11.glVertex2f(scale * (startX), scale * (y - 0.65f));
        GL11.glVertex2f(scale * (startX), scale * (y - 0.65f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	}
	
	private void drawT(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.55f), scale * (y - 0.45f));
        GL11.glVertex2f(scale * (startX - 0.05f), scale * (y - 0.45f));
        GL11.glVertex2f(scale * (startX - 0.3f), scale * (y - 0.1f));
        GL11.glVertex2f(scale * (startX - 0.3f), scale * (y - 1.0f));
	}
	
	private void drawU(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.4f), scale * (y-0.4f));
        GL11.glVertex2f(scale * (startX - 0.4f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.4f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y-0.4f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
	}
	
	private void drawV(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.25f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
	}
	
	private void drawW(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.6f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.4f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.4f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.2f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.2f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX + 0f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX + 0f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX + 0.2f), scale * (y-0.3f));
	}
	
	private void drawX(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
	}
	
	private void drawY(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.33f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1.5f));
	}
	
	private void drawZ(float scale, float startX, float y)
	{
		GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX), scale * (y-0.3f));
        GL11.glVertex2f(scale * (startX - 0.5f), scale * (y - 1));
        GL11.glVertex2f(scale * (startX), scale * (y - 1));
	}

}