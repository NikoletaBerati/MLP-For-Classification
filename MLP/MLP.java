import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;
import java.io.*;  

public class MLP{
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private ArrayList<Point> trainSet;
	private ArrayList<Point> testSet;

	private int d = 2; 
	private	int K = 4; 

	private int h1 = 21; 
	private	int h2 = 18; 
	private	int h3 = 19; 
	
	private int size = 4000;
	private String hiddenLayersFunction = "tanh";
	private String outputLayerFunction = "sigmoid";
	private double learningRate = 0.005;
    private double terminateThreshold = 0.01;

	private int numBatches = 400;

	private ArrayList<Double> errors = new ArrayList<Double>();

	private FileWriter fileWriter = null;
	 

	public MLP(){
		trainSet = new ArrayList<Point>();
		testSet = new ArrayList<Point>();

		Layer inputLayer = new Layer(d,hiddenLayersFunction,learningRate,d); 
		Layer H1 = new Layer(d,hiddenLayersFunction,learningRate,h1);
		Layer H2 = new Layer(h1,hiddenLayersFunction,learningRate,h2);
		Layer H3 = new Layer(h2,hiddenLayersFunction,learningRate,h3);
		Layer outputLayer = new Layer(h3,outputLayerFunction,learningRate,K);
		layers.add(inputLayer);
		layers.add(H1);
		layers.add(H2);
		layers.add(H3);
		layers.add(outputLayer);
	}

	public void loadFile(String filename, boolean isTraining){
		Scanner reader = null;
		try{
			reader = new Scanner(new FileInputStream(filename));
		} 
		catch(FileNotFoundException e){
			System.exit(0);
		}

		while (reader.hasNextLine()){
			String line = reader.nextLine();
			String points[] = line.split(",");
			Point point = new Point(Double.parseDouble(points[0]),Double.parseDouble(points[1]),Integer.parseInt(points[2]));
			if(isTraining){
				trainSet.add(point);
			}else{
				testSet.add(point);
			}
		}
		reader.close();
	}


	public void train(){
		double inputVector[] = new double[d];
		double target[] = new double[K];
		double totalError = 0.0;
		double errorDifference = 1;
		double previousError = Double.MAX_VALUE;

		int epoch = 0;

		while (epoch < 700 || errorDifference > terminateThreshold){
			int counter = 0;
			totalError = 0.0;

			clearDeltas();

			while (counter < size){
				for(int i = 0; i < numBatches; i++){
			
					inputVector[0] = trainSet.get(counter).getX1();
					inputVector[1] = trainSet.get(counter).getX2();

					target = setTarget(trainSet.get(counter).getCategory()); 
					
					forwardPass(inputVector);

					backPropagation(target);

					totalError += calculateTotalError(target);
					counter++;
				}
				updateWeigths();
			}

			System.out.println("Epoch: "+epoch+" -> Error: "+totalError);
			
			errorDifference = Math.abs(previousError - totalError);
			previousError = totalError;

			errors.add(totalError);

			epoch ++;
		}
		createFileWithTotalError(errors);
	
	}

	public void clearDeltas(){
		for(int i=1; i<layers.size(); i++){
			layers.get(i).clearNeuronsDeltas();
		}
	}

	public double[] setTarget(int i){
		double[] target = new double[K];
		target[i-1] = 1.0;
		return target;
	}

	public void forwardPass(double inputVector[]){
		int position = 0;
		for(Neuron neuron: layers.get(0).getNeurons()){
			neuron.setOutput(inputVector[position]);
			position++;
		}
		for(int i=1; i<layers.size(); i++){
			layers.get(i).layerForwardPass(layers.get(i-1));
		}
	}

	public void backPropagation(double target[]){
		layers.get(4).layerBackPropagation(null, target);
		for(int i=layers.size()-2; i>0; i--){
			layers.get(i).layerBackPropagation(layers.get(i+1), null);
		}
		for(int i=1; i<layers.size(); i++){
			layers.get(i).updateDerivativeWeights();
		}
	}

	public double calculateTotalError(double target[]){
		double error = 0.0;
		double output = 0.0;
		for(int i=0; i<layers.get(4).getNeurons().size(); i++){
			output = layers.get(4).getNeurons().get(i).getOutput();
			error += 0.5 * Math.pow(output - target[i],2);
		}
		return error;
	}

	public void updateWeigths(){
		for(int i=1; i<layers.size(); i++){
			layers.get(i).updateLayerWeigths();
		}
		clearDeltas();

	}

	public void evaluateNetwork(){
		double inputVector[] = new double[d];
		int corrects = 0;

		createOutputFile();
		
		double target[] = new double[K];

		for(Point point : testSet){  
			double[] output = new double[K];  
			inputVector[0] = point.getX1();
			inputVector[1] = point.getX2();

			forwardPass(inputVector);

			for(int j=0; j<layers.get(4).getNeurons().size(); j++){
				output[j] = layers.get(4).getNeurons().get(j).getOutput();
			}

			target[point.getCategory()-1] = 1;
			int positionWithMaxValue = findMaxPosition(output);

			if(target[positionWithMaxValue]==1){
				corrects++;
			}
			
			writeInOutputFile(inputVector[0], inputVector[1], positionWithMaxValue+1);
			
			target[point.getCategory()-1] = 0;

		}
		System.out.println("Correct evaluations : " + corrects + " from " +size + " examples");
		System.out.println("Correct percentage : " + (corrects/ (double) size)*100 + "%");
	}


	public int findMaxPosition(double array[]){
		int maxPosition = 0; 
        for(int i=1; i<array.length; i++) {
            if(array[i] > array[maxPosition]) {
                maxPosition = i;
            }
        }
		return maxPosition; 
	}

	public void createFileWithTotalError(ArrayList<Double> errors){
        FileWriter fileWriter = null;
		int epochCounter = 0;
        try {
           fileWriter = new FileWriter("TotalError.csv");
        } catch (IOException e) {
            System.exit(0);
        }

        PrintWriter outputWriter = new PrintWriter(fileWriter);
        outputWriter.flush();
        for (int i = 0; i < errors.size(); i++) {
            outputWriter.println(epochCounter+","+errors.get(i));
            outputWriter.flush();
			epochCounter++;
        }
    }
	public void createOutputFile(){
		try {
			fileWriter = new FileWriter("Output.csv");
		} catch (IOException e) {
			System.exit(0);
		}
	}

	public void writeInOutputFile(double x1, double x2, int evaluatedCategory){
        PrintWriter outputWriter = new PrintWriter(fileWriter);
        outputWriter.flush();
        outputWriter.println(x1+","+x2+","+evaluatedCategory);
        outputWriter.flush();
    }

	public static void main(String[] args) {
		MLP neuralNetwork = new MLP();
		neuralNetwork.loadFile("training_set.csv", true);
		neuralNetwork.train();
		neuralNetwork.loadFile("test_set.csv", false);
		neuralNetwork.evaluateNetwork();

	}
}