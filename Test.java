package teamproject;

public class Test {
	public static void main(String arg[]){
		double[] angle = new double[180];
		for(int i = 0; i < 180; i++){
			angle[i] = i;
		}
		EDA eda = new EDA();
		toScoreFunction f = new toScoreFunction();
		double result = eda.simpleEDA(f, angle, 100);
		System.out.println(String.valueOf(result));
		
	}
}
