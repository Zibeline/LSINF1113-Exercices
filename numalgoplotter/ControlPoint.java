 

import java.awt.Color;
import java.awt.Graphics;

/**
 * Objects of this class represent control points on the screen (shown as small red squares)
 * 
 * @author Ramin Sadre
 */
public class ControlPoint {
    public final static int size=10;    // the size of the control point square
    private int x,y;                    // the position of the control point
    
    // creates a new control point object at the specified position
    public ControlPoint(int x,int y) {
        this.x=x;
        this.y=y;
    }
    
    // returns the x position
    public int getX() {
        return x;
    }
    
    // returns the y position
    public int getY() {
        return y;
    }
    
    // set position
    public void setPosition(int x,int y) {
        this.x=x;
        this.y=y;
    }
    
    // returns true if a point (someX,someY) is inside the control point square
    public boolean isInside(int someX,int someY) {
        return someX>=(x-size/2) && someX<=(x+size/2) && someY>=(y-size/2) && someY<=(y+size/2);
    }
    
    // paint the control point square
    public void paint(Graphics g) {
        g.setColor(Color.RED) ;
        g.drawRect(x-size/2, y-size/2, size, size);
    }
}
