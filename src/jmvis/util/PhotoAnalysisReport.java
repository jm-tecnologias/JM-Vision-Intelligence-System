/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

/**
 *
 * @author JM-Tecnologias
 */
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PhotoAnalysisReport {

    private static final Color PRIMARY_COLOR =
            WebColors.getRGBColor("#17B5C3");

    private static final Color LIGHT_BORDER =
            WebColors.getRGBColor("#D9D9D9");

    private static final Color LABEL_COLOR =
            WebColors.getRGBColor("#191919");

    private PdfFont regularFont;
    private PdfFont boldFont;

    private Document document;
    private PdfDocument pdfDocument;

    /**
     * Gera o relatório completo.
     *
     * @param destinationPath caminho do PDF de saída
     * @param logoPath        caminho do logótipo
     * @param photoPath       caminho da fotografia principal
     * @param metadataIcon    ícone dos metadados
     * @param gpsIcon         ícone do GPS
     * @param cameraIcon      ícone da câmara
     * @param integrityIcon   ícone da integridade
     */
    public void createReport(
            String destinationPath,
            String logoPath,
            String photoPath,
            String metadataIcon,
            String gpsIcon,
            String cameraIcon,
            String integrityIcon
    ) throws IOException {

        PdfWriter writer = new PdfWriter(destinationPath);

        pdfDocument = new PdfDocument(writer);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        document = new Document(pdfDocument, PageSize.A4);

        /*
         * Margens:
         * superior, direita, inferior e esquerda.
         */
        document.setMargins(25, 30, 20, 30);

        regularFont = PdfFontFactory.createFont(
                StandardFonts.COURIER
        );

        boldFont = PdfFontFactory.createFont(
                StandardFonts.COURIER_BOLD
        );

        createHeader(logoPath);

        createMainPhoto(photoPath);

        createInformationSections();

        createVerificationSection(
                metadataIcon,
                gpsIcon,
                cameraIcon,
                integrityIcon
        );

        document.close();

        System.out.println("Relatório criado em: " + destinationPath);
    }

    /**
     * Cria o cabeçalho superior.
     */
    private void createHeader(String logoPath) throws IOException {

        Table header = new Table(
                UnitValue.createPercentArray(
                        new float[]{53, 47}
                )
        );

        header.setWidth(UnitValue.createPercentValue(100));
        header.setMarginBottom(8);

        Cell companyCell = createCompanyInformation(logoPath);
        Cell systemCell = createSystemInformation();

        header.addCell(companyCell);
        header.addCell(systemCell);

        document.add(header);
    }

    /**
     * Cria o lado esquerdo do cabeçalho.
     */
    private Cell createCompanyInformation(String logoPath)
            throws IOException {

        Cell outerCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPadding(0);

        Table companyTable = new Table(
                UnitValue.createPercentArray(
                        new float[]{20, 80}
                )
        );

        companyTable.setWidth(UnitValue.createPercentValue(100));

        Cell logoCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPadding(0)
                .setVerticalAlignment(VerticalAlignment.TOP);

        if (fileExists(logoPath)) {

            Image logo = new Image(
                    ImageDataFactory.create(logoPath)
            );

            logo.scaleToFit(58, 58);

            logoCell.add(logo);
        }

        Cell companyNameCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPaddingLeft(4)
                .setPaddingTop(9);

        companyNameCell.add(
                new Paragraph("JM Tecnologias")
                        .setFont(boldFont)
                        .setFontSize(14)
                        .setFontColor(PRIMARY_COLOR)
                        .setMargin(0)
        );

        companyNameCell.add(
                new Paragraph("JM-Tecnologias, SU, LDA")
                        .setFont(regularFont)
                        .setFontSize(8)
                        .setFontColor(LABEL_COLOR)
                        .setMarginTop(4)
                        .setMarginBottom(0)
        );

        companyTable.addCell(logoCell);
        companyTable.addCell(companyNameCell);

        outerCell.add(companyTable);

        outerCell.add(
                new Paragraph(
                        "Maputo | www.jm-tecnologias.co.mz | "
                                + "comercial@jm-tecnologias.co.mz"
                )
                        .setFont(regularFont)
                        .setFontSize(7.5f)
                        .setMarginTop(2)
                        .setMarginBottom(0)
        );

        return outerCell;
    }

    /**
     * Cria o lado direito do cabeçalho.
     */
    private Cell createSystemInformation() {

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Cell cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(14)
                .setPaddingRight(0)
                .setTextAlignment(TextAlignment.RIGHT);

        cell.add(
                new Paragraph(
                        "JM Vision Intelligence System"
                )
                        .setFont(boldFont)
                        .setFontSize(13)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setMargin(0)
        );

        cell.add(
                new Paragraph(
                        "Issue Date: " + today.format(formatter)
                )
                        .setFont(regularFont)
                        .setFontSize(8)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setMarginTop(7)
                        .setMarginBottom(0)
        );

        return cell;
    }

    /**
     * Adiciona a fotografia principal.
     */
    private void createMainPhoto(String photoPath)
            throws IOException {

        Cell photoCell = new Cell()
                .setBorder(
                        new SolidBorder(LIGHT_BORDER, 0.8f)
                )
                .setPadding(18)
                .setTextAlignment(TextAlignment.CENTER);

        if (fileExists(photoPath)) {

            Image photo = new Image(
                    ImageDataFactory.create(photoPath)
            );

            /*
             * Área aproximada da imagem no relatório.
             */
            photo.scaleToFit(500, 285);

            photo.setHorizontalAlignment(
                    HorizontalAlignment.CENTER
            );

            photoCell.add(photo);

        } else {

            photoCell.add(
                    new Paragraph("PHOTO NOT AVAILABLE")
                            .setFont(boldFont)
                            .setFontSize(12)
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFontColor(ColorConstants.GRAY)
                            .setMinHeight(250)
            );
        }

        Table imageTable = new Table(1);

        imageTable.setWidth(UnitValue.createPercentValue(100));
        imageTable.setMarginBottom(2);
        imageTable.addCell(photoCell);

        document.add(imageTable);
    }

    /**
     * Cria as quatro áreas centrais.
     */
    private void createInformationSections() {

        Table mainTable = new Table(
                UnitValue.createPercentArray(
                        new float[]{50, 50}
                )
        );

        mainTable.setWidth(UnitValue.createPercentValue(100));
        mainTable.setMarginTop(0);

        Cell photoSummary = createSectionCell(
                "PHOTO SUMMARY",
                createPhotoSummaryTable()
        );

        Cell cameraInformation = createSectionCell(
                "CAMERA INFORMATION",
                createCameraInformationTable()
        );

        Cell geolocationData = createSectionCell(
                "GEOLOCATION DATA",
                createGeolocationTable()
        );

        Cell notes = createNotesCell();

        mainTable.addCell(photoSummary);
        mainTable.addCell(cameraInformation);
        mainTable.addCell(geolocationData);
        mainTable.addCell(notes);

        document.add(mainTable);
    }

    /**
     * Cria uma célula de secção com título e conteúdo.
     */
    private Cell createSectionCell(
            String title,
            Table contentTable
    ) {

        Cell cell = new Cell()
                .setBorder(
                        new SolidBorder(LIGHT_BORDER, 0.8f)
                )
                .setPadding(4)
                .setVerticalAlignment(VerticalAlignment.TOP);

        cell.add(
                new Paragraph(title)
                        .setFont(boldFont)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginTop(0)
                        .setMarginBottom(5)
        );

        cell.add(contentTable);

        return cell;
    }

    /**
     * Informações resumidas da fotografia.
     */
    private Table createPhotoSummaryTable() {

        Table table = createDetailsTable();

        addDetailRow(table, "File Name:", "Mula4");
        addDetailRow(table, "Capture Date:", "2022:11:30 18:29:28");
        addDetailRow(table, "Device:", "Hasselblad");
        addDetailRow(table, "Software:", "10.21.51.42");
        addDetailRow(table, "Format:", "JPG");
        addDetailRow(table, "Dimensions:", "5280 × 2970");
        addDetailRow(table, "File size:", "10.0 MB");

        return table;
    }

    /**
     * Informações da câmara.
     */
    private Table createCameraInformationTable() {

        Table table = createDetailsTable();

        addDetailRow(table, "Camera/Model:", "L2D-20c");
        addDetailRow(table, "ISO Speed Ratings:", "100.0");
        addDetailRow(table, "Exposure Time:", "0.04");
        addDetailRow(table, "Shutter Speed Value:", "4.6439");
        addDetailRow(table, "Focal Length:", "12.29");
        addDetailRow(table, "Focal Length In 35mm Film:", "24.0");
        addDetailRow(table, "White Balance:", "0.0");

        return table;
    }

    /**
     * Informações de geolocalização.
     */
    private Table createGeolocationTable() {

        Table table = createDetailsTable();

        addDetailRow(table, "Latitude:", "-25.9576");
        addDetailRow(table, "Longitude:", "32.6067");
        addDetailRow(table, "Altitude:", "7.4720");
        addDetailRow(table, "Country/Code:", "N/A-N/A");
        addDetailRow(table, "City/Region:", "N/A");
        addDetailRow(table, "District:", "N/A");

        return table;
    }

    /**
     * Cria a secção de notas.
     */
    private Cell createNotesCell() {

        Cell cell = new Cell()
                .setBorder(
                        new SolidBorder(LIGHT_BORDER, 0.8f)
                )
                .setPadding(4)
                .setMinHeight(120)
                .setVerticalAlignment(VerticalAlignment.TOP);

        cell.add(
                new Paragraph("Notes")
                        .setFont(boldFont)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginTop(0)
                        .setMarginBottom(10)
        );

        cell.add(
                new Paragraph("teste")
                        .setFont(regularFont)
                        .setFontSize(8.5f)
                        .setMargin(0)
        );

        return cell;
    }

    /**
     * Cria uma tabela para pares:
     * rótulo e valor.
     */
    private Table createDetailsTable() {

        Table table = new Table(
                UnitValue.createPercentArray(
                        new float[]{34, 66}
                )
        );

        table.setWidth(UnitValue.createPercentValue(100));

        return table;
    }

    /**
     * Adiciona uma linha de detalhes.
     */
    private void addDetailRow(
            Table table,
            String label,
            String value
    ) {

        Cell labelCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPadding(0)
                .setPaddingBottom(2)
                .setVerticalAlignment(VerticalAlignment.TOP);

        labelCell.add(
                new Paragraph(label)
                        .setFont(regularFont)
                        .setFontSize(8.2f)
                        .setMargin(0)
        );

        Cell valueCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPadding(0)
                .setPaddingBottom(2)
                .setVerticalAlignment(VerticalAlignment.TOP);

        valueCell.add(
                new Paragraph(value)
                        .setFont(regularFont)
                        .setFontSize(8.2f)
                        .setMargin(0)
        );

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    /**
     * Cria a barra e os indicadores de verificação.
     */
    private void createVerificationSection(
            String metadataIcon,
            String gpsIcon,
            String cameraIcon,
            String integrityIcon
    ) throws IOException {

        Paragraph statusTitle =
                new Paragraph("VERIFICATION STATUS")
                        .setFont(boldFont)
                        .setFontSize(10)
                        .setFontColor(ColorConstants.WHITE)
                        .setBackgroundColor(PRIMARY_COLOR)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPaddingTop(3)
                        .setPaddingBottom(3)
                        .setMarginTop(0)
                        .setMarginBottom(0);

        document.add(statusTitle);

        Table verificationTable = new Table(
                UnitValue.createPercentArray(
                        new float[]{25, 25, 25, 25}
                )
        );

        verificationTable.setWidth(
                UnitValue.createPercentValue(100)
        );

        verificationTable.setMarginTop(0);

        verificationTable.addCell(
                createVerificationItem(
                        metadataIcon,
                        "Meta Data"
                )
        );

        verificationTable.addCell(
                createVerificationItem(
                        gpsIcon,
                        "GPS Data"
                )
        );

        verificationTable.addCell(
                createVerificationItem(
                        cameraIcon,
                        "Camera Data"
                )
        );

        verificationTable.addCell(
                createVerificationItem(
                        integrityIcon,
                        "File Integrity"
                )
        );

        document.add(verificationTable);
    }

    /**
     * Cria um indicador individual de verificação.
     */
    private Cell createVerificationItem(
            String iconPath,
            String title
    ) throws IOException {

        Cell cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPaddingTop(2)
                .setPaddingBottom(0)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        if (fileExists(iconPath)) {

            Image icon = new Image(
                    ImageDataFactory.create(iconPath)
            );

            icon.scaleToFit(27, 27);

            icon.setHorizontalAlignment(
                    HorizontalAlignment.CENTER
            );

            cell.add(icon);

        } else {

            cell.add(
                    new Paragraph("✓")
                            .setFont(boldFont)
                            .setFontSize(16)
                            .setFontColor(PRIMARY_COLOR)
                            .setTextAlignment(TextAlignment.CENTER)
                            .setMargin(0)
            );
        }

        cell.add(
                new Paragraph(title)
                        .setFont(regularFont)
                        .setFontSize(8)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginTop(1)
                        .setMarginBottom(0)
        );

        return cell;
    }

    /**
     * Confirma se um ficheiro existe.
     */
    private boolean fileExists(String path) {

        return path != null
                && !path.isEmpty()
                && new File(path).exists();
    }

    /**
     * Exemplo de execução.
     */
    public static void main(String[] args) {

        PhotoAnalysisReport report =
                new PhotoAnalysisReport();

        try {

            report.createReport(
                    "C:\\Users\\JM-Tecnologias\\Documents\\JM Vision Intelligence\\Report\\photo-analysis-report.pdf",

                    "src/jmvis/assets/logo.png",
                    "/jmvis/assets/icons/Mula0.jpg",

                    "/jmvis/assets/icons/metadata.png",
                    "/jmvis/assets/icons/geographical.png",
                    "/jmvis/assets/icons/camera.png",
                    "/jmvis/assets/icons/trustworthy.png"
            );

        } catch (IOException exception) {

            System.err.println(
                    "Erro ao gerar o relatório: "
                            + exception.getMessage()
            );

            exception.printStackTrace();
        }
    }
}