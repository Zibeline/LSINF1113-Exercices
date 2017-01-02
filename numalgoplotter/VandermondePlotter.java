/**
 * A plotter for polynomials.
 * The polynomial is fitted to the control points by solving the Vandermonde system X*A=Y
 * @author Ramin Sadre
 */
public class VandermondePlotter extends SimpleCurvePlotter {
    // The coefficients of the polynomial y = a_0 + a_1*x + a_3*x^2 + ...
    // Note that we start with a_0 (not a_1 like in the course) because
    // Java arrays start with index 0.
    private double[] vectorA;

    // This method is called whenever a control point is changed or deleted or added.
    // Here is a good place to do calculations that you need to do only once.
    @Override
    public void doPreparations(ControlPoint[] controlPoints) {       
        // Construct and solve the linear system X*A=Y (where X is the Vandermonde matrix).
        // The degree of the polynomial is the number of control points minus 1.
        
        // n = the number of points
        int n=controlPoints.length;
        
        double[][] A = new double[n][n];
        double[] B = new double[n];
        
        for (int i = 0; i < n; i++) {
            B[i] = controlPoints[i].getY();
            double base = controlPoints[i].getX();
            
            for (int j = 0; j<n; j++) {
                A[i][j] = Math.pow(base, j);
            }
        }
        vectorA= GaussianElimination.solve(A, B);
    }

    // This method calculates y=a0+a1*x+a2*x^2+...
    public double calculateY(double x) {
    	double y = 0;
        
        for (int i = 0; i < vectorA.length; i++) {
            y += vectorA[i] * Math.pow(x, i);
        }
        return y;
    }  
}