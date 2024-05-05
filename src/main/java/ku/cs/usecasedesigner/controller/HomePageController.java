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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
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

    @FXML private VBox homePageVBox;

    @FXML private ImageView ovalImageView, actorImageView, systemImageView, lineImageView, arrowImageView;

    @FXML private Pane designPane;

    private double startX;
    private double startY;
    private String projectName, directory;
    private Node startNodeForLink;
    private static int nodeId = 0;
    private static int connectionId = 0;

    @FXML void initialize() {
        // Check if the data is not null
        if (FXRouter.getData() == null) {
            // Disable the home page
            homePageVBox.setDisable(true);
            System.out.println("Home Page Disabled");
        }
        else if(FXRouter.getData() != null){
            // Recieve data from New Project Page
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            if (objects.size() > 1)
            {
                directory = (String) objects.get(1);
            }
            homePageVBox.setDisable(false);
            loadProject();
            saveProject();
            System.out.println("Home Page Enabled");
            System.out.println("Project Name: " + projectName);
            System.out.println("Directory: " + directory);
        }
    }

    public void PaneDragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void PaneDragDropped(DragEvent dragEvent) throws IOException {
        if(dragEvent.getDragboard().hasString()) {
            ImageView imageView = new ImageView();
            Label label = new Label();

            // Set the image of the component
            if(dragEvent.getDragboard().getString().equals("Oval")) {
                imageView.setImage(ovalImageView.getImage());
                System.out.println("Oval Dropped");
            } else if(dragEvent.getDragboard().getString().equals("Actor")) {
                imageView.setImage(actorImageView.getImage());
                System.out.println("Actor Dropped");

                // Create a TextInputDialog
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Enter Label");
                dialog.setHeaderText("Please enter a label for the object:");
                dialog.setContentText("Label:");

                // Show the dialog and get the result
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
                        }
                    }
                    label.setText(enteredLabel);
                }

            } else if (dragEvent.getDragboard().getString().equals("System")) {
                imageView.setImage(systemImageView.getImage());
                System.out.println("System Dropped");

                // Create a TextInputDialog
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Enter Label");
                dialog.setHeaderText("Please enter a label for the object:");
                dialog.setContentText("Label:");

                // Show the dialog and get the result
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
                        }
                    }
                    label.setText(enteredLabel);
                }

            } else if (dragEvent.getDragboard().getString().equals("Line")) {
                imageView.setImage(lineImageView.getImage());
                System.out.println("Line Dropped");

            } else if (dragEvent.getDragboard().getString().equals("Arrow")) {
                imageView.setImage(arrowImageView.getImage());
                System.out.println("Arrow Dropped");
            }

            // Set the size and position of the component
            imageView.setFitWidth(90);
            imageView.setFitHeight(90);

            // Create a new VBox amd add the image and label
            VBox vbox = new VBox();
            vbox.getChildren().addAll(imageView, label);
            vbox.setAlignment(Pos.CENTER);
            vbox.setLayoutX(dragEvent.getX() - 75);
            vbox.setLayoutY(dragEvent.getY() - 75);

            // Add the component to the design pane
            designPane.getChildren().add(vbox);

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
        MenuItem deleteItem = new MenuItem("Delete");

        // Add menu items to the context menu
        contextMenu.getItems().addAll(resizeItem, rotateItem, connectItem, deleteItem);

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
                            ((ImageView) ((VBox) node).getChildren().get(0)).setFitWidth(newWidth);
                            ((ImageView) ((VBox) node).getChildren().get(0)).setFitHeight(newHeight);
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
                designPane.getChildren().remove(node);
                System.out.println("Item Removed");
            }
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
                    System.out.println("Item Right Clicked");
                    contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            }
        });
    }

    @FXML
    private void designPaneMouseClicked(MouseEvent mouseEvent) {
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
        // Create a new line
        Line line = getLine(startNode, endNode);
        line.setId("connection" + connectionId++); // Set the ID of the line
        designPane.getChildren().add(line);

        // Make the component draggable
        MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        // Make the component selectable
        MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    private static Line getLine(Node startNode, Node endNode) {
        Line line = new Line();

        // Assign a unique ID to the line if they don't have one
        if (startNode.getId() == null) {
            startNode.setId("node" + nodeId++);
        }
        if (endNode.getId() == null) {
            endNode.setId("node" + nodeId++);
        }

        // Bind the start and end points of the line to the center points of the nodes
        line.startXProperty().bind(startNode.layoutXProperty().add(startNode.getBoundsInLocal().getWidth() / 2));
        line.startYProperty().bind(startNode.layoutYProperty().add(startNode.getBoundsInLocal().getHeight() / 2));
        line.endXProperty().bind(endNode.layoutXProperty().add(endNode.getBoundsInLocal().getWidth() / 2));
        line.endYProperty().bind(endNode.layoutYProperty().add(endNode.getBoundsInLocal().getHeight() / 2));

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

    public void loadProject()
    {
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
                        imageView.setImage(ovalImageView.getImage());
                        if (!symbol.getLabel().equals("none")) {
                            label.setText(symbol.getLabel());
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
                    } else if (symbol.getSymbol_type().equals("line")) {
                        isImage.set(false);
                        Line line = new Line();
                        line.setStartX(position.getX_position());
                        line.setStartY(position.getY_position());
                        line.setEndX(position.getFit_width());
                        line.setEndY(position.getFit_height());
                        line.setRotate(position.getRotation());
                        designPane.getChildren().add(line);

                        // Make the component draggable and selectable
                        MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
                        MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
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
            if (startNode != null && endNode != null) {
                // Create a new Line object that connects the start and end nodes
                Line line = getLine(startNode, endNode);
                line.setId("connection" + connectionId++); // Set the ID of the line
                designPane.getChildren().add(line);

                // Make the component draggable and selectable
                MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
                MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
            }
        });

        System.out.println("Project Opened");
    }

    public void saveProject()
    {
        // Create new lists
        UseCaseSystemList useCaseSystemList = new UseCaseSystemList();
        SymbolList symbolList = new SymbolList();
        PositionList positionList = new PositionList();
        ConnectionList connectionList = new ConnectionList();

        // Save project name
        DataSource<UseCaseSystemList> useCaseSystemListDataSource = new UseCaseSystemListFileDataSource(directory , projectName + ".csv");
        UseCaseSystem useCaseSystem = new UseCaseSystem(useCaseSystemList.findLastUseCaseSystemId() + 1, projectName);
        useCaseSystemList.addSystem(useCaseSystem);
        useCaseSystemListDataSource.writeData(useCaseSystemList);

        // Save position and symbol
        DataSource<PositionList> positionListDataSource = new PositionListFileDataSource(directory , projectName + ".csv");
        DataSource<SymbolList> symbolListDataSource = new SymbolListFileDataSource(directory , projectName + ".csv");
        DataSource<ConnectionList> connectionListDataSource = new ConnectionListFileDataSource(directory , projectName + ".csv");
        designPane.getChildren().forEach(node -> {
            if (node instanceof Pane)
            {
                // Save position to the list
                Position position = new Position(positionList.findLastPositionId() + 1, positionList.findLastPositionId() + 1, node.getLayoutX(), node.getLayoutY(), ((ImageView) ((VBox) node).getChildren().get(0)).getFitWidth(), ((ImageView) ((VBox) node).getChildren().get(0)).getFitHeight(), ((VBox) node).getChildren().get(0).getRotate());
                positionList.addPosition(position);

                // Save symbol to the list
                String label = ((Label) ((VBox) node).getChildren().get(1)).getText();
                if(label.isEmpty())
                {
                    label = "none";
                }
                Symbol symbol = new Symbol(symbolList.findLastSymbolId() + 1, 0, ((ImageView) ((VBox) node).getChildren().get(0)).getImage().getUrl().substring(((ImageView) ((VBox) node).getChildren().get(0)).getImage().getUrl().lastIndexOf("/") + 1),label);
                symbolList.addSymbol(symbol);
            }
            else if (node instanceof Line)
            {
                // Save connection to the list
                Connection connection = new Connection(((Line) node).getStartX(), ((Line) node).getStartY(), ((Line) node).getEndX(), ((Line) node).getEndY()); // Create a new Connection object                connectionList.addConnection(connection);
                connectionList.addConnection(connection);
            }
        });

        // Write data to CSV
        positionListDataSource.writeData(positionList);
        symbolListDataSource.writeData(symbolList);
        connectionListDataSource.writeData(connectionList);

        System.out.println("Project Saved");
    }
}
