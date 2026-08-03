package jmvis.service;

import java.io.File;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/** Renders an equirectangular image from inside a textured sphere. */
public final class Panorama360Service {

    private static final double MIN_FIELD_OF_VIEW = 30.0;
    private static final double MAX_FIELD_OF_VIEW = 100.0;
    private static final double DRAG_SENSITIVITY = 0.18;

    private final AnchorPane host;
    private SubScene subScene;
    private PerspectiveCamera camera;
    private Rotate yaw;
    private Rotate pitch;
    private double pressedX;
    private double pressedY;
    private double initialYaw;
    private double initialPitch;

    public Panorama360Service(AnchorPane host) {
        this.host = host;
    }

    public void show(String path) {
        Image panorama = new Image(new File(path).toURI().toString(), 4096, 2048,
                true, true, false);
        if (panorama.isError()) {
            throw new IllegalArgumentException("Nao foi possivel carregar o panorama: " + path,
                    panorama.getException());
        }

        Sphere sphere = new Sphere(100, 128);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(panorama);
        material.setSpecularColor(Color.TRANSPARENT);
        sphere.setMaterial(material);
        // Only the inside of the sphere must be visible to the camera.
        sphere.setCullFace(CullFace.FRONT);
        // Correct the horizontal orientation when viewing the texture internally.
        sphere.setScaleX(-1);

        camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(1000.0);
        camera.setFieldOfView(70.0);

        yaw = new Rotate(0, Rotate.Y_AXIS);
        pitch = new Rotate(0, Rotate.X_AXIS);
        Group cameraRig = new Group(camera);
        cameraRig.getTransforms().addAll(yaw, pitch);

        Group world = new Group(sphere, new AmbientLight(Color.WHITE), cameraRig);
        subScene = new SubScene(world, Math.max(1, host.getWidth()),
                Math.max(1, host.getHeight()), true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.BLACK);
        subScene.setCamera(camera);
        subScene.widthProperty().bind(host.widthProperty());
        subScene.heightProperty().bind(host.heightProperty());
        installNavigation();

        host.getChildren().setAll(subScene);
        AnchorPane.setTopAnchor(subScene, 0.0);
        AnchorPane.setRightAnchor(subScene, 0.0);
        AnchorPane.setBottomAnchor(subScene, 0.0);
        AnchorPane.setLeftAnchor(subScene, 0.0);
        subScene.requestFocus();
    }

    private void installNavigation() {
        subScene.setOnMousePressed(event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            pressedX = event.getSceneX();
            pressedY = event.getSceneY();
            initialYaw = yaw.getAngle();
            initialPitch = pitch.getAngle();
        });

        subScene.setOnMouseDragged(event -> {
            if (!event.isPrimaryButtonDown()) {
                return;
            }
            yaw.setAngle(initialYaw + (event.getSceneX() - pressedX) * DRAG_SENSITIVITY);
            double nextPitch = initialPitch - (event.getSceneY() - pressedY) * DRAG_SENSITIVITY;
            pitch.setAngle(Math.max(-85.0, Math.min(85.0, nextPitch)));
        });

        subScene.setOnScroll(event -> {
            double nextFov = camera.getFieldOfView() - event.getDeltaY() * 0.04;
            camera.setFieldOfView(Math.max(MIN_FIELD_OF_VIEW,
                    Math.min(MAX_FIELD_OF_VIEW, nextFov)));
            event.consume();
        });

        subScene.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
                    && subScene.getScene() != null
                    && subScene.getScene().getWindow() instanceof Stage) {
                Stage stage = (Stage) subScene.getScene().getWindow();
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
    }

    public void resetView() {
        if (camera != null) {
            yaw.setAngle(0);
            pitch.setAngle(0);
            camera.setFieldOfView(70.0);
        }
    }
}
