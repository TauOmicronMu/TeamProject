package Part4;

import java.awt.Color;
import java.awt.Graphics;

public class AgilityDown extends Item{

	public AgilityDown(int x) {
		super(x);
		// TODO Auto-generated constructor stub
	}
	
	public void performActio(Ball3 ball) {
		if(ball.getAgility()>=2){
			ball.setAgility(ball.getAgility() - 1);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		
		g.setColor(Color.PINK);
		super.paint(g);
	}
}
