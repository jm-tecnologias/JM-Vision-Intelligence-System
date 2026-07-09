/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import java.io.FileNotFoundException;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import javafx.stage.Modality;
import jmvis.Main;
import jmvis.model.ImageModel;

/**
 *
 * @author JM-Tecnologias
 */
public class PDFReport {

    private Path path = Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/Report/");
    private PdfWriter pdfwriter;
    private PdfDocument pdfDocs;
    private Document document;
    private PdfFont bold;
    private PdfFont normal;

    public PDFReport(ImageModel imgModel) throws MalformedURLException, IOException {
        this.bold = PdfFontFactory.createFont(
                StandardFonts.COURIER_BOLD);
        this.normal = PdfFontFactory.createFont(
                StandardFonts.COURIER);
        String[] reportName = imgModel.getFileName().split("\\.");
        try {
            this.pdfwriter = new PdfWriter(this.path.toString() + "\\" + reportName[0] + ".pdf");
            this.pdfDocs = new PdfDocument(this.pdfwriter);
            // Adiciona o evento de fundo
            this.pdfDocs.addEventHandler(
                    PdfDocumentEvent.START_PAGE,
                    new FundoPaginaEvent(Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/FileImageSystem/bg1.jpg").toString())
            );
            this.pdfDocs.addNewPage();
            this.document = new Document(pdfDocs);
            // Adiciona logo no header
            ImageData data = ImageDataFactory.create(Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/FileImageSystem/LOGO.png").toString());
            Image img = new Image(data);
            img.scaleToFit(150, 150);           // largura x altura máximas
            img.setFixedPosition(0, 730);      // posição absoluta (x, y)
            img.setHorizontalAlignment(HorizontalAlignment.CENTER); // centro
            this.document.add(img);
            // metodos que executam outras funcoes aqui
            createHeader();

            this.document.add(
                    new Paragraph(" ")
            //                            .setBorderBottom(new SolidBorder(1))
            //                            .setWidth(UnitValue.createPercentValue(100))
            );

            addImagePreview(imgModel);
            createCards(imgModel);
            createVerificationStatus();

            this.document.add(new Paragraph("© 2026 JM Tecnologias — Todos os direitos reservados.").setTextAlignment(TextAlignment.CENTER).setFontColor(WebColors.getRGBColor("#e5e5e5")).setFontSize(8));
            closeDocumente();
        } catch (FileNotFoundException ex) {
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/view/ConfirForm.fxml");
//            throw new IllegalStateException("M"+ex.getMessage());
        }
    }

    public boolean closeDocumente() {
        this.document.close();
        return true;
    }

    private void createHeader() throws IOException {
        LocalDate hoje = LocalDate.now();
        float[] columnWidths = {50f, 50f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Linha 1
        Color myColor = WebColors.getRGBColor("#17b5c3");
//        Image icon = new Image(ImageDataFactory.create("src/jmvis/assets/ARO LOGO FINAL.png"))
//                .scaleToFit(50, 50); // tamanho do ícone
////        .setMarginRight(5);  // espaço entre ícone e texto
//
//        Cell c1 = new Cell()
//                .add(icon)
//                .add(new Paragraph("AIR REACH OUT")
//                        .setFont(bold)
//                        .setFontSize(16)
//                        .setFontColor(myColor))
//                //                .setBackgroundColor(myColor)
//                .setBorder(Border.NO_BORDER);

//        Image icon = new Image(ImageDataFactory.create(Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/FileImageSystem/LOGO.png").toString()))
//                .scaleToFit(50, 50);
        Paragraph p = new Paragraph()
                //                .add(icon)
                .add(" JM Tecnologias")
                .setFont(bold)
                .setFontSize(16)
                .setFontColor(myColor);
//                .setVerticalAlignment(VerticalAlignment.TOP);

        Cell c1 = new Cell()
                .add(new Paragraph("JM Tecnologias")
                        .setFont(bold)
                        .setFontSize(14)
                        .setFontColor(myColor))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER);

        Cell c2 = new Cell()
                .add(new Paragraph("JM Vision Intelligence System")
                        .setFont(bold)
                        .setFontSize(14)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER);

        // Linha 2
        Cell c3 = new Cell().add(new Paragraph(". JM-Tecnologias, SU, LDA")
                .setFont(normal)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER);

        Cell c4 = new Cell().add(new Paragraph("Issue Date:" + hoje)
                .setFont(normal)
                .setFontSize(10)
                .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER);

        // Linha 3
        Cell c5 = new Cell(1, 2).add(new Paragraph("Maputo | www.jm-tecnologias.co.mz | comercial@jm-tecnologias.co.mz")
                .setFont(normal)
                .setFontSize(10)
                .setFontColor(ColorConstants.BLACK)
        ).setBorder(Border.NO_BORDER);

        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);

        this.document.add(table);
    }

    private void addImagePreview(ImageModel imgModel) throws MalformedURLException {
        ImageData imgData = ImageDataFactory.create(imgModel.getAbsolutePath());
        Image img = new Image(imgData);

        // definir tamanho fixo
        img.setWidth(480);
        img.setHeight(280);

        // alinhamento
        img.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // container
        Div container = new Div();
        Color cor = WebColors.getRGBColor("#e5e5e5");
        container.setBorder(new SolidBorder(cor, 1));
        container.setPadding(5); // agora funciona

        container.add(img);

        this.document.add(container);
    }

    private void createCards(ImageModel imgModel) throws IOException {
        float[] columnWidths = {50f, 50f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));
        table.addCell(createFotoSummary(imgModel));
        table.addCell(createCameraInfo(imgModel));
        table.addCell(createGEOData(imgModel));
        table.addCell(Notes(imgModel));
        table.setBorder(Border.NO_BORDER);
        table.getChildren().forEach(cell -> {
            ((Cell) cell).setBorder(Border.NO_BORDER);
        });
        this.document.add(table);
    }

