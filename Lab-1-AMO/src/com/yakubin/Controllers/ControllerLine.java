package com.yakubin.Controllers;

import com.yakubin.JsonLoaderUtil;
import com.yakubin.algorithm.LineAlgorithm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerLine extends ReturnController{

    @FXML
    private Button butResult, butLoadJSON;

    @FXML
    private TextField Z, X, Y, G, H;

    @FXML
    private Label labelResult;

    @FXML
    private void initialize() {

        initReturnButton();

        butResult.setOnAction(actionEvent -> {
            if (isEmpty(Z, X, Y, G, H)) {
                labelResult.setText("Заповніть всі поля");
                return;
            }
            labelResult.setText(String.valueOf(new LineAlgorithm(d(Z), d(X), d(Y), d(G), d(H)).calculate()));
            System.out.println(new LineAlgorithm(d(Z), d(X), d(Y), d(G), d(H)).calculate());
        });

        butLoadJSON.setOnAction(actionEvent -> {
            JsonLoaderUtil.loadIntoFields(labelResult, Z,X,Y,G,H);
        });
    }

}
