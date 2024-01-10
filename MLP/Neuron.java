import java.util.Random;
import java.lang.Math;

public class Neuron {
	private double[] weights;		
    private double[] derWeights; 	
	private double[] inputVector; 
	private double u = 0.0;	
	private double output = 0.0;
	private double delta = 0.0;			
	private String function;
	private double learningRate; 
	private int inputsPlusBias;


	public Neuron(int d, String function, double learningRate){
		this.inputsPlusBias = d+1;
		this.weights = new double[inputsPlusBias];
		this.inputVector = new double[inputsPlusBias];
		this.derWeights = new double [inputsPlusBias];
		this.u = u;
        this.output = output;
        this.delta = delta;
		this.function = function;
		this.learningRate = learningRate;
        
		initializeWeights();	
	}

	private void initializeWeights(){
		Random r = new Random();
		weights[0] = 1.0;

		for(int i=1; i<inputsPlusBias; i++){
			weights[i] = r.nextDouble() * (2) - 1 ; 
		}
	}

	public void calculateOutput(){
        u=0;
		for(int i=0; i<inputsPlusBias; i++){
			u += weights[i] * inputVector[i];
		}
		output = activationFunction(function,u);
	}

	public double activationFunction(String function, double inputValue){
		if(function.equals("tanh")){
			return (double)Math.tanh(inputValue);
		}
		else if(function.equals("sigmoid")){
			return (double)(1/(1+Math.exp(-inputValue)));
		}
        else if(function.equals("relu")){
			return (double)Math.max(0, inputValue);
		}
		return 0;
	}

	public void updateDerWeights(){
		derWeights[0] += delta;
		for (int i=1; i<derWeights.length; i++){
			derWeights[i] += delta * inputVector[i];		
		}
	}

    public double calculateDerivatives(String function, double u){
		if(function.equals("tanh")){
            output = activationFunction(function, u);
			return (double)(1 - Math.pow(output,2));
		}else if(function.equals("relu")){
			if(u < 0) {
				return 0.0;
			}else {
				return 1.0;
			}
		}else if(function.equals("sigmoid")){
            output = activationFunction(function, u);
			return (output) * (1 - (output));
		}
        return -1.0;
	}	

	public void updateNeuronWeigths(){
		for (int i=0; i<weights.length; i++){
			weights[i] -= learningRate * derWeights[i];
		}
	}

	public double computeDelta(double target){
		double derivative = calculateDerivatives(function, u);
		double output = activationFunction(function, u);

		if(target != -1.0){
			return derivative * (output - target);
		}else{
			return derivative;
		}
	}

	public void clearDerivativesAndDeltas(){
		derWeights = new double[inputsPlusBias];
		delta = 0.0;
	}

	public double[] getWeights(){
		return weights;
	}

	public double getDelta(){
		return delta;
	}

	public void setDelta(double x){
		this.delta = x;
	}

	public double getOutput(){
		return output;
	}

	public void setOutput(double y){
		this.output = y;
	}

	public void setInputVector(double x, int position){
		this.inputVector[position] = x;
	}
}