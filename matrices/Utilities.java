/**
 * Tools on matrices
 * 
 * @author DenisM 
 * @version Décembre 2016
 */
public class Utilities {
    public static Matrix minus(Matrix a, Matrix b) {
        Matrix res = new ExtendedMatrix(a.getHeight(), b.getWidth());
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++) {
                res.set((double)a.get(i, j)*(double)b.get(j, i), i, j);
            }
        }
        return res;
    }
    
    public static Matrix mult(Matrix a, Matrix b) {
        Matrix res = new ExtendedMatrix(a.getHeight(), a.getWidth());
        for (int i = 0; i < a.getHeight(); i++) {
            for (int j = 0; j < a.getWidth(); j++) {
                res.set((double)a.get(i, j)-(double)b.get(i, j), i, j);
            }
        }
        return res;
    }
    
    public static Matrix getDiagonal(Matrix a) {
        Matrix res = new ExtendedMatrix(a.getHeight(), a.getWidth());
        for (int i = 0; i < a.getHeight(); i++) {
                res.set((double)a.get(i, i), i, i);
        }
        return res;
    }
    
    public static Matrix multiplyByBand(Matrix base, Matrix band) {
        SimpleMatrix ret = new SimpleMatrix(base.getHeight(), base.getWidth());
        for (int i = 0; i < base.getHeight(); i++) {
            for (int j = 0; j < base.getWidth(); j++) {
                double pos = (double) base.get(i, j-1)* (double) band.get(j-1, j) + (double) base.get(i, j)*(double) band.get(j, j);
                ret.set(pos, i, j);
            }
        }
        return ret;
    }
    
    public static void printMatrix(Matrix base) {
        System.out.println("Voici une matrice :");
        for (int i = 0; i < base.getHeight(); i++) {
            System.out.print("│");
            for (int j = 0; j < base.getWidth(); j++) {
                System.out.format("\t%10.3f", base.get(i, j));
            }
            System.out.println("\t│");
        }
        
    }
    
    public static void printLatex(Matrix base) {
        System.out.println("\\begin{pmatrix}");
        for (int i = 0; i < base.getHeight(); i++) {
            System.out.print("\t");
            for (int j = 0; j < base.getWidth(); j++) {
                if (j>0) System.out.print(" & ");
                System.out.print(base.get(i, j));
            }
            System.out.println("\\");
        }
        System.out.println("\\end{pmatrix}");
    }
    
    /**
     * Transforme une matrice dans un string tel que mise dans un document LaTeX en tableau
     */
    public static double[][] fromLatexToArray(String latex) {
        String[] lines = latex.split("\\\\");
        String[] cols_tmp = lines[0].split("&");
        
        double[][] ret = new double[lines.length][cols_tmp.length];
        
        for (int i = 0; i < lines.length; i++) {
            String[] cols = lines[i].split("&");
            for (int j = 0; j < cols.length; j++) {
                ret[i][j] = Double.parseDouble(cols[j]);
            }
        }
        return ret;
    }
    
    public static void main(String[] args) {
        double[][] baseArray = {
            {1., 2., 3., 4.},
            {5., 6., 7., 8.},
            {9., 10., 11., 12.},
            {13., 14., 15., 16.}
        };
        double[][] bandArray = {
            {1., 7., 0., 0.},
            {0., 2., 6., 0.},
            {0., 0., 3., 5.},
            {0., 0., 0., 4.}
        };
        Matrix base = new SimpleMatrix(4, 4, baseArray);
        Matrix band = new SimpleMatrix(4, 4, bandArray);
        
        Matrix mult = multiplyByBand(base, band);
        printMatrix(mult);
    }
}