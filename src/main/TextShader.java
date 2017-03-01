package main;

public class TextShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "shaders/tshader.vs";
	private static final String FRAGMENT_FILE = "shaders/shader.fs";

	public TextShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

}