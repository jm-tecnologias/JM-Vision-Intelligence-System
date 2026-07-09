/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jmvis.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jmvis.Main;
import jmvis.util.LoadAndMoveUtilities;
import jmvis.service.TitleBarService;

/**
 * FXML Controller class
 *
 * @author JM-Tecnologias
 */
public class ConfirFormController implements Initializable {

    private TitleBarService titlebarService;

    @FXML
    private AnchorPane anchorTitleBar;

    @FXML
    private Label labTextConfirmation;

    @FXML
    public void close(ActionEvent event) {
        if (LoadAndMoveUtilities.returnToStage()) {
//        if (true) {
            LoadAndMoveUtilities.setEstadoStage(false);
            LoadAndMoveUtilities.showStage(null, null);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } else if (LoadAndMoveUtilities.returnToBaseAnchor()) {
            LoadAndMoveUtilities.setEstadoPopUP(false);
            LoadAndMoveUtilities.showAsPopUP(null, null);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }else{
            LoadAndMoveUtilities.setEstadoPopUP(false);
            LoadAndMoveUtilities.showAsPopUP(null, null);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titlebarService = new TitleBarService(anchorTitleBar);
        labTextConfirmation.setText(Main.getData().toString());
    }

}
