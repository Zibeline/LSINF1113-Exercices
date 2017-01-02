/**
 * Gauss elimination
 * 3 - Question 1
 * 
 * @author DenisM
 * @version December 2016
 */
public class GaussElimination {
    public static Matrix process(ExtendedMatrix matrice, double[] bs) {
        Matrix res = new SimpleMatrix(matrice.getWidth(), 1);
        
        return forwardSubstitution(matrice, bs, res);
    }
    
    private static Matrix forwardSubstitution(ExtendedMatrix matrice, double[] bs, Matrix res) {
        for (int i = 0; i < matrice.getHeight(); i++) {
            double pivot = (double) matrice.get(i, i);
            // Switch rows
            int maxRow = getRowWhereColumnIsMax(matrice, i, i); 
            matrice.switchRows(i, maxRow);
            double b = bs[i];
            bs[i] = bs[maxRow];
            bs[maxRow] = b;
            
            pivot = matrice.get(i, i); // update pivot value
            
            // Divide actual row
            matrice.divideRow(i, pivot);
            bs[i] /= pivot;
            
            pivot = matrice.get(i, i); // update pivot value
            
            for (int j = i+1; j < matrice.getHeight(); j++) {
                double fact = matrice.get(j, i)/pivot;
                matrice.substractRows(j, i, fact);
                bs[j] -= bs[i]*fact;
            }
        }
        return backwardSubstitution(matrice, bs, res);
    }
    
    private static Matrix backwardSubstitution(ExtendedMatrix matrice, double[] bs, Matrix res) {
        for (int i = matrice.getHeight()-1; i>-1; i--) {
            double min = bs[i];
            
            for (int j = i+1; j<matrice.getWidth()-1; j++) {
                min -= matrice.get(i, j)*(double)res.get(j, 0);
            }
            
            res.set(min/matrice.get(i, i), i, 0);
        }
        return res;
    }
    
    private static int getRowWhereColumnIsMax(ExtendedMatrix matrice, int j, int offset) {
        double max = matrice.get(offset, j);
        int pos = offset;
        for (int i = offset; i < matrice.getHeight(); i++) {
            if (max<matrice.get(i, j)) {
                max = matrice.get(i, j);
                pos = i;
            }
        }
        return pos;
    }
}