package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.*;
import ku.cs.usecasedesigner.services.DataSource;
import ku.cs.usecasedesigner.services.PositionListFileDataSource;
import ku.cs.usecasedesigner.services.SymbolListFileDataSource;
import ku.cs.usecasedesigner.services.UseCaseSystemListFileDataSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomePageController {

    @FXML private VBox homePageVBox;

    @FXML private ImageView ovalImageView, actorImageView, systemImageView, lineImageView, arrowImageView;

    @FXML private Pane designPane;

    private double startX;
    private double startY;
    private String projectName, directory;
    private Node startNodeForLink;

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
            saveProject();
            loadProject();
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

        // Set the action for connect menu item
        connectItem.setOnAction(e -> {
            startNodeForLink = node;
            System.out.println("Connect Clicked");
        });

        // Set the action for delete menu item
        deleteItem.setOnAction(e -> {
            designPane.getChildren().remove(node);
            System.out.println("Item Removed");
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
            Node endNodeForLink = (Node) mouseEvent.getTarget();
            createLink(startNodeForLink, endNodeForLink);
            startNodeForLink = null;
        }
    }

    private void createLink(Node startNode, Node endNode) {
        System.out.println("Creating Link");
        // Create a new line
        Line line = getLine(startNode, endNode);
        designPane.getChildren().add(line);

        // Make the component draggable
        MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        // Make the component selectable
        MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
    }

    private static Line getLine(Node startNode, Node endNode) {
        Line line = new Line();

        // Set the start and end points of the line
        line.startXProperty().bind(startNode.layoutXProperty().add(startNode.getBoundsInParent().getWidth() / 2));
        line.startYProperty().bind(startNode.layoutYProperty().add(startNode.getBoundsInParent().getHeight() / 2));
        line.endXProperty().bind(endNode.layoutXProperty().add(endNode.getBoundsInParent().getWidth() / 2));
        line.endYProperty().bind(endNode.layoutYProperty().add(endNode.getBoundsInParent().getHeight() / 2));

        return line;
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
            // Set the size and position of the component
            imageView.setFitWidth(position.getFit_width());
            imageView.setFitHeight(position.getFit_height());
            imageView.setLayoutX(position.getX_position());
            imageView.setLayoutY(position.getY_position());
            imageView.setRotate(position.getRotation());
            designPane.getChildren().add(imageView);

            // Make the component draggable and selectable
            MakeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1));
            MakeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1));
        });
        System.out.println("Project Opened");
    }

    public void saveProject()
    {
        System.out.println("Saving Project");

        // Create new lists
        UseCaseSystemList useCaseSystemList = new UseCaseSystemList();
        SymbolList symbolList = new SymbolList();
        PositionList positionList = new PositionList();

        // Save project name
        DataSource<UseCaseSystemList> useCaseSystemListDataSource = new UseCaseSystemListFileDataSource(directory , projectName + ".csv");
        UseCaseSystem useCaseSystem = new UseCaseSystem(useCaseSystemList.findLastUseCaseSystemId() + 1, projectName);
        useCaseSystemList.addSystem(useCaseSystem);
        useCaseSystemListDataSource.writeData(useCaseSystemList);

        designPane.getChildren().forEach(node -> {
            //Save position to the list
            DataSource<PositionList> positionListDataSource = new PositionListFileDataSource(directory , projectName + ".csv");
            Position position = new Position(positionList.findLastPositionId() + 1, positionList.findLastPositionId() + 1, node.getLayoutX(), node.getLayoutY(), ((ImageView) node).getFitWidth(), ((ImageView) node).getFitHeight(), node.getRotate());
            positionList.addPosition(position);
            positionListDataSource.writeData(positionList);

            //Save symbol to the list
            DataSource<SymbolList> symbolListDataSource = new SymbolListFileDataSource(directory , projectName + ".csv");
            String type = ((ImageView) node).getImage().getUrl().substring(((ImageView) node).getImage().getUrl().lastIndexOf("/") + 1);
            Symbol symbol = new Symbol(symbolList.findLastSymbolId() + 1, 0, type, "none");
            symbolList.addSymbol(symbol);
            symbolListDataSource.writeData(symbolList);

            System.out.println("Project Saved");
        });
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
}
