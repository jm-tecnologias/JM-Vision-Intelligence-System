/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author JM-Tecnologias
 */
public class ImageUtils {

    public static List<Path> listImages(String folderPath) throws IOException {

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {

            return paths
                    .filter(Files::isRegularFile)
                    .filter(p -> {
                        String name = p.toString().toLowerCase();
                        return name.endsWith(".png")
                                || name.endsWith(".jpg")
                                || name.endsWith(".jpeg")
                                || name.endsWith(".gif")
                                || name.endsWith(".bmp")
                                || name.endsWith(".webp");
                    })
                    .collect(Collectors.toList());
        }
    }

//    public static void main(String[] args) {
//
//        try {
//            List<Path> images = listImages("C:\\Users\\JM-Tecnologias\\Documents\\JM-Image-Analyzer\\File Explore\\teste");
//
//            for (Path img : images) {
//                System.out.println(img.toAbsolutePath());
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
