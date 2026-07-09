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
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.json.JSONObject;

/**
 * Service responsible for reading image metadata and enriching it with
 * geolocation details.
 *
 * @author JM-Tecnologias
 */
public class ImageMetadataService {

    public ImageModel analyze(String imagePath) throws Exception {
        return loadInto(new ImageModel(), imagePath);
    }

    public ImageModel loadInto(ImageModel imageModel, String imagePath) throws Exception {
        imageModel.resetDetails();

        File file = new File(imagePath);
        Metadata metadata = ImageMetadataReader.readMetadata(file);

        imageModel.setDeviceInfo(readDeviceInfo(imageModel, metadata));
        imageModel.setImageSpecification(readImageSpecification(imageModel, metadata));
        imageModel.setImageDate(readImageDate(imageModel, metadata));
        imageModel.setGpsInfo(readGpsInfo(imageModel, metadata));

        imageModel.setAbsolutePath(file.getAbsolutePath());
        imageModel.setFileName(file.getName());
        imageModel.calculateSize();

        return imageModel;
    }

    public List<ImageModel> batchAnalysis(String folderPath) throws Exception {
        List<ImageModel> results = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(ImageMetadataService::isImageFile)
                    .forEach(path -> {
                        try {
                            results.add(analyze(path.toString()));
                        } catch (Exception ignored) {
                            // Keep batch processing resilient; callers can inspect partial results.
                        }
                    });
        }

        return results;
    }

    private ImageModel.DeviceInfo readDeviceInfo(ImageModel imageModel, Metadata metadata) {
        ExifIFD0Directory ifd0 = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        ImageModel.DeviceInfo deviceInfo = imageModel.new DeviceInfo();

        if (ifd0 != null) {
            deviceInfo.setMake(getString(ifd0, ExifIFD0Directory.TAG_MAKE));
            deviceInfo.setModel(getString(ifd0, ExifIFD0Directory.TAG_MODEL));
            deviceInfo.setSoftware(getString(ifd0, ExifIFD0Directory.TAG_SOFTWARE));
        }

        return deviceInfo;
    }

    private ImageModel.ImageSpecification readImageSpecification(ImageModel imageModel, Metadata metadata) {
        ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        ImageModel.ImageSpecification specification = imageModel.new ImageSpecification();

        if (exif != null) {
            specification.setExposureTime(getDouble(exif, ExifSubIFDDirectory.TAG_EXPOSURE_TIME));
            specification.setShutterSpeedValue(getDouble(exif, ExifSubIFDDirectory.TAG_SHUTTER_SPEED));
            specification.setIsoSpeedRatings(getDouble(exif, ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
            specification.setFocalLength(getDouble(exif, ExifSubIFDDirectory.TAG_FOCAL_LENGTH));
            specification.setFocalLengthIn35mmFilm(getDouble(exif, ExifSubIFDDirectory.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH));
            specification.setWhiteBalance(getDouble(exif, ExifSubIFDDirectory.TAG_WHITE_BALANCE_MODE));
            specification.setExifImageHeight(getDouble(exif, ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT));
            specification.setExifImageWidth(getDouble(exif, ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH));
        }

        return specification;
    }

    private ImageModel.ImageDate readImageDate(ImageModel imageModel, Metadata metadata) {
        ExifSubIFDDirectory exif = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        ImageModel.ImageDate imageDate = imageModel.new ImageDate();

        if (exif != null) {
            imageDate.setDateTimeOriginal(getString(exif, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
            imageDate.setDateTimeDigitized(getString(exif, ExifSubIFDDirectory.TAG_DATETIME_DIGITIZED));
            imageDate.setOffsetTime(getString(exif, 0x9010));
        }

        return imageDate;
    }

    private ImageModel.GPSInfo readGpsInfo(ImageModel imageModel, Metadata metadata) {
        GpsDirectory gps = metadata.getFirstDirectoryOfType(GpsDirectory.class);
        Double latitude = null;
        Double longitude = null;
        Double altitude = null;

        if (gps != null) {
            GeoLocation location = gps.getGeoLocation();

            if (location != null && !location.isZero()) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            altitude = getDouble(gps, GpsDirectory.TAG_ALTITUDE);
        }

        ImageModel.GPSInfo gpsInfo = imageModel.new GPSInfo(latitude, longitude, altitude);
        enrichLocation(gpsInfo);
        return gpsInfo;
    }

    private void enrichLocation(ImageModel.GPSInfo gpsInfo) {
        if (gpsInfo.getLatitude() == null || gpsInfo.getLongitude() == null) {
            return;
        }

        JSONObject geoData = reverseGeocode(gpsInfo.getLatitude(), gpsInfo.getLongitude());
        if (geoData == null || !geoData.has("address")) {
            return;
        }

        JSONObject location = geoData.getJSONObject("address");
        gpsInfo.setCountry(location.optString("country"));
        gpsInfo.setCountryCode(location.optString("country_code"));
        gpsInfo.setDistrict(location.optString("district"));
        gpsInfo.setCity(location.optString("city"));
    }

    private JSONObject reverseGeocode(Double latitude, Double longitude) {
        try {
            String urlStr = "https://nominatim.openstreetmap.org/reverse?format=json"
                    + "&lat=" + latitude + "&lon=" + longitude;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty(
                    "User-Agent",
                    "JMVisionIntelligence/1.0 (contact: comercial@jm-tecnologias.co.mz)"
            );

            if (conn.getResponseCode() != 200) {
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            return new JSONObject(response.toString());
        } catch (Exception ex) {
            return null;
        }
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
        } catch (Exception ex) {
            return null;
        }
    }

    private static Double getDouble(Directory directory, int tag) {
        try {
            if (directory == null || !directory.containsTag(tag)) {
                return null;
            }

            return directory.getDoubleObject(tag);
        } catch (Exception ex) {
            return null;
        }
    }
}
