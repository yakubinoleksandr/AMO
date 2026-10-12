package com.yakubin;

import javafx.scene.Scene;
import javafx.scene.control.TextField;

public interface SceneAware {

    void setScene(Scene scene);
    default double d(TextField tf) {
        return Double.parseDouble(tf.getText().trim());
    }
    default boolean isEmpty(TextField... fields) {
        for (TextField tf: fields) {
            if (tf.getText() == null || tf.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
