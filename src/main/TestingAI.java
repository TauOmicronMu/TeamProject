package main;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Run this class to pre-train AI
 * And notice that the argument of GameState is the size of game screen.
 * @author ava
 *
 */
public class TestingAI {
	public static void main(String args[]) throws ClassNotFoundException, IOException, InterruptedException{
		GameState game = new GameState(800,800);
		AIEngine temp = new AIEngine(game);
	}
}
