package com.yakubin.controllers;

import com.google.gson.Gson;
import com.yakubin.SceneAware;
import com.yakubin.Sort;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainController implements SceneAware {
    private List<int[]> steps;
    private int currentIdx = 0;
    private String lastInput = "";

    @FXML
    private Pane drawTree;

    @FXML
    private Label result, time;

    @FXML
    private TextField inputArray;

    @FXML
    private Button drawStep, sorted, statistic, loadJsonFile;

    private Scene scene;

    @FXML
    private void initialize() {

        drawStep.setOnAction(actionEvent -> {
            String currentText = inputArray.getText();

            if (!currentText.equals(lastInput)) {
                steps = null;
                currentIdx = 0;
            }

            if (steps == null) {
                if (currentText.trim().isEmpty()) {
                    inputArray.setPromptText("Введіть числа!");
                    return;
                }

                try {
                    String[] parts = currentText.split(",");
                    int[] numbers = new int[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        numbers[i] = Integer.parseInt(parts[i].trim());
                    }

                    steps = Sort.getSteps(numbers);
                    currentIdx = 0;
                    lastInput = currentText;

                    Sort.draw(drawTree, steps.getFirst());
                    drawStep.setText("Наступний крок");
                } catch (Exception e) {
                    inputArray.setPromptText("Помилка (числа через кому)!");
                    steps = null;
                }
                return;
            }

            if (currentIdx < steps.size() - 1) {
                currentIdx++;
                Sort.draw(drawTree, steps.get(currentIdx));

                if (currentIdx == steps.size() - 1) {
                    drawStep.setText("Готово");
                    steps = null;
                }
            }
        });

        sorted.setOnAction(actionEvent -> {
            String text = inputArray.getText();

            if (text == null || text.trim().isEmpty()) {
                inputArray.setPromptText("Введіть числа!");
                return;
            }

            try {
                int[] numbers = Arrays.stream(text.split(","))
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .toArray();

                result.setText("Результат: "+Arrays.toString(Sort.heapSort(numbers, numbers.length)));
                time.setText("Час сортування: "+Sort.measureTime(numbers)+" ns");
            } catch (Exception e) {
                inputArray.clear();
                inputArray.setPromptText("Помилка формату!");
            }
        });

        statistic.setOnAction(actionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/graphic.fxml"));
                Parent newRoot = loader.load();
                Stage stage = (Stage) this.scene.getWindow();

                SceneAware controller = loader.getController();
                controller.setScene(this.scene);
                this.scene.setRoot(newRoot);
                stage.sizeToScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        loadJsonFile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Виберіть JSON файл");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("JSON Files", "*.json")
            );

            File selectedFile = fileChooser.showOpenDialog(scene.getWindow());

            if (selectedFile != null) {
                try (FileReader reader = new FileReader(selectedFile)) {
                    Gson gson = new Gson();
                    int[] data = gson.fromJson(reader, int[].class);

                    if (data != null) {
                        String formatted = Arrays.stream(data)
                                .mapToObj(String::valueOf)
                                .collect(Collectors.joining(", "));

                        inputArray.setText(formatted);
                        steps = null;
                        currentIdx = 0;
                        inputArray.setPromptText("");
                    }
                } catch (Exception e) {
                    inputArray.clear();
                    inputArray.setPromptText("Помилка JSON: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
