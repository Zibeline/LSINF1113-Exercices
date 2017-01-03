/**
 * Cholesky Factorization
 * 4 - Question 3
 * 
 * @author DenisM
 * @version December 2016
 */
public class CholeskyFactorization {
    private static double somme(int i, int j, Matrix L) {
        double ret = 0;
        for (int k = 0; k < j; k++) {
            ret += (double) L.get(i, k)*(double) L.get(j, k);
        }
        return ret;
    }
    
    public static Matrix getL(Matrix C) {
        SimpleMatrix L = new SimpleMatrix(C.getHeight(), C.getWidth());
        
        for (int j = 0; j < C.getWidth(); j++) {
            double diag = Math.sqrt((double)C.get(j, j)-somme(j, j, L));
            L.set(diag, j, j);
            
            for (int i = j+1; i < C.getHeight(); i++) {
                double val = ((double) C.get(i, j)-somme(i, j, L))/diag;
                L.set(val, i, j);
            }
        }
        return L;
    }

    public static void main(String[] args) {
        double[][] tableau1 = {
            {25, 15, -5},
            {15, 18, 0},
            {-5, 0, 11}
        };
        SimpleMatrix matrice1 = new SimpleMatrix(tableau1);
        
        double[][] tableau2 = {
            {4, 12, -16},
            {12, 37, -43},
            {-16, -43, 98}
        };
        SimpleMatrix matrice2 = new SimpleMatrix(tableau2);
        
        // Exemple de Wikipédia
        double[][] tableau3 = { 
            {1, 1, 1, 1},
            {1, 5, 5, 5},
            {1, 5, 14, 14},
            {1, 5, 14, 15}
        };
        SimpleMatrix matrice3 = new SimpleMatrix(tableau3);
        
        Matrix cholesky1 = getL(matrice1);
        Matrix cholesky2 = getL(matrice2);
        
        System.out.println("===== Matrice 1 =====");
        Utilities.printMatrix(matrice1);
        System.out.println("Sa version L :");
        Utilities.printMatrix(cholesky1);
        System.out.println("===== Matrice 2 =====");
        Utilities.printMatrix(matrice2);
        System.out.println("Sa version L :");
        Utilities.printMatrix(cholesky2);
    }
}