# TeamProject
AI:
Library:
Add the 6 commons-math3-3.6.1- jar files.

Prepare in advance:
Run the TestingAI class to generate the hashtable object file first, which need to run once only.

Start the game:
Put this in the main class(the initializeGame method):

    System.out.println("Reading Hashtable...");
		AI t = new AI(game);  //run this before the game start
		Hashtable<String, Double> database = t.getDB();
		AIThread ai = new AIThread(database, game);
		game.setAI(ai);
		ai.start();
    

Running the game:
Put this in the GameState class(I put it in the top of the updatePhysics method):

        ai.ps = this.platforms;
	    	ai.ball = this.ball;
	    	ai.run();
