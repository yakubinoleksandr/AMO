package com.yakubin.Controllers;

import com.yakubin.SceneAware;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ReturnController implements SceneAware {

    private Scene scene;

    @FXML
    private Button butReturn;

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    protected void initReturnButton(){
        butReturn.setOnAction(actionEvent -> {goBack();});
    }


    private void goBack() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_scene.fxml"));
            Parent newRoot = loader.load();

            SceneAware controller = loader.getController();
            controller.setScene(scene);
            scene.setRoot(newRoot);
            Stage stage = (Stage) scene.getWindow();
            stage.sizeToScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
