package gradual;
import static org.lwjgl.opengl.GL11.glColor4f;

public class Ball3 {

	
	private int x = 400;
	private int y = 25;
	private double dx = 0;
	private double dy = 0;
	private int radius = 20;
	private double gravity = 15;
	private double energyloss = 1;
	private double dt = .2; 
	private double xFriction = 0.9;
	private double gameDy = -75;
	private int agility = 3;
	private int maxSpeed = 10;
	

	public Ball3() {
		// TODO Auto-generated constructor stub
	}
	
	public Ball3(int i, int j) {
		x = i;
		y = j;
	}
	
	
	public double getGameDy() {
		return gameDy;
	}
	public void setGameDy(double gameDy) {
		this.gameDy = gameDy;
	}
	public int getX(){
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getDx() {
		return dx;
	}
	public double getDy() {
		return dy;
	}
	public void setDx(double dx) {
		this.dx = dx;
	}
	public void setDy(double dy) {
		this.dy = dy;
	}
	public double getGravity() {
		return gravity;
	}
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public void moveRight() {
		if(dx+agility < maxSpeed){
			dx += agility;
			
		}
	}
	public void moveLeft() {
		if( dx - agility > -maxSpeed){
			dx -= agility;
		}
	}
	

	public void update(Graphics game) {
		 
			if( x + dx > game.getWidth()-radius-1){
				x= game.getWidth()-radius-1;
				dx = -dx;
			}else if (x+ dx <0+radius){
				x = 0 + radius;
				dx = -dx;
			}
			
			else{
				x += dx;
			}
			
			if( y == game.getHeight()- radius - 1){
				dx *= xFriction;
				if(Math.abs(dx) < 0.8){
					dx = 0;
				}
			}
			
			if(y>game.getHeight() - radius - 1){
				y = game.getHeight() - radius - 1;
				dy *= energyloss;
				dy = -dy;
			}else{
				//velocity formula
				dy += gravity *dt;
				//position formula
				y += dy*dt + .5*gravity*dt*dt;
			}
	}
	
	/**
	 * Draws the pinball
	 * @param posx the current x position of the centre of the pinball
	 * @param posy the current y position of the centre of the pinball
	 */
	public void paint(Graphics game)
	{
		float[] vertices = createCircle(game.changexCoord(x),game.changeyCoord(y),400,0.05f);
		Model circle1 = new Model(vertices);
	
		circle1.render(vertices);
	}
	
	/**
	 * Calculates all the points of the circumference of the circle
	 * @param posx the current x position of the centre of the pinball
	 * @param posy the current y position of the centre of the pinball
	 * @param posz the current z position of the centre of the pinball
	 * @return all the points of the circle
	 */
	private static float[] createCircle(float posx, float posy, float posz, double radius)
	{
		int noSides = 360;
		int noVertices = noSides + 2;
		float doublePI = (float) Math.PI * 2;
		
		int i = 1;
		float[] vertices = new float[noVertices*3];
		float x = posx;
		float y = posy;
		float z = posz;
		vertices[0] = x;
		vertices[1] = y;
		vertices[2] = z;
		for(int j= 3; j < (noVertices * 3); j = j + 3)
		{
			
			glColor4f(0,1,0,0);
			
			vertices[j] = (float) ( x + ((radius/1.3) * Math.cos(i * doublePI / noSides)));
			vertices[j+1] = (float) ( y + (radius * Math.sin(i * doublePI / noSides)));
			vertices[j+2] = z;
			i++;
		}

		return vertices;
	}
}