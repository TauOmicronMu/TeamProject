package teamproject;

public class toScoreFunction {
	public toScoreFunction(){
		
	}
	
	public double[] apply(double[] angle){
		double[] result = new double[angle.length];
		for(int i = 0; i < angle.length -1 ; i++){
			result[i] = scoringFunction(angle[i]);
		}
		return result;
	}	
	
	public double scoringFunction(double angle){//Temp, waiting for function
		return angle+1;
	}
}
