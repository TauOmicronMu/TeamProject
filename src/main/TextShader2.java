package main;

import java.io.Serializable;

public class TextShader2 extends ShaderProgram implements Serializable{
	
	private static final String VERTEX_FILE = "shaders/tshader2.vs";
	private static final String FRAGMENT_FILE = "shaders/shader.fs";

	public TextShader2() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

}