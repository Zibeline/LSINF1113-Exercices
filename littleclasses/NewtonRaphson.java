import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
/**
 * Newton-Raphson method
 * Lecon 11 - Question 4
 * 
 * @author DenisM
 * @version December 2016
 */
public class NewtonRaphson {
    private static double a = 1.;
    private static double b = 0.;
    private static double c = -2.;
    private static double d = 2.;
    private static int max_iterations = 200;
        
    /**
     * Renvoie f(x) = ax^3 + bx^2 + cx + d
     */
    private static double f(double x) {
        return a*Math.pow(x, 3) + b*Math.pow(x, 2) + c*x + d;
    }
    
    /**
     * Renvoie f'(x)
     * On aurais pu utiliser une des méthodes vues à la lecon 8 pour 
     * calculer cette dérivée automatiquement
     */
    private static double der(double x) {
        return 3*a*Math.pow(x, 2) + b*x + c;
    }
    
    /**
     * Renvoie x_{x+1} pour un x_k donné
     */
    private static double getIteration (double oldx) {
        return oldx-(f(oldx)/der(oldx));
    }
    
    /**
     * Calcule la racine pour un x0 donné et une erreur acceptable
     * Renvoie un tableau avec les données suivantes
     * [0] : la racine calculée
     * [1] : f(racine calculée)
     * [2] : le nombre d'itérations pour trouver ce résultat
     * [3] : x0 (= argument recu)
     * [4] : erreur acceptable (= argument recu)
     */
    public static double[] process(double x0, double acceptable_error) {
        int iterations = 0;
        
        double xhere = x0;
        double fhere = f(x0);
        while (Math.abs(fhere)>acceptable_error && iterations<max_iterations) {
            double newx = getIteration(xhere);
            double newf = f(newx);
            xhere = newx;
            fhere = newf;
            iterations++;
        }
        
        double[] ret = {xhere, fhere, iterations, x0, acceptable_error};
        return ret;
    }
    
    /**
     * Affiche le tableau renvoyé par la méthode process proprement dans la console
     */
    public static void displayResult(double[] res) {
        System.out.println("===== Newton-Raphson \n\tx0 = "+res[3]+";\n\tacceptable_error = "+res[4]+"\n");
        if (Math.abs(res[1])<=res[4]) {
            System.out.println("\t"+res[2]+" itérations,\n\tracine : "+res[0]);
        }
        else {
            System.out.println("\t"+res[2]+" itérations, rien trouvé de concluant");
        }
        System.out.println("\tf("+res[0]+") = "+res[1]);
    }
    
    /**
     * Enregistre dans un fichier texte le nombre d'itérations nécessaire pour chaque x0
     * Ne mets pas les points ou le nombre d'itérations = nombre max d'itérations
     * (on considère que dans ce cas la méthode ne converge pas)
     */
    public static void saveData(double[][] data) {
        String filename = "newton_raphson.txt";
        PrintStream stream = null;
        
        try {
            FileOutputStream fileWriter = new FileOutputStream(filename, true);
            stream = new PrintStream(fileWriter);
            
            for (int i = 0; i < data.length; i++) {
                if (Math.abs(data[i][1])<=data[i][4]) {
                    stream.println(data[i][3]+" "+data[i][2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite :/");
        } finally {
            if (stream != null) stream.close();
        }
        System.out.println("Et voila, tout se trouve dans "+filename);
    }
    
    /**
     * Affiche la liste des points ou il n'y a pas de convergence
     */
    public static void displayNoConvergence(double[][] datas) {
        for (int i = 0; i < datas.length; i++) {
            if (Math.abs(datas[i][1])>datas[i][4]) {
                System.out.println(datas[i][3]);
            }
        }
    }
    
    public static void main(String[] args) {
        double acceptable_error = 0.;
        double begin = -10.;
        double end = 10;
        int points = 100;
        
        double step = Math.abs(end-begin)/(points-1);
        double[][] datas = new double[points][5];
        
        for (int i = 0; i < points; i++) {
            double x0 = begin+i*step;
            datas[i] = process(x0, acceptable_error);
            displayResult(datas[i]);
        }
        
        saveData(datas);
    }
}