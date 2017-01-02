import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
/**
 * Calculates the Forward Euler
 * 
 * @author DenisM
 * @version December 2016
 */
public class ForwardEuler {
    public static String filename = "forward_euler.txt";
    public static String filename_exact = "exact_function.txt";
    public static PrintStream stream = null;
    public static PrintStream stream_exact = null;
    
    // Point connu : f(a) = v
    public static double a = 2;
    public static double v = 1;
    
    // Time step h
    public static double h = 0.25;
    
    // Valeur max de x jusqu'ou il faut calculer
    public static double maxI = 5.;
    
    // Si on a la fonction exacte, mettre a true pour aussi calculer les points via la fonction exacte
    // Permets par exemple de créer un graphe comparatif
    public static boolean compareWithExactFunction = true;
    
    /**
     * Renvoie le résultat de la dérivée pour des paramètres donnés
     * F pour xi et fi
     */
    public static double F(double xi, double fi) {
        return 1 + Math.pow((xi - fi), 2);
    }
    
    /**
     * Si on a la fonction réelle, on peut la renvoyer ici (permets de créer un graphe comparatif par exemple)
     */
    public static double exactFunction(double x) {
        return x+1/(1-x);
    }
    
    public static void main(String[] args) {
        try {
            FileOutputStream fileWriter = new FileOutputStream(filename, true);
            stream = new PrintStream(fileWriter);
            
            if (compareWithExactFunction) {
                FileOutputStream fileWriter_exact = new FileOutputStream(filename_exact, true);
                stream_exact = new PrintStream(fileWriter_exact);
            }
            
            double xi = a;
            double fi = v;
            
            stream.println(xi+" "+fi);
            if (compareWithExactFunction) {
                stream_exact.println(xi+" "+exactFunction(xi));
            }
            
            for (double i = a+h; i < maxI+1; i += h) {
                fi = h * F(xi, fi) + fi;
                xi = i;
                stream.println(xi+" "+fi);
                if (compareWithExactFunction) {
                    stream_exact.println(xi+" "+exactFunction(xi));
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite :/");
        } finally {
            if (stream != null) stream.close();
            if (stream_exact != null) stream_exact.close();
        }
        System.out.println("Et voila, tout ce trouve dans "+filename);
    }
}