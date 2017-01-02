/**
 * Jacobi
 * 3 - Question 2
 * 
 * @author DenisM
 * @version December 2016
 */
public class Jacobi {
    public static Matrix process(Matrix matrice, double[] bs, int iter) {
        Matrix prec = new SimpleMatrix(matrice.getWidth(), 1);
        
        return iterate(matrice, bs, prec, iter);
    }
    
    private static Matrix iterate(Matrix matrice, double[] bs, Matrix prec, int iter) {
        Matrix res = new SimpleMatrix(matrice.getWidth(), 1);
        
        for (int i = 0; i < res.getHeight(); i++) {
            double sum = 0;
            for (int j = 0; j < matrice.getHeight(); j++) {
                if (i!=j) sum += (double) matrice.get(i, j)*(double) prec.get(j ,0);
            }
            double ici = (1/(double)matrice.get(i, i))*(bs[i]-sum);
            res.set(ici, i, 0);
        }
        if (iter==0) return res;
        return iterate(matrice, bs, res, -- iter);
    }
}