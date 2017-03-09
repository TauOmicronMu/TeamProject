package main;

//import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.stat.*; 
import org.apache.commons.math3.distribution.*;
//import java.util.function.Function;
import java.util.ArrayList;
//import java.util.Arrays;

public class EDA {
	private final int xv = 100; // number of NEW samples per iteration: x-velocity
    
    private ArrayList<Double> score = new ArrayList<>(); //store score which show where the ball hit
    private ArrayList<Double> xVelocity = new ArrayList<>(); //x-velocity corresponding to score list
    
    //Getter//
    public EDA(){
    	
    	
    }
    
    public ArrayList<Double> getScore(){
        return score;
    }
    
    public ArrayList<Double> getXVelcoity(){
    	return xVelocity;
    }
    
    /**
     * generate a EDA
     * @param f a function that returns score which shows where the ball hit.
     * @param xV range of x-velocity
     * @param time time of iteration, more times will returns a better result
     * @return a best x-velocity of specify x and y distance
     */
    public double simpleEDA(toScore f,double[] xV, int time, int x, int y){
    	int iterationCounter = 0;//count how many time of iteration it did
    	ArrayList<Double> keepSample = new ArrayList<Double>();
    	for(int i = 0; i < xV.length-1; i++){
    		keepSample.add(xV[i]);
    	}
 
    	while (iterationCounter < time){
    		double meanD = mean(keepSample);
            double varianceD = variance(keepSample)+5; //if maximum x velocity is bigger
            NormalDistribution M = new NormalDistribution(meanD,varianceD); //Normal distribution of parameter
            ArrayList<Double> sampleArray = sampleRange(M.sample(xv)); // choose S samples from M distribution
            ArrayList<Double> result = f.apply(sampleArray); // apply sample into function f
            if(result == null){
            	return -1.0;
            }
//            System.out.println("Sample:");
//            printArray(sampleArray);
//            System.out.println("Result:");
//            printArray(result);
            
            keepSample = new ArrayList<Double>();
            double meanResult = mean(result);
            int maxIndex = 0;
            for(int i = 0; i < result.size(); i++){
            	if(result.get(i) > meanResult){
            		keepSample.add(sampleArray.get(i));
            	}
            	if(result.get(i) > result.get(maxIndex)){
            		maxIndex = i;
            	}
            }
            
//            System.out.println("Next sample:");
//            printArray(keepSample);
//            System.out.println("MaxIndex: " + maxIndex + " Result size: " + result.size());
            if(result.size() == 0){
            	return -1.0;
            }
            score.add(result.get(maxIndex));
            xVelocity.add(sampleArray.get(maxIndex));
            iterationCounter++;
    	}
    	int bestXV = 0;//index of x-velocity
    	for(int i = 0; i < score.size(); i++){
    		if(score.get(i) > score.get(bestXV)){
    			bestXV = i;
    		}
    	}
//    	System.out.println(xVelocity.get(bestXV));
    	return xVelocity.get(bestXV);
    }
    
    /**
     * Remove badly sample
     * @param a ArrayList of new sample
     * @return an ArrayList of good sample
     */
    private ArrayList<Double> sampleRange(double[] a){
    	ArrayList<Double> result = new ArrayList<Double>();
    	for(int i = 0; i < a.length -1 ; i++){
    		if(a[i] >= -5 && a[i] <= 5){
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
    		System.out.println(i + ": " + l.get(i));
    	}
    }
}
