package main;

public class PowerUpShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "shaders/pshader.vs";
	private static final String FRAGMENT_FILE = "shaders/shader.fs";

	public PowerUpShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

}
