package com.yakubin;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML private TextField epsField;
    @FXML private TextField x0Field;
    @FXML private TextArea resultArea;

    @FXML
    public void initialize() {
        drawGraph();
    }

    // Початкова функція f(x) = ln(x) + (x+1)^3
    private double f(double x) {
        return Math.log(x) + Math.pow(x + 1, 3);
    }

    // Функція ітерацій x = phi(x)
    // Виведено як: x = exp(-(x+1)^3)
    private double phi(double x) {
        return Math.exp(-Math.pow(x + 1, 3));
    }

    private void drawGraph() {
        lineChart.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("f(x) = ln(x) + (x+1)^3");

        // Малюємо графік на проміжку, де є корінь (приблизно 0.1 - 0.5)
        for (double x = 0.05; x <= 0.8; x += 0.02) {
            series.getData().add(new XYChart.Data<>(x, f(x)));
        }
        lineChart.getData().add(series);
    }

    @FXML
    private void handleCalculate() {
        try {
            double eps = Double.parseDouble(epsField.getText().replace(",", "."));
            double xPrev = Double.parseDouble(x0Field.getText().replace(",", "."));
            double xNext;
            int iter = 0;
            int maxIter = 1000;

            StringBuilder log = new StringBuilder();
            log.append("Пошук кореня методом ітерацій:\n");

            do {
                xNext = phi(xPrev);
                iter++;
                log.append(String.format("Іт. %d: x = %.6f\n", iter, xNext));

                if (Math.abs(xNext - xPrev) < eps) break;

                xPrev = xNext;
                if (iter >= maxIter) {
                    log.append("Досягнуто ліміт ітерацій!\n");
                    break;
                }
            } while (true);

            log.append("\nОстаточний результат:\n");
            log.append(String.format("x ≈ %.6f\n", xNext));
            log.append(String.format("f(x) = %.8f", f(xNext)));

            resultArea.setText(log.toString());

        } catch (NumberFormatException e) {
            resultArea.setText("Помилка: Некоректний формат чисел!");
        } catch (Exception e) {
            resultArea.setText("Помилка обчислень: " + e.getMessage());
        }
    }
}
