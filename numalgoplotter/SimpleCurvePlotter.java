import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Ramin Sadre
 */
public abstract class SimpleCurvePlotter extends CurvePlotter {
    private Color color=Color.black;
    private ControlPoint[] sortedControlPoints;
    
    // sets the color of the plot (default is black)
    public SimpleCurvePlotter setColor(Color color) {
        this.color=color;
        return this;
    }
    
    @Override
    public void controlPointsChanged(List<ControlPoint> controlPoints) {      
        // sort the control points by x position.
        controlPoints.sort(
                (point1,point2) -> Integer.compare(point1.getX(),point2.getX())
        );

        // collect the points in an array before doing calculations
        sortedControlPoints=controlPoints.toArray(new ControlPoint[0]);
        if(sortedControlPoints.length>0) {
            doPreparations(sortedControlPoints);
        }
    }

    @Override
    public void paint(Graphics g) {
        if(sortedControlPoints.length>0) {
            g.setColor(color) ;
            
            // We draw the polynomial from the smallest x of all control points
            // to the greatest x of all control points.
            // Finding the smallest and greatest x is easy because the points are sorted.
            int n=sortedControlPoints.length;
            int startX=sortedControlPoints[0].getX();
            int endX=sortedControlPoints[n-1].getX();           
            
            int previousX=0,previousY=0;
            for(int x=startX;x<=endX;x++) {
                // calculate y
                double y=calculateY(x);
                
                // draw a line between this (x,y) and the previous (x,y)
                if(x!=startX) {
                    g.drawLine(previousX,previousY,x,(int)y);
                }

                previousX=x;
                previousY=(int)y;
            }
        }
    }
    
    public void doPreparations(ControlPoint[] controlPoints) {
        // default implementation: do nothing
    }
    
    public abstract double calculateY(double x);
}