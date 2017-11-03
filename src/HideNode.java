/**
* @(#)InputNode.java      0.1 11/03/2017
* Copyright amazingyb
 */

import java.util.*;

/**
* Class {@code HideNode} is the basic class used as the node of hide layer of neural network
* @author   amazingyb
* @version  0.1, 11/03/2017
* @since    JDK1.8
*
*/
public class HideNode {
    /** The value is the collection of the weight of hide layer. */
    public ArrayList<Double> weight;
    /** The value is helpful for feedback process to reduce computation. */
    public double auxiliary;
    /** The value is the output value of this hide layer node. */
    public double output;

    /**
     * Constructs a hide node with the number of output layer nodes.
     * @param outputN   the number of output layer nodes
     */
    public HideNode(int outputN){
        Random rand = new Random();
        weight = new ArrayList<>();
        for(int i = 0; i < outputN; i++)
            weight.add(rand.nextDouble());

        auxiliary = 0;
        output = 0;
    }

    /**
     * The implementation of sigmoid function.
     * @param x the input of value of sigmoid function
     * @return  1/(1 + exp(-x))
     */
    public double sigmoid(double x){
        return 1.0 / (1 + Math.exp(-x));
    }

}
