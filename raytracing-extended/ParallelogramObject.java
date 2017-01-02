/**
 * Class for a parallelogram object
 * 
 * @author DenisM
 * @version December 2016
 */
public class ParallelogramObject extends RealObject {
    public final Vector3 c;
    public final Vector3 n;
    public final Vector3 v1;
    public final Vector3 v2;
    public double kr;
    
    public ParallelogramObject(Vector3 c, Vector3 v1, Vector3 v2, ColorRGB color) {
        this(c, v1, v2, color, 0.);
    }
    
    public ParallelogramObject(Vector3 c, Vector3 v1, Vector3 v2, ColorRGB color, double kr) {
        super(color, kr);
        this.c  = c;
        this.v1  = v1;
        this.v2  = v2;
        this.n = Vector3.normalize(Vector3.crossProduct(v1, v2));
        this.kr = kr;
    }
    
    public double testCollision(Vector3 rayPosition,Vector3 rayDirection) {
        double dn = Vector3.dotProduct(rayDirection, this.n);
        if (dn==0) return -1;
        Vector3 min = Vector3.subst(this.c, rayPosition);
        double thit = Vector3.dotProduct(min, this.n)/dn;
        Vector3 point = Vector3.add(rayPosition, Vector3.multiply(rayDirection, thit));
        Vector3 pmoinsc = Vector3.subst(point, c);
        double u = Vector3.dotProduct(pmoinsc, v1)/ Math.pow(Vector3.getLength(v1), 2);
        double v = Vector3.dotProduct(pmoinsc, v2)/ Math.pow(Vector3.getLength(v2), 2);
        if (u>=0 && u<=1 && v>=0 && v<=1) return thit;
        return -1;
    }
    
    public Vector3 getNormal(Vector3 pointOnSurface) {
        return this.n;
    }
    
    public ColorRGB getColor(Vector3 pointOnSurface) {
        return this.color;
    }
}