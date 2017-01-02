public abstract class RealObject {  
    public final ColorRGB color;
    public double kr;
    
    /*
        Creates an object with given color.
    */
    public RealObject(ColorRGB color, double kr) {
        this.color=color;
        this.kr = kr;
    }
    
    /*
    	Calculates "t_hit" for the intersection between this object and a ray.
        Returns "t_hit" (or a negative number if no intersection).
        This method is abstract and has to be implemented in sub-classes.
    */
    public abstract double testCollision(Vector3 rayPosition,Vector3 rayDirection) ;
    
    public abstract Vector3 getNormal(Vector3 pointOnSurface);
    
    public abstract ColorRGB getColor(Vector3 pointOnSurface);
}