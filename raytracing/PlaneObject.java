/**
 * Class for representing plane objects
 * 
 * @author DenisM
 * @version December 2016
 */
public class PlaneObject extends RealObject {
    public final Vector3 c;
    public final Vector3 n;
    
    public PlaneObject(Vector3 c, Vector3 n, ColorRGB color) {
        super(color);
        this.c  = c;
        this.n  = n;
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
        return this.color;
    }
}