package main;

//import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.stat.*; 
import org.apache.commons.math3.distribution.*;
//import java.util.function.Function;
import java.util.ArrayList;
//import java.util.Arrays;

/**
 * For each iteration the EDA samples new sample
 * Filters sample using toScore function
 * Re-estimate new parameters(mean, variance)
 * Create a new normal distribution for the next iteration. 
 * @author ava
 *
 */

public class EDA {
	
	private final int xv = 20; // number of NEW sample per iteration: x-velocity
    private ArrayList<Double> xVelocity = new ArrayList<>(); //store sample x-velocity, which is the best result of every iteratons only
    private ArrayList<Double> score = new ArrayList<>(); //store score of sample which corresponding to the xVelocity list
    
    //Getter//
    public EDA(){
    	
    }
    
    /**
     * Return score list, for checking the highest score only.
     * @return score list 
     */
    private ArrayList<Double> getScore(){
        return score;
    }
    
    /**
     * Return result list, for finding the best result only.
     * @return result list
     */
    public ArrayList<Double> getXVelcoity(){
    	return xVelocity;
    }
    
    /**
     * generate an EDA
     * @param f a function that returns score which shows where the ball hit.
     * @param xV range of x-velocity
     * @param time time of iteration, more times will returns a better result
     * @return a best x-velocity of specify x and y distance
     */
    public double simpleEDA(toScore f,double[] xV, int time){
    	int iterationCounter = 0;//count how many time of iteration it did
    	ArrayList<Double> keepSample = new ArrayList<Double>();//keeping the good sample which is using to do the next iteration
    	for(int i = 0; i < xV.length-1; i++){
    		keepSample.add(xV[i]);
    	}
 
    	while (iterationCounter < time){
    		double meanD = mean(keepSample);
            double varianceD = variance(keepSample)+2; //2 is for making the variance bigger, because in this case, result do not need very accurate
            NormalDistribution M = new NormalDistribution(meanD,varianceD); //Create normal distribution of parameter
            ArrayList<Double> sampleArray = sampleRange(M.sample(xv)); // choose xv sample from M distribution
            ArrayList<Double> result = f.apply(sampleArray); // apply sample into toScore function, and evaluate the sample
            
            //If the distance between the ball and platform is too far
            if(result == null || result.size() == 0){
            	return -1.0;
            }

            keepSample = new ArrayList<Double>();//empty the keeping sample list
            //Filter out the bad sample, I using mean as standard to filter here, median might be better.
            double meanResult = mean(result);
            int maxIndex = 0;
            
            for(int i = 0; i < result.size(); i++){
                //Put the good sample in the keepSample list for doing the next iteration.
            	if(result.get(i) > meanResult){
            		keepSample.add(sampleArray.get(i));
            	}
            	//Find the index of the best sample
            	if(result.get(i) > result.get(maxIndex)){
            		maxIndex = i;
            	}
            }

            score.add(result.get(maxIndex));//keep the score of best sample
            xVelocity.add(sampleArray.get(maxIndex));//keep the best sample. Indeed, I was think is this necessary to create a list to keep the best sample. But one thing I know is these two AraayList can change to array[iteration times]. 
            iterationCounter++;

    	}

    	//Find the best sample from all the iterations
    	int bestXV = 0;
    	for(int i = 0; i < score.size(); i++){
    		if(score.get(i) > score.get(bestXV)){
    			bestXV = i;
    		}
    	}

    	return xVelocity.get(bestXV);
    }
    
    /**
     * Remove sample which is not in the range of x-velocity
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
    
    /**
     * Calculate mean of sample using library function
     * @param l list of sample
     * @return mean
     */
    private double mean(ArrayList<Double> l){
    	double[] a = new double[l.size()];
    	for(int i = 0; i < l.size()-1;i++){
    		a[i] = l.get(i);
    	}
    	return StatUtils.mean(a);
    }
    
    /**
     * Calculate variance of sample using library function
     * @param l list of sample
     * @return variance
     */
    private double variance(ArrayList<Double> l){
    	double[] a = new double[l.size()];
    	for(int i = 0; i < l.size()-1;i++){
    		a[i] = l.get(i);
    	}
    	return StatUtils.variance(a);
    }
    
    //For checking only
    private void printArray(ArrayList<Double> l){
    	for(int i = 0; i < l.size(); i++){
    		System.out.println(i + ": " + l.get(i));
    	}
    }
}
