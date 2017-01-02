/**
 * Question 1
 * FlatMatrix
 * 
 * @author DenisM
 * @version December 2016
 */
public class FlatMatrix implements Matrix<Double> {
    int m, n;    
    double[] data;

    public FlatMatrix(int m, int n) {
        this.m = m;
        this.n = n;
        this.data = new double[m*n];
    }
    
    public FlatMatrix(int m, int n, double[][] vals) {
        this(m, n);
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                this.set(vals[i][j], i, j);
            }
        }
    }
    
    public Double get(int i, int j) {
        int pos = j;
        if (i>0) pos += (i-1)*this.n;
        
        return data[pos];
    }
    
    public void set(Double value, int i, int j) {
        int pos = j;
        if (i>0) pos += (i-1)*this.n;
        
        data[pos] = value;
    }
    
    public int getWidth() { return this.n; }
    public int getHeight() { return this.m; }
}