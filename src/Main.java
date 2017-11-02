/**
 * @(#)Main.java    0.1 11/02/2017
 * Copyright    amazingyb
 */

import java.util.*;

/**
 * This is the Main class of this project used to test the neural network.
 * @author amazingyb
 * @version 0.1 11/02/2017
 * @since JDK1.8
 */
public class Main{

    static public void main(String[] args){
        // create a specified neural network
        NeuralNetwork a = new NeuralNetwork(2, 4, 2, 0.1);

        // train the neural network for many times
        for(int loop = 0; loop < 100000; loop++){
            Random rand = new Random();
            double arr[] = new double[2];
            arr[0] = rand.nextDouble() < 0.5 ? 0.0 : 1.0;
            arr[1] = rand.nextDouble() < 0.5 ? 0.0 : 1.0;

            double res[] = new double[2];
            // res[0] = arr[0] == arr[1] ? 1 : 0;
            // res[1] = 1 - res[0];
            res[0] = (arr[0] == 0 && arr[1] == 0) ? 1 : 0;
            res[1] = 1 - res[0];

            a.trainOnce(arr, res);
        }

        // test 10 times of the trained neural network
        for(int i = 0; i < 10; i++){
            double arr[] = new double[2];
            Random rand = new Random();
            arr[0] = rand.nextDouble() < 0.5 ? 0.0 : 1.0;
            arr[1] = rand.nextDouble() < 0.5 ? 0.0 : 1.0;

           double[] output = a.testOnce(arr);

            System.out.println("Case " + i + ":" + arr[0] + "  " + arr[1]);
            String result = "";
            for(int k = 0; k < output.length; k++){
                result += "    " + output[k];
            }
            System.out.println(result);
        }
    }
}
