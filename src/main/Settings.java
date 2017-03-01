package main;

public class Settings {
	static double xLower = -0.02;
	static double xUpper = xLower + 0.05;
	
	static void drawAudioBar() {
        //double[] vertices = {-0.4, -0.025, 0, 0.4, 0, 0, 0.4, -0.05, 0};
		double[] vertices = {-0.4f, 0, 0, 0.4f, 0, 0, 0.4f, -0.05f, 0, -0.4f, -0.05f, 0};
        Rectangle.drawrectangle(vertices);
    }
	
	static void drawSlider(){
		double[] vertices = {getXLower(), 0.01, 0, getXUpper(), 0.01, 0, getXUpper(), -0.06f, 0, getXLower(), -0.06f, 0};
        Rectangle.drawrectangle(vertices);
	}
	
	static void setXLower(double x){
		if(x<=-0.4){
			xLower = -0.4;
		} else if(x>=0.35){
			xLower = 0.35;
		} else{
			xLower = x;
		}
		xUpper = xLower + 0.05;
	}
	
	static double getXLower(){
		return xLower;
	}
	
	static double getXUpper(){
		return xUpper;
	}
}
