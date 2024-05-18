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

public class HomePageController {

    @FXML
    private ImageView ovalImageView, actorImageView, systemImageView, lineImageView, arrowImageView;

    @FXML
    private Pane designPane;

    @FXML
    private Label guideLabel;

    private double startX;
    private double startY;
    private String projectName, directory;
    private Node startNodeForLink;

    private ActorList actorList = new ActorList();
    private ConnectionList connectionList = new ConnectionList();
    private PositionList positionList = new PositionList();
    private SubsystemList subsystemList = new SubsystemList();
    private UseCaseList useCaseList = new UseCaseList();

    private static Line getLine(Node startNode, Node endNode, String text) {
        Line line = new Line();

        // Bind the start and end points of the line to the center points of the nodes
        line.startXProperty().bind(startNode.layoutXProperty().add(startNode.getBoundsInLocal().getWidth() / 2));
        line.startYProperty().bind(startNode.layoutYProperty().add(startNode.getBoundsInLocal().getHeight() / 2));
        line.endXProperty().bind(endNode.layoutXProperty().add(endNode.getBoundsInLocal().getWidth() / 2));
        line.endYProperty().bind(endNode.layoutYProperty().add(endNode.getBoundsInLocal().getHeight() / 2));

        // Create a label and position it in the middle of the line
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

    @FXML
    void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            // Load the project
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            loadProject();
            saveProject();
            System.out.println("Project Name: " + projectName);
            System.out.println("Directory: " + directory);
        }
    }

    public void drawUseCase(double width, double height, double layoutX, double layoutY, String label, int actorID, String preCondition, String description, String postCondition) {
        // Draw a system
        Ellipse ellipse = new Ellipse();
        ellipse.setRadiusX(width);
        ellipse.setRadiusY(height);
        ellipse.setStyle("-fx-fill: transparent; -fx-stroke: black;");

        // Add useCaseName
        Label useCaseName = new Label(label);

        // Add hidden label to the system
        Label type = new Label("useCase");
        type.setMaxSize(0, 0);
        type.setVisible(false);

        Label actorIDLabel = new Label(String.valueOf(actorID));
        actorIDLabel.setMaxSize(0, 0);
        actorIDLabel.setVisible(false);

        Label preConditionLabel = new Label(preCondition);
        preConditionLabel.setMaxSize(0, 0);
        preConditionLabel.setVisible(false);

        Label descriptionLabel = new Label(description);
        descriptionLabel.setMaxSize(0, 0);
        descriptionLabel.setVisible(false);

        Label postConditionLabel = new Label(postCondition);
        postConditionLabel.setMaxSize(0, 0);
        postConditionLabel.setVisible(false);

        // Add an oval and label to StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(ellipse, type, useCaseName, actorIDLabel, preConditionLabel, descriptionLabel, postConditionLabel);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setLayoutX(layoutX);
        stackPane.setLayoutY(layoutY);

        // Add StackPane to designPane
        designPane.getChildren().add(stackPane);

        // Save the position of the use case
        Position position = new Position
                (positionList.findLastPositionId() + 1,  // positionID
                        layoutX, // xPosition
                        layoutY, // yPosition
                        width, // fitWidth
                        height,  // fitHeight
                        0); // rotation
        positionList.addPosition(position);

        // Add the use case to useCaseList
        UseCase useCase = new UseCase
                (useCaseList.findLastUseCaseId() + 1, // useCaseID
                        label,  // useCaseName
                        actorID,  // actorID
                        preCondition,  // preCondition
                        description,  // description
                        postCondition,  // postCondition
                        position.getPositionID()); // positionID
        useCaseList.addUseCase(useCase);

        saveProject();

        // Make the component draggable and selectable
        makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));

        // Double click to open the use case page and receive the use case details from the UseCasePage and Update the use case
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {  // Check if it's a double click
                    // Send the use case details to the UseCasePage
                    ArrayList<Object> objects = new ArrayList<>();
                    objects.add(projectName);
                    objects.add(directory);
                    objects.add(useCase.getUseCaseID());
                    try {
                        FXRouter.popup("UseCasePage", objects);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void drawActor(double width, double height, double layoutX, double layoutY, String label) {
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
        vbox.getChildren().addAll(imageView, type, new Label(label));
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX(layoutX);
        vbox.setLayoutY(layoutY);

        // Add VBox to designPane
        designPane.getChildren().add(vbox);

        // Save the position of the actor
        Position position = new Position
                (positionList.findLastPositionId() + 1,  // positionID
                        layoutX, // xPosition
                        layoutY, // yPosition
                        width, // fitWidth
                        height,  // fitHeight
                        0); // rotation
        positionList.addPosition(position);

        // Add the actor to actorList
        Actor actor = new Actor
                (actorList.findLastActorId() + 1, // actorID
                        label,  // actorName
                        position.getPositionID()); // positionID
        actorList.addActor(actor);

        // Make the component draggable and selectable
        makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public void drawSubSystem(double width, double height, double layoutX, double layoutY, String label) {
        // Draw a system
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setStyle("-fx-fill: transparent; -fx-stroke: black;");

        // Add hidden label to the system
        Label type = new Label("subSystem");
        type.setVisible(false);

        // Add an image and label to VBox
        VBox vbox = new VBox();
        vbox.getChildren().addAll(rectangle, type, new Label(label));
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX(layoutX);
        vbox.setLayoutY(layoutY);

        // Add VBox to designPane
        designPane.getChildren().add(vbox);

        // Save the position of the subsystem
        Position position = new Position
                (positionList.findLastPositionId() + 1,  // positionID
                        layoutX, // xPosition
                        layoutY, // yPosition
                        width, // fitWidth
                        height,  // fitHeight
                        0); // rotation
        positionList.addPosition(position);

        // Add the subsystem to subsystemList
        Subsystem subsystem = new Subsystem
                (subsystemList.findLastSubsystemId() + 1, // subSystemID
                        label,  // subSystemName
                        position.getPositionID()); // positionID
        subsystemList.addSubsystem(subsystem);

        // Make the component draggable and selectable
        makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public void drawLine(double startX, double startY, double endX, double endY) {
        // Create a new line
        Line line = new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        designPane.getChildren().add(line);

        // Make the component draggable and selectable
        makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public void drawArrow(double startX, double startY, double endX, double endY) {
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
        makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public String getTextInput() {
        //Create a popup for text input that can't be empty
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Label");
        dialog.setHeaderText("Please enter the label for the component:");
        dialog.setContentText("Label:");


        // Get the text from the popup
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return "";
    }

    public void ovalDragDetected(MouseEvent mouseEvent) {
        System.out.println("Oval Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Oval");
        dragboard.setContent(clipboardContent);
    }

    public void actorDragDetected(MouseEvent mouseEvent) {
        System.out.println("Actor Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Actor");
        dragboard.setContent(clipboardContent);
    }

    public void systemDragDetected(MouseEvent mouseEvent) {
        System.out.println("System Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("System");
        dragboard.setContent(clipboardContent);
    }

    public void lineDragDetected(MouseEvent mouseEvent) {
        System.out.println("Line Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Line");
        dragboard.setContent(clipboardContent);
    }

    public void arrowDragDetected(MouseEvent mouseEvent) {
        System.out.println("Arrow Drag Detected");
        Dragboard dragboard = ovalImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Arrow");
        dragboard.setContent(clipboardContent);
    }

    private void makeDraggable(Node node) {
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

    private void makeSelectable(Node node) {
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
                            if (node instanceof VBox) {
                                ((ImageView) ((VBox) node).getChildren().get(0)).setFitWidth(newWidth);
                                ((ImageView) ((VBox) node).getChildren().get(0)).setFitHeight(newHeight);
                            } else if (node instanceof StackPane) {
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
                            ((VBox) node).getChildren().get(0).setRotate(newRotate);
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
                makeDraggable(node);
            }
        });

        // Show the context menu
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                    System.out.println("Item Right Clicked");
                    node.setStyle("-fx-border-color: black");
                    contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            }
        });

        // Check if label of node is not null
        Label label = null;
        if (node instanceof VBox) {
            label = (Label) ((VBox) node).getChildren().get(1);
        } else if (node instanceof StackPane) {
            label = (Label) ((StackPane) node).getChildren().get(1);
        }

        Label finalLabel = label;

        if (label != null) {
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

    public void paneDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasString()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void paneDragDropped(DragEvent dragEvent) throws IOException {
        if (dragEvent.getDragboard().hasString()) {
            // Draw a component based on the string
            if (dragEvent.getDragboard().getString().equals("Oval")) {
                // Create a popup for text input
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(projectName);
                objects.add(directory);
                objects.add("useCase");
                objects.add(50.00);
                objects.add(30.00);
                objects.add(dragEvent.getX() - 75);
                objects.add(dragEvent.getY() - 75);
                FXRouter.popup("LabelPage",objects);
            } else if (dragEvent.getDragboard().getString().equals("Actor")) {
                // Create a popup for text input
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(projectName);
                objects.add(directory);
                objects.add("useCase");
                objects.add(75.00);
                objects.add(75.00);
                objects.add(dragEvent.getX() - 75);
                objects.add(dragEvent.getY() - 75);
                FXRouter.popup("LabelPage",objects);
            } else if (dragEvent.getDragboard().getString().equals("System")) {
                drawSubSystem(100, 50, dragEvent.getX() - 75, dragEvent.getY() - 75, getTextInput());
            } else if (dragEvent.getDragboard().getString().equals("Line")) {
                drawLine(dragEvent.getX(), dragEvent.getY(), dragEvent.getX() + 100, dragEvent.getY() + 100);
            } else if (dragEvent.getDragboard().getString().equals("Arrow")) {
                drawArrow(dragEvent.getX(), dragEvent.getY(), dragEvent.getX() + 100, dragEvent.getY() + 100);
            }

            if (!designPane.getChildren().isEmpty()) {
                guideLabel.setVisible(false);
            }
        }
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
        makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        // Make the component selectable
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    public void handleNewMenuItem(ActionEvent actionEvent) throws IOException {
        // Open the new project page
        System.out.println("New Project");
        FXRouter.popup("NewProjectPage");
    }

    public void handleOpenMenuItem(ActionEvent actionEvent) {
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

        // Load positions
        DataSource<PositionList> positionListDataSource = new PositionListFileDataSource(directory, projectName + ".csv");
        PositionList positionList = positionListDataSource.readData(); // Read the PositionList from the CSV file

        // Load actors
        DataSource<ActorList> actorListDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
        ActorList actorList = actorListDataSource.readData(); // Read the ActorList from the CSV file
        // Recreate each actor
        actorList.getActorList().forEach(actor -> {
            // Find the position of the actor
            Position position = positionList.findByPositionId(actor.getPositionID());
            if (position != null) {
                drawActor(position.getFitWidth(), position.getFitHeight(), position.getXPosition(), position.getYPosition(), actor.getActorName());
            }
        });

        // Load subsystems
        DataSource<SubsystemList> subsystemListDataSource = new SubSystemListFileDataSource(directory, projectName + ".csv");
        SubsystemList subsystemList = subsystemListDataSource.readData(); // Read the SubsystemList from the CSV file
        // Recreate each subsystem
        subsystemList.getSubsystemList().forEach(subsystem -> {
            // Find the position of the subsystem
            Position position = positionList.findByPositionId(subsystem.getPositionID());
            if (position != null) {
                drawSubSystem(position.getFitWidth(), position.getFitHeight(), position.getXPosition(), position.getYPosition(), subsystem.getSubSystemName());
            }
        });

        // Load use cases
        DataSource<UseCaseList> useCaseListDataSource = new UseCaseListFileDataSource(directory, projectName + ".csv");
        UseCaseList useCaseList = useCaseListDataSource.readData(); // Read the UseCaseList from the CSV file
        // Recreate each use case
        useCaseList.getUseCaseList().forEach(useCase -> {
            // Find the position of the use case
            Position position = positionList.findByPositionId(useCase.getPositionID());
            if (position != null) {
                drawUseCase(position.getFitWidth(), position.getFitHeight(), position.getXPosition(), position.getYPosition(), useCase.getUseCaseName(), useCase.getActorID(), useCase.getPreCondition(), useCase.getDescription(), useCase.getPostCondition());
            }
        });

        // Load connections
        DataSource<ConnectionList> connectionListDataSource = new ConnectionListFileDataSource(directory, projectName + ".csv");
        ConnectionList connectionList = connectionListDataSource.readData(); // Read the ConnectionList from the CSV file

        // Recreate each connection
        connectionList.getConnectionList().forEach(connection -> {
            // Find the start and end nodes of the connection from position in connection
            Node startNode = connectionList.findNodeByPosition(connection.getStartX(), connection.getStartY(), designPane);
            Node endNode = connectionList.findNodeByPosition(connection.getEndX(), connection.getEndY(), designPane);
            String text = connection.getConnectionType();
            if (text.equals("!@#$%^&*()_+")) {
                text = "";
            }
            if (startNode != null && endNode != null) {
                // Create a new Line object that connects the start and end nodes
                Line line = getLine(startNode, endNode, text);
                designPane.getChildren().add(line);

                // Make the component draggable and selectable
                makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
                makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
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

        // Save the project components
        DataSource<ActorList> actorListDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
        DataSource<ConnectionList> connectionListDataSource = new ConnectionListFileDataSource(directory, projectName + ".csv");
        DataSource<PositionList> positionListDataSource = new PositionListFileDataSource(directory, projectName + ".csv");
        DataSource<SubsystemList> subsystemListDataSource = new SubSystemListFileDataSource(directory, projectName + ".csv");
        DataSource<UseCaseList> useCaseListFileDataSource = new UseCaseListFileDataSource(directory, projectName + ".csv");
        DataSource<UseCaseSystemList> useCaseSystemListDataSource = new UseCaseSystemListFileDataSource(directory, projectName + ".csv");

        // Save the components to the data sources
        UseCaseSystemList useCaseSystemList = new UseCaseSystemList();
        UseCaseSystem useCaseSystem = new UseCaseSystem(useCaseSystemList.findLastUseCaseSystemId() + 1, projectName);
        useCaseSystemList.addSystem(useCaseSystem);

        // Write data to CSV
        actorListDataSource.writeData(actorList);
        connectionListDataSource.writeData(connectionList);
        positionListDataSource.writeData(positionList);
        subsystemListDataSource.writeData(subsystemList);
        useCaseListFileDataSource.writeData(useCaseList);
        useCaseSystemListDataSource.writeData(useCaseSystemList);

        System.out.println("Project Saved");
    }

    public void handlePreferenceMenuItem(ActionEvent actionEvent) throws IOException {
        // Open the preference page
        FXRouter.popup("PreferencePage");
    }
}
