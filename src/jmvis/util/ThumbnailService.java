/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmvis.util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import static jmvis.util.ImageUtils.listImages;

/**
 * Service responsible for loading thumbnails and keeping gallery/carousel state.
 *
 * @author JM-Tecnologias
 */
public class ThumbnailService {

    private final List<ThumbnailItem> loadedThumbs = new ArrayList<>();
    private final List<ThumbnailItem> selectedThumbs = new ArrayList<>();
    private final List<Path> carouselPaths = new ArrayList<>();

    public Task<Void> loadThumbnails(Path folder, Consumer<ThumbnailItem> onThumbnailLoaded) {
        clear();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<Path> images = listImages(folder.toString());

                for (Path imagePath : images) {
                    ThumbnailItem thumbnail = createThumbnail(imagePath);
                    carouselPaths.add(imagePath);
                    loadedThumbs.add(thumbnail);

                    Platform.runLater(() -> onThumbnailLoaded.accept(thumbnail));
                }

                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        return task;
    }

    public void handleSelection(ThumbnailItem thumbnail, boolean keepPreviousSelection) {
        if (!keepPreviousSelection) {
            selectedThumbs.forEach(item -> setSelectedStyle(item, false));
            selectedThumbs.clear();
        }

        if (thumbnail.isSelected()) {
            thumbnail.setSelected(false);
            selectedThumbs.remove(thumbnail);
            setSelectedStyle(thumbnail, false);
        } else {
            thumbnail.setSelected(true);
            selectedThumbs.add(thumbnail);
            setSelectedStyle(thumbnail, true);
        }
    }

    public List<Path> getSelectedImagePaths() {
        return selectedThumbs.stream()
                .map(ThumbnailItem::getPath)
                .collect(Collectors.toList());
    }

    public Path nextAfter(Path currentPath) {
        if (currentPath == null) {
            return null;
        }

        int index = carouselPaths.indexOf(currentPath);
        if (index >= 0 && index < carouselPaths.size() - 1) {
            return carouselPaths.get(index + 1);
        }

        return null;
    }

    public Path previousBefore(Path currentPath) {
        if (currentPath == null) {
            return null;
        }

        int index = carouselPaths.indexOf(currentPath);
        if (index > 0) {
            return carouselPaths.get(index - 1);
        }

        return null;
    }

    public void clear() {
        loadedThumbs.clear();
        selectedThumbs.clear();
        carouselPaths.clear();
    }

    private ThumbnailItem createThumbnail(Path imagePath) {
        Image image = new Image(imagePath.toUri().toString(), 100, 100, true, true, true);
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        HBox.setMargin(imageView, new Insets(10, 15, 0, 15));

        ThumbnailItem thumbnail = new ThumbnailItem(imagePath, imageView);
        setSelectedStyle(thumbnail, false);
        return thumbnail;
    }

    private void setSelectedStyle(ThumbnailItem thumbnail, boolean selected) {
        if (selected) {
            thumbnail.getView().setStyle(
                    " -fx-cursor: hand;"
                    + "-fx-border-color: #41c0b5;"
                    + "-fx-border-width: 3;"
                    + "-fx-effect: dropshadow(gaussian, rgba(0,170,255,0.6), 10, 0.3, 0, 0);"
            );
        } else {
            thumbnail.getView().setStyle(
                    "-fx-cursor: hand;"
                    + "-fx-border-color: transparent;"
                    + "-fx-border-width: 5px;"
            );
        }
    }

    public static class ThumbnailItem {

        private final Path path;
        private final ImageView view;
        private boolean selected;

        public ThumbnailItem(Path path, ImageView view) {
            this.path = path;
            this.view = view;
            this.selected = false;
        }

        public Path getPath() {
            return path;
        }

        public ImageView getView() {
            return view;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
