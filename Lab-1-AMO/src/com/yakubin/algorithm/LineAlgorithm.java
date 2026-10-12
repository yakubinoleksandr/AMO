package com.yakubin.algorithm;

import static java.lang.Math.pow;

public class LineAlgorithm {

    private double z, x, y, g, h;

    public LineAlgorithm(double z, double x, double y, double g, double h) {
        this.z = z;
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
    }

    public double calculate(){
        try {
            double result = 0;
            double t = g + h;
            result = pow((z + x), t) / pow((z + y), t / (g * h));
            return result;
        }
        catch (Exception e){
            return Double.NaN;
        }
    }
}
