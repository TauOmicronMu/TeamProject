package main;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture2 {
	
	private int id;
	private int width;
	private int height;
	private int program;
	private int vs;
	private int fs;
	
	public Texture2(String filename)
	{
		BufferedImage bi;
		
		try{
			program = glCreateProgram();
			
			vs = loadShader("shaders/shadert.vs", GL_VERTEX_SHADER);
			fs = loadShader("shaders/shadert.fs",GL_FRAGMENT_SHADER);
			
			glAttachShader(program,vs);
			glAttachShader(program, fs);
			glBindAttribLocation(program,0, "s_vTexCoord");
			glLinkProgram(program);
			if(glGetProgrami(program,GL_LINK_STATUS) != 1)
			{
				System.err.println(glGetProgramInfoLog(program));
				System.exit(1);
			}
			
			bi = ImageIO.read(new File(filename));
			width = bi.getWidth();
			height = bi.getHeight();
			
			int[] pixels_raw = new int[width*height*4];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
			
			for(int i = 0; i < width; i++)
			{
				for(int j = 0; j < height; j++)
				{
					int pixel = pixels_raw[i*width + j];
					//Colours
					pixels.put((byte)((pixel >> 16) & 0xFF));
					pixels.put((byte)((pixel >> 8) & 0xFF));
					pixels.put((byte)(pixel & 0xFF)); 
					pixels.put((byte)((pixel >> 24) & 0xFF));
					
				}
			}
			
			pixels.flip();
			glEnable(GL_TEXTURE_2D);
			id = glGenTextures();
			
			//glBindTexture(GL_TEXTURE_2D, id);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_BYTE, pixels);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
						
			
			//glEnableVertexAttribArray;

			//glVertexAttribPointer(texCoordID, 2, GL_FLOAT, GL_FALSE, 0, BUFFER_OFFSET(offsetToUVsInBytes));
			
			//texID = glGetUniformLocation(program, "texture");
			
			//glActiveTexture;
			//glUniformli(id, 0);
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static int loadShader(String filename, int type)
	{
		int id = glCreateShader(type);
		glShaderSource(id, readFile(filename));
		glCompileShader(id);
		if(glGetShaderi(id,GL_COMPILE_STATUS) != 1)
		{
			System.err.println(glGetShaderInfoLog(id));
			System.exit(1);
		}
		return id;
	}
	
	/**
	 * Reads the shader files
	 * @param filename
	 * @return the file contents
	 */
	private static String readFile(String filename)
	{
		StringBuilder string = new StringBuilder();
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(new File("src/" + filename)));
			String line;
			while((line = br.readLine()) != null)
			{
				string.append(line);
				string.append("\n");
			}
			
			br.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return string.toString();
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, id);
	}

}
