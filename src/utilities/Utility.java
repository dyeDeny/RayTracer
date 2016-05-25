package utilities;

public class Utility {
	public static final double SMALL_NUMBER=1e-06;
	
	public static boolean equal(double a, double b){
		return a-b < SMALL_NUMBER && a-b > -SMALL_NUMBER;
	}
	public static boolean smaller(double a, double b){
		return a-b < -SMALL_NUMBER;
	}
	public static boolean bigger(double a, double b){
		return a-b > SMALL_NUMBER;
	}
	
	
}
