/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gui;

import com.helper.FileObjectHelper;
import com.helper.ServerConstants;
import jAudio.JAudioMyFeatures;
import jAudioFeatureExtractor.Aggregators.Aggregator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import neural.helper.FileHelper;

/**
 *
 * @author technowings
 */
public class ExtractFeatures {

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            
//       
//        File audioFilePath = new File("D:\\work\\project\\MusicMoodDetection\\MusicMoodDetection-Main\\dataset-tesset\\test\\[Songs.wav");
//        String[] args2 = new String[]{"-s",
//            ServerConstants.PROJECT_DIR + "settings.xml",
//            ServerConstants.PROJECT_DIR + "feature.xml",
//            audioFilePath.getAbsolutePath()
//        };
//
//        Aggregator.features.clear();
//        JAudioMyFeatures.execute(args2);
//        System.out.println(Aggregator.features.get("MFCC Overall Average"));
//
//    }

        String PROJECT_DIR = ServerConstants.PROJECT_DIR;
        System.out.println(Integer.toBinaryString(7));
        String DATASET_DIR = PROJECT_DIR + "dataset\\";
        generateTrainingCSV(DATASET_DIR);
    }

    public static ArrayList<HashMap> generateTrainingCSV(String DATASET_DIR) {
        FileOutputStream fos = null;
        ArrayList<HashMap> arrMap = new ArrayList<HashMap>();
        try {

            String SVM_DIR = ServerConstants.PROJECT_DIR + "svm\\";
            String TYPE_OF_MOODS_FILE = ServerConstants.TYPE_OF_MOODS_FILE;
            File file = new File(SVM_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }


            file = new File(DATASET_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }

            boolean flag = true;
            StringBuffer sb = new StringBuffer();
            File dirs = new File(DATASET_DIR);
            File[] files = dirs.listFiles();
            if (files == null) {
                System.err.println("NOTHING FOUND IN DATASET");
                return null;
            }

            HashMap<String, String> moods = new HashMap<String, String>();
            int cnt = 0;
            String decision = "";
            for (int i = 0; i < files.length; i++) {   //files.length
                file = files[i];
                if (file.isDirectory()) {
//                    File[] files2 = file.listFiles();
                    moods.put(file.getName(), cnt + "");
                    moods.put(cnt + "", file.getName());
                    decision = decision+","+ cnt ;
                    moods.put(processBinary(Integer.toBinaryString(cnt)), file.getName());

                    System.out.println(cnt + "=>" + file.getName());
                    cnt++;
                }
            }
            if(decision.length()>0){
                decision=decision.substring(1);
            }
            FileObjectHelper.saveObject(moods, TYPE_OF_MOODS_FILE);
            StringBuffer neural_input = new StringBuffer();

            StringBuffer neural_output = new StringBuffer();
            StringBuffer svm_input = FileHelper.getFileContent(ServerConstants.SVM_TEMPLATE);
            svm_input.append("\n");
            for (int i = 0; i < files.length; i++) {   //files.length
                file = files[i];
                if (file.isDirectory()) {
                    File[] files2 = file.listFiles();

                    for (int k = 0; k < files2.length; k++) {
                        File file2 = files2[k];
                        String fileName = file2.getName();
                        double[] features = null;

                        features = fetchFeatures(files2[k]);
                        HashMap featureKeys = Aggregator.features;
                        if (flag) {
                            Set set = featureKeys.keySet();
                            StringBuffer header = new StringBuffer();
                            header.append("Sr.No,Folder, Song Name,");
                            for (Iterator it = set.iterator(); it.hasNext();) {
                                Object object = it.next();
                                if (object.toString().indexOf("MFCC Overall Average") != -1) {
                                    ArrayList<Double> arr = (ArrayList<Double>) featureKeys.get(object);
                                    for (int j = 0; j < arr.size(); j++) {
                                        header.append(object + " " + (j + 1) + ",");
                                        flag = false;
                                    }
                                }
                            }
                            header.append("Decision,Binary OP");
                            header.append("\n");
                            System.out.println(header);
                            sb.append(header.toString());
                            flag = false;
                        }
                        System.out.print(fileName + ",");
                        sb.append((k + 1) + "," + file.getName() + "," + file2.getName() + ",");
                        int moodIndex = com.helper.StringHelper.n2i(moods.get(file.getName()));

                        for (int j = 0; j < features.length; j++) {
                            double d = features[j];
                            sb.append(d + ",");

                            neural_input.append(d + ",");

                            svm_input.append(d + ",");
                        }

                        sb.append(moodIndex);
                        sb.append("," + processBinary(Integer.toBinaryString(moodIndex)));
                        sb.append("\n");
                        svm_input.append( moodIndex + "\n");
                        neural_input.append("\n");
                        neural_output.append(processBinaryCSV(Integer.toBinaryString(moodIndex)) + "\n");

//                        Collection c = Aggregator.features.values();
//                        for (Iterator it = c.iterator(); it.hasNext();) {
//
//
//                            ArrayList<Double> object = (ArrayList<Double>) it.next();
//                            for (int j = 0; j < object.size(); j++) {
//                                Double double1 = object.get(j);
//                                System.out.print(double1 + ",");
//                             
//                            }
//
//                        }


                        HashMap map = (HashMap) featureKeys.clone();
                        arrMap.add(map);
                    }
                }
            }
            sb.append("\n\n\n\n");
            sb.append(moods.toString() + "\n");
            fos = new FileOutputStream(SVM_DIR + "training.csv");
            fos.write(sb.toString().getBytes());
            fos.flush();
            fos.close();
            fos = new FileOutputStream(ServerConstants.NEURAL_TRAINING_SET);
            fos.write(neural_input.toString().getBytes());
            fos.flush();
            fos.close();
            fos = new FileOutputStream(ServerConstants.NEURAL_OUTPUT);
            fos.write(neural_output.toString().getBytes());
            fos.flush();
            fos.close();
            String svm_arff=svm_input.toString().replace("#DECI#", decision);
            fos = new FileOutputStream(ServerConstants.SVM_TRAINING_SET);
            fos.write(svm_arff.getBytes());
            fos.flush();
            fos.close();

        } catch (Exception ex) {
            Logger.getLogger(ExtractFeatures.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(ExtractFeatures.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return arrMap;
    }

    public static String processBinary(String binaryString) {
        if (binaryString.length() == 1) {
            binaryString = "00" + binaryString;
        }
        if (binaryString.length() == 2) {
            binaryString = "0" + binaryString;
        }
        String op = "";
        for (int i = 0; i < binaryString.length(); i++) {
            op += binaryString.charAt(i) + "#";

        }
        return op;
    }

    public static String processBinaryCSV(String binaryString) {
        if (binaryString.length() == 1) {
            binaryString = "00" + binaryString;
        }
        if (binaryString.length() == 2) {
            binaryString = "0" + binaryString;
        }
        String op = "";
        for (int i = 0; i < binaryString.length(); i++) {
            op += binaryString.charAt(i) + ",";

        }
        return op;
    }

    public static double[] fetchFeatures(File audioFilePath) {
        HashMap cached_features = FileObjectHelper.readHashMap(ServerConstants.MOODS_DATA_FILE);    // all features are cached 
        if (cached_features == null) {
            cached_features = new HashMap();

        }

        ArrayList<Double> arrDouble = new ArrayList<Double>();
        String[] args2 = new String[]{"-s",
            ServerConstants.PROJECT_DIR + "settings.xml",
            ServerConstants.PROJECT_DIR + "feature.xml",
            audioFilePath.getAbsolutePath()
        };

        String fileName = audioFilePath.getName();
        HashMap features = null;

        if (cached_features.get(fileName) == null) {
            Aggregator.features.clear();
            JAudioMyFeatures.execute(args2);
            features = Aggregator.features;
            if (Aggregator.features.size() > 0) {
                cached_features.put(fileName, features);
                FileObjectHelper.saveObject(cached_features, ServerConstants.MOODS_DATA_FILE);
            }

        } else {
            features = (HashMap) cached_features.get(fileName);
            if (features.size() == 0) {
                Aggregator.features.clear();
                JAudioMyFeatures.execute(args2);
                features = Aggregator.features;
                if (Aggregator.features.size() > 0) {
                    cached_features.put(fileName, features);
                    FileObjectHelper.saveObject(cached_features, ServerConstants.MOODS_DATA_FILE);
                }
            }
            Aggregator.features = features;
        }

//        Aggregator.features.clear();
//        JAudioMyFeatures.execute(args2);
        Set keys = features.keySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            Object key = it.next();
            if (key.toString().indexOf("MFCC Overall Average") != -1) {
                ArrayList<Double> object = (ArrayList<Double>) features.get(key);
                arrDouble.addAll(object);
            }
        }

//        Collection c = Aggregator.features.values();
//       
//        for (Iterator it = c.iterator(); it.hasNext();) {
//            ArrayList<Double> object = (ArrayList<Double>) it.next();
//            arrDouble.addAll(object);
//        }
        double[] arr = new double[arrDouble.size()];
        for (int i = 0; i < arrDouble.size(); i++) {
            Double double1 = arrDouble.get(i);
            arr[i] = double1.doubleValue();
        }
        arrDouble.clear();
        return arr;
    }
}
