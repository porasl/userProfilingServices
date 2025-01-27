package com.porasl.userprofiling.service;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfilingService {
    private MultiLayerNetwork model;

    public UserProfilingService() {
        int inputSize = 5; // Number of input features
        int outputSize = 3; // Number of profile categories
        int hiddenLayerSize = 10;

        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                .seed(123)
                .updater(new Adam(0.01))
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(inputSize)
                        .nOut(hiddenLayerSize)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(hiddenLayerSize)
                        .nOut(outputSize)
                        .activation(Activation.SOFTMAX)
                        .build())
                .build();

        model = new MultiLayerNetwork(config);
        model.init();
    }

    public void train(double[][] features, int[] labels) {
        INDArray input = Nd4j.create(features);
        INDArray output = Nd4j.create(labels).reshape(-1, 1);

        model.fit(input, output);
    }

    public String predict(List<Double> features) {
        INDArray input = Nd4j.create(features.stream().mapToDouble(Double::doubleValue).toArray(), new long[]{1, features.size()});
        INDArray output = model.output(input);
        int maxIndex = Nd4j.argMax(output, 1).getInt(0);

        String[] profiles = {"Basic", "Intermediate", "Advanced"};
        return profiles[maxIndex];
    }
}
