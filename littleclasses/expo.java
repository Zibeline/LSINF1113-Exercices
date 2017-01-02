import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
/**
 * 
 * @author DenisM
 * @version December 2016
 */
public class expo {
    /**
     * Renvoie (e exp(x) - 1) / x
     */
    public static double expo(double x) {
        return (Math.exp(x)-1)/x;
    }

    public static void main(String[] args) {
        String filename = "test.txt";
        PrintStream stream = null;
        double start = Math.pow(10, -8)*-1;
        double end = Math.pow(10, -8);
        int pointsToCalculate = 100;
        double step = Math.abs(end-start)/(pointsToCalculate-1);
        try {
            FileOutputStream fileWriter = new FileOutputStream(filename, true);
            stream = new PrintStream(fileWriter);
            
            for (int i = 0; i < pointsToCalculate; i++) {
                double x = start + i*step;
                stream.println(x+" "+expo(x));
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite :/");
        } finally {
            if (stream != null) stream.close();
        }
        System.out.println("Et voila, tout ce trouve dans "+filename);
    }
}