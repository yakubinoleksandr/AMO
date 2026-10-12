package com.yakubin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Controller {
    @FXML private GridPane matrixGrid;
    @FXML private TextArea resultArea;

    private TextField[][] inputs = new TextField[3][4];

    @FXML
    public void initialize() {
        double[][] defaultMatrix = {
                {1, 3, -5, 27},
                {-3, -12, 13, -82},
                {2, 10, -3, 38}
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                inputs[i][j] = new TextField(String.valueOf(defaultMatrix[i][j]));
                matrixGrid.add(inputs[i][j], j, i);
            }
        }
    }

    @FXML
    protected void handleSolve() {
        try {
            double[][] a = new double[3][3];
            double[] b = new double[3];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    a[i][j] = Double.parseDouble(inputs[i][j].getText());
                }
                b[i] = Double.parseDouble(inputs[i][3].getText());
            }

            double[] solution = GaussSolver.solve(a, b);

            resultArea.setText("Результати:\n" +
                    "x1 = " + Math.round(solution[0]) + "\n" +
                    "x2 = " + Math.round(solution[1]) + "\n" +
                    "x3 = " + Math.round(solution[2]));

        } catch (Exception e) {
            resultArea.setText("Помилка введення даних!");
        }
    }
}