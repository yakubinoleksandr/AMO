package com.yakubin.Controllers;

import com.yakubin.JsonLoaderUtil;
import com.yakubin.algorithm.CycleAlgorithm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class ControllerCycle extends ReturnController {

    @FXML
    private Button butResult, butLoadJSON;

    @FXML
    private TextField N;

    @FXML
    private Label labelResult;

    @FXML
    private void initialize() {

        initReturnButton();

        butResult.setOnAction(actionEvent -> {
            String text = N.getText();

            if (text == null || text.trim().isEmpty()) {
                labelResult.setText("Заповніть поле 15 числами");
                return;
            }

            String[] parts = text.split(",");
            if (parts.length != 15) {
                labelResult.setText("Введіть 15 чисел");
                return;
            }

            double[] numbers = new double[15];
            for (int i = 0; i < 15; i++) {
                try {
                    numbers[i] = Double.parseDouble(parts[i].trim());
                }
                catch (NumberFormatException e) {
                    labelResult.setText("Некоректне значення");
                    return;
                }
            }

            labelResult.setText(String.valueOf(new CycleAlgorithm(numbers).calculate()));
            System.out.println(new CycleAlgorithm(numbers).calculate());

        });

        butLoadJSON.setOnAction(actionEvent -> {
            List<Double> numbers = JsonLoaderUtil.loadJsonAsList(N.getScene().getWindow(), "values");

            if (numbers.isEmpty()) {
                labelResult.setText("JSON пустий або некоректний");
                return;
            }

            String text = numbers.stream()
                    .map(String::valueOf)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            N.setText(text);

            labelResult.setText("JSON успішно завантажено");

        });
    }

}
