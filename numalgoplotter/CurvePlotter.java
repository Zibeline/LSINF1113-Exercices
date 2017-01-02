import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Ramin Sadre
 */
public abstract class CurvePlotter {
    
    // this method is called when the control points have been
    // changed (position changed, new points, etc.).
    public abstract void controlPointsChanged(List<ControlPoint> controlPoints);
    
    // this method is called when the curve plotter has to paint the curve
    public abstract void paint(Graphics g);
}