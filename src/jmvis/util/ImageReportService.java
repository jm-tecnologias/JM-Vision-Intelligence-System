/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import java.nio.file.Path;
import java.util.List;

/**
 * Service responsible for preparing image data and generating PDF reports.
 *
 * @author JM-Tecnologias
 */
public class ImageReportService {

    private final ImageMetadataService imageMetadataService;

    public ImageReportService(ImageMetadataService imageMetadataService) {
        this.imageMetadataService = imageMetadataService;
    }

    public int generateReports(List<Path> selectedImages, ImageModel currentImage, String notes) throws Exception {
        if (selectedImages != null && selectedImages.size() > 1) {
            return generateSelectedReports(selectedImages, notes);
        }

        return generateCurrentReport(currentImage, notes);
    }

    private int generateSelectedReports(List<Path> selectedImages, String notes) throws Exception {
        int generatedReports = 0;

        for (Path path : selectedImages) {
            ImageModel imageModel = imageMetadataService.analyze(path.toString());
            imageModel.setNotes(notes);
            new PDFReport(imageModel);
            generatedReports++;
        }

        return generatedReports;
    }

    private int generateCurrentReport(ImageModel currentImage, String notes) throws Exception {
        if (currentImage == null || currentImage.getAbsolutePath() == null) {
            throw new IllegalArgumentException("Image not selected");
        }

        currentImage.setNotes(notes);
        new PDFReport(currentImage);
        return 1;
    }
}
