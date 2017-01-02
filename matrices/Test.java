/**
 * Tests for GaussElimination and Jacobi
 * 
 * @author DenisM
 * @version December 2016
 */
public class Test {
    public static void main(String[] args) {
        test_jacobi();
        test_gauss();
    }
    
    public static void test_jacobi() {
        double[][] tableau = {
            {10, -1, 2, 0},
            {-1, 11, -1, 3},
            {2, -1, 10, -1},
            {0, 3, -1, 8},
        };
        SimpleMatrix matrice = new SimpleMatrix(4, 4, tableau);
        
        double[] res = {6., 25., -11., 15.};
        
        Matrix sol = Jacobi.process(matrice, res, 4);
        Utilities.printMatrix(sol);
    }
    
    public static void test_gauss() {
        // ***** Matrice 1 *****
        double[][] tableau1 = {
            {1, 1, -2},
            {0, 1, -1},
            {3, -1, 1}
        };
        ExtendedMatrix matrice1 = new ExtendedMatrix(tableau1);
        double[] res1 = {-3., -1., 4.};
       
        // ***** Matrice 2 *****
        double[][] tableau2 = {
            {1, 12, 144},
            {1, 20, 400},
            {1, 24, 576}
        };
        ExtendedMatrix matrice2 = new ExtendedMatrix(tableau2);
        double[] res2 = {4, 16, 17};
        
        // ***** Matrice 3 *****
        double[][] tableau3 = {
            {2, 2.2},
            {3, 3.2}
        };
        ExtendedMatrix matrice3 = new ExtendedMatrix(tableau3);
        double[] res3 = {3, 4};
        
        // ***** Matrice 4 *****
        double[][] tableau4 = {
            {13, 14},
            {14, 15.08}
        };
        ExtendedMatrix matrice4 = new ExtendedMatrix(tableau4);
        double[] res4 = {18, 19.4};
        
        Matrix sol = GaussElimination.process(matrice3, res3);
        Utilities.printMatrix(sol);
    }
}