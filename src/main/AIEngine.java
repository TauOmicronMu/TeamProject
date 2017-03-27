package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

/**
 * This class is for pre-training the AI.
 * Due to the architecture of the software(given a GameState we want to determine the best move).
 * Therefore, pre-training the AI reduces to an optimization problem.
 * We approach this optimization using a normal-distribution-based EDA.
 * And the difficulty of the AI can adjusted according to the number of iterations in the learning process.
 * @author Ava
 *
 */

public class AIEngine {

	Hashtable<String, Double> database;	//Saving training data by using hash table.
	GameState game;
	private final static int platformW = 140; //width of platform
	private final static int level = 5; // number of iterations. Higher and the AI become more accurate.
	private double max_speed = 5;// maximum x-velocity of the ball
	
	/**
	 * Pre-training AI, save the training by using hash table, 
	 * so it can read data more convenient and faster in the play time.
	 * @param g GameState
	 * @throws IOException 
	 */
	public AIEngine(GameState g) throws IOException{
		System.out.println("Start pre-training the AI");
		database = new Hashtable<String, Double>();
		
		//Generate initial samples to EDA
		double[] xV = new double[20];
		int counter = 0;
		for(double i=-max_speed; i<max_speed;i=i+max_speed/10){
			xV[counter] = i;
			counter++;
		}
		
		this.game = g;
		int maxWidth = game.getWindowWidth();
		int maxHeight = game.getWindowHeight();
//		The AI will need the distance between the ball and the platform and the y-velocity of the ball as input
//		Then return the move(x-velocity) of the ball as result

//		Generates x >= 0 only, if x < 0, -result
		
		//vy is the y-velocity of the ball
		for(int vy = -100; vy <= 100; vy++){
			//x is the x-distance between the ball and the platform
			//I only generate if the platform is in right hand side of the ball only
			//Because the left hand side result should be -right hand side result.
			for(int x = -(platformW/2); x < maxWidth-100; x=x+10){
				//y is the y-distance between the ball and the platform
				//In fact, the range might would be smaller, just use the maximum y-velocity to find how far the ball can fly
				//I only minus 100 here, which is the height of the platform + the radius of the ball.
				//Notice that the x and y is +10 every time, it is because the data will be too big if AI learn every grid
				//And according to the game design, it do not need to be too accurate.
				//Therefore, I will use convex interpolate when I read the pre-training data in the play time.
				for(int y = -maxHeight+100; y < maxHeight-100; y=y+10){
					EDA eda = new EDA();					
					toScore f = new toScore(x,y,vy,platformW);
					double result = eda.simpleEDA(f, xV, level);
					//Save the pre-training data as a hash table
					//Using the distance and y-velocity as key, x-velocity as value
					String k = x + "," + y + "," + vy;
					if(result == -1.0){//-1.0 means no result, it mainly because the platform is too far to go
						database.put(k, 5.0);//thus always return 5, but this is for safety only, it should not happen
					}else{
						database.put(k,result);
					}
				}
			}
		}
		
		//Save the hash table as an objective file
		//So the it can be read when the game is run.
		FileOutputStream fos = new FileOutputStream("data.tmp");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(database);
		oos.close();
			
	}
	
	
}

