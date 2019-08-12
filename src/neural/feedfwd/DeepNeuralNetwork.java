/**
 * Introduction to Neural Networks with Java, 2nd Edition Copyright 2008 by
 * Heaton Research, Inc. http://www.heatonresearch.com/books/java-neural-2/
 *
 * ISBN13: 978-1-60439-008-7 ISBN: 1-60439-008-5
 *
 * This class is released under the: GNU Lesser General Public License (LGPL)
 * http://www.gnu.org/copyleft/lesser.html
 */
package neural.feedfwd;

import com.gui.ExtractFeatures;
import com.gui.Main;
import com.helper.FileObjectHelper;
import com.helper.ServerConstants;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import neural.backpropagation.*;
import neural.helper.DataUtils;
import neural.helper.FileHelper;
import neural.helper.NeuralNetworkError;

/**
 * FeedforwardNeuralNetwork: This class implements a feed forward neural
 * network. This class works in conjunction the FeedforwardLayer class. Layers
 * are added to the FeedforwardNeuralNetwork to specify the structure of the
 * neural network.
 *
 * The first layer added is the input layer, the final layer added is the output
 * layer. Any layers added between these two layers are the hidden layers.
 *
 * @author Jeff Heaton
 * @version 2.1
 */
public class DeepNeuralNetwork implements Serializable {

    /**
     * Serial id for this class.
     */
    private static final long serialVersionUID = -136440631687066461L;

    private void normalize(double[][] input) {


        double max[] = findMax(input);

        for (int row = 0; row < input.length; row++) {
            for (int col = 0; col < input[row].length; col++) {
//                double e = max[j];
                input[row][col] /= max[col];
            }
        }
    }

    private void normalizePostTraining(double[] input) {
     
        double[][] inputTrainign = DataUtils.readInputsFromFile(ServerConstants.NEURAL_TRAINING_SET);
        double max[] = findMax(inputTrainign);

        for (int row = 0; row < input.length; row++) {

//                double e = max[j];
            input[row] /= max[row];

        }
    }
//double count[][]

    public double[] findMax(double[][] count) {
//        double count[][] = {{1, 2, 3, 8}, {4, 6, 5, 9}, {0, 8, 9, 1}};
        int r = count.length;
        int c = count[0].length;
        double out[] = new double[c];
        for (int x = 0; x < c; x++) {
            double max = Integer.MIN_VALUE;
            for (int i = 0; i < r; i++) {
                if (Math.abs(count[i][x]) > max) {
                    max = Math.abs(count[i][x]);
                }
            }
            out[x] = max;
        }
        for (int i = 0; i < c; i++) {
            out[i] = Math.ceil(out[i]);
//            System.out.println("Max-"+out[i]);
        }
        return out;

    }
    /**
     * The input layer.
     */
    protected FeedforwardLayer inputLayer;
    /**
     * The output layer.
     */
    protected FeedforwardLayer outputLayer;
    /**
     * All of the layers in the neural network.
     */
    protected List<FeedforwardLayer> layers = new ArrayList<FeedforwardLayer>();

    /**
     * Construct an empty neural network.
     */
    public DeepNeuralNetwork() {
    }
    public static void main(String[] args) {
       String s= new DeepNeuralNetwork().recognize(new File("D:\\work\\project\\MusicMoodDetection\\MusicMoodDetection-Main\\dataset-tesset\\test\\02 Sau Aasmaan.wav"));
         ServerConstants.moods = FileObjectHelper.readHashMap(ServerConstants.TYPE_OF_MOODS_FILE);
         System.out.println("ServerConstants.moods "+ServerConstants.moods.get(s));
    }
    public String recognize(File audioPath) {
        
        double[] d = ExtractFeatures.fetchFeatures(audioPath);
        for (int i = 0; i < d.length; i++) {
            double e = d[i];
            System.out.print(e+",");
        }
        System.out.println("");
        return recognize(d);
    }

    public String recognize(double[] d) {
        DeepNeuralNetwork network = initializeNeural();
        normalizePostTraining(d);
        for (int i = 0; i < d.length; i++) {
            double e = d[i];
            if(e>1){
                System.err.println("Error in normalization. Value greater than input max. Something Fishy!!!");
            }
        }
        double o[] = network.computeOutputs(d);
        String s="";
        for (int i = 0; i < o.length; i++) {
            double e =  o[i];
            //e+" "+
            s+=((int)Math.round(e))+"#";
            System.out.print(Math.round(e)+" ");
        }
        System.out.println("\n"+s);
        return s;
    }

