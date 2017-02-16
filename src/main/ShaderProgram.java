package main;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Allows for different (and cool) colours!
 * @author Ella
 *
 */
public abstract class ShaderProgram {
	
	private int program;
	private int vs; //vertex shader
	private int fs; //fragment shader
	
	/**
	 * Uses the shader files to create a shader which can then be used to bring colour
	 * @param filename
	 */
	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		program = glCreateProgram();
		vs = loadShader(vertexFile, GL_VERTEX_SHADER);
		fs = loadShader(fragmentFile,GL_FRAGMENT_SHADER);
		
		
		glAttachShader(program,vs);
		glAttachShader(program, fs);
		glBindAttribLocation(program, 0, "vertices");
		glLinkProgram(program);
		if(glGetProgrami(program,GL_LINK_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		
		
		glValidateProgram(program);
		if(glGetProgrami(program,GL_VALIDATE_STATUS) != 1)
		{
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
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
	 * Allows you to use the shader
	 */
	public void bind()
	{
		glAttachShader(program,vs);
		glAttachShader(program, fs);
		glLinkProgram(program);
		glUseProgram(program);
	}
	
	
	public void stop()
	{
		glUseProgram(0);
		glDetachShader(program,vs);
		glDetachShader(program,fs);
	}
	
	public void cleanUp()
	{
		stop();
		glDetachShader(program,vs);
		glDetachShader(program,fs);
		glDeleteShader(vs);
		glDeleteShader(fs);
		glDeleteProgram(program);
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
			br = new BufferedReader(new FileReader(new File("C:/Users/Ella/workspace/EngineTry3/src/" + filename)));
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

}
