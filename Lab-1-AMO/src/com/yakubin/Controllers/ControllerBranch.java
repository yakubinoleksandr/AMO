package com.yakubin.Controllers;

import com.yakubin.JsonLoaderUtil;
import com.yakubin.algorithm.BranchingAlgorithm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerBranch extends ReturnController {

    @FXML
    private TextField G, S, B, Y, T, V;

    @FXML
    private Button butResult, butLoadJSON;

    @FXML
    private Label labelResult;

    @FXML
    private void initialize() {

        initReturnButton();

        butResult.setOnAction(actionEvent -> {
            if (isEmpty(G, S, B, Y, T, V)) {
                labelResult.setText("Заповніть всі поля");
                return;
            }
            labelResult.setText(String.valueOf(new BranchingAlgorithm(d(G), d(S), d(B), d(Y), d(T), d(V)).calculate()));
            System.out.println(new BranchingAlgorithm(d(G), d(S), d(B), d(Y), d(T), d(V)).calculate());
        });

        butLoadJSON.setOnAction(actionEvent -> {
            JsonLoaderUtil.loadIntoFields(labelResult, G, S, B, Y, T, V);
        });
    }

}