    public DeepNeuralNetwork initializeNeural() {


        double d = 10e3;
        // original
        DeepNeuralNetwork network = new DeepNeuralNetwork();



        network.addLayer(new FeedforwardLayer(13));
        network.addLayer(new FeedforwardLayer(9));
        network.addLayer(new FeedforwardLayer(3));
        network.reset();

        File f = new File(ServerConstants.NEURAL_MODEL_FILE);

        if (f.exists()) {
            try {
                Object obj = FileObjectHelper.readObject(ServerConstants.NEURAL_MODEL_FILE);

                if (obj != null) {
                    network = (DeepNeuralNetwork) obj;

                }
                System.out.println("Neural Network Already Exists!!! "
                        + f.getCanonicalPath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
              Main.jTextArea1.setText("Neural Training Completed\n");
             Main.jPanel8.setVisible(false);
            return network;
        }
        String DIR = ServerConstants.NEURAL_TRAINING_SET;
        double[][] input = DataUtils.readInputsFromFile(ServerConstants.NEURAL_TRAINING_SET);
        network.normalize(input);


        double[][] ideal_file = DataUtils.readInputsFromFile(ServerConstants.NEURAL_OUTPUT);

        // double[][] input=new
//         double[][]input=new double[][]{{0.1,0.5,0.7},{0.2,0.4,0.6},{0.3,0.6,0.9},{0.4,0.8,0.99}};
//         double[][] ideal=new double[][]{{0},{1},{1},{1}};
//        double[][] ideal = new double[ideal_file.length][1];
//        for (int i = 0; i < ideal_file.length; i++) {
//            ideal[i][0] = ideal_file[i];
//
//        }

//        double[][] input = new double[][]{{1.0000000000, 0.0546732537},
//            {1.0000000000, 0.0546732537}, {1.0000000000, 0.0546732537},
//            {1.0000000000, 0.0546732537}};
//      double[][] ideal = new double[][]{ideal_file};
        double learnRate = 0.6;
        double momentum = 0.9;
        Train train = new Backpropagation(network, input, ideal_file, learnRate,
                momentum);
        int epoch = 1;
        int iterations = 5000;
        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error: "
                    + train.getError());
            epoch++;
        } while (epoch < 5000 && train.getError() > 0.001);

        int correctCount=0;
        for (int i = 0; i < input.length; i++) {
            double o[] = network.computeOutputs(input[i]);
            
            
            // "+input[i][2]+"
            boolean b=true;
            for (int j = 0; j < o.length; j++) {
                double e = Math.round(o[j]);
                double ide=ideal_file[i][j] ;
                if(e==ide){
                    b=b&&true;
                }else{
                    b=b&&false;
                }
            }
            if(b){
                correctCount++;
            }
            System.out.println(input[i][0] + " " + input[i][1] + "  => Actual "
                    +Math.round( o[0]) + " " + Math.round( o[1]) + " " +Math.round( o[2]) + " ==>" + ideal_file[i][0] + " " + ideal_file[i][1] + " " + ideal_file[i][2]+" "+b);
            
        }
        System.out.println("correctCount "+correctCount+"/"+input.length);
        System.out.println("Accuracy "+(correctCount*100/input.length)+"%");
        FileObjectHelper.saveObject(network, ServerConstants.NEURAL_MODEL_FILE);
        Main.jTextArea1.setText("Neural Training Completed\n");
        Main.jPanel8.setVisible(false);
        return network;
        // double o[]=network.computeOutputs(new double[]{0.15,0.45,0.75});
        // System.out.println(" => Actual "+o[0]);
    }

    /**
     * Add a layer to the neural network. The first layer added is the input
     * layer, the last layer added is the output layer.
     *
     * @param layer The layer to be added.
     */
    public void addLayer(final FeedforwardLayer layer) {
        // setup the forward and back pointer
        if (this.outputLayer != null) {
            layer.setPrevious(this.outputLayer);
            this.outputLayer.setNext(layer);
        }

        // update the inputLayer and outputLayer variables
        if (this.layers.size() == 0) {
            this.inputLayer = this.outputLayer = layer;
        } else {
            this.outputLayer = layer;
        }

        // add the new layer to the list
        this.layers.add(layer);
    }

    /**
     * Calculate the error for this neural network. The error is calculated
     * using root-mean-square(RMS).
     *
     * @param input Input patterns.
     * @param ideal Ideal patterns.
     * @return The error percentage.
     * @throws NeuralNetworkException An error happened trying to determine the
     * error.
     */
    public double calculateError(final double input[][], final double ideal[][])
            throws NeuralNetworkError {
        final ErrorCalculation errorCalculation = new ErrorCalculation();

        for (int i = 0; i < ideal.length; i++) {
            computeOutputs(input[i]);
            errorCalculation.updateError(this.outputLayer.getFire(),
                    ideal[i]);
        }
        return (errorCalculation.calculateRMS());
    }

