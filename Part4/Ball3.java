package Part4;
import java.awt.Color;
import java.awt.Graphics;

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
	

	public void update(GameClass game) {
		 
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
	
	public void paint(Graphics g){
		g.setColor(Color.BLUE);
		g.fillOval(x-radius, y-radius, radius*2, radius*2);
	}
}