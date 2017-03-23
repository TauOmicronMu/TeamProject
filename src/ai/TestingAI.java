package ai;

import main.Constants;
import main.GameState;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Hashtable;

/**
 * Run this class at the very beginning. It for training the AI
 */

public class TestingAI {
	public static void main(String args[]) throws ClassNotFoundException, IOException, InterruptedException{
		GameState game = new GameState(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT);
		AIEngine temp = new AIEngine(game);

	}
}
