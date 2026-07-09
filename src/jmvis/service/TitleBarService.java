/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.service;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author JM-Tecnologias
 */
public class TitleBarService {
    private final AnchorPane anchorTitleBar;
    private double x = 0;
    private double y = 0;
    
    public TitleBarService(AnchorPane anchorbar){
        this.anchorTitleBar = anchorbar;
        moveWindows();
    }

    private void moveWindows() {
        //        Funcoes paca controlar Drag da tela
        anchorTitleBar.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        anchorTitleBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) anchorTitleBar.getScene().getWindow();

            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            stage.setOpacity(0.5);
        });

        anchorTitleBar.setOnMouseReleased(event -> {
            Stage stage = (Stage) anchorTitleBar.getScene().getWindow();
            stage.setOpacity(1);
        });
    }

}
