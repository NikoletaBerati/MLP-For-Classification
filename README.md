
# MLP Neural Network for Classification

**MLP-For-Classification** is a Java-based implementation of a Multi-Layer Perceptron (MLP) neural network for classification tasks. This project demonstrates the fundamentals of neural networks, including forward propagation, backpropagation, and activation functions, implemented from scratch without external machine learning libraries.




## ℹ️ About this project
This project was developed as part of the *Computational Intelligence* course at University of Ioannina, in collaboration with a fellow student.

## ✨ Features
- Fully implemented **MLP from scratch**, no external ML libraries required.
- Supports multiple **activation functions**: Sigmoid, Tanh, ReLU.
- Customizable training parameters: learning rate, batch size, termination criteria.

  
## 🛠️ Project Components

- **MLP.java** – Main program. Initializes the network, trains it, and evaluates results.  
- **Layer.java** – Represents layers, handles forward and backward passes.  
- **Neuron.java** – Models individual neurons, including weight updates and error calculation.  
- **Point.java** – Represents input samples for training/testing in `[x1, x2, category]` format.  


## 📊 Datasets
  The repository includes the following data files for training and testing, which are generated through a helper Java class:
  - training_set.csv
  - test_set.csv


## 🔍 Visualization

To help analyze the MLP’s performance, the repository includes Python scripts that can:

- Plot decision boundaries
- Track error rate over epochs
- Compare predicted vs. actual labels


## 🚀 How to run
  - Compile and run:    
    **javac** *.java  
     **java**  MLP  


