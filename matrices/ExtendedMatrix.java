import java.util.ArrayList;
/**
 * Matrice plus complexe
 * 
 * @author DenisM
 * @version Décembre 2016
 */
public class ExtendedMatrix extends SimpleMatrix {
    public ExtendedMatrix(int m, int n) {
        super(m, n);
    }
    
    public ExtendedMatrix(int m, int n, double[][] vals) {
        super(m, n, vals);
    }
    
    public ExtendedMatrix(double[][] vals) {
        super(vals.length, vals.length, vals);
    }

    public double[] getRow(int i) {
        return data[i];
    }
    
    public double[] getColumn(int i) {
        double[] ret = new double[this.getHeight()];
        for (int k = 0; k < this.getHeight(); k++) {
            ret[k] = this.get(k, i);
        }
        return ret;
    }
    
    public void switchRows(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
    
    public void divideRow(int row, double fact) {
        for (int i = 0; i < this.getWidth(); i++) {
            data[row][i] = data[row][i] / fact;
        }
    }
    
    public void substractRows(int row, int tosub, double factor) {
        for (int i = 0; i < this.getWidth(); i++) {
            data[row][i] -= data[tosub][i]*factor;
        }
    }
}