package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ku.cs.usecasedesigner.models.Position;

public class HomepageController {

    @FXML private ImageView ovalImageView;

    @FXML private ImageView actorImageView;

    @FXML private ImageView systemImageView;

    @FXML private ImageView lineImageView;

    @FXML private ImageView arrowImageView;

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
            } else if (dragEvent.getDragboard().getString().equals("System")) {
                imageView.setImage(systemImageView.getImage());
                System.out.println("System Dropped");
            } else if (dragEvent.getDragboard().getString().equals("Line")) {
                imageView.setImage(lineImageView.getImage());
                System.out.println("Line Dropped");
            } else if (dragEvent.getDragboard().getString().equals("Arrow")) {
                imageView.setImage(arrowImageView.getImage());
                System.out.println("Arrow Dropped");
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

    public void SystemDragDetected(MouseEvent mouseEvent) {
        System.out.println("System Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("System");
        dragboard.setContent(clipboardContent);
    }

    public void LineDragDetected(MouseEvent mouseEvent) {
        System.out.println("Line Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Line");
        dragboard.setContent(clipboardContent);
    }

    public void ArrowDragDetected(MouseEvent mouseEvent) {
        System.out.println("Arrow Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Arrow");
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
        MenuItem resizeItem = new MenuItem("Resize");
        MenuItem rotateItem = new MenuItem("Rotate");
        MenuItem deleteItem = new MenuItem("Delete");

        // Add menu items to the context menu
        contextMenu.getItems().addAll(resizeItem, rotateItem, deleteItem);

        // Set the action for delete menu item
        deleteItem.setOnAction(e -> {
            designPane.getChildren().remove(node);
            System.out.println("Item Removed");
        });

        //set the action for edit menu item
        resizeItem.setOnAction(e -> {
            System.out.println("Edit Clicked");
            //Make the node resizable
            node.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.isPrimaryButtonDown()) {
                        double newWidth = mouseEvent.getX() + 10;
                        double newHeight = mouseEvent.getY() + 10;

                        if (newWidth > 0 && newHeight > 0) {
                            ((ImageView) node).setFitWidth(newWidth);
                            ((ImageView) node).setFitHeight(newHeight);
                        }
                        System.out.println("Item Resized to " + newWidth + "x" + newHeight);
                    }
                }
            });
        });

        // Set the action for rotate menu item
        rotateItem.setOnAction(e -> {
            System.out.println("Rotate Clicked");
            //Make the node rotatable
            node.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.isPrimaryButtonDown()) {
                        double newRotate = mouseEvent.getX() + 10;
                        if (newRotate > 0) {
                            node.setRotate(newRotate);
                        }
                        System.out.println("Item Rotated to " + newRotate);
                    }
                }
            });
        });

        // Deselect the node when the mouse is released
        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setOnMouseDragged(null);
                System.out.println("Editing Finished");
                MakeDraggable(node);
            }
        });

        // Show the context menu
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                    // Create a rectangle border around the selected node
//                    Rectangle border = new Rectangle();
//                    border.setWidth(((ImageView) node).getFitWidth());
//                    border.setHeight(((ImageView) node).getFitHeight());
//                    border.setLayoutX(node.getLayoutX());
//                    border.setLayoutY(node.getLayoutY());
//                    border.setStroke(Color.BLACK);
//                    border.setFill(Color.TRANSPARENT);
//
//                    // Add the border to the design pane
//                    designPane.getChildren().add(border);
//                    node.toFront();

                    System.out.println("Item Right Clicked");
                    contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            }
        });


    }

    public void saveProject(ActionEvent actionEvent) {
        System.out.println("Project Saving");
        try {
            // Save the project
            designPane.getChildren().forEach(node -> {
                if (node instanceof ImageView) {
                    System.out.println("Node: " + node);
                    System.out.println("Node Type: " + ((ImageView) node).getImage());
                    System.out.println("Node Position: " + node.getLayoutX() + "," + node.getLayoutY());
                    System.out.println("Node Size: " + ((ImageView) node).getFitWidth() + "x" + ((ImageView) node).getFitHeight());
                    System.out.println("Node Rotation: " + node.getRotate());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
