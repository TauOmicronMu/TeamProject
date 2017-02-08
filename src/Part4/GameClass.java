package Part4;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;



public class GameClass extends Applet implements Runnable, KeyListener{
	

	private Image i;
	private Graphics doubleG;
	Ball3 b;
	Ball3 b2;
	Platform p[] = new Platform[10];
	Item item[] = new Item[3];
	
	
	
	@Override
	public void init() {
		
		setSize(800, 800);
		addKeyListener(this);
		
	}
	
	@Override
	public void start() {
		b = new Ball3();
		//b2 = new Ball2(250, 250);
		for(int i = 0; i < p.length; i++){
			Random r = new Random();
			
			p[i] = new Platform(getWidth()- r.nextInt(700), getHeight() - 200 * i, 140, 20);
		}
		
		for(int i = 0; i < item.length; i++){
			Random r = new Random();
			item[i] = new GravUp( -1000 * i);
		}
		Thread thread = new Thread(this);
		thread.start();
		
	}
	
	@Override
	public void run() {
		//thread information
			while(true){
				Random r = new Random();
				
				for(int i = 0; i< item.length; i++){
					if(item[i].getY() == this.getHeight() + 100){
						item[i]=null;
						switch (r.nextInt(4)){
						case 0:
							item[i]=new GravUp(- 10 * r.nextInt(500));
							break;
						case 1:
							item[i]=new GravDown(-10 * r.nextInt(500));
							break;
						case 2:
							item[i]=new AgilityUp(-10 * r.nextInt(500));
							break;
						case 3:
							item[i]=new AgilityDown(-10 * r.nextInt(500));
							break;
						}
					}
				}
				
				for(int i = 0; i < p.length; i++){
					p[i].update(this, b);
				}
				
				for(int i = 0; i < item.length; i++){
					item[i].update(this, b);
				}
				
				b.update(this);
				glfwSwapBuffers(window);
				//repaint();
				
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	@Override
	public void update(Graphics g) {
		if(i==null){
			i = createImage(this.getSize().width, this.getSize().height);
			doubleG = i.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);
		
		doubleG.setColor(getForeground());
		paint(doubleG);
		
		g.drawImage(i, 0, 0, this);
	}
	
	@Override
	public void paint(Graphics g) {
		b.paint(g);
		
		for(int i = 0; i < p.length; i++){
			p[i].paint(g);
		}
		
		for(int i = 0; i < item.length; i++){
			item[i].paint(g);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			b.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			b.moveRight();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
