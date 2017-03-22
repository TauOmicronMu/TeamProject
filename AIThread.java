package main;

import java.util.Hashtable;

public class AIThread extends Thread{
	/**
	 * Use this method when the ball bit the platform every time
	 * @param ball
	 * @param platform list
	 * @return
	 */
	public Ball ball = null;
	public Platform[] ps = null;
	public Hashtable<String,Double> database;
	public GameState game;
	public double xVelocity = 5.0;
	
	AIThread(Hashtable<String,Double> db, GameState g){
		this.database = db;
		this.game = g;
	}
	
	public void run(){		

		readData rd = new readData();
		while(ps != null){
			xVelocity = rd.AI(ball,ps,database,game);
			game.getBall().dx += xVelocity;
			ps = null;

		}

	}
	
	
}
