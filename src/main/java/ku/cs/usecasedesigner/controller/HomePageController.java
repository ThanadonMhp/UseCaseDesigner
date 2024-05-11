package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.*;
import ku.cs.usecasedesigner.services.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomePageController {

    @FXML private ImageView ovalImageView, actorImageView, systemImageView, lineImageView, arrowImageView;

    @FXML private Pane designPane;

    @FXML private Label guideLabel;

    private double startX;
    private double startY;
    private String projectName, directory;
    private Node startNodeForLink;

    @FXML void initialize() {
        if(FXRouter.getData() != null){
            // Receive data from New Project Page
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            if (objects.size() > 1)
            {
                directory = (String) objects.get(1);
            }
            loadProject();
            saveProject();
            System.out.println("Project Name: " + projectName);
            System.out.println("Directory: " + directory);
        }
    }

    public void DrawUseCase(double width, double height, double layoutX, double layoutY, String label) {
        // Draw a system
        Ellipse ellipse = new Ellipse();
        ellipse.setRadiusX(width);
        ellipse.setRadiusY(height);
        ellipse.setStyle("-fx-fill: transparent; -fx-stroke: black;");

        // Add hidden label to the system
        Label type = new Label("usecase");
        type.setVisible(false);

        // Add an oval and label to StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(ellipse, new Label(label), type);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setLayoutX(layoutX);
        stackPane.setLayoutY(layoutY);

        // Add StackPane to designPane
        designPane.getChildren().add(stackPane);

        // Make the component draggable and selectable
        MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public void DrawActor(double width, double height, double layoutX, double layoutY, String label) {
        // Draw an actor
        ImageView imageView = new ImageView();
        imageView.setImage(actorImageView.getImage());
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        // Add hidden label to the system
        Label type = new Label("actor");
        type.setVisible(false);

        // Add an actor and label to VBox
        VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView, type , new Label(label));
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX(layoutX);
        vbox.setLayoutY(layoutY);

        // Add VBox to designPane
        designPane.getChildren().add(vbox);

        // Make the component draggable and selectable
        MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public void DrawSystem(double width, double height, double layoutX, double layoutY, String label) {
        // Draw a system
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setStyle("-fx-fill: transparent; -fx-stroke: black;");

        // Add hidden label to the system
        Label type = new Label("system");
        type.setVisible(false);

        // Add an image and label to VBox
        VBox vbox = new VBox();
        vbox.getChildren().addAll(rectangle, type, new Label(label));
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX(layoutX);
        vbox.setLayoutY(layoutY);

        // Add VBox to designPane
        designPane.getChildren().add(vbox);

        // Make the component draggable and selectable
        MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public void DrawLine(double startX, double startY, double endX, double endY) {
        // Create a new line
        Line line = new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        designPane.getChildren().add(line);

        // Make the component draggable and selectable
        MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public void DrawArrow(double startX, double startY, double endX, double endY) {
        // Create a new line with arrow head
        Line line = new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);

        // Create a new arrow head
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double arrowLength = 10;
        double arrowWidth = 5;
        double arrowX = endX + arrowLength * Math.cos(angle);
        double arrowY = endY + arrowLength * Math.sin(angle);
        double arrowX1 = arrowX + arrowWidth * Math.cos(angle + Math.PI / 2.0);
        double arrowY1 = arrowY + arrowWidth * Math.sin(angle + Math.PI / 2.0);
        double arrowX2 = arrowX + arrowWidth * Math.cos(angle - Math.PI / 2.0);
        double arrowY2 = arrowY + arrowWidth * Math.sin(angle - Math.PI / 2.0);

        Line arrowHead = new Line();
        arrowHead.setStartX(endX);
        arrowHead.setStartY(endY);
        arrowHead.setEndX(arrowX1);
        arrowHead.setEndY(arrowY1);

        Line arrowHead2 = new Line();
        arrowHead2.setStartX(endX);
        arrowHead2.setStartY(endY);
        arrowHead2.setEndX(arrowX2);
        arrowHead2.setEndY(arrowY2);

        designPane.getChildren().addAll(line, arrowHead, arrowHead2);

        // Make the component draggable and selectable
        MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public String GetTextInput() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Label");
        dialog.setHeaderText("Please enter a label for the object:");
        dialog.setContentText("Label:");

        Optional<String> result = dialog.showAndWait();
        // If a string was entered, use it as the label
        if (result.isPresent()) {
            String enteredLabel = result.get();
            while (enteredLabel.isEmpty()) {
                // Show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Input Error");
                alert.setContentText("Please enter a non-empty label!");

                alert.showAndWait();

                // Re-prompt the user
                result = dialog.showAndWait();
                if (result.isPresent()) {
                    enteredLabel = result.get();
                    return enteredLabel;
                }
            }
            return enteredLabel;

        }
        return null;
    }

    public void OvalDragDetected(MouseEvent mouseEvent) {
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
        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getLayoutX();
            startY = e.getSceneY() - node.getLayoutY();
        });

        node.setOnMouseDragged(e -> {
            double newX = e.getSceneX() - startX;
            double newY = e.getSceneY() - startY;
            node.setLayoutX(newX);
            node.setLayoutY(newY);
        });
    }

    private void MakeSelectable(Node node) {
        // Create a context menu
        ContextMenu contextMenu = new ContextMenu();

        // Create menu items
        MenuItem resizeItem = new MenuItem("Resize");
        MenuItem rotateItem = new MenuItem("Rotate");
        MenuItem connectItem = new MenuItem("Connect");
        MenuItem bringToFront = new MenuItem("Bring to Front");
        MenuItem sendToBack = new MenuItem("Send to Back");
        MenuItem deleteItem = new MenuItem("Delete");

        // Add menu items to the context menu
        contextMenu.getItems().addAll(resizeItem, rotateItem, connectItem, bringToFront, sendToBack, deleteItem);

        //set the action for resize menu item
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
                            if(node instanceof VBox)
                            {
                                ((ImageView) ((VBox) node).getChildren().get(0)).setFitWidth(newWidth);
                                ((ImageView) ((VBox) node).getChildren().get(0)).setFitHeight(newHeight);
                            }
                            else if(node instanceof StackPane)
                            {
                                ((ImageView) ((StackPane) node).getChildren().get(0)).setFitWidth(newWidth);
                                ((ImageView) ((StackPane) node).getChildren().get(0)).setFitHeight(newHeight);
                            }
                        }
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
                            ((ImageView) ((VBox) node).getChildren().get(0)).setRotate(newRotate);
                        }
                    }
                }
            });
        });

        // Set the action for connect menu item
        connectItem.setOnAction(e -> {
            startNodeForLink = node;

            // Pop up to notify user to select the end node
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Node Connection");
            alert.setHeaderText("Please select the another component to connect");
            alert.showAndWait();

            System.out.println("Connect Clicked");
        });

        // Set the action for bring to front menu item
        bringToFront.setOnAction(e -> {
            System.out.println("Bring to Front Clicked");
            designPane.getChildren().remove(node);
            designPane.getChildren().add(node);
        });

        // Set the action for send to back menu item
        sendToBack.setOnAction(e -> {
            System.out.println("Send to Back Clicked");
            designPane.getChildren().remove(node);
            designPane.getChildren().add(0, node);
        });

        // Set the action for delete menu item
        deleteItem.setOnAction(e -> {
            // Pop up to confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Are you sure you want to delete this item?");
            alert.setContentText("Press OK to confirm, or Cancel to go back.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Item Deleted");

                // If node is  a line, remove the label as well
                if (node instanceof Line) {
                    for (Node child : designPane.getChildren()) {
                        if (child instanceof Label) {
                            if (child.getLayoutX() == (((Line) node).getStartX() + ((Line) node).getEndX()) / 2 && child.getLayoutY() == (((Line) node).getStartY() + ((Line) node).getEndY()) / 2) {
                                designPane.getChildren().remove(child);
                                break;
                            }
                        }
                    }
                }

                designPane.getChildren().remove(node);
                System.out.println("Item Removed");
            }
        });

        // Deselect the node when the mouse is released
        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setOnMouseDragged(null);
                node.setStyle("-fx-border-color: transparent");
                System.out.println("Editing Finished");
                MakeDraggable(node);
            }
        });

        // Show the context menu
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                    System.out.println("Item Right Clicked");
                    node.setStyle("-fx-border-color: black");
                    contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            }
        });

        // Double Click to change the label
        Label label = null;
        if (node instanceof VBox) {
            label = (Label) ((VBox) node).getChildren().get(1);
        } else if (node instanceof StackPane) {
            label = (Label) ((StackPane) node).getChildren().get(1);
        }

        Label finalLabel = label;

        if (label != null)
        {
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getClickCount() == 2) {  // Check if it's a double click
                        // Create a TextInputDialog
                        TextInputDialog dialog = new TextInputDialog(finalLabel.getText());
                        dialog.setTitle("Change Label");
                        dialog.setHeaderText("Please enter the new text for the label:");
                        dialog.setContentText("Label:");

                        // Show the dialog and get the result
                        Optional<String> result = dialog.showAndWait();
                        // If a string was entered, use it as the new label text
                        if (result.isPresent()) {
                            finalLabel.setText(result.get());
                        }
                    }
                }
            });
        }
    }

    public void PaneDragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void PaneDragDropped(DragEvent dragEvent) throws IOException {
        if(dragEvent.getDragboard().hasString()) {
            // Draw a component based on the string
            if(dragEvent.getDragboard().getString().equals("Oval")) {
                DrawUseCase(50, 30, dragEvent.getX() - 75, dragEvent.getY() - 75, GetTextInput());
            } else if (dragEvent.getDragboard().getString().equals("Actor")) {
                DrawActor(75, 75, dragEvent.getX() - 75, dragEvent.getY() - 75, GetTextInput());
            } else if (dragEvent.getDragboard().getString().equals("System")) {
                DrawSystem(100, 50, dragEvent.getX() - 75, dragEvent.getY() - 75, GetTextInput());
            } else if (dragEvent.getDragboard().getString().equals("Line")) {
                DrawLine(dragEvent.getX(), dragEvent.getY(), dragEvent.getX() + 100, dragEvent.getY() + 100);
            } else if (dragEvent.getDragboard().getString().equals("Arrow")) {
                DrawArrow(dragEvent.getX(), dragEvent.getY(), dragEvent.getX() + 100, dragEvent.getY() + 100);
            }

            if (!designPane.getChildren().isEmpty()) {guideLabel.setVisible(false); }
        }
    }

    @FXML private void designPaneMouseClicked(MouseEvent mouseEvent) {
        if (startNodeForLink != null) {
            System.out.println("Creating Link");
            Node endNodeForLink = null;
            for (Node node : designPane.getChildren()) {
                if (node.getBoundsInParent().contains(mouseEvent.getX(), mouseEvent.getY())) {
                    endNodeForLink = node;
                    break;
                }
            }
            createLink(startNodeForLink, endNodeForLink);
            startNodeForLink = null;
        }
    }

    private void createLink(Node startNode, Node endNode) {
        System.out.println("Creating Link");
        //Create a pop to get the text for the link
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Link Text");
        dialog.setHeaderText("Please enter the text for the link:");
        dialog.setContentText("Text:");
        // get text from the popup
        Optional<String> result = dialog.showAndWait();
        String text = "";
        if (result.isPresent()) {
            text = result.get();
        }
        // Create a new line
        Line line = getLine(startNode, endNode, text);
        designPane.getChildren().add(line);

        // Make the component draggable
        MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        // Make the component selectable
        MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    private static Line getLine(Node startNode, Node endNode, String text) {
        Line line = new Line();

        // Bind the start and end points of the line to the center points of the nodes
        line.startXProperty().bind(startNode.layoutXProperty().add(startNode.getBoundsInLocal().getWidth() / 2));
        line.startYProperty().bind(startNode.layoutYProperty().add(startNode.getBoundsInLocal().getHeight() / 2));
        line.endXProperty().bind(endNode.layoutXProperty().add(endNode.getBoundsInLocal().getWidth() / 2));
        line.endYProperty().bind(endNode.layoutYProperty().add(endNode.getBoundsInLocal().getHeight() / 2));

        // Creare a label and postion it in the middle of the line
        Label label = new Label(text);
        label.layoutXProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2));
        label.layoutYProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2));

        // Add the label to the design pane
        ((Pane) startNode.getParent()).getChildren().add(label);

        // Add a double click event handler to the label
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {  // Check if it's a double click
                    // Create a TextInputDialog
                    TextInputDialog dialog = new TextInputDialog(label.getText());
                    dialog.setTitle("Change Label");
                    dialog.setHeaderText("Please enter the new text for the label:");
                    dialog.setContentText("Label:");

                    // Show the dialog and get the result
                    Optional<String> result = dialog.showAndWait();
                    // If a string was entered, use it as the new label text
                    if (result.isPresent()) {
                        label.setText(result.get());
                    }
                }
            }
        });

        return line;
    }

    public void handleNewMenuItem(ActionEvent actionEvent) throws IOException {
        // Open the new project page
        System.out.println("New Project");
        FXRouter.popup("NewProjectPage");
    }

    public void handleOpenMenuItem(ActionEvent actionEvent){
        System.out.println("Project Opening");

        // Create a file chooser
        FileChooser fileChooser = new FileChooser();

        // Configure the file chooser
        fileChooser.setTitle("Open Project");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the file chooser
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println("Opening: " + file.getName());
            // Get the project name from the file name
            projectName = file.getName().substring(0, file.getName().lastIndexOf("."));

            // Get the directory from the file path
            directory = file.getParent();

            loadProject();
        } else {
            System.out.println("Open command cancelled");
        }
    }

    public void handleSaveMenuItem(ActionEvent actionEvent) {
        // Save the project
        saveProject();
    }

    public void loadProject() {
        // Clear the design pane
        designPane.getChildren().clear();

        // Get position list
        PositionList positionList = new PositionList();
        DataSource<PositionList> dataSource = new PositionListFileDataSource(directory, projectName + ".csv");
        positionList = dataSource.readData();

        // Get symbol list
        SymbolList symbolList = new SymbolList();
        DataSource<SymbolList> dataSourceSymbol = new SymbolListFileDataSource(directory, projectName + ".csv");
        symbolList = dataSourceSymbol.readData();

        // Add symbol to the design pane
        SymbolList finalSymbolList = symbolList;
        positionList.getPositionList().forEach(position -> {
            ImageView imageView = new ImageView();
            Label label = new Label();
            AtomicBoolean isImage = new AtomicBoolean(true);
            finalSymbolList.getSymbolList().forEach(symbol -> {
                if (symbol.getSymbol_id() == position.getSymbol_id()) {
                    if (symbol.getSymbol_type().equals("box.png")) {
                        imageView.setImage(systemImageView.getImage());
                        if (!symbol.getLabel().equals("none")) {
                            label.setText(symbol.getLabel());
                        }
                    } else if (symbol.getSymbol_type().equals("oval.png")) {
                        // Set the size and position of the component
                        imageView.setImage(ovalImageView.getImage());
                        imageView.setFitWidth(position.getFit_width());
                        imageView.setFitHeight(position.getFit_height());
                        imageView.setRotate(position.getRotation());

                        if (!symbol.getLabel().equals("none")) {
                            label.setText(symbol.getLabel());

                            // Put imageview and label into stackPane
                            StackPane stackPane = new StackPane();
                            stackPane.getChildren().addAll(imageView, label);
                            stackPane.setAlignment(Pos.CENTER);
                            stackPane.setLayoutX(position.getX_position());
                            stackPane.setLayoutY(position.getY_position());

                            designPane.getChildren().add(stackPane);

                            // Make the component draggable and selectable
                            MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
                            MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));

                            isImage.set(false);
                        }
                    } else if (symbol.getSymbol_type().equals("human.png")) {
                        imageView.setImage(actorImageView.getImage());
                        if (!symbol.getLabel().equals("none")) {
                            label.setText(symbol.getLabel());
                        }
                    } else if (symbol.getSymbol_type().equals("line.png")) {
                        imageView.setImage(lineImageView.getImage());
                        if (!symbol.getLabel().equals("none")) {
                            label.setText(symbol.getLabel());
                        }
                    } else if (symbol.getSymbol_type().equals("directional.png")) {
                        imageView.setImage(arrowImageView.getImage());
                        if (!symbol.getLabel().equals("none")) {
                            label.setText(symbol.getLabel());
                        }
                    } else {
                        isImage.set(false);
                    }
                }
            });
            if (isImage.get()){
                // Set the size and position of the component
                imageView.setFitWidth(position.getFit_width());
                imageView.setFitHeight(position.getFit_height());
                imageView.setRotate(position.getRotation());

                VBox vbox = new VBox();
                vbox.getChildren().addAll(imageView, label);
                vbox.setAlignment(Pos.CENTER);
                vbox.setLayoutX(position.getX_position());
                vbox.setLayoutY(position.getY_position());

                designPane.getChildren().add(vbox);

                // Make the component draggable and selectable
                MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
                MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
            }
        });

        // Load connections
        DataSource<ConnectionList> connectionListDataSource = new ConnectionListFileDataSource(directory , projectName + ".csv");
        ConnectionList connectionList = connectionListDataSource.readData(); // Read the ConnectionList from the CSV file

        // Recreate each connection
        connectionList.getConnectionList().forEach(connection -> {
            // Find the start and end nodes of the connection from position in connection
            Node startNode = connectionList.findNodeByPosition(connection.getStartX(), connection.getStartY(), designPane);
            Node endNode = connectionList.findNodeByPosition(connection.getEndX(), connection.getEndY(), designPane);
            String text = connection.getLabel();
            if (text.equals("none")) {
                text = "";
            }
            if (startNode != null && endNode != null) {
                // Create a new Line object that connects the start and end nodes
                Line line = getLine(startNode, endNode, text);
                designPane.getChildren().add(line);

                // Make the component draggable and selectable
                MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
                MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
            }
        });

        // Check if designPane is not empty
        if (!designPane.getChildren().isEmpty()) {
            // Make guideLabel invisible
            guideLabel.setVisible(false);
        }

        System.out.println("Project Opened");
    }

    public void saveProject() {
        // Create new lists
        UseCaseSystemList useCaseSystemList = new UseCaseSystemList();
        SymbolList symbolList = new SymbolList();
        PositionList positionList = new PositionList();
        ConnectionList connectionList = new ConnectionList();

        // Save project name
        DataSource<UseCaseSystemList> useCaseSystemListDataSource = new UseCaseSystemListFileDataSource(directory , projectName + ".csv");
        UseCaseSystem useCaseSystem = new UseCaseSystem(useCaseSystemList.findLastUseCaseSystemId() + 1, projectName);
        useCaseSystemList.addSystem(useCaseSystem);

        // Save position and symbol
        DataSource<PositionList> positionListDataSource = new PositionListFileDataSource(directory , projectName + ".csv");
        DataSource<SymbolList> symbolListDataSource = new SymbolListFileDataSource(directory , projectName + ".csv");
        DataSource<ConnectionList> connectionListDataSource = new ConnectionListFileDataSource(directory , projectName + ".csv");
        designPane.getChildren().forEach(node -> {
            if (node instanceof VBox)
            {
                // Save position to the list
                Position position = new Position(positionList.findLastPositionId() + 1, positionList.findLastPositionId() + 1, node.getLayoutX(), node.getLayoutY(), ((ImageView) ((VBox) node).getChildren().get(0)).getFitWidth(), ((ImageView) ((VBox) node).getChildren().get(0)).getFitHeight(), ((VBox) node).getChildren().get(0).getRotate());
                positionList.addPosition(position);

                // Save symbol to the list
                String label = ((Label) ((VBox) node).getChildren().get(2)).getText();
                if(label.isEmpty())
                {
                    label = "none";
                }

            }
            else if (node instanceof StackPane)
            {
                // Save position to the list
                Position position = new Position(positionList.findLastPositionId() + 1, positionList.findLastPositionId() + 1, node.getLayoutX(), node.getLayoutY(), ((ImageView) ((StackPane) node).getChildren().get(0)).getFitWidth(), ((ImageView) ((StackPane) node).getChildren().get(0)).getFitHeight(), ((ImageView) ((StackPane) node).getChildren().get(0)).getRotate());
                positionList.addPosition(position);

                // Save symbol to the list
                String label = ((Label) ((StackPane) node).getChildren().get(1)).getText();
                if(label.isEmpty())
                {
                    label = "none";
                }
                Symbol symbol = new Symbol(symbolList.findLastSymbolId() + 1, 0, ((ImageView) ((StackPane) node).getChildren().get(0)).getImage().getUrl().substring(((ImageView) ((StackPane) node).getChildren().get(0)).getImage().getUrl().lastIndexOf("/") + 1),label);
                symbolList.addSymbol(symbol);
            }
            else if (node instanceof Line)
            {
                // Set the text for the connection
                String text = "";
                // Search for the text above the line's middle point
                for (Node child : designPane.getChildren()) {
                    if (child instanceof Label) {
                        if (child.getLayoutX() == (((Line) node).getStartX() + ((Line) node).getEndX()) / 2 && child.getLayoutY() == (((Line) node).getStartY() + ((Line) node).getEndY()) / 2) {
                            text = ((Label) child).getText();
                            break;
                        }
                    }
                }

                if (text.isEmpty())
                {
                    text = "none";
                }

                // Save connection to the list
                Connection connection = new Connection(((Line) node).getStartX(), ((Line) node).getStartY(), ((Line) node).getEndX(), ((Line) node).getEndY(), text); // Create a new Connection object                connectionList.addConnection(connection);
                connectionList.addConnection(connection);
            }
        });

        // Write data to CSV
        useCaseSystemListDataSource.writeData(useCaseSystemList);
        positionListDataSource.writeData(positionList);
        symbolListDataSource.writeData(symbolList);
        connectionListDataSource.writeData(connectionList);

        System.out.println("Project Saved");
    }
}
