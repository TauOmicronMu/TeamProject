package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;

/**
 * Input a Ball, Platform[], (AI level)
 * Output a x-velocity 
 * @author ava
 *
 */

public class AIEngine {

	//Saving training data by using hash table.
	Hashtable<String, Double> database;
	GameState game;
	
	/**
	 * Train the AI before the game start
	 * @param g GameState
	 * @throws IOException 
	 */
	public AIEngine(GameState g) throws IOException{
		System.out.println("Start");
		database = new Hashtable<String, Double>();
		
		double[] xV = new double[100];
		int counter = 0;
		for(double i=-5; i<=5;i=i+0.1){//Is the maximum x velocity 5?
			xV[counter] = i;
		}
		
		this.game = g;
		int maxWidth = game.getWindowWidth();
		int maxHeight = game.getWindowHeight();
//		Generate sample input, x means x-distance between ball and platform, y is y-distance between ball and platform
//		Generates x >= 0 only, if x < 0, -result
		for(int vy = 55; vy <= 100; vy++){//change to 10~100
			System.out.println(vy);
			for(int x = -70; x < 40; x++){//if the maximum x velocity is larger, range of x and y can be bigger
				for(int y = 60; y < 250; y++){
					EDA eda = new EDA();
					toScore f = new toScore(x,y,vy);
					double result = eda.simpleEDA(f, xV, 25, x, y);
					String k = x + "," + y + "," + vy;
					if(result == -1.0){
						if(database.get((x-1) + "," + y + "," + vy) != null){
							database.put(k,database.get((x-1) + "," + y + "," + vy));
						}else if(database.get(x + "," + (y-1) + "," + vy) != null){
							database.put(k,database.get(x + "," + (y-1) + "," + vy));
						}else{
							database.put(k,result);
						}
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

