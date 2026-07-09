/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jmvis.Main;

/**
 *
 * @author JM-Tecnologias
 */
public class LoadAndMoveUtilities {

    public static void loadFXML(Modality modal, String path) {
        try {
            Parent root = FXMLLoader.load(LoadAndMoveUtilities.class.getResource(path));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.initStyle(StageStyle.TRANSPARENT);
            /*
            Torna a tela nova como uma Modal(bloqueia a janela anterior ate que
            esta seja fechada)
             */
            if (modal != null) {
                stage.initModality(modal);
                stage.showAndWait();//Bloqueia ate que esta seja fechada
            } else {
                stage.show();
            }
        } catch (IOException ex) {
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/view/ConfirForm.fxml");
        }
    }
    /*
    Variavel que ira armazenar a AnchorPane antes de ser
    removida ou ocultada
     */
    private static AnchorPane baseAnchorPane;
    private static boolean estadoPopUP;

    /*
    Mostra nova tela, ocultando a anterior e bloqueando acesso a controles da tela
    anterior
     */
    public static void showAsPopUP(/*AnchorPane baseAnchorPane,*/ActionEvent aevent, MouseEvent mevent) {
        if (aevent != null) {
            Node source = (Node) aevent.getSource();
            LoadAndMoveUtilities.baseAnchorPane = (AnchorPane) source.getScene().lookup("#anchorImagePreview");
            BoxBlur blur = new BoxBlur(5, 5, 5);
            baseAnchorPane.setEffect(blur);
        }
        if (mevent != null) {
            Node source = (Node) mevent.getSource();
            LoadAndMoveUtilities.baseAnchorPane = (AnchorPane) source.getScene().lookup("#anchorImagePreview");
            BoxBlur blur = new BoxBlur(5, 5, 5);
            baseAnchorPane.setEffect(blur);
        }
    }

    public static boolean isEstadoPopUP() {
        return estadoPopUP;
    }

    public static void setEstadoPopUP(boolean estadoPopUP) {
        LoadAndMoveUtilities.estadoPopUP = estadoPopUP;
    }

    public static boolean returnToBaseAnchor() {
        if (baseAnchorPane != null) {
            setEstadoPopUP(true);
            baseAnchorPane.setEffect(null);

        }
        baseAnchorPane = null;
        return isEstadoPopUP();
    }

    /*
    Variavel que ira armazenar a stage antes de ser
     ocultada
     */
    private static Stage baseStage;
    private static boolean estadoStage;

    /*
    Mostra nova tela, sem ocultando a anterior e  ne bloqueando acesso a controles da tela
    anterior
     */
    public static void showStage(ActionEvent event, MouseEvent evt) {
        if (event != null) {
            LoadAndMoveUtilities.baseStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            LoadAndMoveUtilities.baseStage.setOpacity(0); // Esconde a janela principal

        }
        if (evt != null) {
            LoadAndMoveUtilities.baseStage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            LoadAndMoveUtilities.baseStage.setOpacity(0); // Esconde a janela principal

        }
        
    }

    public static boolean isEstadoStage() {
        return estadoStage;
    }

    public static void setEstadoStage(boolean estadoStage) {
        LoadAndMoveUtilities.estadoStage = estadoStage;
    }

    public static boolean returnToStage() {
        if (baseStage != null) {
            setEstadoStage(true);
            baseStage.setOpacity(1);
        }
        baseStage = null;
        return isEstadoStage();
    }
}
