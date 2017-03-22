package ai;

import main.Ball;
import main.GameState;
import main.Platform;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Random;

/**
 * Call this class
 * @author ava
 *
 */
public class AI {

	private GameState game;
	private Hashtable<String, Double> database;
	private ObjectInputStream ois;
	
	@SuppressWarnings("unchecked")
	/**
	 * Read database
	 * @param g GameState
	 */
	public AI() throws IOException, ClassNotFoundException{
		FileInputStream fin = new FileInputStream("data.tmp");
		ois = new ObjectInputStream(fin);
		this.database = (Hashtable<String, Double>) ois.readObject();
		System.out.println("Read hashtable success");
	}
	
	/**
	 * Use this method when the ball bit the platform every time
	 * @param ball
	 * @param platform list
	 * @return
	 */
	public double apply(Ball b, Platform[] ps) {
		readData rd = new readData();
		double xVelocity = rd.AI(b,ps,database,game);
		xVelocity += (-b.getMaxSpeed() + new Random().nextInt(b.getMaxSpeed()*2))-1;
		return xVelocity;
	}
}
