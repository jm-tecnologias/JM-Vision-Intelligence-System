/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.stage.Modality;
import jmvis.Main;

/**
 *
 * @author JM-Tecnologias
 */
public class FIleDetailReport {

    private static String path = "C:\\JMVIS\\Data";
    private static String fileName;

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fileName) {
        FIleDetailReport.fileName = fileName;
    }

    public static void createFile(Object o) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName + ".jmvis");
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeFile(o);
            } catch (IOException ex) {
                Main.setData("FIleDetailReport Error: " + ex);
                LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
                throw new IOException("Error: " + ex);
            }
        }
    }

    static void writeFile(Object o) {
        File f = new File(path, fileName + ".jmvis");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(o);
            oos.close();

        } catch (FileNotFoundException ex) {
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
        } catch (IOException ex) {
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
        }
    }

    private static Object readFile() {
        File f = new File(path, fileName + ".jmvis");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object obj = null;

        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);

            obj = ois.readObject();
            ois.close();

        } catch (FileNotFoundException ex) {
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
        } catch (IOException ex) {
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
        } catch (ClassNotFoundException ex) {
            Main.setData("Error: " + ex);
            LoadAndMoveUtilities.loadFXML(Modality.APPLICATION_MODAL, "/jmvis/ConfirForm.fxml");
        }
        return obj;
    }

//    public static void main(String[] args) throws Exception {
//        ImageModel image = new ImageModel();
//        image.fromImage("C:\\JMVIS\\File Explore\\tr\\IMG_9940.JPG");
//        FIleDetailReport.fileName = "DJI_0598.JPG";
//        DetailToReport.path = "C:\\JMVIS\\Data";
//        DetailToReport.createFile(image);
//
//        image = (ImageModel) FIleDetailReport.readFile();
//
//        System.out.println(image.getDeviceInfo().getMake());
//        System.out.println(image.getDeviceInfo().getModel());
//        System.out.println(image.getDeviceInfo().getSoftware());
//        System.out.println(image.getNotes());
//        System.out.println(image.getDetailToReport()[0]);
//        System.out.println(image.getDetailToReport()[1]);
//        System.out.println(image.getDetailToReport()[2]);
//        System.out.println(image.getDetailToReport()[3]);
//
//
//    }
}
