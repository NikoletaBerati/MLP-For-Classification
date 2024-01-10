import java.util.ArrayList;

public class Layer {
	private ArrayList<Neuron> neurons = new ArrayList<Neuron>();				
	private int numberOfInputs;			
	private String function;			
	private double learningRate;	
	private int numberOfNeurons; 

	public Layer(int numberOfInputs, String function, double learningRate, int numberOfNeurons){
		this.numberOfInputs = numberOfInputs;
		this.function = function;
		this.learningRate = learningRate;
		this.numberOfNeurons = numberOfNeurons;
		initializeNeurons();
	}

	public void initializeNeurons(){
		for(int i=0; i<numberOfNeurons; i++){
			Neuron newNeuron = new Neuron(numberOfInputs, function, learningRate);
			neurons.add(newNeuron);
		}
	}

	public void layerForwardPass(Layer previousLayer){
		for(Neuron neuron : neurons){
			neuron.setInputVector(1.0, 0);
			for(int i=0; i<numberOfInputs; i++){
				neuron.setInputVector(previousLayer.neurons.get(i).getOutput(), i+1);
			}
			neuron.calculateOutput();
		}		
	}

	public void layerBackPropagation(Layer nextLayer, double target[]){
		if (nextLayer==null){
			for(int i=0; i<numberOfNeurons; i++){
				neurons.get(i).setDelta(neurons.get(i).computeDelta(target[i]));
			}
		}else{
			for(int i=0; i<numberOfNeurons; i++){
				for(Neuron neuron : nextLayer.neurons){
					neurons.get(i).setDelta(neurons.get(i).getDelta() + (neuron.getWeights()[i + 1] * neuron.getDelta()));
				}
				neurons.get(i).setDelta(neurons.get(i).getDelta() * neurons.get(i).computeDelta(-1.0));
			}
		}
	}

	public void updateDerivativeWeights(){
		for(Neuron neuron : neurons){
			neuron.updateDerWeights();
		}
	}

	public void updateLayerWeigths(){
		for(Neuron neuron : neurons){
			neuron.updateNeuronWeigths();
		}
	}

	public void clearNeuronsDeltas(){
		for(Neuron neuron : neurons){
			neuron.clearDerivativesAndDeltas();
		}
	}

	public ArrayList<Neuron> getNeurons(){
		return neurons;
	}
}