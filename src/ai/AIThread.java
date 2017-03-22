package ai;

import main.Ball;
import main.GameState;
import main.Platform;

import java.util.Hashtable;

public class AIThread{
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
	
	public AIThread(Hashtable<String, Double> db, GameState g){
		this.database = db;
		this.game = g;
		this.ball = g.getBall();
		this.ps = g.getPlatforms();
	}
	
	public double run(){
		readData rd = new readData();
		xVelocity = rd.AI(ball,ps,database,game);
		return xVelocity;
	}
	
	
}
