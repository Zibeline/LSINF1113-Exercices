/**
 * Simple matrix representation
 * 
 * @author DenisM
 * @version Décembre 2016
 */
public class SimpleMatrix implements Matrix<Double> {
    int m, n;    
    double[][] data;

    public SimpleMatrix(int m, int n) {
        this.m = m;
        this.n = n;
        this.data = new double[m][n];
    }
    
    public SimpleMatrix(int m, int n, double[][] vals) {
        this(m, n);
        data = vals;
    }
    
    public SimpleMatrix(double[][] vals) {
        this(vals.length, vals[0].length, vals);
    }
    
    public SimpleMatrix(String latex) {
        double[][] vals = Utilities.fromLatexToArray(latex);
        this.m = vals.length;
        this.n = vals[0].length;
        this.data = vals;
    }
    
    public Double get(int i, int j) {
        if (i<0 || j<0) return 0.;
        return data[i][j];
    }
    
    public void set(Double value, int i, int j) {
        data[i][j] = value;
    }
    
    public int getWidth() { return this.n; }
    public int getHeight() { return this.m; }
}