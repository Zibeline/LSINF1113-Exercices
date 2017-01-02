 /**
 * A plotter for polynomials.
 * The polynomial is fitted to the control points by solving the Newton system
 * @author DenisM
 */
public class NewtonPlotter extends SimpleCurvePlotter {
    private double[] ci;
    private double[] xi;
    
    @Override
    public void doPreparations(ControlPoint[] controlPoints) {       
        int n=controlPoints.length;
        
        double[][] A = new double[n][n];
        double[] B = new double[n];
        xi = new double[n];
        for (int i = 0; i < n; i++) {
            double diable = 1;
            xi[i] = controlPoints[i].getX();
            for (int j = 0; j<i+1; j++) {
                if (j>0) diable *= (controlPoints[i].getX()-controlPoints[j-1].getX());
                A[i][j] = diable;
            }
            B[i] = controlPoints[i].getY();
        }
        ci= GaussianElimination.solve(A, B);
    }

    public double calculateY(double x) {
        double y = 0;
        double diable = 1;
        
        for (int i = 0; i < ci.length; i++) {
            if (i>0) diable *= (x-xi[i-1]);
            y += ci[i] * diable;
        }

        return y;
    }  
}