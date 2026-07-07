/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import java.io.File;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author JM-Tecnologias
 */
public class ImageService {

    private ImageView imageView;
    private AnchorPane anchorImagePreview;
    private static double rotationAngle = 360;

    //    Zoom Functions
    private double zoomFactor = 1.0;
    private final double zoomStep = 0.01;
    private final double zoomMin = 0.1;
    private final double zoomMax = 8.0;

    // PAN variables
    private static double panStartX;
    private static double panStartY;
    private static double imageStartX;
    private static double imageStartY;

    public ImageService(AnchorPane anchorImagePreview) {
        this.anchorImagePreview = anchorImagePreview;

        anchorImagePreview.widthProperty().addListener((obs, oldV, newV) -> updateImageSize());
        anchorImagePreview.heightProperty().addListener((obs, oldV, newV) -> updateImageSize());

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(anchorImagePreview.widthProperty());
        clip.heightProperty().bind(anchorImagePreview.heightProperty());
//        clip.setStyle(""
//                + "-fx-background-radius: 20px;"
//                + "-fx-border-radius:20px;");
        anchorImagePreview.setClip(clip);
        anchorImagePreview.setOnScroll(event -> {
            if (imageView == null) {
                return;
            }
            if (event.getDeltaY() > 0) {
                zoomFactor += zoomStep;
            } else {
                zoomFactor -= zoomStep;
            }
            zoomFactor = Math.max(zoomMin, Math.min(zoomMax, zoomFactor));
            applyZoom();
            event.consume();
        });

    }

    public String setImagePreview(String path) {
//        Image image = new Image("file:" + path);
        Image image = new Image(
                new File(path).toURI().toString(),
                1920,
                1080,
                true,
                true,
                true
        );
        imageView = new ImageView(image);
        anchorImagePreview.getChildren().clear();
        anchorImagePreview.getChildren().add(imageView);
        updateImageSize();
        enablePan(); // activa o PAN
        return path;
    }

    private void applyZoom() {
        if (imageView == null) {
            return;
        }
        imageView.setScaleX(zoomFactor);
        imageView.setScaleY(zoomFactor);
        centerImage();
    }

    private void centerImage() {
        if (imageView == null || anchorImagePreview == null) {
            return;

        }

        imageView.setLayoutX(anchorImagePreview.getWidth() / 2);
        imageView.setLayoutY(anchorImagePreview.getHeight() / 2);

        imageView.setX(-imageView.getBoundsInLocal().getWidth() / 2);
        imageView.setY(-imageView.getBoundsInLocal().getHeight() / 2);
    }

    private void enablePan() {
        if (imageView == null) {
            return;
        }

        // Quando o rato é pressionado
        imageView.setOnMousePressed(event -> {
            panStartX = event.getSceneX();
            panStartY = event.getSceneY();

            imageStartX = imageView.getTranslateX();
            imageStartY = imageView.getTranslateY();
        });

        // Enquanto arrasta
        imageView.setOnMouseDragged(event -> {
            double offsetX = event.getSceneX() - panStartX;
            double offsetY = event.getSceneY() - panStartY;

            imageView.setTranslateX(imageStartX + offsetX);
            imageView.setTranslateY(imageStartY + offsetY);
        });
    }

    public void rotateImage(double facotr) {
        if (imageView == null) {
            return;
        }
        rotationAngle *= facotr;

        RotateTransition rt = new RotateTransition(Duration.millis(400), imageView);
        imageView.setRotate(facotr);
//        rt.setToAngle(facotr);s
        // Atualiza continuamente enquanto gira
        rt.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            updateImageSize();
            centerImage();
            applyZoom();

        });
        rt.play();
    }

    /*
    Metodo obsoleto e sem funcao
     */
    public void rotateImageLeft() {
        if (imageView == null) {
            return;
        }
        rotationAngle -= 10;

        RotateTransition rt = new RotateTransition(Duration.millis(1000), imageView);
        rt.setToAngle(rotationAngle);
        // Atualiza continuamente enquanto gira
        rt.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            updateImageSize();
            centerImage();
            applyZoom();
        });
        rt.play();
    }

    public void updateImageSize() {
        if (imageView == null || anchorImagePreview == null) {
            return;
        }
        boolean rotated = rotationAngle == 90 || rotationAngle == 270;
        if (rotated) {
            imageView.setFitWidth(anchorImagePreview.getHeight());
            imageView.setFitHeight(anchorImagePreview.getWidth());
        } else {
            imageView.setFitWidth(anchorImagePreview.getWidth());
            imageView.setFitHeight(anchorImagePreview.getHeight());
        }
    }
}
