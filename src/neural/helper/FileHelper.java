package neural.helper;

import com.helper.StringHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class FileHelper {

    public static void main(String[] args) {
        StringBuffer sb = getFileContent("D:/114");
        System.out.println(sb);
    }

    public static StringBuffer getFileContent(String filepath) {
        InputStream is = null;
        int i;
        char c;
        StringBuffer sb = new StringBuffer();

        try {
            File f = new File(filepath);
            System.out.println(f.getCanonicalPath());
            if (!f.exists()) {
                System.out.println("File Does NOT exist!!");
                return sb;
            }
            is = new FileInputStream(filepath);
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read(b)) != -1) {
//				String s = new String(b);
//				sb.append(s.trim());
                baos.write(b, 0, i);
            }
            sb = new StringBuffer(new String(baos.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb;
    }

    public static ArrayList<String[]> parseFile(String fileName) {
        ArrayList<String[]> arr = new ArrayList<String[]>();
        StringBuffer sb = getFileContent(fileName);
        String[] tokens = sb.toString().split("\n");
        for (int i = 0; i < tokens.length; i++) {
            String string = tokens[i];
            String[] keyTweet = string.split(",");
            arr.add(keyTweet);
        }
        return arr;
    }

    public static ArrayList<double[]> parseFileDouble(String fileName) {
        ArrayList<double[]> arr = new ArrayList<double[]>();
        StringBuffer sb = getFileContent(fileName);
        String[] tokens = sb.toString().split("\n");
        for (int i = 0; i < tokens.length; i++) {
            String string = tokens[i].trim();
            if(string.length()==0){
                continue;
            }
            String[] keyTweet = string.split(",");
            double[] a = new double[keyTweet.length];
            for (int j = 0; j < a.length; j++) {
                a[j] = StringHelper.n2d(keyTweet[j]);

            }
            arr.add(a);
        }
        return arr;
    }

    public static ArrayList<String[]> parseFile(String fileName, String rowDelim, String colDelim) {
        ArrayList<String[]> arr = new ArrayList<String[]>();
        StringBuffer sb = getFileContent(fileName);
        String[] tokens = sb.toString().split(rowDelim);
        for (int i = 0; i < tokens.length; i++) {
            String string = tokens[i];
            String[] keyTweet = string.split(colDelim);
            arr.add(keyTweet);
        }
        return arr;
    }

    public static File[] getFileList(String dirPath) {
        File f = new File(dirPath);
        try {
            System.out.println("Canonical Path " + f.getCanonicalPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        File[] a = f.listFiles();
        if (a != null) {
            System.out.println(" Got Files " + a.length);
        }
        return a;
    }
    //	extn=.txt .jpg

    public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024 * 10];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static File[] getFileList(String dirPath, final String extn) {
        File f = new File(dirPath);
        try {
            System.out.println("Canonical Path " + f.getCanonicalPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        FilenameFilter textFilter = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(extn)) {
                    return true;
                } else {
                    return false;
                }
            }
        };


        File[] a = f.listFiles(textFilter);
        if (a != null) {
            System.out.println(" Got Files " + a.length);
        }
        return a;
    }
}
