package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

/**
 * Input a Ball, Platform[], (AI level)
 * Output a x-velocity 
 * @author Ava
 *
 */

public class AIEngine {

	//Saving training data by using hash table.
	Hashtable<String, Double> database;
	GameState game;
	private final static int platformW = 140;
	
	/**
	 * Train the AI before the game start
	 * @param g GameState
	 * @throws IOException 
	 */
	public AIEngine(GameState g) throws IOException{
		System.out.println("Start");
		database = new Hashtable<String, Double>();
		
		double[] xV = new double[20];
		int counter = 0;
		for(double i=-5; i<=5;i=i+0.5){//Is the maximum x velocity 5?
			xV[counter] = i;
		}
		
		this.game = g;
		int maxWidth = game.getWindowWidth();
		int maxHeight = game.getWindowHeight();
//		Generate sample input, x means x-distance between ball and platform, y is y-distance between ball and platform
//		Generates x >= 0 only, if x < 0, -result
		for(int vy = -100; vy <= 100; vy++){
			System.out.println(vy);
			for(int x = -(platformW/2); x < maxWidth-100; x=x+10){//if the maximum x velocity is larger, range of x and y can be bigger
				for(int y = -maxHeight+100; y < maxHeight-100; y=y+10){//Try y < 350
					EDA eda = new EDA();					
					toScore f = new toScore(x,y,vy,platformW);
					double result = eda.simpleEDA(f, xV, 4, x, y);
					String k = x + "," + y + "," + vy;
					if(result == -1.0){
						database.put(k, 5.0);
//						if(database.get((x-1) + "," + y + "," + vy) != null){
//							database.put(k,database.get((x-1) + "," + y + "," + vy));
//						}else if(database.get(x + "," + (y-1) + "," + vy) != null){
//							database.put(k,database.get(x + "," + (y-1) + "," + vy));
//						}else{
//							database.put(k,result);
//						}
					}else{
						database.put(k,result);
					}
				}
				//System.out.println(x + ": " + database.get(x + "," + (x+140) + "," +vy));
			}
		}
		FileOutputStream fos = new FileOutputStream("data.tmp");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(database);
		oos.close();
		
		/*
		For testing only
		EDA eda1 = new EDA();
		toScore f = new toScore(0,100,90);
		double result = eda1.simpleEDA(f, xV, 50, 0, 100);
		System.out.println(result);
		EDA eda2 = new EDA();
		toScore f2 = new toScore(-70,100,90);
		double result2 = eda2.simpleEDA(f2, xV, 50, -70, 100);//Test when ball x-position is same as platform x-position
		System.out.println(result2);
 */
		
	}
	
	
}

