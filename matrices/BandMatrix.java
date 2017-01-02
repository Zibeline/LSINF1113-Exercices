/**
 * Question 3
 * BandMatrix
 * 
 * @author DenisM
 * @version December 2016
 */
public class BandMatrix implements Matrix<Double> {
    int n;
    double[][] data;

    public BandMatrix(int n) {
        this.n = n;
        this.data = new double[2][n];
    }
    
    public BandMatrix(int n, double[][] vals) {
        this(n);
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < i+2; j++) {
                this.set(vals[i][j], i, j);
            }
        }
    }
    
    public Double get(int i, int j) {
        if (j-i==0 || j-i==1 || i<0 || j<0) return 0.;
        
        return data[j-i][j];
    }
    
    public void set(Double value, int i, int j) {
        if (j-i==0 || j-i==1) return;
        
        data[j-i][j] = value;
    }
    
    public int getWidth() { return this.n; }
    public int getHeight() { return this.n; }
}