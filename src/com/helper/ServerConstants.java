/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helper;

import java.util.HashMap;

/**
 *
 * @author technowings
 */
public class ServerConstants {

    public static final String PROJECT_DIR = "E:\\work\\project\\MusicMoodDetection\\MusicMoodDetection-Main\\";
    public static final String SVM_DIR = PROJECT_DIR + "svm\\";
  
    public static final String SVM_MODEL_FILE = SVM_DIR + "svm.model";
    public static final String SVM_TRAINING_SET = SVM_DIR + "svmTraining2.arff";
    public static final String SVM_TEMPLATE = SVM_DIR + "svm_template.txt";
    public static final String TYPE_OF_MOODS_FILE = SVM_DIR + "moods.bin";
    public static final String PLAYLIST_DIR = PROJECT_DIR + "my_playlist\\";
    public static final String MOODS_DATA_FILE = SVM_DIR + "moods_cached_all_Features.bin";
    public static final String MOODS_DATA_ATTR_NAMES = SVM_DIR + "moods_cached_all_Features_keys.bin";
    public static HashMap moods = null;
    
    public static String NEURAL_TRAINING_SET = SVM_DIR + "neural_input.txt";	// 2 files are 
    public static String NEURAL_OUTPUT = SVM_DIR + "neural_ideal_output.txt";	// 2 files are
    public static String NEURAL_MODEL_FILE = SVM_DIR + "neural_network_model80.net";	// 2 files are
}
