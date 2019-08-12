package neural.backpropagation;


import neural.feedfwd.DeepNeuralNetwork;


public interface Train {

	/**
	 * Get the current error percent from the training.
	 * @return The current error.
	 */
	public double getError();

	/**
	 * Get the current best network from the training.
	 * @return The best network.
	 */
	public DeepNeuralNetwork getNetwork();

	
	/**
	 * Perform one iteration of training.
	 */
	public void iteration();
}
