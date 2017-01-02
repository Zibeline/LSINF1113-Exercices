import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Ramin Sadre
 */
public class PlotPanel extends JPanel {
    private final List<CurvePlotter> curvePlotters=new LinkedList<>();
    private final List<ControlPoint> controlPoints;
    private final Action showCooordinatesAction;
    
    public PlotPanel(List<ControlPoint> controlPoints) {
        this.controlPoints=controlPoints;
        
        // we want to listen to mouse actions in the panel
        MouseAdapter myMouseAdapter=new MyMouseAdapter();
        this.addMouseListener(myMouseAdapter);
        this.addMouseMotionListener(myMouseAdapter);
        
        showCooordinatesAction=new AbstractAction("Show control point coordinates") {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        };
        showCooordinatesAction.putValue(Action.SELECTED_KEY, Boolean.TRUE);
    }
    
    public void add(CurvePlotter curvePlotter) {
        curvePlotters.add(curvePlotter);
        curvePlotter.controlPointsChanged(controlPoints);
    }
    
    @Override
    public void paint(Graphics g) {        
        // clear the background
        g.setColor(Color.white) ;
		g.fillRect(0,0,getWidth(),getHeight()) ;
        
        // put the center in the middle of the panel
        g.translate(getWidth()/2, getHeight()/2);
        
        // paint the axes
        g.setColor(Color.lightGray);
        g.drawLine(-getWidth()/2,0,getWidth()/2,0); // x-axis
        g.drawLine(0,-getHeight()/2,0,getHeight()/2); // y-axis
        
        // paint the curves
        for(CurvePlotter curvePlotter : curvePlotters) {
            curvePlotter.paint(g);
        }
        
        // paint the control points
        for(ControlPoint cp : controlPoints) {
            cp.paint(g);
        }
        
        // paint the coordinates of the control points (if the option is activated)
        if(showCooordinatesAction.getValue(Action.SELECTED_KEY).equals(Boolean.TRUE)) {
            g.setColor(Color.BLACK) ;
            for(ControlPoint cp : controlPoints) {
                String s="("+cp.getX()+","+cp.getY()+")";
                Rectangle2D r=g.getFontMetrics().getStringBounds(s,g);
                g.drawString(s,cp.getX()-(int)r.getWidth()/2,cp.getY()+cp.size/2+(int)r.getHeight());
            }
        }
    }
    
    private void controlPointsChanged() {
        for(CurvePlotter curvePlotter : curvePlotters) {
            curvePlotter.controlPointsChanged(controlPoints);
        }
        repaint();
    }
    
    private class MyMouseAdapter extends MouseAdapter {
        private ControlPoint draggedControlPoint;       // the currently dragged control point.
                                                        // null means "no control point dragged".
        private int deltaX, deltaY;                     // the difference between the mouse position
                                                        // and the control point position when the drag started.
        
        @Override
        public void mouseDragged(MouseEvent e) {
            // are we dragging a control point?
            if(draggedControlPoint!=null) {
                // change the position of the control point
                draggedControlPoint.setPosition(e.getX()+deltaX, e.getY()+deltaY);
                // we have to repaint everything
                controlPointsChanged();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // stop dragging
            draggedControlPoint=null;
            
            // right mouse button pressed?
            if(e.isPopupTrigger()) {  
                // show the context menu
                showPopupMenu(e.getX(),e.getY());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // left mouse button pressed?
            if(e.getButton()==MouseEvent.BUTTON1) {
                // start dragging if mouse was pressed on a control point
                ControlPoint hitControlPoint=findHitControlPoint(e.getX(),e.getY());
                if(hitControlPoint!=null) {
                    draggedControlPoint=hitControlPoint;
                    deltaX=hitControlPoint.getX()-e.getX();
                    deltaY=hitControlPoint.getY()-e.getY();
                }
            }
        }

        // returns the control point that was hit at position (x,y).
        // returns null if there is no control point at that position.
        private ControlPoint findHitControlPoint(int x,int y) {
            int centeredX=x-getWidth()/2;
            int centeredY=y-getHeight()/2;
            
            // check all control points to see whether we have hit one of them
            ControlPoint hitControlPoint=null;
            for(ControlPoint cp : controlPoints) {
                if(cp.isInside(centeredX,centeredY)) {
                    // we have found one
                    hitControlPoint=cp;
                    break;
                }
            }
            return hitControlPoint;
        }
        
        // show the popup menu
        private void showPopupMenu(int x,int y) {
            JPopupMenu popupMenu=new JPopupMenu();
            
            // show actions in the context menu depending on whether a control point was selected
            ControlPoint hitControlPoint=findHitControlPoint(x,y);
            if(hitControlPoint!=null) {
                popupMenu.add(new AbstractAction("Delete this control point") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // remove the control point
                        controlPoints.remove(hitControlPoint);
                        // we have to repaint everything
                        controlPointsChanged();
                    }
                });
            }
            else {
                popupMenu.add(new AbstractAction("Add a new control point here") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // add a the control point
                        controlPoints.add(new ControlPoint(x-getWidth()/2,y-getHeight()/2));
                        // we have to repaint everything
                        controlPointsChanged();
                    }
                });
            }
            
            popupMenu.add(new JCheckBoxMenuItem(showCooordinatesAction));
            
            popupMenu.show(PlotPanel.this,x,y);
        }
    }
}