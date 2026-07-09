/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Service responsible for opening map locations outside the JavaFX controller.
 *
 * @author JM-Tecnologias
 */
public class MapService {

    public void openLocation(ImageModel imageModel) throws IOException, URISyntaxException {
        if (!hasCoordinates(imageModel)) {
            throw new IllegalArgumentException("Coordinates not set");
        }

        String url = "https://www.google.com/maps?q="
                + imageModel.getGpsInfo().getLatitude()
                + ","
                + imageModel.getGpsInfo().getLongitude();

        Desktop.getDesktop().browse(new URI(url));
    }

    private boolean hasCoordinates(ImageModel imageModel) {
        return imageModel != null
                && imageModel.getGpsInfo() != null
                && imageModel.getGpsInfo().getLatitude() != null
                && imageModel.getGpsInfo().getLongitude() != null;
    }
}
