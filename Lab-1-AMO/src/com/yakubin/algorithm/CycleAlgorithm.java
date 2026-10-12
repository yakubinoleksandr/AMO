package com.yakubin.algorithm;

public class CycleAlgorithm {

    private final byte m = 15;
    private double[] n = new double[15];

    public CycleAlgorithm(double[] n) {
        this.n = n;
    }

    public double calculate() {
        double result = 1.0;

        for (int i = 0; i < m; i++) {
            if (n[i] != 0) {
                result *= 1 / n[i];
            }
            else return Double.NaN;
        }

        return result;
    }
}
