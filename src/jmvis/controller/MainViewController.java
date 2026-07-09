/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jmvis.controller;

import jmvis.service.ImageService;
import jmvis.service.TitleBarService;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import jmvis.Main;
import jmvis.service.CopyService;
import jmvis.service.DialogService;
import jmvis.service.DirectoryTreeService;
import jmvis.service.FileManagerService;
import jmvis.util.ImageDetailsBinder;
import jmvis.model.ImageModel;
import jmvis.service.ImageMetadataService;
import jmvis.service.ImageReportService;
import jmvis.service.MapService;
import jmvis.service.ReportFileService;
import jmvis.service.ThumbnailService;
import jmvis.service.ThumbnailService.ThumbnailItem;

/**
 * FXML Controller class
 * 
 * <p> Classe controladora da MainView.fxml. esta classe controla todos componentes visuais da view</p>
 *
 * @author JM-Tecnologias
 * @version 1.0
 * @since 2026
 */
public class MainViewController implements Initializable {

    // Path Cliced at Pallet
    private File file;

    @FXML
    private Slider sliderRotate;

    @FXML
    private Label lblStatus;

    private ImageService imageService;
    private ImageDetailsBinder imageDetailsBinder;
    private ImageMetadataService imageMetadataService;
    private ImageReportService imageReportService;
    private MapService mapService;
    private ThumbnailService thumbnailService;
    private CopyService copyService;
    private DialogService dialogService;
    private DirectoryTreeService directoryTreeService;
    private ReportFileService reportFileService;
    private ImageModel imgModel;
    private TitleBarService titlebarService;
    private FileManagerService fm;

    @FXML
    private AnchorPane anchorTitleBar;

    @FXML
    private AnchorPane anchorFrame;

    @FXML
    private AnchorPane anchorMain;

    @FXML
    private AnchorPane anchorPallet;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox tabPaneProps, hBoxButtonsControlls;

    @FXML
    private Button btnPalletCollapse;

    @FXML
    private Button btnPropsCollapse;

    @FXML
    private Button btnMakeDir;

    @FXML
    private AnchorPane anchorImagePreview;

    @FXML
    private TreeView<File> treePalette;

    @FXML
    private HBox imageScroll;

    private String imagePath;

    @FXML
    private Label labAltitude;

    @FXML
    private Label labDateTimeDigitized;

    @FXML
    private Label labDateTimeOriginal;

    @FXML
    private Label labExposureTime;

    @FXML
    private Label labFocalLengthIn35mmFilm;

    @FXML
    private Label labISOSpeedRating;

    @FXML
    private Label labLatitude;

    @FXML
    private Label labLongitude;

    @FXML
    private Label labMaker;

    @FXML
    private Label labModel;

    @FXML
    private Label labOffsetTime;

    @FXML
    private Label labShutterSpeedValue;

    @FXML
    private Label labFocalLength;

    @FXML
    private Label labSoftware;


    @FXML
    private Label labCountry;
    @FXML
    private Label labCountryCode;
    @FXML
    private Label labCity;
    @FXML
    private Label labRegion;
    
    @FXML
    private TextArea txtAreaNotes;

    private Path auxPath;

    @FXML
    public void close(ActionEvent evt) {
        System.exit(0);
    }

    @FXML
    public void minimize(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        stage.setIconified(true); // minimiza a janela
    }

    @FXML
    public void maximize(ActionEvent evt) {
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        stage.setMaximized(!stage.isMaximized()); // alterna entre maximizar e restaurar
    }

    @FXML
    public void makeDir(ActionEvent evt) {
        if (file == null) {
            dialogService.showMessage("Root folder not seledcted!");
            return;
        }

        String data = dialogService.requestText(evt, "Name for new folder:");
        if (data == null || data.trim().isEmpty()) {
            return;
        }

        try {
            if (fm.makeDir(file.toString() + "/" + data)) {
                lblStatus.setText("Directory " + data + " created Succefuly");
                dialogService.showMessage(evt, "Directory " + data + " created Succefuly");
                refreshTree();
            }
        } catch (IOException ex) {
            dialogService.showMessage(evt, "Error: " + ex);
        }
    }

    @FXML
    public void renameDir(ActionEvent evt) {
        if (file == null) {
            dialogService.showMessage("Root folder not seledcted!");
            return;
        }

        String newName = dialogService.requestText(evt, "Enter a new name: ");
        if (newName == null || newName.trim().isEmpty()) {
            return;
        }

        try {
            if (fm.renameDir(file.toString(), newName)) {
                lblStatus.setText("Directory renamed Succefuly");
                dialogService.showMessage(evt, "Directory renamed Succefuly");
                refreshTree();
            }
        } catch (IOException ex) {
            dialogService.showMessage(evt, "Error: " + ex);
        }
    }

