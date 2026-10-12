package com.yakubin;

import com.yakubin.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/scene/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Лабораторна робота 2 з АМО");
        stage.show();

        MainController controller = loader.getController();
        controller.setScene(scene);
        Sort.heapSort(new int[]{6, 1, 3, 8}, 4);
    }
}
