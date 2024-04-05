package ku.cs.usecasedesigner.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

public class HomepageController {
    @FXML
    private ImageView ovalImageView;

    @FXML
    void initialize() {
        ovalImageView.setOnDragDetected(this::handleDragDetected);
        ovalImageView.setOnDragOver(this::handleDragOver);
        ovalImageView.setOnDragDropped(this::handleDragDropped);
    }

    @FXML
    private void handleDragDetected(MouseEvent event) {
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.COPY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(ovalImageView.getImage());

        dragboard.setContent(content);

        event.consume();
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != ovalImageView &&
                event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.COPY);
        }

        event.consume();
    }

    @FXML
    private void handleDragDropped(DragEvent event) {
        boolean success = false;

        if (event.getDragboard().hasImage()) {
            ImageView imageView = new ImageView(event.getDragboard().getImage());
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            // Add the image to the container or pane where you want to drop it
            // For example, if you have a Pane named "containerPane", you can do:
            // containerPane.getChildren().add(imageView);

            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }
}