    private Table createFotoSummary(ImageModel imgModel) throws IOException {
        float[] columnWidths = {30f, 70f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        Color myColor = WebColors.getRGBColor("#e5e5e5");

        Cell header = new Cell(1, 2) // ⬅️ aqui está o "colspan"
                .add(new Paragraph("PHOTO SUMMARY")
                        .setFont(bold)
                        .setFontSize(12)
                        .setFontColor(ColorConstants.BLACK))
                //                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER);
        // Linha 1
        Cell c1 = new Cell()
                .add(new Paragraph("File Name:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);
        String[] nome = imgModel.getFileName().split("\\.");
        Cell c2 = new Cell()
                .add(new Paragraph(nome[0])
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK)
                )
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 2
        Cell c3 = new Cell()
                .add(new Paragraph("Capture Date:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c4 = new Cell()
                .add(new Paragraph(imgModel.getImageDate().getDateTimeOriginal() == null ? "N/A" : imgModel.getImageDate().getDateTimeOriginal())
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 3
        Cell c5 = new Cell()
                .add(new Paragraph("Device:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c6 = new Cell()
                .add(new Paragraph(imgModel.getDeviceInfo().getMake() == null ? "N/A" : imgModel.getDeviceInfo().getMake())
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 4
        Cell c7 = new Cell()
                .add(new Paragraph("Software:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c8 = new Cell()
                .add(new Paragraph(imgModel.getDeviceInfo().getSoftware() == null ? "N/A" : imgModel.getDeviceInfo().getSoftware())
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 5
        Cell c9 = new Cell()
                .add(new Paragraph("Format:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c10 = new Cell()
                .add(new Paragraph(nome[1] == null ? "N/A" : (nome[1]))
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 6
        Cell c11 = new Cell()
                .add(new Paragraph("Dimensions:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c12 = new Cell()
                .add(new Paragraph(String.valueOf(
                        String.format("%.4f",
                                imgModel.getImageSpecification().getExifImageWidth()) == null ? "0.0"
                        : String.format("%.4f", imgModel.getImageSpecification().getExifImageWidth()))
                        + " x " + String.valueOf(
                                String.format("%.4f",
                                        imgModel.getImageSpecification().getExifImageHeight()) == null ? "0.0"
                                : String.format("%.4f", imgModel.getImageSpecification().getExifImageHeight()))
                ).setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 7
        Cell c13 = new Cell()
                .add(new Paragraph("File size:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c14 = new Cell()
                .add(new Paragraph(String.format("%.1f", imgModel.getSize()) == null ? "0.0" : String.format("%.1f", imgModel.getSize()) + "MB")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        table.addCell(header);
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);
        table.addCell(c8);
        table.addCell(c9);
        table.addCell(c10);
        table.addCell(c11);
        table.addCell(c12);
        table.addCell(c13);
        table.addCell(c14);

        Border solidBorder = new SolidBorder(myColor, 2);
        table.setBorder(solidBorder);
        table.getChildren().forEach(cell -> {
            ((Cell) cell).setBorder(Border.NO_BORDER);
        });

        return table;
    }

    private Table createCameraInfo(ImageModel imgModel) throws IOException {
        float[] columnWidths = {50f, 50f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        Color myColor = WebColors.getRGBColor("#e5e5e5");

        Cell header = new Cell(1, 2) // ⬅️ aqui está o "colspan"
                .add(new Paragraph("CAMERA INFORMATION")
                        .setFont(bold)
                        .setFontSize(12)
                        .setFontColor(ColorConstants.BLACK))
                //                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER);
        // Linha 1
        Cell c1 = new Cell()
                .add(new Paragraph("Camera/Model:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c2 = new Cell()
                .add(new Paragraph(imgModel.getDeviceInfo().getMake() == null ? "N/A" : imgModel.getDeviceInfo().getMake() + "-" + imgModel.getDeviceInfo().getModel() == null ? "N/A" : imgModel.getDeviceInfo().getModel())
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 2
        Cell c3 = new Cell()
                .add(new Paragraph("ISO Speed Ratings:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c4 = new Cell()
                .add(new Paragraph(imgModel.getImageSpecification().getIsoSpeedRatings() == null ? "N/A" : imgModel.getImageSpecification().getIsoSpeedRatings() + "")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 3
        Cell c5 = new Cell()
                .add(new Paragraph("Exposure Time:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c6 = new Cell()
                .add(new Paragraph(imgModel.getImageSpecification().getExposureTime() == null ? "N/A" : imgModel.getImageSpecification().getExposureTime() + "")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 4
        Cell c7 = new Cell()
                .add(new Paragraph("Shutter Speed Value:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c8 = new Cell()
                .add(new Paragraph(imgModel.getImageSpecification().getShutterSpeedValue() == null ? "N/A" : imgModel.getImageSpecification().getShutterSpeedValue() + "")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 5
        Cell c9 = new Cell()
                .add(new Paragraph("Focal Length:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c10 = new Cell()
                .add(new Paragraph(imgModel.getImageSpecification().getFocalLength() == null ? "N/A" : imgModel.getImageSpecification().getFocalLength() + "")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 6
        Cell c11 = new Cell()
                .add(new Paragraph("Focal Length In 35mm Film:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c12 = new Cell()
                .add(new Paragraph(imgModel.getImageSpecification().getFocalLengthIn35mmFilm() == null ? "N/A" : imgModel.getImageSpecification().getFocalLengthIn35mmFilm() + "")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 7
        Cell c13 = new Cell()
                .add(new Paragraph("White Balance:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c14 = new Cell()
                .add(new Paragraph(imgModel.getImageSpecification().getWhiteBalance() == null ? "N/A" : imgModel.getImageSpecification().getWhiteBalance() + "")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        table.addCell(header);
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);
        table.addCell(c8);
        table.addCell(c9);
        table.addCell(c10);
        table.addCell(c11);
        table.addCell(c12);
        table.addCell(c13);
        table.addCell(c14);

        Border solidBorder = new SolidBorder(myColor, 2);
        table.setBorder(solidBorder);
        table.getChildren().forEach(cell -> {
            ((Cell) cell).setBorder(Border.NO_BORDER);
        });

        return table;
    }

    private Table createGEOData(ImageModel imgModel) throws IOException {
        float[] columnWidths = {30f, 70f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        Color myColor = WebColors.getRGBColor("#e5e5e5");

        Cell header = new Cell(1, 2) // ⬅️ aqui está o "colspan"
                .add(new Paragraph("GEOLOCATION DATA")
                        .setFont(bold)
                        .setFontSize(12)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER);
        // Linha 1
        Cell c1 = new Cell()
                .add(new Paragraph("Latitude:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);
        Cell c2 = new Cell()
                .add(new Paragraph(String.valueOf(imgModel.getGpsInfo().getLatitude() == null ? "N/A" : String.format("%.4f", imgModel.getGpsInfo().getLatitude())))
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 2
        Cell c3 = new Cell()
                .add(new Paragraph("Longitude:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c4 = new Cell()
                .add(new Paragraph(String.valueOf(imgModel.getGpsInfo().getLongitude() == null ? "N/A" : String.format("%.4f", imgModel.getGpsInfo().getLongitude())))
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 3
        Cell c5 = new Cell()
                .add(new Paragraph("Altitude:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c6 = new Cell()
                .add(new Paragraph(String.valueOf(imgModel.getGpsInfo().getAltitude() == null ? "N/A" : String.format("%.4f", imgModel.getGpsInfo().getAltitude())))
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 4
        Cell c7 = new Cell()
                .add(new Paragraph("Country/Code:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c8 = new Cell()
                .add(new Paragraph((imgModel.getGpsInfo().getCountry() == null ? "N/A" : imgModel.getGpsInfo().getCountry()) + "-" + (imgModel.getGpsInfo().getCountryCode() == null ? "N/A" : imgModel.getGpsInfo().getCountryCode()))
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 5
        Cell c9 = new Cell()
                .add(new Paragraph("City/Region:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);
        Cell c10 = new Cell()
                .add(new Paragraph(imgModel.getGpsInfo().getCity() == null ? "N/A" : imgModel.getGpsInfo().getCity())
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        // Linha 6
        Cell c11 = new Cell()
                .add(new Paragraph("District:")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        Cell c12 = new Cell()
                .add(new Paragraph(imgModel.getGpsInfo().getDistrict() == null ? "N/A" : imgModel.getGpsInfo().getDistrict())
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.LEFT);

        table.addCell(header);
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);
        table.addCell(c8);
        table.addCell(c9);
        table.addCell(c10);
        table.addCell(c11);
        table.addCell(c12);

        Border solidBorder = new SolidBorder(myColor, 2);
        table.setBorder(solidBorder);
        table.getChildren().forEach(cell -> {
            ((Cell) cell).setBorder(Border.NO_BORDER);
        });

        return table;
    }

    private Table Notes(ImageModel imgModel) throws IOException {
        float[] columnWidths = {50f, 50f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        Color myColor = WebColors.getRGBColor("#e5e5e5");

        Cell header = new Cell(1, 2) // ⬅️ aqui está o "colspan"
                .add(new Paragraph("Notes")
                        .setFont(bold)
                        .setFontSize(12)
                        .setFontColor(ColorConstants.BLACK))
                //                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER);
        Cell notes = new Cell(1, 2) // ⬅️ aqui está o "colspan"
                .add(new Paragraph(imgModel.getNotes())
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                //                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.LEFT);

        table.addCell(header);
        table.addCell(notes);

        table.setBorder(Border.NO_BORDER);
        table.getChildren().forEach(cell -> {
            ((Cell) cell).setBorder(Border.NO_BORDER);
        });
        return table;
    }

    private void createVerificationStatus() throws IOException {
        float[] columnWidths = {25f, 25f, 25f, 25f};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        Color myColor = WebColors.getRGBColor("#17b5c3");

        Cell header = new Cell(1, 4) // ⬅️ aqui está o "colspan"
                .add(new Paragraph("VERIFICATION STATUS")
                        .setFont(bold)
                        .setFontSize(12)
                        .setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER);

        Image img = new Image(ImageDataFactory.create(Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/FileImageSystem/metadata.png").toString()));

        Cell ico1 = new Cell()
                .add(img
                        .scaleToFit(20, 20)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);

        Image img2 = new Image(ImageDataFactory.create(Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/FileImageSystem/geographical.png").toString()));
        Cell ico2 = new Cell()
                .add(img2
                        .scaleToFit(20, 20)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);

        Image img3 = new Image(ImageDataFactory.create(Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/FileImageSystem/camera.png").toString()));
        Cell ico3 = new Cell()
                .add(img3
                        .scaleToFit(20, 20)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                )
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);

        Image img4 = new Image(ImageDataFactory.create(Paths.get(System.getProperty("user.home"), "Documents", "JM Vision Intelligence/FileImageSystem/trustworthy.png").toString()));
        Cell ico4 = new Cell()
                .add(img4
                        .scaleToFit(20, 20)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                )
                //                .setBackgroundColor(myColor)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBorder(Border.NO_BORDER);

        table.addCell(header);
        table.addCell(ico1);
        table.addCell(ico2);
        table.addCell(ico3);
        table.addCell(ico4);

        Cell c1 = new Cell()
                .add(new Paragraph("Meta Data")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER);
        Cell c2 = new Cell()
                .add(new Paragraph("GPS Data")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER);
        Cell c3 = new Cell()
                .add(new Paragraph("Camera Data")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER);
        Cell c4 = new Cell()
                .add(new Paragraph("File Integrity")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER);

        Cell c5 = new Cell()
                .add(new Paragraph("Verified")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER);
        Cell c6 = new Cell()
                .add(new Paragraph("Verified")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER);
        Cell c7 = new Cell()
                .add(new Paragraph("Verified")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER);
        Cell c8 = new Cell()
                .add(new Paragraph("Verified")
                        .setFont(bold)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.BLACK))
                .setTextAlignment(TextAlignment.CENTER);
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
//        table.addCell(c5);
//        table.addCell(c6);
//        table.addCell(c7);
//        table.addCell(c8);

        table.setBorder(Border.NO_BORDER);
        table.getChildren().forEach(cell -> {
            ((Cell) cell).setBorder(Border.NO_BORDER);
        });
        this.document.add(table);
    }

//    public static void main(String[] args) throws MalformedURLException, IOException, Exception {
//        ImageModel imgModel = new ImageModel();
//        imgModel.setNotes("Notas de teste para report");
//        imgModel.fromImage("C:\\JMVIS\\File Explore\\tr\\DJI_0593.JPG");
//        PDFReport r = new PDFReport(imgModel);
//    }
}