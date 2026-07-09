/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package jmvis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * <p>Esta e a classe de entrada para o programa
 * e atravez dela que se carrega a primeira janela no programa</p>
 *
 * @author JM-Tecnologias
 * @version 1.0
 * @since Junho de 2026
 * 
 */
public class Main extends Application {
    /**
     * Variavel que premite fazer a troca de dados/Informacao entre classes/interfaces 
    */
    private static Object data;

    public static Object getData() {
        return data;
    }

    public static void setData(Object data) {
        Main.data = data;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));

        Scene scene = new Scene(root);

//      Remover os botoes de close, maximize e minimize
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
