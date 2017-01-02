/**
 * Class for a sphere object
 * 
 * @author DenisM
 * @version December 2016
 */
public class SphereObject extends RealObject {
    public Vector3 c;
    public double radius;
    public ColorRGB color;
    public double kr;
    
    public SphereObject(Vector3 c, double radius, ColorRGB color) {
        this(c, radius, color, 0.);
    }
    
    public SphereObject(Vector3 c, double radius, ColorRGB color, double kr) {
        super(color, kr);
        this.color = color;
        this.c = c;
        this.radius = radius;
        this.kr = kr;
    }
    
    /**
     * Renvoie -1 si pas de collision ou la distance si il y a une collision
     * Pour le fonctionnement, voir l'algo dans les slides
     */
    public double testCollision(Vector3 rayPosition, Vector3 rayDirection) {
        Vector3 f = Vector3.subst(rayPosition, this.c);
        double a = Vector3.dotProduct(f, rayDirection);
        if (a>0) return -1;
        
        double f2 = Vector3.dotProduct(f, f);
        double b = f2 - Math.pow(this.radius, 2);
        
        double delta = Math.pow(a, 2) - b;
        
        if (delta<0) return -1;
        if (delta==0) return -1*a;
        return -1*a-Math.sqrt(delta);
    }
    
    /**
     * Renvoie le vecteur normal à la position passée en argument
     */
    public Vector3 getNormal(Vector3 pointOnSurface) {
        Vector3 min = Vector3.subst(pointOnSurface, c);
        return new Vector3(min.x/radius, min.y/radius, min.z/radius);
    }
    
    public ColorRGB getColor(Vector3 pointOnSurface) {
        return this.color;
    }
}