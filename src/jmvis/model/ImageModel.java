/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.model;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import jmvis.service.ImageMetadataService;

/**
 *
 * @author JM-Tecnologias
 */
public class ImageModel implements Serializable {

    private String fileName;
    private String absolutePath;
    private double size;
    private String notes;
    private DeviceInfo deviceInfo;
    private GPSInfo gpsInfo;
    private ImageSpecification imageSpecification;
    private ImageDate imageDate;

    public ImageModel() {
        resetDetails();
    }

    public final void resetDetails() {
        this.deviceInfo = new DeviceInfo();
        this.gpsInfo = new GPSInfo(null, null, null);
        this.imageSpecification = new ImageSpecification();
        this.imageDate = new ImageDate();
    }

    public class DeviceInfo implements Serializable {

        private String make;
        private String model;
        private String software;

        public void setMake(String make) {
            this.make = make;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setSoftware(String software) {
            this.software = software;
        }

        public String getMake() {
            return make;
        }

        public String getModel() {
            return model;
        }

        public String getSoftware() {
            return software;
        }
    }

    public class GPSInfo implements Serializable {

        private final Double latitude;
        private final Double longitude;
        private final Double altitude;
        private String country;
        private String countryCode;
        private String city;
        private String district;

        public GPSInfo(Double latitude, Double longitude, Double altitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public Double getAltitude() {
            return altitude;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }

    public class ImageSpecification implements Serializable {

        private Double exposureTime;
        private Double shutterSpeedValue;
        private Double isoSpeedRatings;
        private Double focalLength;
        private Double focalLengthIn35mmFilm;
        private Double whiteBalance;
        private Double exifImageHeight;
        private Double exifImageWidth;

        public Double getExposureTime() {
            return exposureTime;
        }

        public Double getShutterSpeedValue() {
            return shutterSpeedValue;
        }

        public Double getIsoSpeedRatings() {
            return isoSpeedRatings;
        }

        public Double getFocalLength() {
            return focalLength;
        }

        public Double getFocalLengthIn35mmFilm() {
            return focalLengthIn35mmFilm;
        }

        public Double getWhiteBalance() {
            return whiteBalance;
        }

        public Double getExifImageHeight() {
            return exifImageHeight;
        }

        public Double getExifImageWidth() {
            return exifImageWidth;
        }

        public void setExposureTime(Double exposureTime) {
            this.exposureTime = exposureTime;
        }

        public void setShutterSpeedValue(Double shutterSpeedValue) {
            this.shutterSpeedValue = shutterSpeedValue;
        }

        public void setIsoSpeedRatings(Double isoSpeedRatings) {
            this.isoSpeedRatings = isoSpeedRatings;
        }

        public void setFocalLength(Double focalLength) {
            this.focalLength = focalLength;
        }

        public void setFocalLengthIn35mmFilm(Double focalLengthIn35mmFilm) {
            this.focalLengthIn35mmFilm = focalLengthIn35mmFilm;
        }

        public void setWhiteBalance(Double whiteBalance) {
            this.whiteBalance = whiteBalance;
        }

        public void setExifImageHeight(Double exifImageHeight) {
            this.exifImageHeight = exifImageHeight;
        }

        public void setExifImageWidth(Double exifImageWidth) {
            this.exifImageWidth = exifImageWidth;
        }

    }

    public class ImageDate implements Serializable {

        private String dateTimeOriginal;
        private String dateTimeDigitized;
        private String offsetTime;

        public String getDateTimeOriginal() {
            return dateTimeOriginal;
        }

        public String getDateTimeDigitized() {
            return dateTimeDigitized;
        }

        public String getOffsetTime() {
            return offsetTime;
        }

        public void setDateTimeOriginal(String dateTimeOriginal) {
            this.dateTimeOriginal = dateTimeOriginal;
        }

        public void setDateTimeDigitized(String dateTimeDigitized) {
            this.dateTimeDigitized = dateTimeDigitized;
        }

        public void setOffsetTime(String offsetTime) {
            this.offsetTime = offsetTime;
        }

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getSize() {
        return size;
    }

    public void calculateSize() {
        File file = new File(absolutePath);
        this.size = file.length() / 1024.0 / 1024.0;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public GPSInfo getGpsInfo() {
        return gpsInfo;
    }

    public void setGpsInfo(GPSInfo gpsInfo) {
        this.gpsInfo = gpsInfo;
    }

    public ImageSpecification getImageSpecification() {
        return imageSpecification;
    }

    public void setImageSpecification(ImageSpecification imageSpecification) {
        this.imageSpecification = imageSpecification;
    }

    public ImageDate getImageDate() {
        return imageDate;
    }

    public void setImageDate(ImageDate imageDate) {
        this.imageDate = imageDate;
    }

    public ImageModel fromImage(String imagePath) throws Exception {
        return new ImageMetadataService().loadInto(this, imagePath);
    }

    public List<ImageModel> batchAnalysis(String folderPath) throws Exception {
        return new ImageMetadataService().batchAnalysis(folderPath);
    }

}
