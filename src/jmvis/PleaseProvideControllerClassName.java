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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import static jmvis.util.ImageUtils.listImages;
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
import jmvis.util.ImageModel;
import jmvis.util.LoadAndMoveUtilities;
import jmvis.util.PDFReport;

/**
 * FXML Controller class
 *
 * @author JM-Tecnologias
 */
public class PleaseProvideControllerClassName implements Initializable {

    private final List<ImageThumb> loadedThumbs = new ArrayList<>();
    private final List<ImageThumb> selectedImages = new ArrayList<>();

    // Path Cliced at Pallet
    private File file;

    @FXML
    private Slider sliderRotate;

    @FXML
    private Label lblStatus;

    private ImageService imageService;
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
    private Label labLatitudePrev;
    @FXML
    private Label labLongitudePrev;
    @FXML
    private Label labAltitudePrev;

    @FXML
    private Label labNamePrev;
    @FXML
    private Label labDatePrev;

//    @FXML
//    private CheckBox dateChk;
//
//    @FXML
//    private CheckBox deviceChk;
//
//    @FXML
//    private CheckBox gpsChk;
//
//    @FXML
//    private CheckBox imageChk;
    @FXML
    private TextArea txtAreaNotes;

    private List<Path> carouselHelper;
    private Path auxPath;
    private int countCarouselHelper;
    private int countNext;
    private int countPrev;
    private int key;

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

        // Pasta padrão
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
//        LoadAndMoveUtilities.showAsPopUP(e, null);
//        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/MapView.fxml");

//        try {
//        Double lat = imgModel.getGpsInfo().getLatitude();
//        Double lng = imgModel.getGpsInfo().getLongitude();
        if (imgModel.getGpsInfo() != null) {
            if (imgModel.getGpsInfo().getLatitude() != null || imgModel.getGpsInfo().getLongitude() != null) {
                try {
                    String url = "https://www.google.com/maps?q=" + imgModel.getGpsInfo().getLatitude() + "," + imgModel.getGpsInfo().getLongitude();
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException ex) {
                        Main.setData("Error: " + ex);
                        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                    }
                } catch (URISyntaxException ex) {
                    Main.setData("Error: " + ex);
                    LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                }
            } else {
                Main.setData("Coordenates not set");
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
            }
        } else {
            Main.setData("Coordenates not set");
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

//    @FXML
//    void show_and_hide_scrollPane(ActionEvent event) {
//        TranslateTransition trans = new TranslateTransition(Duration.millis(400), scrollPane);
//        if (scrollPane.getTranslateY() == 0) {
//            trans.setToY(200);
////            AnchorPane.setBottomAnchor(anchorImagePreview, 50.0);
//            AnchorPane.setBottomAnchor(hBoxButtonsControlls, 60.0);
//
//        } else {
//            trans.setToY(0);
////            AnchorPane.setBottomAnchor(anchorImagePreview, 220.0);
//            AnchorPane.setBottomAnchor(hBoxButtonsControlls, 235.0);
//
//        }
//        trans.play();
//    }
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
//            AnchorPane.setRightAnchor(scrollPane, 345.0);
//            AnchorPane.setRightAnchor(anchorImagePreview, 345.0);

        } else {
            transition.setToX(335);
//            AnchorPane.setRightAnchor(scrollPane, 50.0);
//            AnchorPane.setRightAnchor(anchorImagePreview, 50.0);
        }
        transition.play();
        trans.play();
    }

//    @FXML
//    void show_and_hide_props(ActionEvent event) {
//
//        boolean aberto = tabPaneProps.getTranslateX() == 0;
//
//        // Painel lateral
//        TranslateTransition panelTransition
//                = new TranslateTransition(Duration.millis(400), tabPaneProps);
//
//        // ScrollPane
//        TranslateTransition scrollTransition
//                = new TranslateTransition(Duration.millis(400), scrollPane);
//
//        // Preview
//        TranslateTransition previewTransition
//                = new TranslateTransition(Duration.millis(400), anchorImagePreview);
//
//        if (aberto) {
//            // Fechar painel
//            panelTransition.setToX(335);
//
//            scrollTransition.setToX(0);
//            previewTransition.setToX(0);
//
//        } else {
//            // Abrir painel
//            panelTransition.setToX(0);
//
//            scrollTransition.setToX(-295);
//            previewTransition.setToX(-295);
//        }
//
//        panelTransition.play();
//        scrollTransition.play();
//        previewTransition.play();
//    }
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
            lblStatus.setText("Atingiu o limite máximo de caracteres");
            return null;
        });
        txtAreaNotes.setTextFormatter(formatter);

        imageService = new ImageService(anchorImagePreview);
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
    // 🔥 MÉTODO RECURSIVO PARA MONTAR A ÁRVORE

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
        countCarouselHelper = 0;
        countNext = 0;
        countPrev = 0;
        key = 0;

        carouselHelper = new ArrayList<>();
        loadedThumbs.clear();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                List<Path> images = listImages(folder.toString());
                countCarouselHelper = images.size();
                for (Path img : images) {
                    carouselHelper.add(img);

                    Image image = new Image(img.toUri().toString(), 100, 100, true, true, true);
//
                    ImageView iv = new ImageView(image);
                    iv.setFitWidth(150);
                    iv.setFitHeight(150);
                    iv.setPreserveRatio(true);
//
//                    // opcional: estilo tipo card
                    HBox.setMargin(iv, new Insets(10, 15, 0, 15));

                    // estilo base (não selecionado)
                    iv.setStyle(""
                            + "-fx-cursor: hand;"
                            + "-fx-border-color: transparent;"
                            + "-fx-border-width: 5px;");

                    ImageThumb thumb = new ImageThumb(img, iv);
                    loadedThumbs.add(thumb);
                    iv.setOnMouseClicked((MouseEvent e) -> {
                        handleSelection(thumb, e);
                        auxPath = img;
                        imageService.setImagePreview(img.toString());
                        sliderRotate.setValue(0);
                        showProps(img);
                    });

                    Platform.runLater(() -> imageScroll.getChildren().add(iv));

                }

                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

