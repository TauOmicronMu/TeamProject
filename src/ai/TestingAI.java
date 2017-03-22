package ai;

import main.Constants;
import main.GameState;

import java.io.IOException;
import java.util.Hashtable;

public class TestingAI {
	public static void main(String args[]) throws ClassNotFoundException, IOException, InterruptedException{
		GameState game = new GameState(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);
		AIEngine temp = new AIEngine(game);  //run this at the very beginning
	}
}
