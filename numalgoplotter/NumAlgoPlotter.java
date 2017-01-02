import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author Ramin Sadre
 */
public class NumAlgoPlotter {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LinkedList<ControlPoint> controlPoints=new LinkedList<>();
        controlPoints.add(new ControlPoint(-300,-100));
        controlPoints.add(new ControlPoint(-100,0));
        controlPoints.add(new ControlPoint(100,0));
        controlPoints.add(new ControlPoint(300,100));     
        
        MainFrame mainFrame=new MainFrame(controlPoints);
        
        mainFrame.add(new SimpleCurvePlotter() {
            @Override
            public double calculateY(double x) {
                // tan:
                // return Math.tan(x/200.0)*100.0;                
                // runge function:
                return -1.0/(1.0+x*x/3600.0)*100.0;
            }
        }.setColor(Color.green));
        
        //mainFrame.add(new VandermondePlotter().setColor(Color.red));
        mainFrame.add(new NewtonPlotter().setColor(Color.blue));
        mainFrame.add(new CubicSplinePlotter());
        
        mainFrame.setVisible(true);
    }    
}