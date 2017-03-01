package teamproject;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.stat.*; 
import org.apache.commons.math3.distribution.*;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.Arrays;

public class EDA {
	private final int A = 100; // number of NEW samples per iteration: angel
	private final int P = 5;
    private final int Dim = 2; // dimension of vectors: Power & Angle
    
    private ArrayList<Double> score = new ArrayList<>(); //store score which show where the ball hit
    private ArrayList<Double> angle = new ArrayList<>(); //angle corresponding to score list
    private ArrayList<Double> power = new ArrayList<>(); //power corresponding to score list 
    
    //Getter//
    public EDA(){
    	
    	
    }
    
    public ArrayList<Double> getScore(){
        return score;
    }
    
    public ArrayList<Double> getAngle(){
    	return angle;
    }
    
    public ArrayList<Double> getPower(){
    	return power;
    }
    
    /*
    public ArrayList<Double[]> getAnglePower(){
    	return anglePower;
    }*/

    public double simpleEDA(toScoreFunction f,double[] a, int time){//time: times of iteration
    	int iterationCounter = 0;//count how many time of iteration it did
    	//double[] keepAngle = a;
    	ArrayList<Double> keepAngle = new ArrayList<Double>();
    	for(int i = 0; i < a.length-1; i++){
    		keepAngle.add(a[i]);
    	}
    	/*
    	double[] temp = new double[keepAngle.size()];
    	for(int i = 0; i < keepAngle.size()-1;i++){
    		temp[i] = keepAngle.get(i);
    	}
    	*/
    	while (iterationCounter < time)
    	{
    		double meanD = mean(keepAngle);
            double varianceD = variance(keepAngle) % keepAngle.size(); 
            //NormalDistribution M = new NormalDistribution(meanD,varianceD); //Normal distribution of parameter
            NormalDistribution M = new NormalDistribution(meanD,varianceD); //Normal distribution of parameter
            ArrayList<Double> sampleArray = sampleRange(M.sample(A)); // choose S samples angle from M distribution

            ArrayList<Double> result = f.apply(sampleArray); // apply sample into function f
        
            System.out.println("Sample Angle:");
            printArray(sampleArray);
            System.out.println("Result:");
            printArray(result);
            
            keepAngle = new ArrayList<Double>();
            double meanResult = mean(result);
            int maxIndex = 0;
            for(int i = 0; i < result.size(); i++){
            	if(result.get(i) > meanResult){
            		keepAngle.add(sampleArray.get(i));
            	}
            	if(result.get(i) > result.get(maxIndex)){
            		maxIndex = i;
            	}
            }
            
            System.out.println("Next sample:");
            printArray(keepAngle);
            
            score.add(result.get(maxIndex));
            angle.add(sampleArray.get(maxIndex));
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
    
    private ArrayList<Double> sampleRange(double[] a){
    	ArrayList<Double> result = new ArrayList<Double>();
    	for(int i = 0; i < a.length -1 ; i++){
    		if(a[i] > 0 && a[i] <= 180){
    			result.add(a[i]);
    		}
    	}
    	return result;
    }
    
    private double mean(ArrayList<Double> l){
    	double[] a = new double[l.size()];
    	for(int i = 0; i < l.size()-1;i++){
    		a[i] = l.get(i);
    	}
    	return StatUtils.mean(a);
    }
    
    private double variance(ArrayList<Double> l){
    	double[] a = new double[l.size()];
    	for(int i = 0; i < l.size()-1;i++){
    		a[i] = l.get(i);
    	}
    	return StatUtils.variance(a);
    }
    
    private void printArray(ArrayList<Double> l){
    	for(int i = 0; i < l.size(); i++){
    		System.out.println(l.get(i));
    	}
    }
}
