 

import java.util.Arrays;

/**
 * Code performing Gaussian Elimination for a given Linear System
 * @author Quentin De Coninck
 * @date 5/10/2016
 */
public class GaussianElimination {
	
	private static double epsilon = 1e-8;
	
    /**
     * Solves the linear system A*X=B.
     * @return X
     */
    public static double[] solve(double[][] A,double[] B) {
        double[] X=Arrays.copyOf(B,B.length);
		forwardSubstitution(A,X);
		backwardSubstitution(A,X);
        return X;
    }
    
	/**
	 * @pre matrix is not null and square, 0 <= offset < matrix.length
	 * @post return the line with the largest pivot (in absolute value) for partial pivoting 
	 */
	private static int findPivot(double[][] matrix, int offset) {
		int maxLine = offset;
		for (int i = offset + 1; i < matrix.length; i++) {
			if (Math.abs(matrix[i][offset]) > Math.abs(matrix[maxLine][offset]))
				maxLine = i;
		}
		return maxLine;
	}
	
	/**
	 * @pre matrix is not null and square, vector is not null and vector.length == matrix.length, 0 <= line1 < matrix.length, 0 <= line2 < matrix.length
	 * @post Swap matrix lines "line1" and line2" and swap vector elements "line1" and "line2"
	 */
	private static void swapLines(double[][] matrix, double[] vector, int line1, int line2) {
		double[] tmpLine = matrix[line1];
		double tmpVectElem = vector[line1];
		matrix[line1] = matrix[line2];
		vector[line1] = vector[line2];
		matrix[line2] = tmpLine;
		vector[line2] = tmpVectElem;
	}
	
	/**
	 * @pre matrix is not null and square, vector is not null and vector.length == matrix.length, 0 <= offset < matrix.length, all elems in matrix[offset][0, offset-1] are 0
	 * @post If matrix[offset][offset] >= epsilon, the line matrix[offset] and the element vector[offset] are divided by the pivot element, matrix[offset][offset]
	 * 		 Else, does nothing
	 */
	private static void setPivotToOne(double[][] matrix, double[] vector, int offset) {
		double value = matrix[offset][offset];
		if (Math.abs(value) < epsilon)
			// Already 0, avoid dividing by 0 (should not happen in a linear system with unique solution)
			return;
		for (int j = offset; j < matrix[offset].length; j++) {
			matrix[offset][j] /= value;
		}
		vector[offset] /= value;
	}
	
	/**
	 * @pre matrix is not null and square, vector is not null and vector.length == matrix.length, 0 <= offset < lineToNull < matrix.length
	 * @post matrix[lineToNull] = matrix[lineToNull] - (matrix[lineToNull][offset] * matrix[offset]) and vector[lineToNull] = vector[lineToNull] - (matrix[lineToNull][offset] * vector[offset])
	 */
	private static void nullifyUnderPivot(double[][] matrix, double[] vector, int offset, int lineToNull) {
		double value = matrix[lineToNull][offset];
		for (int j = offset; j < matrix[offset].length; j++) {
			matrix[lineToNull][j] -= (value * matrix[offset][j]);
		}
		vector[lineToNull] -= (value * vector[offset]);
	}
	
	/**
	 * @pre matrix is not null and square, vector is not null and vector.length == matrix.length, 0 <= lineToNull < offset < matrix.length
	 * @post matrix[lineToNull] = matrix[lineToNull] - (matrix[lineToNull][offset] * matrix[offset]) and vector[lineToNull] = vector[lineToNull] - (matrix[lineToNull][offset] * vector[offset])
	 */
	private static void nullifyAbovePivot(double[][] matrix, double[] vector, int offset, int lineToNull) {
		double value = matrix[lineToNull][offset];
		for (int j = matrix.length - 1; j > lineToNull; j--) {
			matrix[lineToNull][j] -= (value * matrix[offset][j]);
		}
		vector[lineToNull] -= (value * vector[offset]);
	}
	
	/**
	 * @pre matrix is not null and square, vector is not null and vector.length == matrix.length
	 * @post Forward substitution is applied on linear system formed by matrix and vector, matrix is upper triangular
	 */
	private static void forwardSubstitution(double[][] matrix, double[] vector) {
		int pivotLine;
		for (int offset = 0; offset < matrix.length; offset++) {
			pivotLine = findPivot(matrix, offset);
			if (pivotLine != offset)
				swapLines(matrix, vector, pivotLine, offset);
			setPivotToOne(matrix, vector, offset);
			for (int sub = offset + 1; sub < matrix.length; sub++) {
				nullifyUnderPivot(matrix, vector, offset, sub);
			}
		}
	}
	
	/**
	 * @pre matrix is not null and square, vector is not null and vector.length == matrix.length, forward substitution was applied on linear system formed by matrix and vector, matrix is upper triangular
	 * @post Backward substitution is applied on linear system formed by matrix and vector, matrix is the identity matrix, vector contains the value of the variables to solve linear system
	 */
	private static void backwardSubstitution(double[][] matrix, double[] vector) {
		// We start from the bottom right of the matrix
		for (int offset = matrix.length - 1; offset > 0; offset--) {
			// For all lines above the "pivot" one, nullify the column elements
			for (int i = 0; i < offset; i++) {
				nullifyAbovePivot(matrix, vector, offset, i);
			}
		}
	}
	
	/**
	 * @pre matrix is not null and square, vector is not null and vector.length == matrix.length
	 * @post Print the linear system on standard output
	 */
	private static void printSystem(double[][] matrix, double[] vector) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("| " + vector[i]);
		}
	}
            
    /*
	public static void main(String[] args) {
		// Linear system on slide 2
		double[][] matrix = {
				{ 13.0, 14.0},
				{ 14.0, 15.08}
		};
		double[] vector = { 18.02, 19.422};
		
		forwardSubstitution(matrix, vector);
		backwardSubstitution(matrix, vector);
		printSystem(matrix, vector);
	}
    */
}
