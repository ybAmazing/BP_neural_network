/**
* @(#)InputNode.java      0.1 11/03/2017
* Copyright amazingyb
 */

import java.util.*;

/**
* Class {@code InputNode} is the basic class used as the node of input layer of neural network.
* @author   amazingyb
* @version  0.1, 11/03/2017
* @since    JDK1.8
*
*/
public class InputNode {
    /** The value is the collection of the weight of hide layer. */
    public ArrayList<Double> weight;
    /** The value is input value of the node. */
    public double inputValue;

    /**
     * Constructs a input node with the number of hide layer nodes.
     * @param   hideN   The number of hide layer nodes
     */
    public InputNode(int hideN){
        Random rand = new Random();
        weight = new ArrayList<>();
        for(int i = 0; i < hideN; i++)
            weight.add(rand.nextDouble());

        inputValue = 0;
    }

}
