/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.service;

import javafx.event.ActionEvent;
import javafx.stage.Modality;
import jmvis.Main;
import jmvis.util.LoadAndMoveUtilities;

/**
 * Centralizes small modal interactions used by the JavaFX controllers.
 *
 * @author JM-Tecnologias
 */
public class DialogService {

    private static final String CONFIRM_VIEW = "/jmvis/view/ConfirForm.fxml";
    private static final String INPUT_VIEW = "/jmvis/view/InputText.fxml";

    public void showMessage(String message) {
        Main.setData(message);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, CONFIRM_VIEW);
    }

    public void showMessage(ActionEvent event, String message) {
        Main.setData(message);
        LoadAndMoveUtilities.showAsPopUP(event, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, CONFIRM_VIEW);
    }

    public String requestText(ActionEvent event, String prompt) {
        Main.setData(prompt);
        LoadAndMoveUtilities.showAsPopUP(event, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, INPUT_VIEW);

        Object data = Main.getData();
        return data == null ? null : data.toString();
    }
}
