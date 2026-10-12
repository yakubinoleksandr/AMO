package com.yakubin.function;

import java.util.function.DoubleUnaryOperator;

public final class Lagrange {

    // Формула вузлів з методички: xi = a + h*i, де h = (b-a)/10
    // Додаємо k як зміщення початкового індексу для гнучкості
    public static double[] argument(int a, int b, int k, int n) {
        double[] x = new double[n + 1];
        double h = (double) (b - a) / 10.0;
        for (int i = 0; i <= n; i++) {
            x[i] = a + h * (k + i);
        }
        return x;
    }

    public static double[] function(double[] x, DoubleUnaryOperator function) {
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = function.applyAsDouble(x[i]);
        }
        return y;
    }

    public static double interpolation(double x0, double[] x, double[] y) {
        double result = 0;
        int m = x.length;
        for (int i = 0; i < m; i++) {
            double basis = 1;
            for (int j = 0; j < m; j++) {
                if (i != j) {
                    basis *= (x0 - x[j]) / (x[i] - x[j]);
                }
            }
            result += y[i] * basis;
        }
        return result;
    }

    public static double[] getEstimateDeltaN(double x0, int a, int b, int k, int n, DoubleUnaryOperator function) {
        double[] res = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            double[] xCurr = argument(a, b, k, i);
            double[] yCurr = function(xCurr, function);
            double pCurr = interpolation(x0, xCurr, yCurr);

            double[] xNext = argument(a, b, k, i + 1);
            double[] yNext = function(xNext, function);
            double pNext = interpolation(x0, xNext, yNext);

            res[i] = Math.abs(pCurr - pNext);
        }
        return res;
    }

    public static double[] getCoefficient(double x0, int a, int b, int k, int n, DoubleUnaryOperator function) {
        double[] res = new double[n + 1];
        double[] deltaEst = getEstimateDeltaN(x0, a, b, k, n, function);
        double fExact = function.applyAsDouble(x0);

        for (int i = 0; i <= n; i++) {
            double[] x = argument(a, b, k, i);
            double[] y = function(x, function);
            double pI = interpolation(x0, x, y);
            double deltaExact = pI - fExact;

            if (Math.abs(deltaEst[i]) > 1e-16) {
                res[i] = 1 - (deltaExact / deltaEst[i]);
            } else {
                res[i] = 0;
            }
        }
        return res;
    }
}