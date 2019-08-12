package com.gui;

import com.helper.FileObjectHelper;
import com.helper.ServerConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import weka.classifiers.functions.LibSVM;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;




public class SvmClassifier {

    public static void main(String[] args) {
        System.out.println("Train Model : " + trainModel(ServerConstants.SVM_TRAINING_SET));
        double[] featureArray = new double[]{10, 5501, 27, 51, 1364.0, 0.470245, 12.696621, 27, 27};
        File testFile = new File("D:\\work\\project\\MusicMoodDetection\\MusicMoodDetection-Main\\test-dataset\\05 Kaabil Hoon (Sad Version).wav");
        double[] d = ExtractFeatures.fetchFeatures(testFile);
////        d[0]/=5000;
           ServerConstants.moods = FileObjectHelper.readHashMap(ServerConstants.TYPE_OF_MOODS_FILE);
        System.out.println(d[0]+" "+d[1]);
        int i=(int) getSVMPredication(d);
        System.out.println( ServerConstants.moods.get(i+""));

//	ArrayList<double[]> data=new SvmClassifier().getSVMTrainingData(ServerConstants.SVM_DATA);
//	System.out.println("Size");

    }

    public static void save(LibSVM model, String path) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(model);
        out.close();
    }

    public ArrayList<double[]> getSVMTrainingData(String inputFilePath) {
        DataSource test;
        try {
            test = new DataSource(inputFilePath);

            Instances testData = test.getDataSet();
            if (testData.classIndex() == -1) {
                testData.setClassIndex(testData.numAttributes() - 1);
            }
            ArrayList<double[]> data = new ArrayList<double[]>();
            for (int i = 0; i < testData.numInstances(); i++) {
                Instance instance = testData.instance(i);
                data.add(instance.toDoubleArray());

            }
            return data;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
  public static StringBuffer logs=new StringBuffer();
    public static LibSVM trainModel(String inputFilePath) {
        // DataSource source = new DataSource("./data/Convert.arff");
        System.out.println("Referring to SVM training "+inputFilePath);
        if(logs.length()>1)
        logs.delete(0, logs.length()-1);
        int total = 0;
        int right = 0;
        double accuracy;
        DataSource source;
        LibSVM svmClassifier = null;
        try {
            source = new DataSource(inputFilePath);
            Instances data = source.getDataSet();
            if (data.classIndex() == -1) {
                data.setClassIndex(data.numAttributes() - 1);
            }
            System.out.println(data.classIndex());
            if (svmClassifier == null) {
                svmClassifier = new LibSVM();
                // svmClassifier.setOptions(options);
                System.out.println("start training... ");
                logs.append("Start Training \n");
                svmClassifier.buildClassifier(data);
                File f = new File(ServerConstants.SVM_MODEL_FILE);
                System.out.println(f.getCanonicalPath());
                SvmClassifier.save(svmClassifier, ServerConstants.SVM_MODEL_FILE);
            }
            DataSource test = new DataSource(inputFilePath);
            Instances testData = test.getDataSet();
            if (testData.classIndex() == -1) {
                testData.setClassIndex(testData.numAttributes() - 1);
            }

            for (int i = 0; i < testData.numInstances(); i++) {
                Instance instance = testData.instance(i);

                System.out.print(testData.classAttribute().value(
                        (int) instance.classValue())
                        + " -- ");

                double result = svmClassifier.classifyInstance(instance);
                System.out.print(result + " --- ");
                System.out.println(testData.classAttribute().value((int) result));
                if (Double.compare(result, instance.classValue()) == 0) {
                    right++;
                }
                total++;
            }
            logs.append("SVM Model Created\n");
            logs.append("Instances  are "+total+" \n");
            logs.append("Total / Right Decisions ["+total+"/"+right+"] \n");
            accuracy = ((right / (double) total) * 100);
            System.out.println("Right Decision = " + right
                    + "\nTotal Instances = " + total + "\nAccuracy = "
                    + (right / (double) total) * 100);
            logs.append("Total / Accuracy ["+((right / (double) total) * 100)+"%] \n");
               logs.append("SVM Training Completed \n");
            System.out.println("Svm Model Updated");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return svmClassifier;
    }

    public static LibSVM load(String path) {
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            System.out.println("Try to load model... from " + path);
            long startTime = System.currentTimeMillis();
            Object obj = in.readObject();
            System.out.print("Load model done. "
                    + (System.currentTimeMillis() - startTime) / 1000 + "s");
            in.close();
            fileIn.close();
            if (obj instanceof LibSVM) {
                System.out.println("\nUsing model from: " + path);
                return (LibSVM) obj;
            } else {
                return null;
            }
        } catch (FileNotFoundException e1) {
            System.err.println("Warning: File not found, retrain model...");
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found, retrain model...");
            return null;
        } catch (IOException e) {
            System.err.println("Can't read object. retrain model...");
            return null;
        }
    }

    public static double getSVMPredication(double[] featureArray) {
        // 1=TRUE=SPAM && 0=FALSE=HAM
        boolean predication = false;
        // LibSVM svmClassifier = trainModel("./data/Convert.arff");
        LibSVM svmClassifier = SvmClassifier.load(ServerConstants.SVM_MODEL_FILE);
        try {
            System.out.println("Svm Prediction.......");
            DataSource test = new DataSource(ServerConstants.SVM_TRAINING_SET);
            Instances testData = test.getDataSet();
            int attribute = testData.numAttributes() - 1;
            Instance instance = testData.instance(0);
            for (int j = 0; j < featureArray.length; j++) {
                instance.setValue(j, featureArray[j]);
            }
            int svm_result = 0;
            double result = svmClassifier.classifyInstance(instance);
            System.out.println("Svm Prediction Result : " + (int) result);
         
            /*
             * Writing email Features and SVM Result to train.arff file
             */
//			File dataFile = new File(ServerConstants.SVM_DATA);
//			FileOutputStream fop;

//			fop = new FileOutputStream(dataFile, true);
//			String writeableString = getWritableString(featureArray, svm_result);
//			byte[] contentInBytes = writeableString.getBytes();
//			fop.write(contentInBytes);
//			fop.flush();
//			fop.close();
//			System.out.println("Training DataSet Updated");
//			trainModel(ServerConstants.SVM_DATA);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
     * Predicting SVM Result Using SVM Model
     */
    public static boolean getSVMPredication(int[] featureArray, String svmModel) {
        // 1=TRUE=SPAM && 0=FALSE=HAM
        boolean predication = false;
        // LibSVM svmClassifier = trainModel("./data/Convert.arff");
        // LibSVM svmClassifier =SvmClassifier.load("./svm.model");
        LibSVM svmClassifier = SvmClassifier.load(svmModel);
        try {
            DataSource test = new DataSource("./data/Convert1.arff");
            Instances testData = test.getDataSet();
            int attribute = testData.numAttributes() - 1;
            Instance instance = testData.instance(0);

            // int[] featureArray = new int[] { 0,0,0,0,0,0,0,0 };
            for (int j = 0; j < featureArray.length; j++) {
                instance.setValue(j, featureArray[j]);
            }

            double result = svmClassifier.classifyInstance(instance);
            if (result == 0) {
                predication = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return predication;
    }

    /*
     * binding email feature and svm predicted result in a single string
     */
    public static String getWritableString(int featureArray[], int svm_result) {
        String line = "\n";
        for (int i = 0; i < featureArray.length - 1; i++) {
            line += featureArray[i] + ",";
        }
        line += svm_result;
        return line;
    }
}