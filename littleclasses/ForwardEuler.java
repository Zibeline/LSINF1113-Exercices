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
    // Si on a la fonction exacte, mettre à true pour aussi calculer les points 
    // via la fonction exacte
    // Permets par exemple de créeer un graphe comparatif
    private static boolean compareWithExactFunction = true;
    
    /**
     * Renvoie le résultat de la dérivée pour des paramètres donnés
     * F pour xi et fi
     */
    public static double F(double xi, double fi) {
        return 1 + Math.pow((xi - fi), 2);
    }
    
    /**
     * Si on a la fonction réelle, on peut la renvoyer ici (permets de créer un 
     * graphe comparatif par exemple)
     */
    public static double exactFunction(double x) {
        return x+1/(1-x);
    }
    
    /**
     * Renvoie le résultat d'une itération
     * f_{i+1} pour x_i et f_i donnés
     */
    public static double getIteration(double xi, double fi, double h) {
        return h * F(xi, fi) + fi;
    }
    
    /**
     * Applique la méthode Forward Euler
     * Valeur initiale : f(a) = v
     * h: time step
     * maxX : valeur ou l'on veut évaluer f
     * Renvoie un tableau de tous les points évalués, la dernière entrée du tableau 
     * est donc {maxX, f(maxX)}
     * Si compareWithExactFunction est à true, le tableu renvoyé contient une 3e 
     * colonne avec la valeur exacte pour x
     */
    public static double[][] process(double a, double v, double h, double maxX) {
        int points = (int) 1+ (int) ((maxX-a)/h);
        
        int width = (compareWithExactFunction) ? 3 : 2;
        double[][] ret = new double[points][width];
        
        ret[0][0] = a;
        ret[0][1] = v;
        if (compareWithExactFunction) ret[0][2] = v;
            
        for (int i = 1; i < points; i++) {
            double xi = a+((maxX-a)*i/(points-1));
            double fi = getIteration(ret[i-1][0], ret[i-1][1], h);
            
            ret[i][0] = xi;
            ret[i][1] = fi;
            if (compareWithExactFunction) ret[i][2] = exactFunction(xi);
        }
        
        return ret;
    }
    
    public static void saveData(double[][] data) {
        String filename = "forward_euler.txt";
        PrintStream stream = null;
        
        try {
            FileOutputStream fileWriter = new FileOutputStream(filename, true);
            stream = new PrintStream(fileWriter);
            
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    stream.print(data[i][j]+" ");
                }
                stream.println();
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite :/");
        } finally {
            if (stream != null) stream.close();
        }
        System.out.println("Et voila, tout se trouve dans "+filename);
    }
    
    public static void main(String[] args) {
        // Point connu : f(a) = v
        double a = 2;
        double v = 1;
    
        double maxX = 6.; // Valeur max de x jusqu'ou il faut calculer
        double h = 0.25; // Time step h
        System.out.println("h = "+h);
        
        double[][] data = process(a, v, h, maxX);
        
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i][0]+" - "+data[i][1]);
        }
        saveData(data);
    }
}