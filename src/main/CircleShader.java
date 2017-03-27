package main;

public class CircleShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "shaders/cshader.vs";
	private static final String FRAGMENT_FILE = "shaders/shader.fs";

	public CircleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}
}
