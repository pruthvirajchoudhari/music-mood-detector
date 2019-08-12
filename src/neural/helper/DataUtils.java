package neural.helper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataUtils {

    /**
     * Parse input txt data separated by "," in two-dimensional float array
     *
     * @param fileURI
     * @return float[][] with data
     */
    public static double[][] readInputsFromFile(String fileURI) {
        double[][] fArray = new double[0][];

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fileURI),
                    StandardCharsets.UTF_8);

            fArray = new double[lines.size()][];

            for (int i = 0; i < lines.size(); i++) {
                String ln = lines.get(i).trim();
                
                if (ln.endsWith(",")) {
                    ln = ln.substring(0, ln.length() - 1);
                }
                if(ln.length()==0){
                    continue;
                }
                fArray[i] = convertStringArrayToFloatArray(ln.split(
                        ","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fArray;
    }

    public double[] normalizeDouble(double[][] fArray) {
        double normalize[] = new double[fArray[0].length];
        for (int i = 0; i < fArray[0].length; i++) {
            double max = computeMax(fArray, i);
            if (max > 1) {
                normalize(fArray, i, max);
            }
            normalize[i] = max;
        }
        return normalize;
    }

    public void normalize(double[][] fArray, int col, double max) {
        for (int i = 0; i < fArray.length; i++) {
            fArray[i][col] = fArray[i][col] / max;
        }

    }

    public double computeMax(double[][] fArray, int col) {
        double max = 0;
        for (int i = 0; i < fArray.length; i++) {
            if (fArray[i][col] > max) {
                max = fArray[i][col];
            }
        }
        return max;
    }

    /**
     * Parse output txt data separated by "," in two-dimensional float array
     *
     * @param fileURI
     * @return int[] with data
     */
    public static double[] readOutputsFromFile(String fileURI) {
        double[] iArray = new double[0];

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fileURI),
                    StandardCharsets.UTF_8);
            iArray = new double[lines.size()];

            for (int i = 0; i < lines.size(); i++) {
                   String ln = lines.get(i);
                if (ln.endsWith(",")) {
                    ln = ln.substring(0, ln.length() - 1);
                }
                if(ln.length()==0){
                    continue;
                }
                iArray[i] = Double.parseDouble((ln));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return iArray;
    }

   

    /**
     * Convert array of strings to array of float
     *
     * @param num Array of string
     * @return array of float
     */
    private static double[] convertStringArrayToFloatArray(String[] num) {
        if (num != null) {
            double fArray[] = new double[num.length];
            for (int i = 0; i < num.length; i++) {
                fArray[i] = Double.parseDouble(num[i]);
            }
            return fArray;
        }
        return null;
    }
}
