package ku.cs.usecasedesigner.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;

import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.*;
import ku.cs.usecasedesigner.services.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class HomePageController {

    @FXML
    private ImageView ovalImageView, actorImageView, systemImageView, lineImageView;

    @FXML
    private Pane designPane;

    @FXML
    private TitledPane actorsPane;

    @FXML
    private AnchorPane actorsAnchor;

    @FXML
    private ScrollPane actorsScrollPane;

    @FXML
    private Label guideLabel;

    @FXML
    private HBox subSystemHBox;

    @FXML
    private TextArea noteTextArea;

    private int subSystemID, existingActorID;
    private double startX, startY;
    private String projectName, directory;

    private ActorList actorList = new ActorList();
    private ConnectionList connectionList = new ConnectionList();
    private ComponentPreferenceList componentPreferenceList = new ComponentPreferenceList();
    private PositionList positionList = new PositionList();
    private PreferenceList preferenceList = new PreferenceList();
    private Preference preference;
    private NoteList noteList = new NoteList();
    private SubSystemList subsystemList = new SubSystemList();
    private UseCaseList useCaseList = new UseCaseList();
    private UseCaseSystemList useCaseSystemList = new UseCaseSystemList();

    @FXML
    void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            // Load the project
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            if(objects.size() == 3) {
                subSystemID = (int) objects.get(2);
            }
            loadProject();
            saveProject();
            System.out.println("Project Name: " + projectName);
            System.out.println("Directory: " + directory);
        }
    }

    public void drawUseCase(double width, double height, double layoutX, double layoutY,
                            String label, String actorID, String preCondition, String description, String actorAction,
                            int useCaseID, int positionID) {
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

        Label actorActionLabel = new Label(actorAction);
        actorActionLabel.setMaxSize(0, 0);
        actorActionLabel.setVisible(false);

        // find component preference
        if (componentPreferenceList.isPreferenceExist(positionID, "useCase")) {
            ComponentPreference componentPreference = componentPreferenceList.findByIDAndType(positionID, "useCase");
            useCaseName.setTextFill(componentPreference.getFontColor());
            ellipse.setStroke(componentPreference.getStrokeColor());
            ellipse.setStrokeWidth(componentPreference.getStrokeWidth());
        } else {
            useCaseName.setTextFill(preference.getFontColor());
            ellipse.setStroke(preference.getStrokeColor());
            ellipse.setStrokeWidth(preference.getStrokeWidth());
        }

        // Add an oval and label to StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(ellipse, type, useCaseName, actorIDLabel, preConditionLabel, descriptionLabel, actorActionLabel);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setLayoutX(layoutX);
        stackPane.setLayoutY(layoutY);

        // Add StackPane to designPane
        designPane.getChildren().add(stackPane);

        // Make the component draggable and selectable
        makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1), "useCase", positionID);
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1), "useCase", positionID);

        // Double click to open the use case page
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {  // Check if it's a double click
                    // Send the use case details to the UseCasePage
                    ArrayList<Object> objects = new ArrayList<>();
                    objects.add(projectName);
                    objects.add(directory);
                    objects.add(useCaseID);
                    try {
                        saveProject();
                        FXRouter.popup("UseCasePage", objects);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public ArrayList<Integer> addToUseCaseList(double width, double height, double layoutX, double layoutY,
                                               String label, String actorID, String preCondition, String description, String actorAction) {
        // Add the use case to useCaseList
        UseCase useCase = new UseCase
                (useCaseList.findLastUseCaseId() + 1, // useCaseID
                        label,  // useCaseName
                        actorID,  // actorID
                        preCondition,  // preCondition
                        description,  // description
                        actorAction, // actorAction
                        "!@#$%^&*()_+",  // note
                        positionList.findLastPositionId() + 1); // positionID
        useCaseList.addUseCase(useCase);

        // Add the position of the use case
        Position position = new Position
                (positionList.findLastPositionId() + 1,  // positionID
                        layoutX, // xPosition
                        layoutY, // yPosition
                        width, // fitWidth
                        height,  // fitHeight
                        0); // rotation
        positionList.addPosition(position);

        saveProject();

        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(position.getPositionID());
        objects.add(useCase.getUseCaseID());

        return objects;
    }

    public void drawActor(double width, double height, double layoutX, double layoutY, String label,
                          int actorID, int positionID) {
        // Draw an actor
        ImageView imageView = new ImageView();
        imageView.setImage(actorImageView.getImage());
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        // Add hidden label to the system
        Label type = new Label("actor");
        type.setVisible(false);

        // Add label to the actor
        Label actorName = new Label(label);

        // find component preference
        if (componentPreferenceList.isPreferenceExist(positionID, "actor")) {
            ComponentPreference componentPreference = componentPreferenceList.findByIDAndType(positionID, "actor");
            // set color of actorName
            actorName.setTextFill(componentPreference.getFontColor());
        } else {
            actorName.setTextFill(preference.getFontColor());
        }

        // Add an actor and label to VBox
        VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView, type, actorName);
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX(layoutX);
        vbox.setLayoutY(layoutY);

        // Add VBox to designPane
        designPane.getChildren().add(vbox);

        // Make the component draggable and selectable
        makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1), "actor", positionID);
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1), "actor", positionID);

        // Double click to open the label page
        vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {  // Check if it's a double click
                    // Send the actor details to the LabelPage
                    ArrayList<Object> objects = new ArrayList<>();
                    objects.add(projectName);
                    objects.add(directory);
                    objects.add("editLabel");
                    objects.add("actor");
                    objects.add(positionID);
                    try {
                        saveProject();
                        FXRouter.popup("LabelPage", objects);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void addToActorList(double width, double height, double layoutX, double layoutY, String label, String note, int actorID){
        // Add the actor to actorList
        Actor actor = new Actor
                (actorID, // actorID
                        label,  // actorName
                        note,  // note
                        positionList.findLastPositionId() + 1); // positionID
        actorList.addActor(actor);

        // Add the position of the actor
        Position position = new Position
                (positionList.findLastPositionId() + 1,  // positionID
                        layoutX, // xPosition
                        layoutY, // yPosition
                        width, // fitWidth
                        height,  // fitHeight
                        0,  // rotation
                        subSystemID); // subSystemID
        positionList.addPosition(position);

        saveProject();
    }

    public void drawSubSystem(double width, double height, double layoutX, double layoutY, String label,
                              int subSystemID, int positionID) {
        // Draw a system
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setFill(Color.TRANSPARENT);

        // Add hidden label to the system
        Label type = new Label("subSystem");
        type.setVisible(false);

        // Add label to the system
        Label subSystemName = new Label(label);

        // find component preference
        if (componentPreferenceList.isPreferenceExist(positionID, "subSystem")) {
            ComponentPreference componentPreference = componentPreferenceList.findByIDAndType(positionID, "subSystem");
            // set color of type
            subSystemName.setTextFill(componentPreference.getFontColor());
            rectangle.setStroke(componentPreference.getStrokeColor());
            rectangle.setStrokeWidth(componentPreference.getStrokeWidth());
        } else {
            subSystemName.setTextFill(preference.getFontColor());
            rectangle.setStroke(preference.getStrokeColor());
            rectangle.setStrokeWidth(preference.getStrokeWidth());
        }

        // Add an image and label to VBox
        VBox vbox = new VBox();
        vbox.getChildren().addAll(rectangle, type, subSystemName);
        vbox.setAlignment(Pos.CENTER);
        vbox.setLayoutX(layoutX);
        vbox.setLayoutY(layoutY);

        // Add VBox to designPane
        designPane.getChildren().add(vbox);

        // Make the component draggable and selectable
        makeDraggable(designPane.getChildren().get(designPane.getChildren().size() - 1), "subSystem", positionID);
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1), "subSystem", positionID);

        // Double click to open the label page
        vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {  // Check if it's a double click
                    // Send the actor details to the LabelPage
                    ArrayList<Object> objects = new ArrayList<>();
                    objects.add(projectName);
                    objects.add(directory);
                    objects.add("editLabel");
                    objects.add("subSystem");
                    objects.add(subSystemID);
                    try {
                        saveProject();
                        FXRouter.popup("LabelPage", objects);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public ArrayList<Integer> addToSubSystemList(double width, double height, double layoutX, double layoutY, String label){
        // Add the subsystem to subsystemList
        SubSystem subsystem = new SubSystem
                (subsystemList.findLastSubSystemId() + 1, // subSystemID
                        label,  // subSystemName
                        positionList.findLastPositionId() + 1); // positionID
        subsystemList.addSubSystem(subsystem);

        // Add the position of the subsystem
        Position position = new Position
                (positionList.findLastPositionId() + 1,  // positionID
                        layoutX, // xPosition
                        layoutY, // yPosition
                        width, // fitWidth
                        height,  // fitHeight
                        0); // rotation
        positionList.addPosition(position);

        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(position.getPositionID());
        objects.add(subsystem.getSubSystemID());

        return objects;
    }

    public void drawLine(int connectionID, double startX, double startY, double endX, double endY, String label,
                         String arrowHead, String lineType, String arrowTail) {
        // Create a new line
        Line line = new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(1);

        if (Objects.equals(lineType, "dash")){
            line.setStyle("-fx-stroke: black; -fx-stroke-dash-array: 10 10;");
        }

        // Create Start and End points of the line
        Circle startPoint = createDraggablePoint(line.getStartX(), line.getStartY());
        Circle endPoint = createDraggablePoint(line.getEndX(), line.getEndY());

        // Add arrow to the start and end points of the line
        Label arrowHeadPolygon = createDraggableArrow(line, true, arrowHead);
        Label arrowTailPolygon = createDraggableArrow(line, false, arrowTail);

        // Add mouse Event handlers for dragging
        startPoint.setOnMouseDragged(e -> handlePointMouseDragged(e, line, true, label, arrowHead, arrowTail));
        endPoint.setOnMouseDragged(e -> handlePointMouseDragged(e, line, false, label, arrowHead, arrowTail));

        // Add mouse Event handlers for releasing
        startPoint.setOnMouseReleased(e -> handlePointMouseReleased(e, line, connectionID, label, arrowHead, arrowTail));
        endPoint.setOnMouseReleased(e -> handlePointMouseReleased(e, line, connectionID, label, arrowHead, arrowTail));

        // Add label to the line
        Label addLabel = createLabel(label, (line.getStartX() + line.getEndX()) / 2, (line.getStartY() + line.getEndY()) / 2);
        if (Objects.equals(label, "!@#$%^&*()_+") || (Objects.equals(label, "Generalization")) || (Objects.equals(label, "Association"))) {
            addLabel.setVisible(false);
        }

        // find component preference
        if (componentPreferenceList.isPreferenceExist(connectionID, "connection")) {
            ComponentPreference componentPreference = componentPreferenceList.findByIDAndType(connectionID, "connection");
            addLabel.setTextFill(componentPreference.getFontColor());
            line.setStroke(componentPreference.getStrokeColor());
            line.setStrokeWidth(componentPreference.getStrokeWidth());
        } else {
            addLabel.setTextFill(preference.getFontColor());
            line.setStroke(preference.getStrokeColor());
            line.setStrokeWidth(preference.getStrokeWidth());
        }

        // Add the line to the designPane
        designPane.getChildren().addAll(startPoint, endPoint, arrowHeadPolygon, arrowTailPolygon, addLabel, line);

        // make line selectable
        makeSelectable(designPane.getChildren().get(designPane.getChildren().size() - 1), "connection", connectionID);

        // double click to open the connection page
        line.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {  // Check if it's a double click
                    // Send the connection details to the ConnectionPage
                    ArrayList<Object> objects = new ArrayList<>();
                    objects.add(projectName);
                    objects.add(directory);
                    objects.add(subSystemID);
                    objects.add(connectionID);
                    try {
                        saveProject();
                        FXRouter.popup("ConnectionPage", objects);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void handlePointMouseDragged(MouseEvent event, Line line, Boolean startPoint, String label, String arrowHead, String arrowTail) {
        // remove the arrowHead and arrowTail
        for (Node arrow : designPane.getChildren()) {
            if (arrow instanceof Label) {
                if (arrow.getLayoutX() == line.getStartX() - 5 && arrow.getLayoutY() == line.getStartY() - 5) {
                    designPane.getChildren().remove(arrow);
                } else if (arrow.getLayoutX() == line.getEndX() - 5 && arrow.getLayoutY() == line.getEndY() - 5) {
                    designPane.getChildren().remove(arrow);
                }
            }
        }

        // remove the label
        for (Node labelNode : designPane.getChildren()) {
            if (labelNode instanceof Label) {
                if (labelNode.getLayoutX() == (line.getStartX() + line.getEndX()) / 2 && labelNode.getLayoutY() == (line.getStartY() + line.getEndY()) / 2) {
                    designPane.getChildren().remove(labelNode);
                }
            }
        }

        Circle point = (Circle) event.getSource();
        if (startPoint) {
            line.setStartX(event.getX());
            line.setStartY(event.getY());
        } else {
            line.setEndX(event.getX());
            line.setEndY(event.getY());
        }
        point.setCenterX(event.getX());
        point.setCenterY(event.getY());

    }

    public void handlePointMouseReleased(MouseEvent event, Line line, int connectionID, String label, String arrowHead, String arrowTail) {
        // Add the arrowHead and arrowTail
        Label arrowHeadPolygon = createDraggableArrow(line, true, arrowHead);
        Label arrowTailPolygon = createDraggableArrow(line, false, arrowTail);

        // Add the label
        Label addLabel = createLabel(label, (line.getStartX() + line.getEndX()) / 2, (line.getStartY() + line.getEndY()) / 2);
        if (Objects.equals(label, "!@#$%^&*()_+") || (Objects.equals(label, "Generalization")) || (Objects.equals(label, "Association"))) {
            addLabel.setVisible(false);
        }

        // Add the arrowHead and arrowTail to the designPane
        designPane.getChildren().addAll(arrowHeadPolygon, arrowTailPolygon, addLabel);

        // Update the connection
        connectionList.updateConnection(connectionID, line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        saveProject();
    }

    public Circle createDraggablePoint(double x, double y) {
        Circle point = new Circle(x,y, 5, Color.RED);
        point.setStrokeWidth(0);
        point.setCenterX(x);
        point.setCenterY(y);
        return point;
    }

    public Label createDraggableArrow(Line line, boolean head, String arrowType) {
        // if arrowType is none set the arrow to invisible
        if (Objects.equals(arrowType, "none")) {
            Label arrow = new Label("!@#$%^&*()_+");
            arrow.setDisable(true);
            arrow.setVisible(false);
            return arrow;
        } else if (Objects.equals(arrowType, "close")){
            if (head) {
                Label arrow = new Label("⨞");
                arrow.setLayoutX(line.getStartX() - 5);
                arrow.setLayoutY(line.getStartY() - 5);
                arrow.setDisable(true);

                // set center of rotation to the center of the arrow
                arrow.setRotationAxis(Rotate.Z_AXIS);

                // set rotation
                double angle = Math.toDegrees(Math.atan2((line.getEndY() - line.getStartY()), (line.getEndX() - line.getStartX())));
                arrow.setRotate(angle);

                return arrow;
            } else {
                Label arrow = new Label("▷");
                arrow.setLayoutX(line.getEndX() - 5);
                arrow.setLayoutY(line.getEndY() - 5);
                arrow.setDisable(true);

                // set center of rotation to the center of the arrow
                arrow.setRotationAxis(Rotate.Z_AXIS);

                // set rotation
                double angle = Math.toDegrees(Math.atan2((line.getEndY() - line.getStartY()), (line.getEndX() - line.getStartX())));
                arrow.setRotate(angle + 180);

                return arrow;
            }
        } else if (Objects.equals(arrowType, "open")){
            if (head) {
                Label arrow = new Label(">");
                arrow.setLayoutX(line.getStartX() - 5);
                arrow.setLayoutY(line.getStartY() - 5);
                arrow.setDisable(true);

                // set center of rotation to the center of the arrow
                arrow.setRotationAxis(Rotate.Z_AXIS);

                // set rotation
                double angle = Math.toDegrees(Math.atan2((line.getEndY() - line.getStartY()), (line.getEndX() - line.getStartX())));
                arrow.setRotate(angle);

                return arrow;
            } else {
                Label arrow = new Label("<");
                arrow.setLayoutX(line.getEndX() - 5);
                arrow.setLayoutY(line.getEndY() - 5);
                arrow.setDisable(true);

                // set center of rotation to the center of the arrow
                arrow.setRotationAxis(Rotate.Z_AXIS);

                // set rotation
                double angle = Math.toDegrees(Math.atan2((line.getEndY() - line.getStartY()), (line.getEndX() - line.getStartX())));
                arrow.setRotate(angle + 180);

                return arrow;
            }
        } else {
            System.out.println("Invalid arrow type");
            return null;

        }
    }

    public Label createLabel(String text, double x, double y) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setDisable(true);
        return label;
    }

    public void addToConnectionList(double startX, double startY, double endX, double endY, String label,
                                    String arrowHead, String lineType, String arrowTail) {
        // Save the connection
        Connection connection = new Connection(
                connectionList.findLastConnectionID() + 1,  // connectionID
                startX,  // startX
                startY,  // startY
                endX,  // endX
                endY,  // endY
                label,  // label
                arrowHead,  // arrowHead
                lineType,  // lineType
                arrowTail,  // arrowTail
                "!@#$%^&*()_+",  // note
                subSystemID);  // subSystemID
        connectionList.addConnection(connection);
        saveProject();
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
        Dragboard dragboard = actorImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Actor");
        dragboard.setContent(clipboardContent);
    }

    public void systemDragDetected(MouseEvent mouseEvent) {
        System.out.println("System Drag Detected");
        Dragboard dragboard = systemImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("System");
        dragboard.setContent(clipboardContent);
    }

    public void lineDragDetected(MouseEvent mouseEvent) {
        System.out.println("Line Drag Detected");
        Dragboard dragboard = lineImageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString("Line");
        dragboard.setContent(clipboardContent);
    }

    private void makeDraggable(Node node, String type, int ID) {
        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getLayoutX();
            startY = e.getSceneY() - node.getLayoutY();
        });

        node.setOnMouseDragged(e -> {
            double newX = e.getSceneX() - startX;
            double newY = e.getSceneY() - startY;
            node.setLayoutX(newX);
            node.setLayoutY(newY);

            positionList.updatePosition(ID, newX, newY);
        });
    }

    private void makeSelectable(Node node, String type, int ID){
        // Create a context menu
        ContextMenu contextMenu = new ContextMenu();

        // Create menu items
        MenuItem resizeItem = new MenuItem("Resize");
        MenuItem propertiesItem = new MenuItem("Properties");
        MenuItem deleteItem = new MenuItem("Delete");

        // Create a menu item for sending the component to a subsystem
        Menu sendToSubSystemItem = new Menu("Send to SubSystem");

        // Add menu items to the context menu
        if (!Objects.equals(type, "connection")) {
            contextMenu.getItems().add(resizeItem);
        }

        if (!Objects.equals(type, "subSystem") && !Objects.equals(type, "connection")) {
            contextMenu.getItems().add(sendToSubSystemItem);
        }

        contextMenu.getItems().add(propertiesItem);
        contextMenu.getItems().add(deleteItem);

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
                            if (Objects.equals(type, "actor")){
                                ((ImageView) ((VBox) node).getChildren().get(0)).setFitWidth(newWidth);
                                ((ImageView) ((VBox) node).getChildren().get(0)).setFitHeight(newHeight);
                                // Update the position of the actor
                                positionList.updatePosition(ID, node.getLayoutX(), node.getLayoutY());
                                // Update the size of the actor
                                positionList.updateSize(ID, newWidth, newHeight);

                            } else if (Objects.equals(type, "useCase")) {
                                ((Ellipse) ((StackPane) node).getChildren().get(0)).setRadiusX(newWidth);
                                ((Ellipse) ((StackPane) node).getChildren().get(0)).setRadiusY(newHeight);
                                // Update the position of the use case
                                positionList.updatePosition(ID, node.getLayoutX(), node.getLayoutY());
                                // Update the size of the use case
                                positionList.updateSize(ID, newWidth, newHeight);

                            } else if (Objects.equals(type, "subSystem")) {
                                ((Rectangle) ((VBox) node).getChildren().get(0)).setWidth(newWidth);
                                ((Rectangle) ((VBox) node).getChildren().get(0)).setHeight(newHeight);
                                // Update the position of the subsystem
                                positionList.updatePosition(ID, node.getLayoutX(), node.getLayoutY());
                                // Update the size of the subsystem
                                positionList.updateSize(ID, newWidth, newHeight);
                            }
                        }
                    }
                }
            });
        });

        // Set the action for properties menu item
        propertiesItem.setOnAction(e -> {
            System.out.println("Properties Clicked");
            // Send the component details to the PropertyPage
            ArrayList<Object> objects = new ArrayList<>();
            objects.add(projectName);
            objects.add(directory);
            objects.add(subSystemID);
            objects.add(type);
            objects.add(ID);
            saveProject();
            try {
                FXRouter.popup("PropertyPage", objects);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

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
                // Remove the item from the list
                if(Objects.equals(type, "connection")) {
                    connectionList.removeConnectionByID(ID);
                    // remove points of the line
                    for (Node point : designPane.getChildren()) {
                        if (point instanceof Circle) {
                            if (point.getLayoutX() == ((Line) node).getStartX() && point.getLayoutY() == ((Line) node).getStartY()) {
                                designPane.getChildren().remove(point);
                            } else if (point.getLayoutX() == ((Line) node).getEndX() && point.getLayoutY() == ((Line) node).getEndY()) {
                                designPane.getChildren().remove(point);
                            }
                        }
                    }
                } else if(Objects.equals(type, "useCase")) {
                    useCaseList.removeUseCaseByPositionID(ID);
                    positionList.removePositionByID(ID);
                } else if(Objects.equals(type, "actor")) {
                    actorList.removeActorByPositionID(ID);
                    positionList.removePositionByID(ID);
                } else if(Objects.equals(type, "subSystem")) {
                    Integer subSystemIDToRemove = subsystemList.findSubSystemIDByPositionID(ID);
                    // Remove all the components in the subsystem from the position list
                    if (positionList.findBySubSystemID(subSystemIDToRemove) != null){
                        positionList.findBySubSystemID(subSystemIDToRemove).forEach(position -> {
                            positionList.removePositionByID(position.getPositionID());
                            useCaseList.removeUseCaseByPositionID(position.getPositionID());
                            actorList.removeActorByPositionID(position.getPositionID());
                        });
                    }
                    connectionList.removeConnectionBySubSystemID(subSystemIDToRemove);
                    subsystemList.removeSubSystemByPositionID(ID);
                    positionList.removePositionByID(ID);
                }
                designPane.getChildren().remove(node);
                saveProject();
                loadProject();
                System.out.println("Item Removed");
            }
        });

        // Deselect the node when the mouse is released
        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setOnMouseDragged(null);
                saveProject();
                if (!Objects.equals(type, "connection")) {
                    makeDraggable(node, type, ID);
                }
            }
        });

        // Show the context menu
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                    System.out.println("Item Right Clicked");
                    contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());

                    if(!Objects.equals(type, "subSystem") && !Objects.equals(type, "connection"))
                    {
                        if (subSystemID != 0) {
                            MenuItem mainSystemItem = new MenuItem("Main");
                            mainSystemItem.setOnAction(e -> {
                                Position position = positionList.findByPositionId(ID);
                                position.setSubSystemID(0);
                                saveProject();
                                subSystemID = 0;
                                loadProject();
                            });
                            // Add the Main to the sendToSubSystemItem
                            sendToSubSystemItem.getItems().add(mainSystemItem);
                        }

                        subsystemList.getSubSystemList().forEach(subSystem -> {
                            if (subSystemID != subSystem.getSubSystemID()) {
                                MenuItem subSystemItem = new MenuItem(subSystem.getSubSystemName());
                                subSystemItem.setOnAction(e -> {
                                    Position position = positionList.findByPositionId(ID);
                                    position.setSubSystemID(subSystem.getSubSystemID());
                                    saveProject();
                                    subSystemID = subSystem.getSubSystemID();
                                    loadProject();
                                });
                                // Add the subSystemItem to the sendToSubSystemItem
                                sendToSubSystemItem.getItems().add(subSystemItem);
                            }
                        });
                    }

                    if (!Objects.equals(type,"connection"))
                    {
                        makeDraggable(node, type, ID);
                    }
                }
            }
        });
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
                saveProject();
                // Create a popup for text input
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(projectName);
                objects.add(directory);
                objects.add("useCase");
                objects.add(50.00);
                objects.add(30.00);
                objects.add(dragEvent.getX() - 75);
                objects.add(dragEvent.getY() - 75);
                objects.add(subSystemID);
                FXRouter.popup("LabelPage",objects);
            } else if (dragEvent.getDragboard().getString().equals("Actor")) {
                saveProject();
                // Create a popup for text input
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(projectName);
                objects.add(directory);
                objects.add("actor");
                objects.add(75.00);
                objects.add(75.00);
                objects.add(dragEvent.getX() - 75);
                objects.add(dragEvent.getY() - 75);
                objects.add(subSystemID);
                FXRouter.popup("LabelPage",objects);
            } else if (dragEvent.getDragboard().getString().equals("System")) {
                saveProject();
                // Create a popup for text input
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(projectName);
                objects.add(directory);
                objects.add("subSystem");
                objects.add(100.00);
                objects.add(100.00);
                objects.add(dragEvent.getX() - 75);
                objects.add(dragEvent.getY() - 75);
                objects.add(subsystemList.findLastSubSystemId() + 1);
                FXRouter.popup("LabelPage",objects);
            } else if (dragEvent.getDragboard().getString().equals("Line")) {
                addToConnectionList(dragEvent.getX() - 45, dragEvent.getY() + 45 , dragEvent.getX() + 45, dragEvent.getY() - 45, "Association", "none", "solid", "none");
                drawLine(connectionList.findLastConnectionID(), dragEvent.getX() - 45, dragEvent.getY() + 45 , dragEvent.getX() + 45, dragEvent.getY() - 45, "Association", "none", "solid", "none");
            } else if (dragEvent.getDragboard().getString().equals("Existing Actor")) {
                // draw the existing actor
                Actor actor = actorList.findByActorId(existingActorID);
                drawActor(75.00, 75.00, dragEvent.getX() - 75, dragEvent.getY() - 75, actor.getActorName(), actor.getActorID(), positionList.findLastPositionId() + 1);
                // save to actorList
                addToActorList(75.00, 75.00, dragEvent.getX() - 75, dragEvent.getY() - 75, actor.getActorName(), actor.getNote(), actor.getActorID());
                saveProject();
                loadProject();
            } else {
                System.out.println(dragEvent.getDragboard().getString());
            }

            if (!designPane.getChildren().isEmpty()) {
                guideLabel.setVisible(false);
            }
        }
    }

    public void addNewSubSystemButton(ActionEvent actionEvent) throws IOException {
        // Open LabelPage to create new subSystem
        saveProject();
        // Create a popup for text input
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(projectName);
        objects.add(directory);
        objects.add("subSystem");
        objects.add(100.00);
        objects.add(100.00);
        // Add default position for subSystem
        objects.add(10.00);
        objects.add(10.00);
        objects.add(subsystemList.findLastSubSystemId() + 1);
        FXRouter.popup("LabelPage",objects);
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
            subSystemID = 0;
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
        // Clear the lists
        actorList.clear();
        connectionList.clear();
        positionList.clear();
        subsystemList.clear();
        useCaseList.clear();

        // Load positions
        DataSource<PositionList> positionListDataSource = new PositionListFileDataSource(directory, projectName + ".csv");
        positionList = positionListDataSource.readData(); // Read the PositionList from the CSV file

        // Load actors
        DataSource<ActorList> actorListDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
        actorList = actorListDataSource.readData(); // Read the ActorList from the CSV file

        // Load subsystems
        DataSource<SubSystemList> subsystemListDataSource = new SubSystemListFileDataSource(directory, projectName + ".csv");
        subsystemList = subsystemListDataSource.readData(); // Read the SubsystemList from the CSV file

        // Load use cases
        DataSource<UseCaseList> useCaseListDataSource = new UseCaseListFileDataSource(directory, projectName + ".csv");
        useCaseList = useCaseListDataSource.readData(); // Read the UseCaseList from the CSV file

        // Load connections
        DataSource<ConnectionList> connectionListDataSource = new ConnectionListFileDataSource(directory, projectName + ".csv");
        connectionList = connectionListDataSource.readData(); // Read the ConnectionList from the CSV file

        // Load notes
        DataSource<NoteList> noteListDataSource = new NoteListFileDataSource(directory, projectName + ".csv");
        noteList = noteListDataSource.readData(); // Read the NoteList from the CSV file

        // Load component preferences
        DataSource<ComponentPreferenceList> componentPreferenceListDataSource = new ComponentPreferenceListFileDataSource(directory, projectName + ".csv");
        componentPreferenceList = componentPreferenceListDataSource.readData(); // Read the ComponentPreferenceList from the CSV file

        // Load preferences
        DataSource<PreferenceList> preferenceListDataSource = new PreferenceListFileDataSource(directory, projectName + ".csv");
        preferenceList = preferenceListDataSource.readData(); // Read the PreferenceList from the CSV file
        preferenceList.getPreferenceList().forEach(tempPreference -> {
            preference = tempPreference;
        });
        if (preference == null) {
            preference = new Preference(1,Color.BLACK,Color.BLACK,"Light");
            preferenceList.addPreference(preference);
        }

        // load systemList
        useCaseSystemList = new UseCaseSystemList();
        UseCaseSystem useCaseSystem = new UseCaseSystem(useCaseSystemList.findLastUseCaseSystemId() + 1, projectName);
        useCaseSystemList.addSystem(useCaseSystem);

        loadDesignPane();
        loadSubSystemButton();
        loadActorsList();
    }

    public void loadDesignPane() {
        // Clear the design pane and note text area
        designPane.getChildren().clear();
        noteTextArea.clear();

        // Recreate each actor
        actorList.getActorList().forEach(actor -> {
            // Find the position of the actor
            Position position = positionList.findByPositionId(actor.getPositionID());
            if (position != null && position.getSubSystemID() == subSystemID) {
                drawActor(position.getFitWidth(), position.getFitHeight(), position.getXPosition(), position.getYPosition(), actor.getActorName(), actor.getActorID(), actor.getPositionID());
            }
        });

        // Recreate each subsystem
        subsystemList.getSubSystemList().forEach(subsystem -> {
            // Find the position of the subsystem
            Position position = positionList.findByPositionId(subsystem.getPositionID());
            if (position != null && subSystemID == 0) {
                drawSubSystem(position.getFitWidth(), position.getFitHeight(), position.getXPosition(), position.getYPosition(), subsystem.getSubSystemName(), subsystem.getSubSystemID(), subsystem.getPositionID());
            }
        });

        // Recreate each use case
        useCaseList.getUseCaseList().forEach(useCase -> {
            // Find the position of the use case
            Position position = positionList.findByPositionId(useCase.getPositionID());
            if (position != null && position.getSubSystemID() == subSystemID) {
                drawUseCase(position.getFitWidth(), position.getFitHeight(), position.getXPosition(), position.getYPosition(), useCase.getUseCaseName(), useCase.getActorID(), useCase.getPreCondition(), useCase.getDescription(), useCase.getPostCondition(), useCase.getUseCaseID(), useCase.getPositionID());
            }
        });

        // Recreate each connection
        connectionList.getConnectionList().forEach(connection -> {
            if (connection.getSubSystemID() == subSystemID) {
                drawLine(connection.getConnectionID(), connection.getStartX(), connection.getStartY(), connection.getEndX(), connection.getEndY(), connection.getLabel(), connection.getArrowHead(), connection.getLineType(), connection.getArrowTail());
            }
        });

        // Recreate each note to noteTextArea
        Note note = noteList.findBySubSystemID(subSystemID);
        if ((note != null) && (!Objects.equals(note.getNote(), "!@#$%^&*()_+"))) {
            noteTextArea.setText(note.getNote());
        }

        // Check if designPane is not empty
        if (!designPane.getChildren().isEmpty()) {
            // Make guideLabel invisible
            guideLabel.setVisible(false);
        }
    }

    public void loadSubSystemButton() {
        // Clear the subSystemHBox except for addNewSubSystemButton
        subSystemHBox.getChildren().remove(0, subSystemHBox.getChildren().size()-1);
        // Add a button for main subSystem
        Button mainSubSystemButton = new Button("Main");
        subSystemHBox.getChildren().add(subSystemHBox.getChildren().size() - 1, mainSubSystemButton);
        // Set the action for the button
        mainSubSystemButton.setOnAction(e -> {
            saveProject();
            // Set the subSystemID
            subSystemID = 0;
            // Load the project
            loadProject();
        });

        // Add a button for each subsystem
        DataSource<SubSystemList> subsystemListDataSource = new SubSystemListFileDataSource(directory, projectName + ".csv");
        SubSystemList subsystemList = subsystemListDataSource.readData(); // Read the SubsystemList from the CSV file
        subsystemList.getSubSystemList().forEach(subsystem -> {
            Button button = new Button(subsystem.getSubSystemName());
            subSystemHBox.getChildren().add(subSystemHBox.getChildren().size() - 1, button);
            // Set the action for the button
            button.setOnAction(e -> {
                saveProject();
                // Set the subSystemID
                subSystemID = subsystem.getSubSystemID();
                // Load the project
                loadProject();
            });
        });

        // Highlight the button if it is the current subSystem
        for (Node node : subSystemHBox.getChildren()) {
            if (node instanceof Button) {
                if (subSystemID == 0 && ((Button) node).getText().equals("Main")) {
                    ((Button) node).setStyle("-fx-background-color: #10b981; -fx-text-fill: white;");
                } else if (subsystemList.findBySubSystemId(subSystemID) != null && ((Button) node).getText().equals(subsystemList.findBySubSystemId(subSystemID).getSubSystemName())) {
                    ((Button) node).setStyle("-fx-background-color: #10b981; -fx-text-fill: white;");
                } else {
                    ((Button) node).setStyle("");
                }
            }
        }
    }

    public void loadActorsList(){
        // If actorList is not empty show the actorsPane
        actorsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        actorsScrollPane.setContent(actorsAnchor);
        if (!actorList.getActorList().isEmpty()) {
            actorsAnchor.getChildren().clear();
            actorsPane.setVisible(true);
        } else {
            actorsPane.setVisible(false);
        }

        // create a loop from first actor ID to last actor ID
        for (int i = actorList.findFirstActorId(); i <= actorList.findLastActorId(); i++) {
            Actor actor = actorList.findByActorId(i);
            if (actor != null) {
                // Add the actor to the actorsAnchor
                // Draw an actor
                ImageView imageView = new ImageView();
                imageView.setImage(actorImageView.getImage());
                imageView.setFitWidth(90);
                imageView.setFitHeight(90);

                // Add hidden label to the system
                Label type = new Label("actor");
                type.setVisible(false);

                // Add an actor and label to VBox
                VBox vbox = new VBox();
                vbox.getChildren().addAll(imageView, type, new Label(actor.getActorName()));
                vbox.setAlignment(Pos.CENTER);

                // set the position of the actor in the actorsAnchor
                if(actorsAnchor.getChildren().size() % 2 == 0){
                    vbox.setLayoutX(0);
                    vbox.setLayoutY(5 + 90 * actorsAnchor.getChildren().size());
                } else {
                    vbox.setLayoutX(100);
                    vbox.setLayoutY(5 + 90 * (actorsAnchor.getChildren().size() - 1));
                }

                if (subSystemID != 0)
                {
                    vbox.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            System.out.println("Actor Drag Detected");
                            Dragboard dragboard = vbox.startDragAndDrop(TransferMode.ANY);
                            ClipboardContent clipboardContent = new ClipboardContent();
                            clipboardContent.putString("Existing Actor");
                            existingActorID = actor.getActorID();
                            dragboard.setContent(clipboardContent);

                            mouseEvent.consume();
                        }
                    });
                }

                actorsAnchor.getChildren().add(vbox);
            }
        }
    }

    public void saveProject() {
        // Save the project components
        DataSource<ActorList> actorListDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
        DataSource<ConnectionList> connectionListDataSource = new ConnectionListFileDataSource(directory, projectName + ".csv");
        DataSource<ComponentPreferenceList> componentPreferenceListDataSource = new ComponentPreferenceListFileDataSource(directory, projectName + ".csv");
        DataSource<NoteList> noteListDataSource = new NoteListFileDataSource(directory, projectName + ".csv");
        DataSource<PositionList> positionListDataSource = new PositionListFileDataSource(directory, projectName + ".csv");
        DataSource<SubSystemList> subsystemListDataSource = new SubSystemListFileDataSource(directory, projectName + ".csv");
        DataSource<UseCaseList> useCaseListFileDataSource = new UseCaseListFileDataSource(directory, projectName + ".csv");
        DataSource<UseCaseSystemList> useCaseSystemListDataSource = new UseCaseSystemListFileDataSource(directory, projectName + ".csv");
        DataSource<PreferenceList> preferenceListFileDataSource = new PreferenceListFileDataSource(directory, projectName + ".csv");

        // find note from subSystemID
        if (noteList.findBySubSystemID(subSystemID) == null) {
            if (!noteTextArea.getText().isEmpty()) {
                noteList.addNote(new Note(subSystemID, noteTextArea.getText()));
            } else {
                noteList.addNote(new Note(subSystemID, "!@#$%^&*()_+"));
            }
        } else {
            if (!noteTextArea.getText().isEmpty()) {
                noteList.updateNoteBySubSystemID(subSystemID, noteTextArea.getText());
            } else {
                noteList.updateNoteBySubSystemID(subSystemID, "!@#$%^&*()_+");
            }
        }

        // Write data to CSV
        actorListDataSource.writeData(actorList);
        connectionListDataSource.writeData(connectionList);
        componentPreferenceListDataSource.writeData(componentPreferenceList);
        noteListDataSource.writeData(noteList);
        positionListDataSource.writeData(positionList);
        subsystemListDataSource.writeData(subsystemList);
        useCaseListFileDataSource.writeData(useCaseList);
        useCaseSystemListDataSource.writeData(useCaseSystemList);
        preferenceListFileDataSource.writeData(preferenceList);

        System.out.println("Project Saved");
    }

    public void handlePreferenceMenuItem(ActionEvent actionEvent) throws IOException {
        // Open the preference page
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(projectName);
        objects.add(directory);
        objects.add(subSystemID);
        FXRouter.popup("PreferencePage", objects);
    }

    public void handleExportMenuItem(ActionEvent actionEvent) {
        boolean noteAdded = false;
        // add note to the top left corner of the designPane
        if (noteTextArea.getText() != null) {
            Label note = new Label(noteTextArea.getText());
            note.setLayoutX(10);
            note.setLayoutY(10);
            designPane.getChildren().add(note);
            noteAdded = true;
        }

        designPane.getChildren().removeIf(node -> node instanceof Circle && ((Circle) node).getFill().equals(Color.RED));

        // save Pane to image
        WritableImage image = designPane.snapshot(new SnapshotParameters(), null);

        if (noteAdded) {
            designPane.getChildren().remove(designPane.getChildren().size() - 1);
        }

        try {
            // Create a file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export Project");
            fileChooser.setInitialFileName(projectName);
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void handleExportAllMenuItem(ActionEvent actionEvent) {
        int subSystemIDTemp = subSystemID;
        // load all the subSystems to the designPane
        subSystemID = 0;
        loadProject();

        // ArrayList to store the images
        ArrayList<WritableImage> images = new ArrayList<>();

        // add note to the top left corner of the designPane
        if (noteTextArea.getText() != null) {
            Label note = new Label(noteTextArea.getText());
            note.setLayoutX(10);
            note.setLayoutY(10);
            designPane.getChildren().add(note);
        }

        // remove all red circles in the designPane
        designPane.getChildren().removeIf(node -> node instanceof Circle && ((Circle) node).getFill().equals(Color.RED));

        // save Pane to image
        WritableImage image = designPane.snapshot(new SnapshotParameters(), null);

        // add the image to the images ArrayList
        images.add(image);

        subsystemList.getSubSystemList().forEach(subsystem -> {
            subSystemID = subsystem.getSubSystemID();
            System.out.println(subSystemID);
            loadDesignPane();

            // add note to the top left corner of the designPane
            if (noteTextArea.getText() != null) {
                Label note = new Label(noteTextArea.getText());
                note.setLayoutX(10);
                note.setLayoutY(10);
                designPane.getChildren().add(note);
            }
            // remove all red circles in the designPane
            designPane.getChildren().removeIf(node -> node instanceof Circle && ((Circle) node).getFill().equals(Color.RED));

            // save Pane to image
            WritableImage imageTemp = designPane.snapshot(new SnapshotParameters(), null);

            // add the image to the images ArrayList
            images.add(imageTemp);
        });

        // reset the subSystemID
        subSystemID = subSystemIDTemp;
        loadProject();

        try {
            // Create a file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export Project");
            fileChooser.setInitialFileName(projectName);
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                // save the main image to the file
                ImageIO.write(SwingFXUtils.fromFXImage(images.get(0), null), "png", new File(file.getParent() + "/" + projectName + "-" + "Main" + ".png"));
                // save the images to the file
                AtomicInteger i = new AtomicInteger();
                subsystemList.getSubSystemList().forEach(subsystem -> {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(images.get(i.get()), null), "png", new File(file.getParent() + "/" + projectName + "-" + subsystem.getSubSystemName() + ".png"));
                        i.getAndIncrement();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void handleManualMenu(ActionEvent actionEvent) {
        // load manual pdf
        try {
            File file = new File("src/main/resources/manual.pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
