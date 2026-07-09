/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import javafx.scene.control.Label;

/**
 * Binds image metadata values to the labels used by the details panel.
 *
 * @author JM-Tecnologias
 */
public class ImageDetailsBinder {

    private final Label labMaker;
    private final Label labModel;
    private final Label labSoftware;
    private final Label labLatitude;
    private final Label labLongitude;
    private final Label labAltitude;
    private final Label labCountry;
    private final Label labCountryCode;
    private final Label labCity;
    private final Label labRegion;
    private final Label labExposureTime;
    private final Label labShutterSpeedValue;
    private final Label labISOSpeedRating;
    private final Label labFocalLength;
    private final Label labFocalLengthIn35mmFilm;
    private final Label labDateTimeOriginal;
    private final Label labDateTimeDigitized;
    private final Label labOffsetTime;

    public ImageDetailsBinder(
            Label labMaker,
            Label labModel,
            Label labSoftware,
            Label labLatitude,
            Label labLongitude,
            Label labAltitude,
            Label labCountry, 
            Label labCountryCode,
            Label labCity,
            Label labRegion,
            Label labExposureTime,
            Label labShutterSpeedValue,
            Label labISOSpeedRating,
            Label labFocalLength,
            Label labFocalLengthIn35mmFilm,
            Label labDateTimeOriginal,
            Label labDateTimeDigitized,
            Label labOffsetTime) {
        this.labMaker = labMaker;
        this.labModel = labModel;
        this.labSoftware = labSoftware;
        this.labLatitude = labLatitude;
        this.labLongitude = labLongitude;
        this.labAltitude = labAltitude;
        this.labCountry = labCountry;
        this.labCountryCode = labCountryCode;
        this.labCity = labCity;
        this.labRegion = labRegion;
        this.labExposureTime = labExposureTime;
        this.labShutterSpeedValue = labShutterSpeedValue;
        this.labISOSpeedRating = labISOSpeedRating;
        this.labFocalLength = labFocalLength;
        this.labFocalLengthIn35mmFilm = labFocalLengthIn35mmFilm;
        this.labDateTimeOriginal = labDateTimeOriginal;
        this.labDateTimeDigitized = labDateTimeDigitized;
        this.labOffsetTime = labOffsetTime;
    }

    public void show(ImageModel imageModel) {
        bindDeviceInfo(imageModel.getDeviceInfo());
        bindGpsInfo(imageModel.getGpsInfo());
        bindImageSpecification(imageModel.getImageSpecification());
        bindImageDate(imageModel.getImageDate());
    }

    private void bindDeviceInfo(ImageModel.DeviceInfo deviceInfo) {
        labMaker.setText(text(deviceInfo == null ? null : deviceInfo.getMake()));
        labModel.setText(text(deviceInfo == null ? null : deviceInfo.getModel()));
        labSoftware.setText(text(deviceInfo == null ? null : deviceInfo.getSoftware()));
    }

    private void bindGpsInfo(ImageModel.GPSInfo gpsInfo) {
        labLatitude.setText(number(gpsInfo == null ? null : gpsInfo.getLatitude(), "%.4f"));
        labLongitude.setText(number(gpsInfo == null ? null : gpsInfo.getLongitude(), "%.4f"));
        labAltitude.setText(number(gpsInfo == null ? null : gpsInfo.getAltitude(), "%.4f"));
        labCountry.setText(text(gpsInfo == null ? null : gpsInfo.getCountry()));
        labCountryCode.setText(text(gpsInfo == null ? null : gpsInfo.getCountryCode()));
        labCity.setText(text(gpsInfo == null ? null : gpsInfo.getCity()));
        labRegion.setText(text(gpsInfo == null ? null : gpsInfo.getDistrict()));
    }

    private void bindImageSpecification(ImageModel.ImageSpecification specification) {
        labExposureTime.setText(number(specification == null ? null : specification.getExposureTime(), "%.4f"));
        labShutterSpeedValue.setText(number(specification == null ? null : specification.getShutterSpeedValue(), "%.4f"));
        labISOSpeedRating.setText(number(specification == null ? null : specification.getIsoSpeedRatings(), "%.2f"));
        labFocalLength.setText(number(specification == null ? null : specification.getFocalLength(), "%.2f"));
        labFocalLengthIn35mmFilm.setText(number(specification == null ? null : specification.getFocalLengthIn35mmFilm(), "%.4f"));
    }

    private void bindImageDate(ImageModel.ImageDate imageDate) {
        labDateTimeOriginal.setText(text(imageDate == null ? null : imageDate.getDateTimeOriginal()));
        labDateTimeDigitized.setText(text(imageDate == null ? null : imageDate.getDateTimeDigitized()));
        labOffsetTime.setText(text(imageDate == null ? null : imageDate.getOffsetTime()));
    }

    private String text(String value) {
        return value == null || value.trim().isEmpty() ? "N/A" : value;
    }

    private String number(Double value, String format) {
        return value == null ? "N/A" : String.format(format, value);
    }
}
