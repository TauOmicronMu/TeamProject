package teamproject;

public class AI {
	
	private Move move;
	
	public void AI(int dx, int dy){
		Distribution d = new Distribution(dx,dy);
		move = d.getBestMove();
	}
}
