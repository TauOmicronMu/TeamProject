package main;

public class PowerUpShader extends ShaderProgram{
	
	private static String VERTEX_FILE = "shaders/pshader.vs";
	private static final String FRAGMENT_FILE = "shaders/shader.fs";

	public PowerUpShader(String vs) {
		super(VERTEX_FILE, FRAGMENT_FILE);
		VERTEX_FILE = ("shaders/" + vs + ".vs");
	}

}
