/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.stage.Modality;
import jmvis.Main;
import jmvis.util.LoadAndMoveUtilities;

/**
 *
 * @author JM-Tecnologias
 */
public final class FileManagerService {

    private Path data;
    private Path reportPath;
    private Path baseimages;
    private Path root;

    public FileManagerService() {

        this.root = Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/File Explore");
        this.reportPath = Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/Report");
        this.baseimages = Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/FileImageSystem");
        this.data =  Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/Data");
        if (!Files.exists(this.root)) {
            try {
                Files.createDirectories(this.root);
            } catch (IOException ex) {
                Main.setData("Error: " + ex);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/view/ConfirForm.fxml");
//                throw new IllegalAccessError("error: "+ex);
            }
        }
        if (!Files.exists(this.reportPath)) {
            try {
                Files.createDirectories(this.reportPath);
            } catch (IOException ex) {
                Main.setData("Error: " + ex);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/view/ConfirForm.fxml");
//                throw new IllegalAccessError("error: "+ex);
            }
        }
        
        if (!Files.exists(this.baseimages)) {
            try {
                Files.createDirectories(this.baseimages);
            } catch (IOException ex) {
                Main.setData("Error: " + ex);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/view/ConfirForm.fxml");
//                throw new IllegalAccessError("error: "+ex);
            }
        }
        
        if (!Files.exists(this.data)) {
            try {
                Files.createDirectories(this.data);
            } catch (IOException ex) {
                Main.setData("Error: " + ex);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/view/ConfirForm.fxml");
//                throw new IllegalAccessError("error: "+ex);
            }
        }

    }

    public Path getRoot() {
        return root;
    }

    public Path getReport() {
        return reportPath;
    }

    public boolean makeDir(String path) throws IOException {
        File file = new File(path);
        if (!Files.exists(file.toPath())) {
            Files.createDirectories(file.toPath());
            return true;
        }
        return  false;
    }

    public boolean renameDir(String path, String newName) throws IOException {
        if (!newName.equals("")) {
            File file = new File(path);
            String newPath = path.replace(file.getName(), newName);
            File newNameFile = new File(newPath);
            Files.move(file.toPath(), newNameFile.toPath());
            return true;
        }
        return false;
    }

    public boolean deleteDir(String path) throws IOException {
        File file = new File(path);
//        Files.deleteIfExists(file.toPath());
        return Files.deleteIfExists(file.toPath());
    }

}