//                imageService.setImagePreview(carouselHelper.get(countNext).toString());
    }

    private void showProps(Path path) {
        try {

            imgModel.fromImage(path.toString());
            Main.setData(imgModel);

            labMaker.setText(imgModel.getDeviceInfo().getMake() == null ? "N/A" : imgModel.getDeviceInfo().getMake());
            labModel.setText(imgModel.getDeviceInfo().getModel() == null ? "N/A" : imgModel.getDeviceInfo().getModel());
            labSoftware.setText(imgModel.getDeviceInfo().getSoftware() == null ? "N/A" : imgModel.getDeviceInfo().getSoftware());

            labLatitude.setText(String.valueOf(
                    String.format("%.4f",
                            imgModel.getGpsInfo().getLatitude() == null ? "0.11111" : imgModel.getGpsInfo().getLatitude()
                    )
            ));
            labLatitude.setText(imgModel.getGpsInfo().getLatitude() == null ? "N/A" : String.format("%.4f", imgModel.getGpsInfo().getLatitude()));
            labLongitude.setText(imgModel.getGpsInfo().getLongitude() == null ? "N/A" : String.format("%.4f", imgModel.getGpsInfo().getLongitude()));
            labAltitude.setText(imgModel.getGpsInfo().getAltitude() == null ? "N/A" : String.format("%.4f", imgModel.getGpsInfo().getAltitude()));
            labCountry.setText(imgModel.getGpsInfo().getCountry() == null ? "N/A" : imgModel.getGpsInfo().getCountry());
            labCountryCode.setText(imgModel.getGpsInfo().getCountryCode() == null ? "N/A" : imgModel.getGpsInfo().getCountryCode());
            labCity.setText(imgModel.getGpsInfo().getCity() == null ? "N/A" : imgModel.getGpsInfo().getCity());
            labRegion.setText(imgModel.getGpsInfo().getDistrict() == null ? "N/A" : imgModel.getGpsInfo().getDistrict());

            labExposureTime.setText(String.valueOf(imgModel.getImageSpecification().getExposureTime() == null ? "N/A" : imgModel.getImageSpecification().getExposureTime()));
            labShutterSpeedValue.setText(String.valueOf(imgModel.getImageSpecification().getShutterSpeedValue() == null ? "N/A" : imgModel.getImageSpecification().getShutterSpeedValue()));
            labISOSpeedRating.setText(String.valueOf(imgModel.getImageSpecification().getIsoSpeedRatings() == null ? "N/A" : imgModel.getImageSpecification().getIsoSpeedRatings()));
            labFocalLength.setText(String.valueOf(imgModel.getImageSpecification().getFocalLength() == null ? "N/A" : imgModel.getImageSpecification().getFocalLength()));
            labFocalLengthIn35mmFilm.setText(String.valueOf(imgModel.getImageSpecification().getFocalLengthIn35mmFilm() == null ? "N/A" : imgModel.getImageSpecification().getFocalLengthIn35mmFilm()));

            labDateTimeOriginal.setText(imgModel.getImageDate().getDateTimeOriginal() == null ? "N/A" : imgModel.getImageDate().getDateTimeOriginal());
            labDateTimeDigitized.setText(imgModel.getImageDate().getDateTimeDigitized() == null ? "N/A" : imgModel.getImageDate().getDateTimeDigitized());
            labOffsetTime.setText(imgModel.getImageDate().getOffsetTime() == null ? "N/A" : imgModel.getImageDate().getOffsetTime());
        } catch (Exception ex) {
//            System.err.println("Erro: " + ex);
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
//            System.err.println("____________________________________________________ ");
//            throw new RuntimeException("Erro ao inserir contacto fornecedor", ex);
//            throw new Exception("")
//            
        }

    }

    @FXML
    void next(ActionEvent evt) {
        nextImage();
    }

    void nextImage() {
        if (countNext < (countCarouselHelper - 1)) {
            countNext = carouselHelper.indexOf(auxPath) + 1;
            imageService.setImagePreview(carouselHelper.get(countNext).toString());
            sliderRotate.setValue(0);
            countPrev = countNext;
            showProps(carouselHelper.get(countNext));
            auxPath = carouselHelper.get(countNext);
        }
    }

    @FXML
    void prev(ActionEvent evt) {
        previuslyImage();
    }

    void previuslyImage() {
        if (countPrev == countNext && countPrev != 0) {
            countPrev = carouselHelper.indexOf(auxPath) - 1;
            imageService.setImagePreview(carouselHelper.get(countPrev).toString());
            sliderRotate.setValue(0);
            countNext = countPrev;
            showProps(carouselHelper.get(countPrev));
            auxPath = carouselHelper.get(countPrev);

        }
    }

    @FXML
    void saveImageDetail(ActionEvent evt) throws IOException, Exception {
//        imgModel.setNotes(txtAreaNotes.getText());
//        FIleDetailReport.setFileName(imgModel.getFileName());
//        FIleDetailReport.createFile(imgModel);
//        PDFReport r = new PDFReport(imgModel);

        // Usaer dentro de um loop para estrair todos nomes para pesquisar
//        String nome = selectedImages.get(2).path.getFileName().toString();
//        System.out.println("nome: " + nome);
//        System.out.println("size: " + selectedImages.size());
        if (selectedImages.size() > 1) {
            for (int i = 0; i < selectedImages.size(); i++) {
                imgModel.fromImage(selectedImages.get(i).path.toString());
                imgModel.setNotes(txtAreaNotes.getText());
                PDFReport pdfReport = new PDFReport(imgModel);
                if (pdfReport.closeDocumente()) {
                    Main.setData("Reports Genarated");
                    LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                }
            }
        } else {
            imgModel.setNotes(txtAreaNotes.getText());
            PDFReport r = new PDFReport(imgModel);
            if (r.closeDocumente()) {
                Main.setData("Report Genarated");
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
            }
        }
    }

    private static class ImageThumb {

        Path path;
        ImageView view;
        boolean selected;

        ImageThumb(Path path, ImageView view) {
            this.path = path;
            this.view = view;
            this.selected = false;
        }
    }

    private void handleSelection(ImageThumb thumb, MouseEvent e) {

        boolean ctrl = e.isControlDown();

        if (!ctrl) {
            // limpa seleção anterior
            selectedImages.forEach(t -> setSelectedStyle(t, false));
            selectedImages.clear();
        }

        if (thumb.selected) {
            thumb.selected = false;
            selectedImages.remove(thumb);
            setSelectedStyle(thumb, false);
        } else {
            thumb.selected = true;
            selectedImages.add(thumb);
            setSelectedStyle(thumb, true);
        }
    }

    private void setSelectedStyle(ImageThumb thumb, boolean selected) {

        if (selected) {
            thumb.view.setStyle(
                    " -fx-cursor: hand;"
                    + "-fx-border-color: #41c0b5;"
                    + "-fx-border-width: 3;"
                    + "-fx-effect: dropshadow(gaussian, rgba(0,170,255,0.6), 10, 0.3, 0, 0);"
            );
        } else {
            thumb.view.setStyle("-fx-cursor: hand; -fx-border-color: transparent;");
        }
    }

    public List<Path> getSelectedImagePaths() {
        return selectedImages.stream()
                .map(t -> t.path)
                .collect(java.util.stream.Collectors.toList());
    }

    @FXML
    void startCopy(ActionEvent event) {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Selecionar pasta de origem");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File origemFile = chooser.showDialog(stage);

        if (origemFile == null) {
            lblStatus.setText("Operação cancelada.");
            return;
        }

        File destinoFile = new File("C:/JMVIS");

        if (destinoFile == null) {
            lblStatus.setText("Operação cancelada.");
            return;
        }

        Path origem = origemFile.toPath();
        if (file == null) {
            lblStatus.setText("Destination not set.");
            return;
        }
        Path destino = file.toPath();

        CopyService service = new CopyService();
        Task<Void> task = service.copyImages(origem, destino);

        lblStatus.textProperty().bind(task.messageProperty());

        new Thread(task).start();
//        loadImagesAsync(destino);
        Main.setData(" Images Imported");
        LoadAndMoveUtilities.showAsPopUP(event, null);
        LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
    }

}
