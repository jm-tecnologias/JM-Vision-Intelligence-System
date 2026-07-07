/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

/**
 *
 * @author JM-Tecnologias
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.stage.Modality;
import jmvis.Main;
import org.json.JSONObject;

public class ReverseGeocoder {

    public static JSONObject geoReverseDecoder(Double lat, Double lon) {
        JSONObject jsonResponse = null;

        try {

            String urlStr = "https://nominatim.openstreetmap.org/reverse?format=json"
                    + "&lat=" + lat + "&lon=" + lon;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty(
                    "User-Agent",
                    "JMVisionIntelligence/1.0 (contact: comercial@jm-tecnologias.co.mz)"
            );
            int status = conn.getResponseCode();

            if (status != 200) {
                Main.setData("HTTP Error: " + status);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                return null;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            jsonResponse = new JSONObject(response.toString());

            in.close();
        } catch (IOException ex) {
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
        }

        return jsonResponse;
    }

//    public static void main(String[] args) throws Exception {
//        JSONObject json;
//        if (hasInternet()) {
//            System.out.println("Internet OK");
//            json = ReverseGeocoder.geoReverseDecoder(232.3, 232.3).getJSONObject("address");
//        } else {
//            System.out.println("Sem internet");
//            return;
//        }
//
//        JSONObject address = json.getJSONObject("address");
//
//        String country = address.optString("country");
//        String countryCode = address.optString("country_code");
//        String district = address.optString("district");
//        String city = address.optString("city");
//
//        System.out.println("Country: " + country);
//        System.out.println("Country Code: " + countryCode);
//        System.out.println("District: " + district);
//        System.out.println("City: " + city);
//    }
}
