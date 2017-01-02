/**
 * Class for representing vectors
 * 
 * @author DenisM
 * @version December 2016
 */
public class Vector3 {
    public double x,y,z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }    
    
    /**
     * Addition de deux vecteurs
     */
    public static Vector3 add(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
    
    /**
     * Addition de 4 vecteurs
     */
    public static Vector3 add(Vector3 v1, Vector3 v2, Vector3 v3, Vector3 v4) {
        return add(add(v1, v2), add(v3, v4));
    }
    
    /**
     * Soustraction de deux vecteurs
     */
    public static Vector3 subst(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }
    
    /**
     * Multiplication d'un vecteur par un facteur constant
     */
    public static Vector3 multiply(Vector3 v1, double factor) {
        return new Vector3(v1.x * factor, v1.y * factor, v1.z * factor);
    }
    
    /**
     * Division d'un vecteur par un facteur constant
     */
    public static Vector3 divide(Vector3 v1, double factor) {
        if (factor==0) return v1;
        return new Vector3(v1.x / factor, v1.y / factor, v1.z / factor);
    }

    /**
     * Renvoie la longueur du vecteur
     */
    public static double getLength(Vector3 v1) {
        return Math.sqrt(Math.pow(v1.x, 2) + Math.pow(v1.y, 2) + Math.pow(v1.z, 2));
    }
    
    /**
     * Renvoie la version normalisée d'un vecteur
     */
    public static Vector3 normalize(Vector3 v1) {
        double length = getLength(v1);
        if (length==0) return new Vector3(0, 0, 0);
        return divide(v1, length);
    }
    
    /**
     * Renvoie le ploduit scalaire de deux vecteurs
     */
    public static double dotProduct(Vector3 v1, Vector3 v2) {
        return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
    }
    
    /**
     * Renvoie le produit vectoriel de deux vecteurs
     */
    public static Vector3 crossProduct(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.y*v2.z-v1.z*v2.y, v1.z*v2.x-v1.x*v2.z, v1.x*v2.y-v1.y*v2.x);
    }
}