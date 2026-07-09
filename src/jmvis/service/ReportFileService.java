/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.service;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Handles report file selection and opening.
 *
 * @author JM-Tecnologias
 */
public class ReportFileService {

    public void openReport(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Opening Reports");

        File defaultDir = Paths.get(
                System.getProperty("user.home"),
                "Documents",
                "JM Vision Intelligence",
                "Report"
        ).toFile();

        if (defaultDir.exists()) {
            chooser.setInitialDirectory(defaultDir);
        }

        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Documents", "*.pdf"),
                new FileChooser.ExtensionFilter("Todos os ficheiros", "*.*")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File report = chooser.showOpenDialog(stage);

        if (report != null) {
            Desktop.getDesktop().open(report);
        }
    }
}
