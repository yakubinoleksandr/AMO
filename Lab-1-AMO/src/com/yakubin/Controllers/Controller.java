package com.yakubin.Controllers;

import com.yakubin.SceneAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller implements SceneAware {

    @FXML
    private Button butLine, butBranch, butCycle;

    private final String[] list_scene = {"0", "/line_scene.fxml", "/branch_scene.fxml", "/cycle_scene.fxml"};
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    private void switchScene(String scene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scene));
            Parent newRoot = loader.load();
            Stage stage = (Stage) this.scene.getWindow();

            SceneAware controller = loader.getController();
            controller.setScene(this.scene);
            this.scene.setRoot(newRoot);
            stage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        butLine.setOnAction(actionEvent -> {switchScene(list_scene[1]);});
        butBranch.setOnAction(actionEvent -> {switchScene(list_scene[2]);});
        butCycle.setOnAction(actionEvent -> {switchScene(list_scene[3]);});
    }

}
