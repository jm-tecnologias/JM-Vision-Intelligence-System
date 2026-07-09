/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.service;

/**
 *
 * @author JM-Tecnologias
 */
import javafx.concurrent.Task;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class CopyService {

    public Task<Integer> importImages(Path source, Path destination) {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                validateSource(source);
                validateDestination(destination);

                List<Path> files;
                try (Stream<Path> paths = Files.list(source)) {
                    files = paths
                            .filter(Files::isRegularFile)
                            .filter(CopyService::isImageFile)
                            .collect(Collectors.toList());
                }

                int count = 0;
                for (Path file : files) {

                    if (isCancelled()) {
                        break;
                    }

                    Path target = destination.resolve(createTargetName(destination, file, count));

                    Files.copy(
                            file,
                            target,
                            StandardCopyOption.REPLACE_EXISTING
                    );

                    count++;
                    updateMessage("Imagem " + count + " de " + files.size() + " importada...");
                }

                updateMessage(count + " imagens importadas com sucesso!");
                return count;
            }
        };
    }

    public void start(Task<?> task) {
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void validateSource(Path source) {
        if (source == null || !Files.isDirectory(source)) {
            throw new IllegalArgumentException("Source folder not set.");
        }
    }

    private void validateDestination(Path destination) {
        if (destination == null || !Files.isDirectory(destination)) {
            throw new IllegalArgumentException("Destination folder not set.");
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

    private String createTargetName(Path destination, Path sourceFile, int count) {
        String extension = getExtension(sourceFile);
        return destination.getFileName().toString() + count + extension;
    }

    private String getExtension(Path file) {
        String fileName = file.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(dotIndex);
    }
}
