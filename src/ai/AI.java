package ai;

import main.GameState;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

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
	
	public Hashtable<String, Double> getDB(){
		return database;
	}

}
