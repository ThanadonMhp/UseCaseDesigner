package ku.cs.usecasedesigner.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class HomepageController {

    @FXML private ImageView ovalImageView;

    @FXML private ImageView actorImageView;

    @FXML private Pane designPane;

    private double startX;
    private double startY;

    @FXML void initialize() {

    }

    public void PaneDragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void PaneDragDropped(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasString()) {
            ImageView imageView = new ImageView();

            // Set the image of the component
            if(dragEvent.getDragboard().getString().equals("Oval")) {
                imageView.setImage(ovalImageView.getImage());
                System.out.println("Oval Dropped");
            } else if(dragEvent.getDragboard().getString().equals("Actor")) {
                imageView.setImage(actorImageView.getImage());
                System.out.println("Actor Dropped");
            }

            // Set the size and position of the component
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setLayoutX(dragEvent.getX() - 75);
            imageView.setLayoutY(dragEvent.getY() - 75);

            // Add the component to the design pane
            designPane.getChildren().add(imageView);

            // Make the component draggable
            MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
            // Make the component selectable
            MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        }
    }

    public void ovalDragDetected(MouseEvent mouseEvent) {
        System.out.println("Oval Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Oval");
        dragboard.setContent(clipboardContent);
    }

    public void ActorDragDetected(MouseEvent mouseEvent) {
        System.out.println("Actor Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Actor");
        dragboard.setContent(clipboardContent);
    }

    private void MakeDraggable(Node node) {
        System.out.println("Making " + node + " draggable");
        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();
        });

        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startX);
            node.setTranslateY(e.getSceneY() - startY);
        });
    }

    private void MakeSelectable(Node node) {
        // Create a context menu
        ContextMenu contextMenu = new ContextMenu();

        // Create menu items
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem editItem = new MenuItem("Edit");

        // Add menu items to the context menu
        contextMenu.getItems().addAll(deleteItem, editItem);

        // Set the action for the menu items
        deleteItem.setOnAction(e -> {
            designPane.getChildren().remove(node);
            System.out.println("Item Removed");
        });

        // Set the action for the menu items
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    System.out.println("Item Left Clicked");
                } else
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                    System.out.println("Item Right Clicked");
                    contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            }
        });


    }
}
