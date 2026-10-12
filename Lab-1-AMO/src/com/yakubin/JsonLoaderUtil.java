package com.yakubin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class JsonLoaderUtil {

    private JsonLoaderUtil() {}

    public static void loadIntoFields(Label statusLabel,
                                      TextField... fields) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON files", "*.json")
        );

        File file = chooser.showOpenDialog(fields[0].getScene().getWindow());
        if (file == null) return;

        try (FileReader reader = new FileReader(file)) {

            Gson gson = new Gson();
            Map<String, Double> data =
                    gson.fromJson(reader, Map.class);

            for (TextField tf : fields) {

                if (data.containsKey(tf.getId())) {
                    tf.setText(String.valueOf(data.get(tf.getId())));
                }
            }

            statusLabel.setText("JSON успішно завантажено.");

        } catch (IOException e) {
            statusLabel.setText("Помилка читання файлу.");
        } catch (Exception e) {
            statusLabel.setText("Некоректний JSON.");
        }
    }

    public static List<Double> loadJsonAsList(Window owner, String key) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON files", "*.json")
        );

        File file = chooser.showOpenDialog(owner);
        if (file == null) return new ArrayList<>();

        try (FileReader reader = new FileReader(file)) {

            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            Map<String, Object> data = gson.fromJson(reader, mapType);

            List<Double> result = new ArrayList<>();
            if (data.containsKey(key)) {
                Object value = data.get(key);

                if (value instanceof List<?> list) {
                    for (Object o : list) {
                        if (o instanceof Number n) {
                            result.add(n.doubleValue());
                        } else if (o instanceof String s) {
                            try {
                                result.add(Double.parseDouble(s));
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                } else if (value instanceof Number n) {
                    result.add(n.doubleValue());
                }
            }

            return result;

        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Некоректний JSON: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}
