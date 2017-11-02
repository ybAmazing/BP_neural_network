/**
 * @(#)NeuralNetwork.java   0.1 11/02/2017
 * Copyright    amazingyb
 */

import java.util.*;

/**
 * Class {@code NeuralNetwork} consist of the component of nueral network such as input layer, hide layer and ouput layer
 * @author amazingyb
 * @version 0.1 11/02/2017
 * @since JDK1.8
 */
public class NeuralNetwork {
    /** the input layer of neural network */
    public ArrayList<InputNode> inputLayer;
    /** the hide layer of neural network */
    public ArrayList<HideNode> hideLayer;
    /** the output layer of neural network */
    public ArrayList<OutputNode> outputLayer;

    /** the offset of the hide layer */
    public double a[];
    /** the offset of the output layer */
    public double b[];
    /** the learning rate of neural network */
    public double mu;


    /**
     * the constructor of neural network
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
     * assign input value to the node of input layer of neural network
     * @param arr   the input value vector
     */
    public void assginInput(double arr[]){
        for(int i = 0; i < arr.length; i++)
            inputLayer.get(i).inputValue = arr[i];
    }

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
     * compute the difference between the real value and the output value
     * @param arr   the real value vector
     */
    public void getError(double arr[]){
        for(int i = 0; i < outputLayer.size(); i++){
            outputLayer.get(i).err = arr[i] - outputLayer.get(i).output;
        }
    }

    /**
     * adjust the weight and offset of neural network according to the err of output node
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
     * use the train data to train the neural network, but only one record once
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
     * use the test data to test the neural network, but only one record once
     * @param input the input value of the record of test data
     * @return      the output value array of the neural network using the <code>input</code> value
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
