package linear;


public class Operations {
	
	/** Subtracts a vector from another vector. */
	public static double[] subtract(double[] sub, double[] vector){
		double[] v = new double[vector.length];
		for(int i = 0; i<sub.length;i++){
			v[i] = vector[i] - sub[i];
		}
		return v;
	}
	
	/** Multiply a matrix with a Scalar. */
	public static double[][] multiply(double[][] matrix, double scalar){
		double[][] ret = new double[matrix.length][matrix[0].length];
		for(int m = 0; m<matrix.length; m++){
			for(int n = 0; n<matrix.length; n++){
				ret[m][n] = matrix[m][n] * scalar;
			}
		}
		return ret;
	}
	/** Multiply a Scalar with a matrix. */
	public static double[][] multiply(double scalar, double[][] matrix){
		double[][] ret = new double[matrix.length][matrix[0].length];
		for(int m = 0; m<matrix.length; m++){
			for(int n = 0; n<matrix.length; n++){
				ret[m][n] = scalar * matrix[m][n];
			}
		}
		return ret;
	}
	
	/** Multiply a vector with a Scalar. */
	public static double[] multiply(double[] vector, double scalar){
		double[] v = new double[vector.length];
		for(int i = 0; i < vector.length; i++){
			v[i] = vector[i] *scalar;
		}
		return v;
	}
	
	/** Multiply a matrix with a vector. */
	public static double[] multiply(double[][] m, double[] v){
		double[] vector = new double[v.length];
		for (int i = 0; i<m[0].length; i++){
			for (int j = 0; j<m.length; j++){
				vector[i] += m[j][i] * v[j];	
			}
		}
		return vector;
	}
	
	/** Multiply a vector with a matrix. */
	public static double[] multiply(double[] v, double[][] m){
		double[] vector = new double[v.length];
		for (int i = 0; i<m[0].length; i++){
			for (int j = 0; j<m.length; j++){
				vector[i] += v[j] * m[j][i];	
			}
		}
		return vector;
	}
	
	/** Create the Identity matrix (I). */
	public static double[][] identityMatrix(int size){
		double[][] I = new double[size][size];
		for(int i = 0; i < size; i++){
			I[i][i] = 1.0f;
		}
		return I;
	}

	/** Multiply two Matrices. */
	public static double[][] multiply(double[][] m1, double[][] m2){
		double[][] matrix = new double[m1.length][m2.length];
		
		for (int i = 0; i<m1[0].length; i++){
			matrix[i] = multiply(m1, m2[i]);
		}
		return matrix;
	}
	
	/** Calculate the Inverse of a matrix. */
	public static double[][] invers(double[][] m){
		double[][] I = identityMatrix(m.length);
		return multiply(m, I);
	}
	
	/** Calculate the Adjugate of a matrix. */
	public static double cofactor(double[][] matrix, int row, int col){
		return (Math.pow(-1.0f,row + col) * det(removeRowCol(matrix, row, col)));
	}
	
	/** Calulate the Determinant of a n-n matrix. */
	public static double det(double[][] m){
		if(m.length > 2){
			double val = 0.0f;
			for(int n = 0; n < m[0].length; n++)
				val += m[0][n] * cofactor(m, 0, n);
			return val;
		}else
			return m[0][0] * m[1][1] - m[0][1] * m[1][0];
	}
	
	/** Calculate the Adjugate of a matrix. */
	public static double[][] adj(double[][] matrix){
		double[][] result = new double[matrix.length][matrix[0].length];
		
		for(int m=0; m<matrix.length; m++){
			for(int n=0; n<matrix[0].length; n++){
				result[m][n] = cofactor(matrix, m, n);
			}
		}
		 return result;
	}
	
	/** Removes a row and column from a matrix and returns the reduced matrix. */
	public static double[][] removeRowCol(double[][] matrix, int row, int col){
		double[][] result = new double[matrix.length-1][matrix[0].length-1];
		int i = 0;
		for(int m=0; m<matrix.length; m++){
			for(int n=0; n<matrix[0].length; n++){
				if(m!=row && n!=col){
					result[(int) Math.floor(i/result.length)][i%result.length] = matrix[m][n];
					i++;
				}
			}
		}
		return result;
	}
	
	/** Solves a system and returns the result. */
	public static double[] solveSystem(double[][] A, double[] B){
		double[][] invA = Operations.multiply(1/Operations.det(A), Operations.adj(A));
		double[] X = Operations.multiply(invA,B);
		return X;
	}
	
	/** Solves a system using Gaussian Elimination and returns the result. */
	public static double[] gaussianElimination(double[][] A, double[] B){
		if(A.length == A[0].length && A[0].length == B.length){
			int n;
			double scalar;
			double val;
			double[] sub;
			for(int m = 0; m < A.length; m++){
				n = 0;
				while(n < A[m].length && A[m][n] == 0)
					n++;
				scalar = 1.0f/A[m][n];
				A[m] = multiply(A[m], scalar);
				B[m] *= scalar;
				for(int m2 = 0; m2 < A.length; m2++){
					if(m!=m2){
						val = A[m2][n]/A[m][n];
						if(val != 0.0f){
							sub = multiply(A[m], val);
							A[m2] = subtract(sub, A[m2]);
							B[m2] -= B[m]*val;
						}
					}
				}
			}
			return B;
		}
		return new double[0];
	}
	
	public static void print(double[][] matrix){
		String str = "";
		for(double[] m : matrix){
			str += "[";
			for(double v : m)
				str += " "+v;
			str += " ]\n";
		}
		System.out.println(str);
	}
	
	public static void print(double[] vector){
		String str = "[";
		for(double v : vector)
			str += " "+v;
		str += "]";
		System.out.println(str);
	}
}