    /**
     * Calculate the total number of neurons in the network across all layers.
     *
     * @return The neuron count.
     */
    public int calculateNeuronCount() {
        int result = 0;
        for (final FeedforwardLayer layer : this.layers) {
            result += layer.getNeuronCount();
        }
        return result;
    }

    /**
     * Return a clone of this neural network. Including structure, weights and
     * threshold values.
     *
     * @return A cloned copy of the neural network.
     */
    @Override
    public Object clone() {
        final DeepNeuralNetwork result = cloneStructure();
        final Double copy[] = MatrixCODEC.networkToArray(this);
        MatrixCODEC.arrayToNetwork(copy, result);
        return result;
    }

    /**
     * Return a clone of the structure of this neural network.
     *
     * @return A cloned copy of the structure of the neural network.
     */
    public DeepNeuralNetwork cloneStructure() {
        final DeepNeuralNetwork result = new DeepNeuralNetwork();

        for (final FeedforwardLayer layer : this.layers) {
            final FeedforwardLayer clonedLayer = new FeedforwardLayer(layer.getNeuronCount());
            result.addLayer(clonedLayer);
        }

        return result;
    }

    /**
     * Compute the output for a given input to the neural network.
     *
     * @param input The input provide to the neural network.
     * @return The results from the output neurons.
     * @throws MatrixException A matrix error occurred.
     * @throws NeuralNetworkException A neural network error occurred.
     */
    public double[] computeOutputs(final double input[]) {

        if (input.length != this.inputLayer.getNeuronCount()) {
            throw new NeuralNetworkError(
                    "Size mismatch: Can't compute outputs for input size="
                    + input.length + " for input layer size="
                    + this.inputLayer.getNeuronCount());
        }

        for (final FeedforwardLayer layer : this.layers) {
            if (layer.isInput()) {
                layer.computeOutputs(input);
            } else if (layer.isHidden()) {
                layer.computeOutputs(null);
            }
        }

        return this.outputLayer.getFire();
    }

    /**
     * Compare the two neural networks. For them to be equal they must be of the
     * same structure, and have the same matrix values.
     *
     * @param other The other neural network.
     * @return True if the two networks are equal.
     */
    public boolean equals(final DeepNeuralNetwork other) {
        final Iterator<FeedforwardLayer> otherLayers = other.getLayers().iterator();

        for (final FeedforwardLayer layer : this.getLayers()) {
            final FeedforwardLayer otherLayer = otherLayers.next();

            if (layer.getNeuronCount() != otherLayer.getNeuronCount()) {
                return false;
            }

            // make sure they either both have or do not have
            // a weight matrix.
            if ((layer.getMatrix() == null) && (otherLayer.getMatrix() != null)) {
                return false;
            }

            if ((layer.getMatrix() != null) && (otherLayer.getMatrix() == null)) {
                return false;
            }

            // if they both have a matrix, then compare the matrices
            if ((layer.getMatrix() != null) && (otherLayer.getMatrix() != null)) {
                if (!layer.getMatrix().equals(otherLayer.getMatrix())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Get the count for how many hidden layers are present.
     *
     * @return The hidden layer count.
     */
    public int getHiddenLayerCount() {
        return this.layers.size() - 2;
    }

    /**
     * Get a collection of the hidden layers in the network.
     *
     * @return The hidden layers.
     */
    public Collection<FeedforwardLayer> getHiddenLayers() {
        final Collection<FeedforwardLayer> result = new ArrayList<FeedforwardLayer>();
        for (final FeedforwardLayer layer : this.layers) {
            if (layer.isHidden()) {
                result.add(layer);
            }
        }
        return result;
    }

    /**
     * Get the input layer.
     *
     * @return The input layer.
     */
    public FeedforwardLayer getInputLayer() {
        return this.inputLayer;
    }

    /**
     * Get all layers.
     *
     * @return All layers.
     */
    public List<FeedforwardLayer> getLayers() {
        return this.layers;
    }

    /**
     * Get the output layer.
     *
     * @return The output layer.
     */
    public FeedforwardLayer getOutputLayer() {
        return this.outputLayer;
    }

    /**
     * Get the size of the weight and threshold matrix.
     *
     * @return The size of the matrix.
     */
    public int getWeightMatrixSize() {
        int result = 0;
        for (final FeedforwardLayer layer : this.layers) {
            result += layer.getMatrixSize();
        }
        return result;
    }

    /**
     * Reset the weight matrix and the thresholds.
     *
     * @throws MatrixException
     */
    public void reset() {
        for (final FeedforwardLayer layer : this.layers) {
            layer.reset();
        }
    }
}
