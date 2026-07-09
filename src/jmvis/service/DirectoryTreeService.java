/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.service;

import java.io.File;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Builds and refreshes the folder tree used by the palette.
 *
 * @author JM-Tecnologias
 */
public class DirectoryTreeService {

    public void configure(TreeView<File> treeView, File rootFolder) {
        treeView.setRoot(createNode(rootFolder));
        treeView.setShowRoot(true);
        treeView.setCellFactory(tv -> new TreeCell<File>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);

                if (empty || file == null) {
                    setText(null);
                } else {
                    setText(file.getName().isEmpty()
                            ? file.getAbsolutePath()
                            : file.getName());
                }
            }
        });
    }

    public void refresh(TreeView<File> treeView, File rootFolder) {
        treeView.setRoot(createNode(rootFolder));
        treeView.setShowRoot(true);
        treeView.refresh();
    }

    private TreeItem<File> createNode(File file) {
        if (file == null || !file.isDirectory()) {
            return null;
        }

        TreeItem<File> item = new TreeItem<>(file);
        item.getChildren().add(new TreeItem<>(new File("loading")));

        item.expandedProperty().addListener((obs, oldVal, isExpanded) -> {
            if (isExpanded && item.getChildren().size() == 1) {
                item.getChildren().clear();

                File[] files = file.listFiles();
                if (files != null) {
                    for (File childFile : files) {
                        if (childFile.isDirectory()) {
                            TreeItem<File> child = createNode(childFile);
                            if (child != null) {
                                item.getChildren().add(child);
                            }
                        }
                    }
                }
            }
        });

        return item;
    }
}
