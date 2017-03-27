package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

/**
 * This class is for reading pre-train data as a hash table only.
 * @author ava
 *
 */
public class AI {

	private GameState game;
	private Hashtable<String, Double> database;
	private ObjectInputStream ois;
	
	@SuppressWarnings("unchecked")
	/**
	 * Read pre-training data from the data.tmp objective file to hash table
	 * @param g GameState
	 */
	public AI(GameState g) throws IOException, ClassNotFoundException{
		this.game = g;
		FileInputStream fin = new FileInputStream("data.tmp");
		ois = new ObjectInputStream(fin);
		this.database = (Hashtable<String, Double>) ois.readObject();
		System.out.println("Read hashtable success");
	}
	
	/**
	 * Return pre-training data as hash table type
	 * @return pre-training data
	 */
	public Hashtable<String, Double> getDB(){
		return database;
	}

}
