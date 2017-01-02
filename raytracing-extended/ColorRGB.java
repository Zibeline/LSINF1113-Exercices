/**
 * Class for representing colors
 * 
 * @author DenisM
 * @version December 2016
 */
public class ColorRGB {
    public double r,g,b;
    
    public ColorRGB(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    /**
     * Ajoute la couleur passée en argument à la couleur actuelle
     */
    public void add(ColorRGB color) {
        this.r += color.r;
        this.g += color.g;
        this.b += color.b;
    }
    
    /**
     * Multiplie les composantes de deux couleurs
     */
    public static ColorRGB multiply(ColorRGB a, ColorRGB b) {
        return new ColorRGB(a.r*b.r, a.g*b.g, a.b*b.b);
    }
    
    /**
     * Multiplie les composantes d'une couleur par un facteur constant
     */
    public static ColorRGB multiply(ColorRGB a, double factor) {
        return new ColorRGB(a.r*factor, a.g*factor, a.b*factor);
    }
    
    /**
     * Multiplie les composantes de deux couleurs d'abord entre elles puis par un facteur constant
     */
    public static ColorRGB multiply(ColorRGB a, ColorRGB b, double factor) {
        return multiply(multiply(a, b), factor);
    }
}