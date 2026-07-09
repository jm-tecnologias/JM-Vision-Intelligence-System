/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jmvis;

import java.awt.Desktop;
import jmvis.util.ImageService;
import jmvis.util.TitleBarService;
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
import javafx.scene.control.TreeItem;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import jmvis.util.CopyService;
import jmvis.util.FileManager;
import jmvis.util.ImageDetailsBinder;
import jmvis.util.ImageModel;
import jmvis.util.ImageMetadataService;
import jmvis.util.ImageReportService;
import jmvis.util.LoadAndMoveUtilities;
import jmvis.util.MapService;
import jmvis.util.ThumbnailService;
import jmvis.util.ThumbnailService.ThumbnailItem;

/**
 * FXML Controller class
 * 
 * <p> Classe controladora da MainView.fxml. esta classe controla todos componentes visuais da view</p>
 *
 * @author JM-Tecnologias
 * @version 1.0
 * @since 2026
 */
public class PleaseProvideControllerClassName implements Initializable {

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
    private ImageModel imgModel;
    private TitleBarService titlebarService;
    private FileManager fm;

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
    private VBox vBoxDetails1, vBoxDetails2, vBoxDetails3, vBoxDetails4, vBoxDetails5;

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
        Main.setData("Name for new folder:");
        if (Main.getData() != null) {
            if (file == null) {
                Main.setData("Root folder not seledcted!");
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                return;
            }
            LoadAndMoveUtilities.showAsPopUP(evt, null);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/InputText.fxml");
            try {
                String data = (String) Main.getData();
                if (fm.makeDir(file.toString() + "/" + data)) {
                    lblStatus.setText("Directory " + data + " created Succefuly");
                    Main.setData("Directory " + data + " created Succefuly");
                    LoadAndMoveUtilities.showAsPopUP(evt, null);
                    LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                    refreshTree();
                }

            } catch (IOException ex) {
                Main.setData("Error: " + ex);
                LoadAndMoveUtilities.showAsPopUP(evt, null);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
            }
        }

    }

    @FXML
    public void renameDir(ActionEvent evt) {
        Main.setData("Enter a new name: ");
        if (Main.getData() != null) {
            try {
                if (file == null) {
                    Main.setData("Root folder not seledcted!");
                    LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                    return;
                }
                LoadAndMoveUtilities.showAsPopUP(evt, null);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/InputText.fxml");
                if (fm.renameDir(file.toString(), (String) Main.getData())) {
                    lblStatus.setText("Directory renamed Succefuly");
                    Main.setData("Directory renamed Succefuly");
                    LoadAndMoveUtilities.showAsPopUP(evt, null);
                    LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                    refreshTree();
                }
            } catch (IOException ex) {
                Main.setData("Error: " + ex);
                LoadAndMoveUtilities.showAsPopUP(evt, null);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
            }
        }
    }

    @FXML
    public void deleteDir(ActionEvent evt) {
        Main.setData("Confirm folder Deleting. Type: 'DELETE' ");
        if (Main.getData() != null) {
            try {
                if (file == null && !Main.getData().equals("DELETE")) {
                    Main.setData("Folder not seledcted!");
                    LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                    return;
                }
                LoadAndMoveUtilities.showAsPopUP(evt, null);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/InputText.fxml");
                if (fm.deleteDir(file.toString())) {
                    lblStatus.setText("Directory deleted Succefuly");
                    Main.setData("Directory deleted created Succefuly");
                    LoadAndMoveUtilities.showAsPopUP(evt, null);
                    LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                    refreshTree();
                }
            } catch (IOException ex) {
                Main.setData("Error: " + ex);
                LoadAndMoveUtilities.showAsPopUP(evt, null);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
            }

        }
    }

    @FXML
    private void abrirDocumento(ActionEvent event) throws IOException {

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Opening Reports");

        // Pasta padrao
        File defaultDir = Paths.get(
                System.getProperty("user.home"),
                "Documents",
                "JM Vision Intelligence",
                "Report"
        ).toFile();

        if (defaultDir.exists()) {
            chooser.setInitialDirectory(defaultDir);
        }

        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Documents",
                        "*.pdf"
                ),
                new FileChooser.ExtensionFilter(
                        "Todos os ficheiros",
                        "*.*"
                )
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        File file = chooser.showOpenDialog(stage);

        if (file != null) {
            Desktop.getDesktop().open(file);
        }
    }

    @FXML
    public void getMapView(ActionEvent e) {
        try {
            mapService.openLocation(imgModel);
        } catch (IllegalArgumentException ex) {
            Main.setData("Coordenates not set");
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
        } catch (Exception ex) {
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
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
        fm = new FileManager();
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
//        imageService.setImagePreview("C:\\Users\\JM-Tecnologias\\Documents\\JM Vision Intelligence\\File Explore\\Amilcar\\IMG_9940.JPG");

        treePalette.setRoot(createNode(fm.getRoot().toFile()));
        treePalette.setShowRoot(true);
        initClickListener();

        treePalette.setCellFactory(tv -> new javafx.scene.control.TreeCell<File>() {
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
    // ðŸ”¥ MÃ‰TODO RECURSIVO PARA MONTAR A ÃRVORE

    private TreeItem<File> createNode(File file) {

        if (!file.isDirectory()) {
            return null;
        }

        TreeItem<File> item = new TreeItem<>(file);

        item.setValue(file);

        // Lazy loading
        item.getChildren().add(new TreeItem<>(new File("loading")));

        item.expandedProperty().addListener((obs, oldVal, isExpanded) -> {

            if (isExpanded && item.getChildren().size() == 1) {

                item.getChildren().clear();

                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {

                        if (f.isDirectory()) {
                            TreeItem<File> child = createNode(f);
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

    public void refreshTree() {
        TreeItem<File> newRoot = createNode(fm.getRoot().toFile());

        treePalette.setRoot(newRoot);
        treePalette.setShowRoot(true);

        treePalette.refresh();
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
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
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

        Main.setData(generatedReports > 1 ? "Reports Genarated" : "Report Genarated");
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
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
            showMessage(event, task.getValue() + " Images Imported");
        });

        task.setOnFailed(workerStateEvent -> {
            lblStatus.textProperty().unbind();
            lblStatus.setText("Import failed.");
            showMessage(event, "Error: " + task.getException());
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

    private void showMessage(ActionEvent event, String message) {
        Main.setData(message);
        LoadAndMoveUtilities.showAsPopUP(event, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
    }

}