    @FXML
    public void deleteDir(ActionEvent evt) {
        if (file == null) {
            dialogService.showMessage("Folder not seledcted!");
            return;
        }

        String confirmation = dialogService.requestText(evt, "Confirm folder Deleting. Type: 'DELETE' ");
        if (!"DELETE".equals(confirmation)) {
            return;
        }

        try {
            if (fm.deleteDir(file.toString())) {
                lblStatus.setText("Directory deleted Succefuly");
                dialogService.showMessage(evt, "Directory deleted created Succefuly");
                refreshTree();
            }
        } catch (IOException ex) {
            dialogService.showMessage(evt, "Error: " + ex);
        }
    }

    @FXML
    private void abrirDocumento(ActionEvent event) throws IOException {
        reportFileService.openReport(event);
    }

    @FXML
    public void getMapView(ActionEvent e) {
        try {
            mapService.openLocation(imgModel);
        } catch (IllegalArgumentException ex) {
            dialogService.showMessage("Coordenates not set");
        } catch (Exception ex) {
            dialogService.showMessage("Error: " + ex);
        }

    }

    @FXML
    void hide_show_Pallet(ActionEvent event) {
        TranslateTransition trans = new TranslateTransition(Duration.millis(800), anchorPallet);
        if (anchorPallet.getTranslateX() == -320) {
            trans.setToX(0);
            AnchorPane.setLeftAnchor(anchorPallet, 0.0);
            AnchorPane.setLeftAnchor(scrollPane, 330.0);
            AnchorPane.setLeftAnchor(anchorImagePreview, 330.0);
        } else {
            trans.setToX(-320);
            AnchorPane.setLeftAnchor(anchorPallet, null);
            AnchorPane.setLeftAnchor(scrollPane, 10.0);
            AnchorPane.setLeftAnchor(anchorImagePreview, 10.0);
        }
        trans.play();
    }
    @FXML
    void show_and_hide_scrollPane(ActionEvent event) {
        showhideScrollPane();
    }

    void showhideScrollPane() {
        boolean fechado = scrollPane.getTranslateY() == 0;

        TranslateTransition scrollTransition
                = new TranslateTransition(Duration.millis(400), scrollPane);

        TranslateTransition buttonsTransition
                = new TranslateTransition(Duration.millis(400), hBoxButtonsControlls);

        if (fechado) {
            // esconder
            scrollTransition.setToY(200);
            buttonsTransition.setToY(185);

        } else {
            // mostrar
            scrollTransition.setToY(0);
            buttonsTransition.setToY(0);
        }

        scrollTransition.play();
        buttonsTransition.play();
    }

    @FXML
    void show_and_hide_props(ActionEvent event) {
        showhideProps();
    }

    void showhideProps() {
        TranslateTransition transition
                = new TranslateTransition(Duration.millis(400), tabPaneProps);
        TranslateTransition trans = new TranslateTransition(Duration.millis(1000), scrollPane);

        if (tabPaneProps.getTranslateX() == 335) {
            transition.setToX(0);
        } else {
            transition.setToX(335);
        }
        transition.play();
        trans.play();
    }

    @FXML
    void rotateImageLeft(ActionEvent event) {
        imageService.rotateImageLeft();
    }

    @FXML
    void spiner(MouseEvent event) {
        sliderRotate.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (sliderRotate.isValueChanging()) {
                imageService.rotateImage(sliderRotate.getValue());
            }
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fm = new FileManagerService();
        //Inicializa o servico para atribuir o servico de arrasta a barra de titulo
        titlebarService = new TitleBarService(anchorTitleBar);
        Platform.runLater(() -> {
            anchorFrame.getScene().setOnKeyPressed(event -> {

//                if (event.isControlDown() && event.getCode() == KeyCode.S) {
//                    System.out.println("CTRL + S");
//                }
//
//                if (event.isControlDown()
//                        && event.isShiftDown()
//                        && event.getCode() == KeyCode.N) {
//                    System.out.println("CTRL + SHIFT + N");
//                }
//                if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
//                    System.out.println("ALT + ENTER");
//                }
                if (event.isControlDown() && event.getCode() == KeyCode.S) {
                    showhideScrollPane();
                }
                if (event.isControlDown() && event.getCode() == KeyCode.P) {
                    showhideProps();
                }

                if (event.isControlDown()
                        && event.getCode() == KeyCode.LEFT) {
                    previuslyImage();
                }
                if (event.isControlDown()
                        && event.getCode() == KeyCode.RIGHT) {
                    nextImage();
                }
            }
            );
        });

        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 300) {
                return change;
            }
            lblStatus.setText("Atingiu o limite maximo de caracteres");
            return null;
        });
        txtAreaNotes.setTextFormatter(formatter);

        imageService = new ImageService(anchorImagePreview);
        imageMetadataService = new ImageMetadataService();
        imageReportService = new ImageReportService(imageMetadataService);
        mapService = new MapService();
        thumbnailService = new ThumbnailService();
        copyService = new CopyService();
        dialogService = new DialogService();
        directoryTreeService = new DirectoryTreeService();
        reportFileService = new ReportFileService();
        imageDetailsBinder = new ImageDetailsBinder(
                labMaker,
                labModel,
                labSoftware,
                labLatitude,
                labLongitude,
                labAltitude,
                labCountry,
                labCountryCode,
                labCity,
                labRegion,
                labExposureTime,
                labShutterSpeedValue,
                labISOSpeedRating,
                labFocalLength,
                labFocalLengthIn35mmFilm,
                labDateTimeOriginal,
                labDateTimeDigitized,
                labOffsetTime
        );
        imgModel = new ImageModel();
        //        Inicializa logo 
        imageService.setImagePreview(Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/FileImageSystem/LOGO.png").toString());

        directoryTreeService.configure(treePalette, fm.getRoot().toFile());
        initClickListener();

    }
    // ðŸ”¥ MÃ‰TODO RECURSIVO PARA MONTAR A ÃRVORE

    public void refreshTree() {
        directoryTreeService.refresh(treePalette, fm.getRoot().toFile());
    }

