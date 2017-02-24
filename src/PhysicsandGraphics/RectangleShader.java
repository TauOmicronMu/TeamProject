package PhysicsandGraphics;

public class RectangleShader extends ShaderProgram{
	
	private static final String VERTEX_FILE = "shaders/rshader.vs";
	private static final String FRAGMENT_FILE = "shaders/shader.fs";

	public RectangleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

}
