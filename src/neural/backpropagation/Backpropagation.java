package neural.backpropagation;

import java.util.HashMap;
import java.util.Map;

import neural.feedfwd.DeepNeuralNetwork;
import neural.helper.NeuralNetworkError;
public class Backpropagation implements Train {
	/**
	 * The error from the last iteration.
	 */
	private double error;

	/**
	 * The learning rate. This is the degree to which the deltas will affect the
	 * current network.
	 */
	private final double learnRate;

	/**
	 * The momentum, this is the degree to which the previous training cycle
	 * affects the current one.
	 */
	private final double momentum;

	/**
	 * THe network that is being trained.
	 */
	private final DeepNeuralNetwork network;

	/**
	 * A map between neural network layers and the corresponding
	 * BackpropagationLayer.
	 */
	private final Map<FeedforwardLayer, BackpropagationLayer> layerMap = new HashMap<FeedforwardLayer, BackpropagationLayer>();

	/**
	 * Input patterns to train with.
	 */
	private final double input[][];

	/**
	 * The ideal output for each of the input patterns.
	 */
	private final double ideal[][];

	/**
	 * 
	 * @param network
	 * @param input
	 * @param ideal
	 * @param learnRate
	 *            The rate at which the weight matrix will be adjusted based on
	 *            learning.
	 * @param momentum
	 *            The influence that previous iteration's training deltas will
	 *            have on the current iteration.
	 */
	public Backpropagation(final DeepNeuralNetwork network,
			final double input[][], final double ideal[][],
			final double learnRate, final double momentum) {
		this.network = network;
		this.learnRate = learnRate;
		this.momentum = momentum;
		this.input = input;
		this.ideal = ideal;

		for (final FeedforwardLayer layer : network.getLayers()) {
			final BackpropagationLayer bpl = new BackpropagationLayer(this,
					layer);
			this.layerMap.put(layer, bpl);
		}
	}

	/**
	 * Calculate the error for the recognition just done.
	 * 
	 * @param ideal
	 *            What the output neurons should have yielded.
	 */
	public void calcError(final double ideal[]) {

		if (ideal.length != this.network.getOutputLayer().getNeuronCount()) {
			throw new NeuralNetworkError(
					"Size mismatch: Can't calcError for ideal input size="
							+ ideal.length + " for output layer size="
							+ this.network.getOutputLayer().getNeuronCount());
		}

		// clear out all previous error data
		for (final FeedforwardLayer layer : this.network.getLayers()) {
			getBackpropagationLayer(layer).clearError();
		}

		for (int i = this.network.getLayers().size() - 1; i >= 0; i--) {
			final FeedforwardLayer layer = this.network.getLayers().get(i);
			if (layer.isOutput()) {

				getBackpropagationLayer(layer).calcError(ideal);
			} else {
				getBackpropagationLayer(layer).calcError();
			}
		}
	}

	/**
	 * Get the BackpropagationLayer that corresponds to the specified layer.
	 * @param layer The specified layer.
	 * @return The BackpropagationLayer that corresponds to the specified layer.
	 */
	public BackpropagationLayer getBackpropagationLayer(
			final FeedforwardLayer layer) {
		final BackpropagationLayer result = this.layerMap.get(layer);

		if (result == null) {
			throw new NeuralNetworkError(
					"Layer unknown to backpropagation trainer, was a layer added after training begain?");
		}

		return result;
	}

	/**
	 * Returns the root mean square error for a complete training set.
	 * 
	 * @param len
	 *            The length of a complete training set.
	 * @return The current error for the neural network.
	 */
	public double getError() {
		return this.error;
	}

	/**
	 * Get the current best neural network.
	 * @return The current best neural network.
	 */
	public DeepNeuralNetwork getNetwork() {
		return this.network;
	}

	/**
	 * Perform one iteration of training.
	 */
	public void iteration() {

		for (int j = 0; j < this.input.length; j++) {
			this.network.computeOutputs(this.input[j]);
			calcError(this.ideal[j]);
		}
		learn();
		
		this.error = this.network.calculateError(this.input, this.ideal);
	}

	/**
	 * Modify the weight matrix and thresholds based on the last call to
	 * calcError.
	 */
	public void learn() {

		for (final FeedforwardLayer layer : this.network.getLayers()) {
			getBackpropagationLayer(layer).learn(this.learnRate, this.momentum);
		}

	}
}
