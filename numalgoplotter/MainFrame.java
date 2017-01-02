import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 *
 * @author Ramin Sadre
 */
public class MainFrame extends JFrame {
    private final PlotPanel plotPanel;
    
    public MainFrame(List<ControlPoint> controlPoints) {
        super("NumAlgoPlotter");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        plotPanel=new PlotPanel(controlPoints);
        add(plotPanel,BorderLayout.CENTER);
        
        JTextArea helpArea=new JTextArea("Drag the control points (the red squares) with the left mouse button.\n"
                                        + "Press the right mouse button to open the context menu. "
                                        + "In the context menu you can add and remove control points.");
        helpArea.setEditable(false);
        helpArea.setBackground(getBackground());
        add(helpArea,BorderLayout.NORTH);
        
        JLabel statusLabel=new JLabel(" ");
        add(statusLabel,BorderLayout.SOUTH);
        
        plotPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                statusLabel.setText("Position: x = "+e.getX()+" , y = "+e.getY());
            }
        });
    }
    
    public void add(CurvePlotter curvePlotter) {
        plotPanel.add(curvePlotter);
    }
}
