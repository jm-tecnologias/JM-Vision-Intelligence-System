/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.nio.file.Paths;
import javafx.stage.Modality;
import jmvis.Main;
import static jmvis.util.InternetCheck.hasInternet;
import static jmvis.util.ReverseGeocoder.geoReverseDecoder;
import org.json.JSONObject;

/**
 *
 * @author JM-Tecnologias
 */
public class ImageModel implements Serializable {

    private String fileName;
    private String absolutePath;
    private double size;
    private String notes;
//    public final Boolean[] detailToReport = {false, false, false, false};
    private DeviceInfo deviceInfo;
    private GPSInfo gpsInfo;
    private ImageSpecification imageSpecification;
    private ImageDate imageDate;

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
            geoDecoder();
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

        private void geoDecoder() {
            JSONObject location;
            if (hasInternet()) {
                if (latitude != null && longitude != null) {
                    JSONObject geoData = geoReverseDecoder(latitude, longitude);
                    location = geoData.getJSONObject("address");
                    country = location.optString("country");
                    countryCode = location.optString("country_code");
                    district = location.optString("district");
                    city = location.optString("city");
                }
            } else {
                Main.setData("Internet connection needed for more details");
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
            }
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

//    public Boolean[] getDetailToReport() {
//        return detailToReport;
//    }
    public ImageModel fromImage(String imagePath) throws Exception {
        File file = new File(imagePath);

        Metadata metadata = ImageMetadataReader.readMetadata(file);

//        ImageModel imageModel = new ImageModel();
        ExifIFD0Directory ifd0 = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        GpsDirectory gps = metadata.getFirstDirectoryOfType(GpsDirectory.class);

        if (ifd0 != null) {
            DeviceInfo di = new DeviceInfo();

            di.setMake(getString(ifd0, ExifIFD0Directory.TAG_MAKE));
            di.setModel(getString(ifd0, ExifIFD0Directory.TAG_MODEL));
            di.setSoftware(getString(ifd0, ExifIFD0Directory.TAG_SOFTWARE));
            setDeviceInfo(di);
        }

        if (exif != null) {
            ImageSpecification is = new ImageSpecification();
            ImageDate id = new ImageDate();
            is.setExposureTime(getDouble(exif, ExifSubIFDDirectory.TAG_EXPOSURE_TIME));
            is.setShutterSpeedValue(getDouble(exif, ExifSubIFDDirectory.TAG_SHUTTER_SPEED));
            is.setIsoSpeedRatings(getDouble(exif, ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
            is.setFocalLength(getDouble(exif, ExifSubIFDDirectory.TAG_FOCAL_LENGTH));
            is.setFocalLengthIn35mmFilm(getDouble(exif, ExifSubIFDDirectory.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH));
            is.setWhiteBalance(getDouble(exif, ExifSubIFDDirectory.TAG_WHITE_BALANCE_MODE));
            is.setExifImageHeight(getDouble(exif, ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT));
            is.setExifImageWidth(getDouble(exif, ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH));
            setImageSpecification(is);

            id.setDateTimeOriginal(getString(exif, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
            id.setDateTimeDigitized(getString(exif, ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED));
            id.setOffsetTime(getString(exif, 0x9010));
            setImageDate(id);
        }

        if (gps != null) {
            GeoLocation location = gps.getGeoLocation();
            Double latitude = null;
            Double longitude = null;
            Double altitude = null;

            if (location != null && !location.isZero()) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            altitude = getDouble(gps, GpsDirectory.TAG_ALTITUDE);

            setGpsInfo(new GPSInfo(latitude, longitude, altitude));

        }

        setAbsolutePath(file.getAbsolutePath());
        setFileName(file.getName());
        calculateSize();

        return this;
    }

    public List<ImageModel> batchAnalysis(String folderPath) throws Exception {
        List<ImageModel> results = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(ImageModel::isImageFile)
                    .forEach(path -> {
                        try {
                            results.add(fromImage(path.toString()));
                        } catch (Exception e) {
                            Main.setData("Error: " + e);
                            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                        }
                    });
        }

        return results;
    }

    private static boolean isImageFile(Path path) {
        String name = path.getFileName().toString().toLowerCase();

        return name.endsWith(".jpg")
                || name.endsWith(".jpeg")
                || name.endsWith(".png")
                || name.endsWith(".tif")
                || name.endsWith(".tiff")
                || name.endsWith(".webp")
                || name.endsWith(".heic");
    }

    private static String getString(Directory directory, int tag) {
        try {
            if (directory == null || !directory.containsTag(tag)) {
                return null;
            }

            return directory.getString(tag);
        } catch (Exception e) {
            Main.setData("Error: " + e);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
            return null;
        }
    }

    private static Double getDouble(Directory directory, int tag) {
        try {
            if (directory == null || !directory.containsTag(tag)) {
                return null;
            }

//            Object value = directory.getObject(tag);
//            if (value instanceof Rational rational) {
//                return rational.doubleValue();
//            }
//            if (value instanceof Number number) {
//                return number.doubleValue();
//            }
            return directory.getDoubleObject(tag);
        } catch (Exception e) {
            Main.setData("Error: " + e);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
            return null;
        }
    }

}
