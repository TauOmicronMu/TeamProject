package teamproject;

public class Distribution {
	
	private int distanceX;
	private int distanceY;
	private int min_power = 1;
	private int max_power = 10;
	private int min_angle = 40;
	private int max_angle = 140;
	private Move bestMove = new Move();
	
	public Distribution(int dx, int dy){
		this.distanceX = dx;
		this.distanceY = dy;
		int minimum = 0; 
		int maximum = 0;
		Move[] move;
		int[] score;
		while(minimum < 50){//or score.length < x
			move = new Move[max_angle-min_angle-1];
			score = generateMove(move);
			setRange(move,score);
			//check when to stop generate new move
			minimum = score[0];
			for(int i = 0; i < score.length; i++){
				if(minimum > score[i]) minimum = score[i];
			}
			//choose the highest score to be the best move
			for(int i = 0; i < score.length; i++){
				if(maximum < score[i]){
					bestMove.setAngle(move[i].getAngle());
					bestMove.setPower(move[i].getPower());
					maximum = score[i];
				}
			}
		}
	}	
	
	public Move getBestMove(){
		return bestMove;
	}

	//use average score to reset the range of power and angle
	private void setRange(Move[] move, int[] score){
		int average;
		int total = 0;
		for(int i = 0; i < score.length; i++){
			total += score[i];
		}
		average = total / score.length;
		int t1 = 0;
		while(score[t1] >= average||t1 >= score.length -1){
			min_angle = move[t1].getAngle();
			min_power = move[t1].getPower();
			t1++;
		}
		int t2 = score.length - 1;
		while(score[t2] >= average || t2 == 0){
			max_angle = move[t2].getAngle();
			max_angle = move[t2].getPower();
			t2--;
		}
	}
	
	//generate candidate move
	private int[] generateMove(Move[] move){
		int index = 0;
		for(int p = min_power; p <= max_power; p++){
			for(int a = min_angle; a <= max_angle; a += (max_angle-min_angle)/10){
				try{
					move[index].setPower(p);
					move[index].setAngle(a);
					index++;
				}catch(Exception ex){
					
				}
			}
		}
		return moveToScore(move);
	}
	
	//generate a new array to record the scores of moves
	private int[] moveToScore(Move[] move){
		int[] score = new int[move.length];
		for(int i = 0; i <= move.length; i++){
			score[i] = calculate(move[i].getPower(), move[i].getAngle());
		}
		return score;
	}
	
	//calculate the score of move
	private int calculate(int p, int a){
		int s = 0;
		//function to calculate where the ball hit
		//if the ball miss the platform, s = 0
		//if the ball hit the middle of the platform, s = 100
		//if the ball hit near the bound of the platform, 0 < s < 100 
		return s;
	}
}