//    Imprime o caminho do no clicado na pallet
    private void initClickListener() {

        treePalette.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal != null && newVal.getValue() != null) {

                file = newVal.getValue();

                imageScroll.getChildren().clear();
                loadImagesAsync(file.toPath());
            }
        });
    }

    private void loadImagesAsync(Path folder) {
        auxPath = null;
        thumbnailService.loadThumbnails(folder, thumb -> {
            thumb.getView().setOnMouseClicked((MouseEvent e) -> selectThumbnail(thumb, e));
            imageScroll.getChildren().add(thumb.getView());
        });

    }

    private void showProps(Path path) {
        try {

            imgModel = imageMetadataService.analyze(path.toString());
            Main.setData(imgModel);
            imageDetailsBinder.show(imgModel);
        } catch (Exception ex) {
            dialogService.showMessage("Error: " + ex);
        }

    }

    @FXML
    void next(ActionEvent evt) {
        nextImage();
    }

    void nextImage() {
        Path nextPath = thumbnailService.nextAfter(auxPath);
        if (nextPath != null) {
            previewImage(nextPath);
        }
    }

    @FXML
    void prev(ActionEvent evt) {
        previuslyImage();
    }

    void previuslyImage() {
        Path previousPath = thumbnailService.previousBefore(auxPath);
        if (previousPath != null) {
            previewImage(previousPath);
        }
    }

    @FXML
    void saveImageDetail(ActionEvent evt) throws IOException, Exception {
        int generatedReports = imageReportService.generateReports(
                getSelectedImagePaths(),
                imgModel,
                txtAreaNotes.getText()
        );

        dialogService.showMessage(generatedReports > 1 ? "Reports Genarated" : "Report Genarated");
    }

    private void selectThumbnail(ThumbnailItem thumb, MouseEvent e) {

        thumbnailService.handleSelection(thumb, e.isControlDown());
        previewImage(thumb.getPath());

    }


    private void previewImage(Path imagePath) {
        auxPath = imagePath;
        imageService.setImagePreview(imagePath.toString());
        sliderRotate.setValue(0);
        showProps(imagePath);
    }

    public List<Path> getSelectedImagePaths() {
        return thumbnailService.getSelectedImagePaths();
    }

    @FXML
    void startCopy(ActionEvent event) {
        Path source = chooseSourceFolder(event);
        if (source == null) {
            return;
        }

        if (file == null) {
            lblStatus.setText("Destination not set.");
            return;
        }

        Path destination = file.toPath();
        Task<Integer> task = copyService.importImages(source, destination);
        lblStatus.textProperty().bind(task.messageProperty());

        task.setOnSucceeded(workerStateEvent -> {
            lblStatus.textProperty().unbind();
            lblStatus.setText(task.getValue() + " images imported.");
            refreshImportedImages(destination);
            dialogService.showMessage(event, task.getValue() + " Images Imported");
        });

        task.setOnFailed(workerStateEvent -> {
            lblStatus.textProperty().unbind();
            lblStatus.setText("Import failed.");
            dialogService.showMessage(event, "Error: " + task.getException());
        });

        copyService.start(task);
    }

    private Path chooseSourceFolder(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Selecionar pasta de origem");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File sourceFolder = chooser.showDialog(stage);

        if (sourceFolder == null) {
            lblStatus.setText("Operacao cancelada.");
            return null;
        }

        return sourceFolder.toPath();
    }

    private void refreshImportedImages(Path destination) {
        imageScroll.getChildren().clear();
        loadImagesAsync(destination);
    }

}
