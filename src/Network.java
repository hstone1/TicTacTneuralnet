import com.google.common.collect.ImmutableList;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import org.nd4j.linalg.util.ArrayUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Network {
  private MultiLayerNetwork model;

  public Network(int numInputs, int numHidden, int numOutputs) {
    //number of rows and columns in the input pictures

    MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .miniBatch(false)
            .seed(123)
            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            .iterations(1)
            .activation(Activation.TANH)
            .weightInit(WeightInit.XAVIER)
            .learningRate(0.0015)
            .list()
            .layer(0, new DenseLayer.Builder()
                    .activation(Activation.TANH)
                    .nIn(numInputs)
                    .nOut(numHidden)
                    .build())
            .layer(1, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
                    .activation(Activation.IDENTITY)
                    .nIn(numHidden)
                    .nOut(numOutputs)
                    .build())
            .pretrain(false).backprop(true)
            .build();

    model = new MultiLayerNetwork(conf);
    model.init();
  }

  public void train(float[] input, float[] target) {
    float[] realInput = new float[9];
    for (int i = 0; i < 9; i++) {
      realInput[i] = (float) (input[i] + 0.00001);
    }
//
//    System.out.println("Trained once on:");
//    System.out.println(Arrays.toString(realInput));
//    System.out.println(Arrays.toString(target));
//    System.out.println("");

    model.fit(Nd4j.create(realInput), Nd4j.create(target));
  }

  public float[] activate(float[] input) {
    INDArray inputArr = Nd4j.create(input);
    List<INDArray> outputArray = model.feedForward(inputArr);
    float[] output = new float[9];
    for (int i = 0; i < 9; i++) {
      output[i] = outputArray.get(2).getFloat(i);
    }
//
//    System.out.println("");
//    System.out.println(Arrays.toString(input));
//    System.out.println(Arrays.toString(output));
//    System.out.println("");
    return output;
  }
}
