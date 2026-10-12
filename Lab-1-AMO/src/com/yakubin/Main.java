package com.yakubin;


import com.yakubin.Controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource ("/main_scene.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Лабораторна робота 1 з АМО");
        stage.setResizable(false);
        stage.show();

        Controller controller = loader.getController();
        controller.setScene(scene);
    }
}
