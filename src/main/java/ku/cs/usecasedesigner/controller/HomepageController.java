package ku.cs.usecasedesigner.controller;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
public class HomepageController {
    @FXML
    private ImageView ovalImageView;

    @FXML
    void handleDragDetected(MouseEvent event) {
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.COPY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(ovalImageView.getImage());

        dragboard.setContent(content);

        event.consume();
    }

    @FXML
    void handleDragOver(DragEvent event) {
        event.getDragboard().hasImage();
        event.acceptTransferModes(TransferMode.COPY);


        event.consume();
    }

    @FXML
    void handleDragDropped(DragEvent event) {
        boolean success = false;

        if (event.getDragboard().hasImage()) {
            ImageView imageView = new ImageView(event.getDragboard().getImage());
            imageView.setFitWidth(100); // ปรับขนาดของรูปภาพตามที่คุณต้องการ
            imageView.setFitHeight(100); // ปรับขนาดของรูปภาพตามที่คุณต้องการ
//            imageView.getChildren().add(imageView);
            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }
}
