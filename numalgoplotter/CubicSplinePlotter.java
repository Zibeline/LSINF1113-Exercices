import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Ramin Sadre
 */
public class CubicSplinePlotter extends CurvePlotter {
    private ControlPoint[] controlPoints;
    private int n;
    private double[] B; // the b_i coefficients of the spline
    private double[] C; // the c_i coefficients of the spline
    private double[] D; // the d_i coefficients of the spline

    @Override
    public void controlPointsChanged(List<ControlPoint> lcp) {
        // sort the control points by x position.
        // this makes things easier later when we paint the function.
        lcp.sort(
                (point1,point2) -> Integer.compare(point1.getX(),point2.getX())
        );

        // collect the points in an array before doing the calculations.
        // again, this makes things easier for us.
        controlPoints=lcp.toArray(new ControlPoint[0]);
        n=controlPoints.length;
        if(n<2) {
            // nothing to do here
            return;
        }
        
        // Construct the linear system S*C=T with n-2 equations.
        // The matrix S contains the left hand side of the equations on slide 13.
        // The matrix Z contains the right hand side of the equations on slide 13.
        double[][] S=new double[n-2][n-2];
        double[] Z=new double[n-2];
        
        for (int i = 0; i < n-2; i++) {
            int realX = i+1;
            double ha = (lcp.get(realX+0).getX()-lcp.get(realX-1).getX());
            double hb = (lcp.get(realX+1).getX()-lcp.get(realX+0).getX());
            
            double ya = (lcp.get(realX+0).getY()-lcp.get(realX-1).getY());
            double yb = (lcp.get(realX+1).getY()-lcp.get(realX+0).getY());
            
            if (i>0) S[i][i-1] = ha;
            S[i][i] = 2*(ha+hb);
            if (i<n-3) S[i][i+1] = hb;
            
            Z[i] = 3*((yb/hb)-(ya/ha));
        }
        
        double[] ci= GaussianElimination.solve(S, Z);
        B = new double[n];
        C = new double[n];
        D = new double[n];
        for (int i = 0; i < ci.length; i++) {
            C[i+1] = ci[i];
        }
        
        for (int i = 0; i < n-1; i++) {
            double ha = (lcp.get(i+1).getX()-lcp.get(i).getX());
            D[i] = (C[i+1]-C[i])/(3*ha);
            B[i] = (lcp.get(i+1).getY()-lcp.get(i).getY())/ha-C[i]*ha-D[i]*Math.pow(ha, 2);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black) ;
        
        // Paint the n-1 polynomials
        for(int i=0;i<n-1;i++) {
            int x_i=controlPoints[i].getX();
            int x_iplus1=controlPoints[i+1].getX();
            double a_i=controlPoints[i].getY();
            
            int previousX=0,previousY=0;
            for(int x=x_i;x<x_iplus1;x++) {
            	double y = a_i + B[i]*(x-x_i) + C[i]*Math.pow((x-x_i), 2) + D[i]*Math.pow((x-x_i), 3);
                
                // draw a line between this (x,y) and the previous (x,y)
                if(x!=x_i) {
                    g.drawLine(previousX,previousY,x,(int)y);
                }
                previousX=x;
                previousY=(int)y;
            }
        }
    }
}