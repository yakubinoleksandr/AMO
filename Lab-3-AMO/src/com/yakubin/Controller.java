package com.yakubin;

import com.yakubin.function.Lagrange;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.function.DoubleUnaryOperator;

public class Controller {

    @FXML private TextField txtA, txtB, txtK, txtN, txtX0;
    @FXML private Label lblResult;

    private final DoubleUnaryOperator myFunc = x -> Math.pow(2, x) - Math.log(x);

    @FXML
    private void handleCalculate() {
        try {
            double x0 = Double.parseDouble(txtX0.getText());
            int a = Integer.parseInt(txtA.getText());
            int b = Integer.parseInt(txtB.getText());
            int k = Integer.parseInt(txtK.getText());
            int n = Integer.parseInt(txtN.getText());

            double[] x = Lagrange.argument(a, b, k, n);
            double[] y = Lagrange.function(x, myFunc);
            double res = Lagrange.interpolation(x0, x, y);

            lblResult.setText(String.format("P_n(x0) = %.8f", res));
        } catch (Exception e) {
            lblResult.setText("Помилка обчислення!");
        }
    }

    @FXML
    private void handleShowGraphs() {
        double x0 = Double.parseDouble(txtX0.getText());
        int a = Integer.parseInt(txtA.getText());
        int b = Integer.parseInt(txtB.getText());
        int k = Integer.parseInt(txtK.getText());
        int n = Integer.parseInt(txtN.getText());

        // 1. Графік Sin(x)
        showAnalysisGraph("Аналіз Sin(x)", a, b, k, n, Math::sin, "Sin(x)");

        // 2. Графік вашої функції
        showAnalysisGraph("Аналіз 2^x - ln(x)", a, b, k, n, myFunc, "2^x - ln(x)");

        // 3. Графік похибок
        showErrorGraph("Оцінка похибки та розмитості", x0, a, b, k, n, myFunc);
    }

    private void showAnalysisGraph(String title, int a, int b, int k, int n, DoubleUnaryOperator func, String name) {
        Stage stage = new Stage();
        NumberAxis xAxis = new NumberAxis(); xAxis.setLabel("x");
        NumberAxis yAxis = new NumberAxis(); yAxis.setLabel("f(x)");
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(title);
        chart.setCreateSymbols(true); // Дозволяємо символи (вимкнемо зайві в CSS)

        XYChart.Series<Number, Number> exact = new XYChart.Series<>(); exact.setName("Точна");
        XYChart.Series<Number, Number> interp = new XYChart.Series<>(); interp.setName("Лагранж");
        XYChart.Series<Number, Number> nodes = new XYChart.Series<>(); nodes.setName("Вузли");

        double[] xNodes = Lagrange.argument(a, b, k, n);
        double[] yNodes = Lagrange.function(xNodes, func);

        for (double x = a; x <= b; x += 0.05) {
            exact.getData().add(new XYChart.Data<>(x, func.applyAsDouble(x)));
            interp.getData().add(new XYChart.Data<>(x, Lagrange.interpolation(x, xNodes, yNodes)));
        }
        for (int i = 0; i < xNodes.length; i++) {
            nodes.getData().add(new XYChart.Data<>(xNodes[i], yNodes[i]));
        }

        chart.getData().addAll(exact, interp, nodes);
        display(stage, chart);
    }

    private void showErrorGraph(String title, double x0, int a, int b, int k, int n, DoubleUnaryOperator func) {
        Stage stage = new Stage();
        LineChart<Number, Number> chart = new LineChart<>(new NumberAxis(), new NumberAxis());
        chart.setTitle(title);
        chart.setCreateSymbols(true);

        XYChart.Series<Number, Number> s1 = new XYChart.Series<>(); s1.setName("Δn");
        double[] d = Lagrange.getEstimateDeltaN(x0, a, b, k, n, func);
        for (int i = 0; i < d.length; i++) s1.getData().add(new XYChart.Data<>(i, d[i]));

        XYChart.Series<Number, Number> s2 = new XYChart.Series<>(); s2.setName("k_Δ");
        double[] kv = Lagrange.getCoefficient(x0, a, b, k, n, func);
        for (int i = 0; i < kv.length; i++) s2.getData().add(new XYChart.Data<>(i, kv[i]));

        chart.getData().addAll(s1, s2);
        display(stage, chart);
    }

    private void display(Stage s, LineChart<Number, Number> c) {
        Scene scene = new Scene(c, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        s.setScene(scene); s.show();
    }
}