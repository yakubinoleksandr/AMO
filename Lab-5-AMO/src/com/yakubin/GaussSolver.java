package com.yakubin;

public class GaussSolver {
    public static double[] solve(double[][] matrix, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {
            // Пошук головного елемента (для стабільності)
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(matrix[i][p]) > Math.abs(matrix[max][p])) {
                    max = i;
                }
            }

            // Перестановка рядків
            double[] temp = matrix[p]; matrix[p] = matrix[max]; matrix[max] = temp;
            double t = b[p]; b[p] = b[max]; b[max] = t;

            // Перетворення до одиничного коефіцієнта
            double factor = matrix[p][p];
            for (int j = p; j < n; j++) matrix[p][j] /= factor;
            b[p] /= factor;

            // Елімінація
            for (int i = 0; i < n; i++) {
                if (i != p) {
                    double alpha = matrix[i][p];
                    b[i] -= alpha * b[p];
                    for (int j = p; j < n; j++) {
                        matrix[i][j] -= alpha * matrix[p][j];
                    }
                }
            }
        }
        return b;
    }
}