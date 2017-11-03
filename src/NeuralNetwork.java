/**
 * @(#)NeuralNetwork.java   0.1 11/03/2017
 * Copyright    amazingyb
 */

import java.util.*;

/**
 * Class {@code NeuralNetwork} consist of the component of nueral network such as input layer, hide layer and ouput layer
 * @author amazingyb
 * @version 0.1 11/03/2017
 * @since JDK1.8
 */
public class NeuralNetwork {
    /** The input layer of neural network which composed of a array of input node. */
    public ArrayList<InputNode> inputLayer;
    /** The hide layer of neural network which composed of a array of hide node. */
    public ArrayList<HideNode> hideLayer;
    /** The output layer of neural network which composed of a array of output node. */
    public ArrayList<OutputNode> outputLayer;

    /** The offsets of the hide layer nodes */
    public double a[];
    /** The offsets of the output layer nodes */
    public double b[];
    /** The learning rate of neural network */
    public double mu;


    /**
     * Constructs neural network with the number of input layer, hide layer and output layer.
     * @param inputNodeN    the number of input layer nodes
     * @param hideNodeN     the number of hide layer nodes
     * @param outputNodeN   the number of output layer nodes
     * @param learnRate     the learning rate of the neural network
     */
    public NeuralNetwork(int inputNodeN, int hideNodeN, int outputNodeN, double learnRate){
        outputLayer = new ArrayList<>();
        for(int i = 0; i < outputNodeN; i++)
            outputLayer.add(new OutputNode());


        hideLayer = new ArrayList<>();
        for(int i = 0; i < hideNodeN; i++)
            hideLayer.add(new HideNode(outputNodeN));

        inputLayer = new ArrayList<>();
        for(int i = 0; i < inputNodeN; i++)
            inputLayer.add(new InputNode(hideNodeN));

        a = new double[hideNodeN];
        Random rand = new Random();
        for(int j = 0; j < a.length; j++)
            a[j] = rand.nextDouble();

        b = new double[outputNodeN];
        for(int k = 0; k < b.length; k++)
            b[k] = rand.nextDouble();

        mu = learnRate;
    }

    /**
     * Assign input value to the node of input layer of neural network.
     * @param arr   the input value vector
     */
    public void assginInput(double arr[]){
        for(int i = 0; i < arr.length; i++)
            inputLayer.get(i).inputValue = arr[i];
    }

    /**
     * Use the input value assigned to the input layer to compute the value of hide node and output node.
     */
    public void propagate(){
        for(int i = 0; i < hideLayer.size(); i++){
            double sum = 0;
            for(int j = 0; j < inputLayer.size(); j++){
                sum += inputLayer.get(j).weight.get(i) * inputLayer.get(j).inputValue;
            }
            sum += a[i];
            hideLayer.get(i).output = hideLayer.get(i).sigmoid(sum);
        }

        for(int i = 0; i < outputLayer.size(); i++){
            double sum = 0;
            for(int j = 0; j < hideLayer.size(); j++){
                sum += hideLayer.get(j).output * hideLayer.get(j).weight.get(i);
            }
            sum += b[i];
            outputLayer.get(i).output = sum;
        }
    }

    /**
     * Compute the difference between the real value and the output value.
     * @param arr   the real value vector
     */
    public void getError(double arr[]){
        for(int i = 0; i < outputLayer.size(); i++){
            outputLayer.get(i).err = arr[i] - outputLayer.get(i).output;
        }
    }

    /**
     * Adjust the weight and offset of neural network according to the err of output node.
     */
    public void feedback(){
        // update hidelayer output weight
        for(int j = 0; j < hideLayer.size(); j++){
            hideLayer.get(j).auxiliary = 0;
            for(int k = 0; k < outputLayer.size(); k++){
                hideLayer.get(j).auxiliary += hideLayer.get(j).weight.get(k) * outputLayer.get(k).err;
                hideLayer.get(j).weight.set(k, hideLayer.get(j).weight.get(k) + mu * hideLayer.get(j).output *
                                                    outputLayer.get(k).err);
            }
        }

        // update inputlayer output weight
        for(int i = 0; i < inputLayer.size(); i++){
            for(int j = 0; j < hideLayer.size(); j++){
                inputLayer.get(i).weight.set(j, inputLayer.get(i).weight.get(j) + mu * hideLayer.get(j).output *
                        (1-hideLayer.get(j).output) * inputLayer.get(i).inputValue * hideLayer.get(j).auxiliary);
            }
        }

        // update offset
        for(int k = 0; k < b.length; k++){
            b[k] = b[k] + mu * outputLayer.get(k).err;
        }

        for(int j = 0; j < a.length; j++){
            a[j] = a[j] + mu * hideLayer.get(j).output * (1-hideLayer.get(j).output) * hideLayer.get(j).auxiliary;
        }
    }

    /**
     * Use the train data to train the neural network, but only one record once.
     * @param input     the input value of the record of train data
     * @param output    the real output value of the record of train data
     */
    public void trainOnce(double input[], double output[]){
        if(input == null || input.length != inputLayer.size()){
            System.out.println("the size of input is not equal to the size of inputLayer");
            return;
        }

        if(output == null || output.length != outputLayer.size()){
            System.out.println("the size of output is not equal to the size of outputLayer");
            return;
        }

        assginInput(input);
        propagate();
        getError(output);
        feedback();
    }

    /**
     * Use the test data to test the neural network, but only one record once.
     * @param input the input value of the record of test data
     * @return      the output value array of the neural network using the {@code input} value
     */
    public double[] testOnce(double input[]){
        if(input == null || input.length != inputLayer.size()){
            System.out.println("the size of input is not equal to the size of inputLayer");
            return null;
        }

        assginInput(input);
        propagate();

        double[] output = new double[outputLayer.size()];
        for(int i = 0; i < output.length; i++)
            output[i] = outputLayer.get(i).output;

        return output;
    }
}
