package ai;

import main.Constants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;


public class AIEngine {

	//Saving training data by using hash table.
	Hashtable<String, Double> database;
	main.GameState game;
	private final static int platformW = Constants.PLATFORM_WIDTH;

	/**
	 * Train the AI before the game start
	 * @param g GameState
	 * @throws IOException
	 */
	public AIEngine(main.GameState g) throws IOException{
		System.out.println("Start");
		database = new Hashtable<String, Double>();

		double[] xV = new double[20];
		int counter = 0;
		for(double i=-Constants.MAX_SPEED; i<Constants.MAX_SPEED;i=i+(Constants.MAX_SPEED/10)){
			xV[counter] = i;
		}

		this.game = g;
		int maxWidth = game.getWindowWidth();
		int maxHeight = game.getWindowHeight();
//		Generate sample input, x means x-distance between ball and platform, y is y-distance between ball and platform
//		Generates x >= 0 only, if x < 0, -result
		for(int vy = -100; vy <= 100; vy++){
			System.out.println(vy);
			for(int x = -(platformW/2); x < maxWidth-100; x=x+10){
				for(int y = -maxHeight+100; y < maxHeight-100; y=y+10){
					EDA eda = new EDA();
					toScore f = new toScore(x,y,vy,platformW);
					double result = eda.simpleEDA(f, xV, 10, x, y);
					String k = x + "," + y + "," + vy;
					if(result == -1.0){
						database.put(k, 20.0);
					}else{
						database.put(k,result);
					}
				}
			}
		}

		//Output the result as an Object file
		FileOutputStream fos = new FileOutputStream("data.tmp");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(database);
		oos.close();



	}


}

