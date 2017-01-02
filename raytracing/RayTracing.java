import java.util.LinkedList;

/**
 *
 * @author ramin
 */
public class RayTracing {
    private static final int screenWidth=800;
    private static final int screenHeight=600;
    
    // Camera : position, direction and up
    private Vector3 pc;
    private final Vector3 dc;
    private final Vector3 up;
    
    private final OutputWindow outputWindow;
      
    // List of all objects
    LinkedList<RealObject> objects=new LinkedList<>();
    
    public RayTracing() {
        outputWindow=new OutputWindow(screenWidth,screenHeight);
        
        // setting up camera
        pc = new Vector3(0, 0, 15);
        dc = new Vector3(0, 0, -1);
        up = new Vector3(0, 1, 0);
        
        ColorRGB dark_grey = new ColorRGB(0.2, 0.2, 0.2);
        ColorRGB red       = new ColorRGB(1, 0, 0);
        ColorRGB yellow    = new ColorRGB(1, 1, 0);
        
        // initialize scene
        objects.add(new SphereObject(new Vector3(0, 2, 0), 2, dark_grey));
        objects.add(new SphereObject(new Vector3(4, 0, 2), 2, red));
        objects.add(new PlaneObject(new Vector3(0, -4, 0), new Vector3(0, 1, 0), yellow));
    }   

    private ColorRGB traceRay(Vector3 rayPosition,Vector3 rayDirection) {
        RealObject hittedObject = null;
        double tret = Double.MAX_VALUE;
        for (int i = 0; i < objects.size(); i++) {
            RealObject here = objects.get(i);
            double thit = here.testCollision(rayPosition, rayDirection);
            
            if (thit>0 && thit<tret) {
                tret = thit;
                hittedObject = here;
            }
        }
        
        if (hittedObject==null) return new ColorRGB(0, 0, 0);
        
        ColorRGB objectColor = hittedObject.color;
        
        double distanceFactor = 120/Math.pow(tret, 2);
        return ColorRGB.multiply(objectColor, distanceFactor);
    }
    
    private void renderScreen() {  
		Vector3 right = Vector3.crossProduct(dc, up);
        
        // calculate delta_x and delta_y
        Vector3 delta_x = new Vector3(right.x/screenHeight, right.y/screenHeight, right.z/screenHeight);
        Vector3 delta_y = new Vector3(up.x/screenHeight, up.y/screenHeight, up.z/screenHeight);//Vector3.multiply(up, 1/screenHeight); 
        
        // go through all points of the image plane
        for (int y = -screenHeight/2; y < screenHeight/2-1; y++) {
            for (int x = -screenWidth/2; x < screenWidth/2; x++) {
                Vector3 xdeltax = Vector3.multiply(delta_x, x);
                Vector3 ydeltay = Vector3.multiply(delta_y, y);
                Vector3 toNormalize = new Vector3(dc.x+xdeltax.x+ydeltay.x, dc.y+xdeltax.y+ydeltay.y, dc.z+xdeltax.z+ydeltay.z);
                Vector3 normalized = Vector3.normalize(toNormalize);
                ColorRGB color = traceRay(pc, normalized);
                
                int hx = x+screenWidth/2;
                int hy = screenHeight/2-1-y;
                outputWindow.setPixel(hx,hy,color);      
            }
        }
        
        // show the result
        outputWindow.showResult();
    }
    
    public static void main(String[] args) {
        new RayTracing().renderScreen();               
    }
    
}
