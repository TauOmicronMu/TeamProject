package main;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Hashtable;

import org.junit.Test;

public class AITest {

	@Test
	public void test() throws ClassNotFoundException, IOException {
		GameState game = new GameState(800,800);
		System.out.println("Reading Hashtable...");
		AI db = new AI(game);
		Hashtable<String,Double> database = db.getDB();
		double xv = database.get("-70,200,100");
		System.out.println(xv);
		int result = (int)Math.round(xv);
		assertEquals(0,result);
	}

}

