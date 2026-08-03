/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
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
    private final Panorama360Service panoramaService;
    private String currentImagePath;
    private boolean panoramaMode;
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
        this.panoramaService = new Panorama360Service(anchorImagePreview);

        anchorImagePreview.widthProperty().addListener((obs, oldV, newV) -> updateImageSize());
        anchorImagePreview.heightProperty().addListener((obs, oldV, newV) -> updateImageSize());

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(anchorImagePreview.widthProperty());
        clip.heightProperty().bind(anchorImagePreview.heightProperty());
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
        currentImagePath = path;
        if (isLikelyEquirectangular(path)) {
            showPanorama(path);
            return path;
        }
        Image image = new Image(
                new File(path).toURI().toString(),
                1920,
                1080,
                true,
                true,
                true
        );
        showFlatImage(image);
        return path;
    }

    private void showFlatImage(Image image) {
        panoramaMode = false;
        zoomFactor = 1.0;
        imageView = new ImageView(image);
        anchorImagePreview.getChildren().clear();
        anchorImagePreview.getChildren().add(imageView);
        updateImageSize();
        enablePan(); // activa o PAN
    }

    private void showPanorama(String path) {
        panoramaMode = true;
        imageView = null;
        panoramaService.show(path);
    }

    private boolean isLikelyEquirectangular(String path) {
        try (ImageInputStream input = ImageIO.createImageInputStream(new File(path))) {
            if (input == null) {
                return false;
            }
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) {
                return false;
            }
            ImageReader reader = readers.next();
            try {
                reader.setInput(input, true, true);
                int height = reader.getHeight(0);
                double ratio = (double) reader.getWidth(0) / height;
                return height > 0 && Math.abs(ratio - 2.0) <= 0.08;
            } finally {
                reader.dispose();
            }
        } catch (IOException | RuntimeException ex) {
            return false;
        }
    }

    /** Switches the current image between the standard and spherical viewers. */
    public boolean togglePanoramaMode() {
        if (currentImagePath == null) {
            return false;
        }
        if (panoramaMode) {
            Image image = new Image(new File(currentImagePath).toURI().toString(),
                    1920, 1080, true, true, true);
            showFlatImage(image);
        } else {
            showPanorama(currentImagePath);
        }
        return panoramaMode;
    }

    public boolean isPanoramaMode() {
        return panoramaMode;
    }

    public void resetPanoramaView() {
        if (panoramaMode) {
            panoramaService.resetView();
        }
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
