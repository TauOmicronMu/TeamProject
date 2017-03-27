package main;

import java.util.Hashtable;

/**
 * This class should be called when game state is update.
 * Therefore, I change this class as a thread.
 * And run synchronously with the game state's update.
 * @author ava
 *
 */
public class AIThread extends Thread{
	
	public Ball ball = null;
	public Platform[] ps = null;
	public Hashtable<String,Double> database;
	public GameState game;
	public double xVelocity = 5.0;//maximum velocity of x
	
	/**
	 * AIThread constructor
	 * @param db AI pre-training data
	 * @param g game state
	 */
	AIThread(Hashtable<String,Double> db, GameState g){
		this.database = db;
		this.game = g;
	}
	
	/**
	 * update x-velocity of the ball(changed by +-1). 
	 */
	public void run(){		
		readData rd = new readData();
		while(ps != null){
			xVelocity = rd.AI(ball,ps,database,game);
			game.getBall().dx += xVelocity;
			ps = null;

		}

	}
	
	
}
