package Part4;

import java.awt.Color;
import java.awt.Graphics;

public class AgilityUp extends Item{

	public AgilityUp(int x) {
		super(x);
		// TODO Auto-generated constructor stub
	}
	
	public void performActio(Ball3 ball) {
		if(ball.getAgility() < 8){
			ball.setAgility(ball.getAgility() + 1);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		
		g.setColor(Color.ORANGE);
		super.paint(g);
	}
}
