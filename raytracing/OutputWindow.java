 

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ramin
 */
public class OutputWindow extends JFrame {
    private JFrame frame ;
    private JPanel panel ;
    private BufferedImage canvas;
    
    /*
        Create an output window of size width x height
    */
    public OutputWindow(int width,int height) {
        frame=new JFrame("Output window") ;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        canvas=new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        
        panel=new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(canvas.getWidth(), canvas.getHeight());
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(canvas, null, null);
            }            
        } ;
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    private int channelToInt(double c) {
        return Math.max(0,Math.min(255,(int)(c*255+0.5))) ;
    }
    
    /*
        Write the color pixel at position x,y in the output window.
        For performance reasons, the result is NOT directly shown.
        You have to call showResult().
    */
    public void setPixel(int x,int y,ColorRGB color) {
        int rgb=255<<24 | (channelToInt(color.r)<<16) | (channelToInt(color.g)<<8) | channelToInt(color.b);
        canvas.setRGB(x, y, rgb);
    }
    
    /*
        Show the image in the window.
    */
    public void showResult() {
        panel.repaint() ;
    }
}
