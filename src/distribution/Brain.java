package distribution;

import java.text.NumberFormat;
import java.text.ParseException;

import exceptions.MinValueToBigException;
import linear.Operations;


public class Brain {
	
	public static double[] calculatedValues;
	private static final NumberFormat NF = NumberFormat.getCurrencyInstance();
	
	/**
	 * The main method of the Brain class. It receives information about what
	 * KEY value should be used, what amount to be distributed and on what data
	 * to calculate.
	 * */
	public synchronized static String calculateKey(String clipBoardContent, final double key, final double amount) throws NumberFormatException {
		
		double[] clipBoardArray = stringToDoubleArray(clipBoardContent);
		
		double sum = sum(clipBoardArray);
		
		int minIndex = 0;
		int maxIndex = 0;
		

		/* Get maximum and minimum members indexes, and convert
		 * the values to percentage. */
		for(int i = 0; i < clipBoardArray.length; i++){
			clipBoardArray[i] = (clipBoardArray[i]/sum);
			if(clipBoardArray[i] < clipBoardArray[minIndex]){ minIndex = i; }
			if(clipBoardArray[i] > clipBoardArray[maxIndex]){ maxIndex = i; }
		}
		
		if(clipBoardArray[minIndex] >= clipBoardArray[maxIndex]/key){
			
			/* Everything OK, convert back from percentage to kr. */
			for(int n = 0; n < clipBoardArray.length; n++)
				clipBoardArray[n] *= amount;
			
			return doubleArrayToString(clipBoardArray);
		}

		/* Create the mathematical matrix. */
		double[][] A = new double[clipBoardArray.length][clipBoardArray.length];
		double[] B = new double[clipBoardArray.length];
		
		/* Prepare the matrix by filling it with values. */
		double T, N, mean = sum(clipBoardArray)/clipBoardArray.length;
		for(int m = 0; m<clipBoardArray.length; m++){
			T = (mean - clipBoardArray[m])/(mean - clipBoardArray[minIndex]);
			N = clipBoardArray[m]+(T*-clipBoardArray[minIndex]);

			A[m][minIndex] = -T;
			A[m][m] = 1.0f;
			B[m] = N;
		}
		
		A[minIndex] = new double[A[minIndex].length];
		A[minIndex][minIndex] = key;
		A[minIndex][maxIndex] = -1.0f;

		for(int n = 0; n<A[0].length; n++)
			A[maxIndex][n] = 1.0f;
		B[maxIndex] = 1.0f;
		
		/* Do the matrix calculations. */
		double[] X = Operations.gaussianElimination(A, B);
		
		/* Convert back from percentage to kr and
		 * evenout the total sum. */
		sum = 0;
		for(int n = 0; n < X.length; n++){
			X[n] = Math.round(X[n] * amount);
			sum += X[n];
		}
		X[maxIndex] += amount-sum;
		
		return doubleArrayToString(X);
	}
		
	public synchronized static String calculateRoof(String clipBoardContent, final double roof, final double amount) throws NumberFormatException, MinValueToBigException {
		double[] clipBoardArray = stringToDoubleArray(clipBoardContent);
		
		double rest = amount - (roof*clipBoardArray.length);
		
		double nr = ((clipBoardArray.length-1)*(clipBoardArray.length))/2.0f;
		double divNr = rest/nr;
		
		double[][] elements = new double[clipBoardArray.length][2];
		
		for(int i = 0; i < clipBoardArray.length; i++){
			elements[i][0] = clipBoardArray[i];
			elements[i][1] = i;
		} 
		
		elements = bubleSortByFirst(elements);
		elements[0][0] = roof;
		for(int j = 1; j < clipBoardArray.length; j++){
			elements[j][0] = roof + j*divNr;
		}
		
		elements = bubleSortBySecond(elements);
		
		for(int k = 0; k < clipBoardArray.length; k++){
			clipBoardArray[k] = elements[k][0];
			if(clipBoardArray[k] < roof) throw new MinValueToBigException();
		}
		
		return doubleArrayToString(clipBoardArray);
	}
	
	private static double[][] bubleSortByFirst(double[][] array){
		boolean sorted = false;
		while(!sorted){
			sorted = true;
			for(int i = 0; i<array.length-1; i++){
				if(array[i][0] > array[i+1][0]){
					double temp[] = array[i];
					array[i] = array[i+1];
					array[i+1] = temp;
					sorted = false;
					break;
				}
			}
		}
		return array;
	} 
	
	private static double[][] bubleSortBySecond(double[][] array){
		boolean sorted = false;
		while(!sorted){
			sorted = true;
			for(int i = 0; i<array.length-1; i++){
				if(array[i][1] > array[i+1][1]){
					double temp[] = array[i];
					array[i] = array[i+1];
					array[i+1] = temp;
					sorted = false;
					break;
				}
			}
		}
		return array;
	} 
	
	/**
	 * Calculates the sum of an array.
	 * */
	private static double sum(double[] array){
		double sum = 0;
		for(double p : array)
			sum += p;
		return sum;
	}
	
	/**
	 * Converts an array of doubles to a String.
	 * */
	private static String doubleArrayToString(double[] array){
    	String result = "";
    	for(double d : array)
    		result += (String.valueOf(d).replace('.', ',') + "\r");
    	return result;
    }
	
	/**
	 * Converts a String to an Array of doubles.
	 * */
	private static double[] stringToDoubleArray(String s) throws NumberFormatException{
		
    	String[] stringArray = s.split("[\\r?\\n]");
    	double[] valuesArray = new double[stringArray.length];
    	
    	for(int i = 0; i < stringArray.length; i++){
			if(!stringArray[i].equals("")){
    			try {
    				valuesArray[i] = NF.parse(stringArray[i]).doubleValue();
				} catch (ParseException e) {
					try {
						valuesArray[i] = Double.valueOf(stringArray[i]);
					} catch (NumberFormatException e2) {
						stringArray[i] = stringArray[i].replaceAll("," , ".");
						valuesArray[i] = Double.valueOf(stringArray[i]);
					}
					
				}
    		}
    	}
    	return valuesArray;
    }
	
}




