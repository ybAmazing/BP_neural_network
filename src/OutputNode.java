/**
 * @(#)OutputNode.java  0.1 11/03/2017
 * Copyright amazingyb
 */

/**
 * Class {@code OutputNode} is the basic class used as the node of output layer of neural network.
 * @author  amazingyb
 * @version 0.1 11/03/2017
 * @since   JDK1.8
 */
public class OutputNode {
    /** This value is the output value of output node. */
    public double output;
    /** This value is difference between the real value and the output value of output node. */
    public double err;

    /**
     * The default constructor of output node assign the init value of output and err to 0.
     */
    public OutputNode(){
        output = 0;
        err = 0;
    }
}
