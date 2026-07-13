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
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.stage.Modality;
import jmvis.Main;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author JM-Tecnologias
 */
/*
a ideia e que este programa permita registar o objecto CountUsage no fichiero 
ConfigData.jmvis com o numeros de vezes(usedTimes) que ja foram usados, e 
comparar com o limit de uso(limitUsage) e a data actual ao ultimo dia 
de uso(lastDayOfUsage), caso um desse dados tenha sido excedido nao retornar um false ou true
para que essa bool(true or false) retornada, possa ser usada para tomadas de decisoes futuras
 */
public class TrialControllerUtil {

//    private final Path path;
    private final String path;
    private final File file;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TrialControllerUtil() {
//        this.path = Paths.get(
//                System.getProperty("user.home"),
//                "Documents",
//                "JM Vision Intelligence",
//                "Data"
//        );
        this.path = "C:\\ProgramData\\DataConfig";

//        this.file = new File(path.toFile(), "ConfigData.jmvis");
        this.file = new File(path, "ConfigData.jmvis");
    }

    private static class CountUsage implements Serializable {

        private static final long serialVersionUID = 1L;

        private int usedTimes = 0;
        private int limitUsage = 20;
        private String lastDayOfUsage = "16/07/2026";
    }

    public boolean canUseProgram() {

        try {
            CountUsage usage = loadUsage();

            LocalDate today = LocalDate.now();
            LocalDate limitDate = LocalDate.parse(
                    usage.lastDayOfUsage,
                    formatter
            );

            // Verifica se passou do limite de usos
            if (usage.usedTimes >= usage.limitUsage) {
                return false;
            }

            // Verifica se passou da data limite
            if (today.isAfter(limitDate)) {
                return false;
            }

            // Incrementa o número de usos
            usage.usedTimes++;

            // Guarda novamente no ficheiro
            saveUsage(usage);

            return true;

        } catch (Exception ex) {
            Main.setData("Erro ao verificar uso do sistema: " + ex);
            LoadAndMoveUtilities.loadFXML(
                    Modality.APPLICATION_MODAL,
                    "/jmvis/view/ConfirForm.fxml"
            );

            return false;
        }
    }

    private CountUsage loadUsage() throws IOException, ClassNotFoundException {

        if (!Files.exists(new File(path).toPath())) {
            Files.createDirectories(new File(path).toPath());
        }

        if (!file.exists()) {
            CountUsage usage = new CountUsage();
            saveUsage(usage);
            return usage;
        }

        try ( ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file)
        )) {
            return (CountUsage) ois.readObject();
        }
    }

    private void saveUsage(CountUsage usage) throws IOException {

        if (!Files.exists(new File(path).toPath())) {
            Files.createDirectories(new File(path).toPath());
        }

        try ( ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(file)
        )) {
            oos.writeObject(usage);
        }
    }

//    public static void main(String[] args) {
//        TrialControllerUtil report = new TrialControllerUtil();
//
//        if (report.canUseProgram()) {
//            System.out.println("Programa autorizado.");
//        } else {
//            System.out.println("Limite de uso excedido ou licença expirada.");
//        }
//    }
}
