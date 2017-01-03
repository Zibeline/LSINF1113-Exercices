import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Ramin Sadre
 */
public class BSplinePlotter extends CurvePlotter {
    private ControlPoint[] controlPoints;
    private int n;
    private final static int m = 3;  // the degree of the B-spline
    
    @Override
    public void controlPointsChanged(List<ControlPoint> cp) {
        controlPoints=cp.toArray(new ControlPoint[0]);
        n=controlPoints.length;
    }
    
    @Override
    public void paint(Graphics g) {
        // draw lines between the control points (so the user can see the order of the points)
        g.setColor(Color.lightGray) ;
        for(int i=0;i<n;i++) {
            if(i!=0) {
                ControlPoint c1=controlPoints[i-1];
                ControlPoint c2=controlPoints[i];
                g.drawLine(c1.getX(),c1.getY(),c2.getX(),c2.getY());
            }
        }
        
        // now, draw the curve
        g.setColor(Color.black) ;
        
        int previousX=0,previousY=0;
        for(double t=0.0;t<=n-m;t+=0.01) {
            
            // Missing code:
            // Calculate x and y
            double x = 0, y = 0;
            
            for (int i = 0; i < n; i++) {
                ControlPoint here = controlPoints[i];
                double b = b(i, m, t);
                x += here.getX()*b;
                y += here.getY()*b;
            }
                        
            // draw a line between this (x,y) and the previous (x,y)
            if(t>0.0) {
                g.drawLine(previousX,previousY,(int)x,(int)y);
            }
            previousX=(int)x;
            previousY=(int)y;
        }
    }
    
    private int t(int i) {
        // Missing code:
        if (i <= m) return 0;
        if (i >= n) return n-m;
        return i-m;
    }
    
    private double b(int i,int k,double t) {       
        // Missing code:
        if (k==0) {
            if (t>=t(i) && t<=t(i+1)) return 1;
            return 0;
        }
        
        double b = 0;
        
        double denom1 = t(i+k)-t(i);
        if (denom1!=0) {
            b += ((t-t(i))/denom1)*b(i, k-1, t);
        }
        
        double denom2 = t(i+k+1)-t(i+1);
        if (denom2!=0) {
            b += ((t(i+k+1)-t)/denom2)*b(i+1, k-1, t);
        }
        return b;
    }
}