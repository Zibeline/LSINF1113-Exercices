/**
 * Class for a texturized plane
 * 
 * @author DenisM
 * @version December 2016
 */
public class TexturizedPlaneObject extends PlaneObject {
    public final Vector3 c;
    public final Vector3 n;
    public final Vector3 v1;
    public final Vector3 v2;
    public double kr;
    
    public TexturizedPlaneObject(Vector3 c, Vector3 v1, Vector3 v2, ColorRGB color) {
        this(c, v1, v2, color, 0.);
    }
    
    public TexturizedPlaneObject(Vector3 c, Vector3 v1, Vector3 v2, ColorRGB color, double kr) {
        super(c, Vector3.normalize(Vector3.crossProduct(v1, v2)), color, kr);
        this.c  = c ;
        this.n  = Vector3.normalize(Vector3.crossProduct(v1, v2));
        this.v1 = v1;
        this.v2 = v2;
        this.kr = kr;
    }
    
    public double testCollision(Vector3 rayPosition,Vector3 rayDirection) {
        double dn = Vector3.dotProduct(rayDirection, this.n);
        if (dn==0) return -1;
        Vector3 min = Vector3.subst(this.c, rayPosition);
        return Vector3.dotProduct(min, this.n)/dn;
    }
    
    public Vector3 getNormal(Vector3 pointOnSurface) {
        return this.n;
    }
    
    public ColorRGB getColor(Vector3 pointOnSurface) {
        Vector3 pc = Vector3.subst(pointOnSurface, c);
        
        double u = (Vector3.dotProduct(pc, v1))/(Math.pow(Vector3.getLength(v1), 2));
        double v = (Vector3.dotProduct(pc, v2))/(Math.pow(Vector3.getLength(v2), 2));
        
        return ((int) (Math.floor(u)+Math.floor(v)) & 1) == 0 ? new ColorRGB(1, 1, 0) : color;
    }
}