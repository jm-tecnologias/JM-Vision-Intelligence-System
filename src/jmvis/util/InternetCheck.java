/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.stage.Modality;
import jmvis.Main;

/**
 *
 * @author JM-Tecnologias
 */
public class InternetCheck {

    public static boolean hasInternet() {
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(3000); // 3s
            conn.setReadTimeout(3000);

            int responseCode = conn.getResponseCode();
            return (200 <= responseCode && responseCode < 400);

        } catch (IOException e) {
            Main.setData("No internet connection...");
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
            return false;
        }
    }

//    public static void main(String[] args) {
//        if (hasInternet()) {
//            System.out.println("Internet OK");
//        } else {
//            System.out.println("Sem internet");
//        }
//    }
}
