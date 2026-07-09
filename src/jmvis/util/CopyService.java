/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

/**
 *
 * @author JM-Tecnologias
 */
import javafx.concurrent.Task;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class CopyService {

    public static Task<Void> copyImages(Path source, Path destination) {
        
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                List<Path> files = Files.list(source)
                        .filter(Files::isRegularFile)
                        .filter(path -> {
                            String name = path.getFileName().toString().toLowerCase();
                            return name.endsWith(".jpg")
                                    || name.endsWith(".jpeg")
                                    || name.endsWith(".png")
                                    || name.endsWith(".tif")
                                    || name.endsWith(".tiff")
                                    || name.endsWith(".webp")
                                    || name.endsWith(".heic");
                        })
                        .collect(Collectors.toList());
                int count = 0;
                for (Path file : files) {
                    
                    if (isCancelled()) {
                        break;
                    }
                    String[] vet = file.getFileName().toString().split("\\.");
                    Path target = destination.resolve(destination.getFileName()+""+count+"."+vet[1]);

                    Files.copy(
                            file,
                            target,
                            StandardCopyOption.REPLACE_EXISTING
                    );

                    updateMessage("Copia Concluida...");
//                    Thread.sleep(1000);
                count++;
                }
                updateMessage("Imagens inportadas com sucesso!");

                return null;
            }
        };

    }
}
