package teamproject;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.stat.*; 
import org.apache.commons.math3.distribution.*;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.Arrays;

public class EDA {
	private final int S = 100; // number of NEW samples per iteration
    private final int Dim = 2; // dimension of vectors: Power & Angle
    
    private ArrayList<Double> score = new ArrayList<>(); //store score which show where the ball hit
    private ArrayList<Double> angle = new ArrayList<>(); //angle coor to score list
    //private ArrayList<Double[]> anglePower = new ArrayList<Double[]>(); //Store arrays of angle and power
    
    //Getter//
    public EDA(){
    	
    }
    
    public ArrayList<Double> getScore(){
        return score;
    }
    
    public ArrayList<Double> getAngle(){
    	return angle;
    }
    
    /*
    public ArrayList<Double[]> getAnglePower(){
    	return anglePower;
    }*/

    public double simpleEDA(toScoreFunction f,double[] a, /*double[] power,*/ int time){//time: times of iteration
    	int iterationCounter = 0;
    	//double[] keepAngle = a;
    	ArrayList<Double> keepAngle = new ArrayList<Double>();
    	for(int i = 0; i < a.length-1; i++){
    		keepAngle.add(a[i]);
    	}
    	double[] temp = new double[keepAngle.size()];
    	for(int i = 0; i < keepAngle.size()-1;i++){
    		temp[i] = keepAngle.get(i);
    	}
    	
    	while (iterationCounter < time)
    	{
    		double meanD = StatUtils.mean(temp);

            double varianceD = StatUtils.variance(temp) % keepAngle.size(); 
            NormalDistribution M = new NormalDistribution(meanD,varianceD); //Normal distribution of parameter
            double[] sampleArray = sampleRange(M.sample(S)); // choose S samples angle from M distribution
            double[] result = f.apply(sampleArray); // apply sample into function f
            
            keepAngle = new ArrayList<Double>();
            double meanResult = StatUtils.mean(result);
            int maxIndex = 0;
            for(int i = 0; i < result.length-1; i++){
            	if(result[i] > 100){
            		keepAngle.add(sampleArray[i]);
            	}
            	if(result[i] > result[maxIndex]){
            		maxIndex = i;
            	}
            }
            score.add(result[maxIndex]);
            angle.add(sampleArray[maxIndex]);
            iterationCounter++;
    	}
    	int bestAngle = 0;//index of angle
    	for(int i = 0; i < score.size(); i++){
    		if(score.get(i) > score.get(bestAngle)){
    			bestAngle = i;
    		}
    	}
    	
    	return angle.get(bestAngle);
    }
    
    private double[] sampleRange(double[] a){
    	double[] result = new double[a.length];
    	int c = 0;
    	for(int i = 0; i < a.length -1 ; i++){
    		if(a[i] > 0 && a[i] <= 180){
    			result[c] = a[i];
    			c++;
    		}
    	}
    	return result;
    }
    
    /*
    private double[] listToArray(ArrayList<Double> a){
    	
    }
    */
}
