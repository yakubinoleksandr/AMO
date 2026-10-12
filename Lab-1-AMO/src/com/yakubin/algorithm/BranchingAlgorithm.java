package com.yakubin.algorithm;

import static java.lang.Math.sqrt;

public class BranchingAlgorithm {

    private double g, s, b, y, t, v;

    public BranchingAlgorithm(double g, double s, double b, double y, double t, double v) {
        this.g = g;
        this.s = s;
        this.b = b;
        this.y = y;
        this.t = t;
        this.v = v;
    }

    public double calculate() {
        double result = 0;
        if (v * b != 0) {
            result = sqrt(24 * g - s * b - (y * t) / (v * b));
        }
        else return Double.NaN;

        return result;
    }
}
