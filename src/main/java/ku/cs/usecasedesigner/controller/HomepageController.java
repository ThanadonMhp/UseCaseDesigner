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
import ku.cs.usecasedesigner.models.PositionList;
import ku.cs.usecasedesigner.models.Symbol;
import ku.cs.usecasedesigner.models.SymbolList;
import ku.cs.usecasedesigner.services.DataSource;
import ku.cs.usecasedesigner.services.PositionListFileDataSource;
import ku.cs.usecasedesigner.services.SymbolListFileDataSource;

import java.io.File;

public class HomepageController {

    @FXML private ImageView ovalImageView;

    @FXML private ImageView actorImageView;

    @FXML private ImageView systemImageView;

    @FXML private ImageView lineImageView;

    @FXML private ImageView arrowImageView;

    @FXML private Pane designPane;

    private double startX;
    private double startY;
    private String projectName = "NewProject";

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
        System.out.println("Making " + node + " selectable");
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

    public void handleNewMenuItem(ActionEvent actionEvent) {
        System.out.println("New Project");
        designPane.getChildren().clear();
    }

    public void handleOpenMenuItem(ActionEvent actionEvent){
        System.out.println("Project Opening");
        try {
            // Open the project

            //get position list
            PositionList positionList = new PositionList();
            DataSource<PositionList> dataSource = new PositionListFileDataSource("data" , projectName + ".csv");
            positionList = dataSource.readData();

            //get symbol list
            SymbolList symbolList = new SymbolList();
            DataSource<SymbolList> dataSourceSymbol = new SymbolListFileDataSource("data" , projectName + ".csv");
            symbolList = dataSourceSymbol.readData();

            //add symbol to the design pane
            SymbolList finalSymbolList = symbolList;
            positionList.getPositionList().forEach(position -> {
                ImageView imageView = new ImageView();
                finalSymbolList.getSymbolList().forEach(symbol -> {
                    if (symbol.getSymbol_id() == position.getSymbol_id()) {
                        if (symbol.getSymbol_type().equals("box.png")) {
                            imageView.setImage(systemImageView.getImage());
                        } else if (symbol.getSymbol_type().equals("oval.png")) {
                            imageView.setImage(ovalImageView.getImage());
                        } else if (symbol.getSymbol_type().equals("human.png")) {
                            imageView.setImage(actorImageView.getImage());
                        } else if (symbol.getSymbol_type().equals("line.png")) {
                            imageView.setImage(lineImageView.getImage());
                        } else if (symbol.getSymbol_type().equals("directional.png")) {
                            imageView.setImage(arrowImageView.getImage());

                        }

                    }

                });

                imageView.setFitWidth(position.getFit_width());
                imageView.setFitHeight(position.getFit_height());
                imageView.setLayoutX(position.getX_position());
                imageView.setLayoutY(position.getY_position());
                imageView.setRotate(position.getRotation());
                designPane.getChildren().add(imageView);

                MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
                MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
            });
            System.out.println("Project Opened");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSaveMenuItem(ActionEvent actionEvent) {
        System.out.println("Project Saving");
        try {
            // Save the project
            PositionList positionList = new PositionList();
            SymbolList symbolList = new SymbolList();
            designPane.getChildren().forEach(node -> {
                System.out.println("Node: " + node);
                System.out.println("Node Type: " + ((ImageView) node).getImage().getUrl().substring(((ImageView) node).getImage().getUrl().lastIndexOf("/") + 1));
                String type = ((ImageView) node).getImage().getUrl().substring(((ImageView) node).getImage().getUrl().lastIndexOf("/") + 1);
                System.out.println("Node Position: " + node.getLayoutX() + "," + node.getLayoutY());
                System.out.println("Node Size: " + ((ImageView) node).getFitWidth() + "x" + ((ImageView) node).getFitHeight());
                System.out.println("Node Rotation: " + node.getRotate());

                //Save position to the list
                DataSource<PositionList> dataSource = new PositionListFileDataSource("data" , projectName + ".csv");
                Position position = new Position(positionList.findLastPositionId() + 1, positionList.findLastPositionId() + 1, node.getLayoutX(), node.getLayoutY(), ((ImageView) node).getFitWidth(), ((ImageView) node).getFitHeight(), node.getRotate());
                positionList.addPosition(position);
                dataSource.writeData(positionList);

                //Save symbol to the list
                DataSource<SymbolList> dataSourceSymbol = new SymbolListFileDataSource("data" , projectName + ".csv");
                Symbol symbol = new Symbol(symbolList.findLastSymbolId() + 1, 0, type, "none");
                symbolList.addSymbol(symbol);
                dataSourceSymbol.writeData(symbolList);

                System.out.println("Project Saved");

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
