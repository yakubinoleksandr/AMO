package com.yakubin.controllers;

import com.yakubin.SceneAware;
import com.yakubin.Sort;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphicController implements SceneAware {

    private Scene scene;

    @FXML
    private Pane pane;

    @FXML
    private Button main;

    public void drawComplexityGraph(Pane chartPane) {

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setTickUnit(1000);
        xAxis.setUpperBound(10000);
        xAxis.setLabel("Кількість елементів (n)");
        yAxis.setLabel("Операції/Час(масштабовано)");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Аналіз Heap Sort: Операції vs Час\nВибірка: n=1000..10000 (крок 1000)");
        lineChart.setCreateSymbols(false);

        XYChart.Series<Number, Number> stepSeries = new XYChart.Series<>();
        stepSeries.setName("Кроки алгоритму");

        XYChart.Series<Number, Number> timeSeries = new XYChart.Series<>();
        timeSeries.setName("Час виконання (нс / 10)");

        XYChart.Series<Number, Number> avgSeries = new XYChart.Series<>();
        avgSeries.setName("Середній час (5 запусків)");

        for (int n = 0; n <= 10000; n += 1000) {
            int[] testArray = generateRandomArray(n);

            int steps = Sort.countSteps(testArray.clone());
            stepSeries.getData().add(new XYChart.Data<>(n, steps));

            int[] timeTestArray = testArray.clone();
            long startTime = System.nanoTime();
            Sort.heapSort(timeTestArray, timeTestArray.length);
            long duration = System.nanoTime() - startTime;
            timeSeries.getData().add(new XYChart.Data<>(n, duration / 10.0));

            long totalDuration = 0;
            int trials = 5;
            for (int i = 0; i < trials; i++) {
                int[] tempArray = generateRandomArray(n);
                long startTrial = System.nanoTime();
                Sort.heapSort(tempArray, tempArray.length);
                totalDuration += (System.nanoTime() - startTrial);
            }
            avgSeries.getData().add(new XYChart.Data<>(n, (totalDuration / (double) trials) / 10.0));
        }

        lineChart.getData().addAll(stepSeries, timeSeries, avgSeries);

        chartPane.getChildren().clear();
        chartPane.getChildren().add(lineChart);
    }

    private int[] generateRandomArray(int n) {
        int[] res = new int[n];
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < n; i++) {
            res[i] = rand.nextInt(1000);
        }
        return res;
    }

    @FXML
    private void initialize() {
        drawComplexityGraph(pane);
        main.setOnAction(actionEvent -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/main.fxml"));
                Parent newRoot = loader.load();

                SceneAware controller = loader.getController();
                controller.setScene(scene);
                scene.setRoot(newRoot);
                Stage stage = (Stage) scene.getWindow();
                stage.sizeToScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
