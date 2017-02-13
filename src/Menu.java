public class Menu {
	
	public Menu(){

	}
	
	public void drawplay(){
		float[] vertices = {-0.4f,0,0,0.4f,0,0,0.4f,-0.2f,0,-0.4f,-0.2f,0};
		Rectangle.drawrectangle(vertices);
	}
	
	public void drawsettings(){
		float[] vertices = {-0.4f,-0.25f,0,0.4f,-0.25f,0,0.4f,-0.45f,0,-0.4f,-0.45f,0};
		Rectangle.drawrectangle(vertices);
	}
	
	public void drawquit(){
		float[] vertices = {-0.4f,-0.5f,0,0.4f,-0.5f,0,0.4f,-0.7f,0,-0.4f,-0.7f,0};
		Rectangle.drawrectangle(vertices);
	}
	
	public static void drawbacktomenu(){
		float[] vertices = {-0.95f,0.95f,0,-0.85f,0.95f,0,-0.85f,0.9f,0,-0.95f,0.9f,0};
		Rectangle.drawrectangle(vertices);
	}
	
	public void drawall(){
		drawplay();
		drawsettings();
		drawquit();
	}
	
}