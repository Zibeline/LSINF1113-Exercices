import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Calculates the Classical Runge Kutta
 * 
 * @author DenisM
 * @version December 2016
 */
public class RK4 {
    /**
     * Renvoie le résultat de la dérivée pour des paramètres donnés
     * F pour xi et fi
     */
    public static double F(double xi, double fi) {
        return xi+Math.sqrt(fi);
    }
    
    /**
     * Renvoie le résultat d'une itération
     * f_{i+1} pour x_i et f_i donnés
     */
    public static double getIteration(double xi, double fi, double xi1, double h) {
        double k1 = F(xi, fi);
        double k2 = F(xi+(h/2), fi+(h/2)*k1);
        double k3 = F(xi+(h/2), fi+(h/2)*k2);
        double k4 = F(xi1, fi+h*k3);
        return fi+ (h/6) * (k1+2*k2+2*k3+k4);
    }
    
    /**
     * Applique la méthode Classic Runge Kutta
     * Valeur initiale : f(a) = v
     * h: time step
     * maxX : valeur ou l'on veut évaluer f
     * Renvoie un tableau de tous les points évalués, la dernière entrée du tableau 
     * est donc {maxX, f(maxX)}
     */
    public static double[][] process(double a, double v, double h, double maxX) {
        int points = (int) 1+ (int) ((maxX-a)/h);
        
        double[][] ret = new double[points][2];
        
        ret[0][0] = a;
        ret[0][1] = v;
            
        for (int i = 1; i < points; i++) {
            double xi = a+((maxX-a)*i/(points-1));
            double fi = getIteration(ret[i-1][0], ret[i-1][1], xi, h);
            
            ret[i][0] = xi;
            ret[i][1] = fi;
        }
        
        return ret;
    }
    
    public static void saveData(double[][] data) {
        String filename = "classic_runge_kutta.txt";
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
        double a = 1;
        double v = 2;
    
        double maxX = 3.; // Valeur max de x jusqu'ou il faut calculer
        double h = 0.125; // Time step h
        System.out.println("h = "+h);
        
        /*double[][] data = process(a, v, h, maxX);
        
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i][0]+" - "+data[i][1]);
        }
        saveData(data);*/
        
        double exactSolution = 106446;
        double actualSolution = -1;
        int pointsBegin = 2;
        int points = pointsBegin;
        do {
            h = (maxX-a)/(points-1);
            double[][] data = process(a, v, h, maxX);
            actualSolution = data[data.length-1][1];
            System.out.println(h+" - "+actualSolution);
            points++;
        } while (points<pointsBegin+100 && Math.abs(Math.round(actualSolution*100)-exactSolution)>=1);
            System.out.println(actualSolution);
        
        System.out.println("h = "+h);
        System.out.println("points = "+points);
    }
    
    public static void calculate_biggest_h() {
        // Point connu : f(a) = v
        double a = 1;
        double v = 2;
    
        double maxX = 3.; // Valeur max de x jusqu'ou il faut calculer
        
        double exactSolution = 106446;
        double actualSolution = v;
        double h = -1;
        int pointsBegin = 2;
        int points = pointsBegin;
        do {
            h = (maxX-a)/(points-1);
            double[][] data = process(a, v, h, maxX);
            actualSolution = data[data.length-1][1];
            points++;
        } while (points<pointsBegin+100 && Math.abs(Math.round(actualSolution*100)-exactSolution)>=1);
        
        System.out.println("f("+maxX+") = "+actualSolution);
        System.out.println("h = "+h);
        System.out.println("points = "+points);
    }
}