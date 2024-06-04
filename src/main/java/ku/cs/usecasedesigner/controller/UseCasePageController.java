package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.*;
import ku.cs.usecasedesigner.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UseCasePageController {

    @FXML
    private Label errorLabel;

    @FXML
    private TextField useCaseIDTextField, useCaseNameTextField, actorTextField, preConditionTextField, postConditionTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private VBox actorActionVBox, systemActionVBox;

    @FXML
    private ScrollPane actorActionScrollPane, systemActionScrollPane;

    @FXML
    private Button actorChoiceButton, preConditionChoiceButton, postConditionChoiceButton;

    private String directory, projectName;
    private UseCase useCase;
    private UseCaseList useCaseList;
    private ActorList actorList;
    private PositionList positionList;
    private UseCaseDetailList useCaseDetailList;
    private DataSource<UseCaseList> useCaseListDataSource;
    private DataSource<ActorList> actorListFileDataSource;
    private DataSource<PositionList> positionListFileDataSource;
    private DataSource<UseCaseDetailList> useCaseDetailListDataSource;

    @FXML
    void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            int useCaseID = (int) objects.get(2);

            // Read the data from the csv files
            useCaseListDataSource = new UseCaseListFileDataSource(directory, projectName + ".csv");
            useCaseList = useCaseListDataSource.readData();
            actorListFileDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
            actorList = actorListFileDataSource.readData();
            positionListFileDataSource = new PositionListFileDataSource(directory, projectName + ".csv");
            positionList = positionListFileDataSource.readData();
            useCaseDetailListDataSource = new UseCaseDetailListFileDataSource(directory, projectName + ".csv");
            useCaseDetailList = useCaseDetailListDataSource.readData();

            // Find the use case by useCaseID
            useCase = useCaseList.findByUseCaseId(useCaseID);

            useCaseIDTextField.setText(useCase.getUseCaseID() + "");
            useCaseNameTextField.setText(useCase.getUseCaseName());

            if (!Objects.equals(useCase.getDescription(), "!@#$%^&*()_+")) {
                descriptionTextArea.setText(useCase.getDescription());
            }
            if (!Objects.equals(useCase.getPreCondition(), "!@#$%^&*()_+")) {
                preConditionTextField.setText(useCase.getPreCondition());
            }
            if (!Objects.equals(useCase.getActorID(), 0)) {
                // load actorID to actorTextField
                // split the actorID by "/" and get the actorName from the actorList
                String[] actorIDs = useCase.getActorID().split("/");
                // find each actor by actorID and add the actorName to the actorTextField
                for (String actorID : actorIDs) {
                    Actor actor = actorList.findByActorId(Integer.parseInt(actorID));
                    if (actor != null) {
                        if (actorTextField.getText().isEmpty()) {
                            actorTextField.setText(actor.getActorName());
                        } else {
                            actorTextField.appendText("/" + actor.getActorName());
                        }
                    }
                }

            }
            if (!Objects.equals(useCase.getPostCondition(), "!@#$%^&*()_+")) {
                postConditionTextField.setText(useCase.getPostCondition());
            }

            // load useCaseDetail to the actorActionVBox and systemActionVBox
            for (UseCaseDetail useCaseDetail : useCaseDetailList.getUseCaseDetailList()) {
                if (useCaseDetail.getUseCaseID() == useCase.getUseCaseID()) {
                    if (useCaseDetail.getType().equals("actor")) {
                        HBox hBox = new HBox();
                        TextArea textArea = new TextArea();
                        textArea.setPrefHeight(20);
                        textArea.setWrapText(true);
                        textArea.setText(useCaseDetail.getDetail());
                        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
                            textArea.setPrefHeight(textArea.getText().split("\n").length * 20);
                        });
                        Button deleteButton = new Button("-");
                        deleteButton.setPrefHeight(20);
                        deleteButton.setOnAction(event -> {
                            actorActionVBox.getChildren().remove(hBox);
                        });
                        hBox.getChildren().add(textArea);
                        hBox.getChildren().add(deleteButton);
                        actorActionVBox.getChildren().add(hBox);
                    } else if (useCaseDetail.getType().equals("system")) {
                        HBox hBox = new HBox();
                        TextArea textArea = new TextArea();
                        textArea.setPrefHeight(20);
                        textArea.setWrapText(true);
                        textArea.setText(useCaseDetail.getDetail());
                        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
                            textArea.setPrefHeight(textArea.getText().split("\n").length * 20);
                        });
                        Button deleteButton = new Button("-");
                        deleteButton.setPrefHeight(20);
                        deleteButton.setOnAction(event -> {
                            systemActionVBox.getChildren().remove(hBox);
                        });
                        hBox.getChildren().add(textArea);
                        hBox.getChildren().add(deleteButton);
                        systemActionVBox.getChildren().add(hBox);
                    }
                }
            }
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        // Set value for useCaseID
        // Check if useCase ID is not empty and not being used by another use case
        if (!useCaseIDTextField.getText().isEmpty()) {
            int useCaseID = Integer.parseInt(useCaseIDTextField.getText());
            if (useCaseList.findByUseCaseId(useCaseID) == null || useCaseID == useCase.getUseCaseID()) {
                errorLabel.setText("");
                useCase.setUseCaseID(useCaseID);
            } else {
                errorLabel.setText("This use case ID is already being used by another use case.");
                return;
            }
        } else {
            errorLabel.setText("Please enter the use case ID.");
            return;
        }

        // Set value for useCaseName
        // Check if useCaseName is not empty and not being used by another use case
        if (!useCaseNameTextField.getText().isEmpty()) {
            String useCaseName = useCaseNameTextField.getText();
            if (!useCaseList.isUseCaseNameExist(useCaseName) || useCaseName.equals(useCase.getUseCaseName())) {
                errorLabel.setText("");
                useCase.setUseCaseName(useCaseName);
            } else {
                errorLabel.setText("This use case name is already being used by another use case.");
                return;
            }
        } else {
            errorLabel.setText("Please enter the use case name.");
            return;
        }

        // Set value for actorID
        // Check if actorTextField is not empty
        if (!actorTextField.getText().isEmpty()) {
            String[] actorNames = actorTextField.getText().split("/");
            String actorID = "";
            for (String actorName : actorNames) {
                Actor actor = actorList.findByActorName(actorName);
                if (actor != null) {
                    if (actorID.isEmpty()) {
                        actorID = actor.getActorID() + "";
                    } else {
                        actorID += "/" + actor.getActorID();
                    }
                }
            }
            useCase.setActorID(actorID);
        }

        // Set value for description
        if (!descriptionTextArea.getText().isEmpty()) {
            useCase.setDescription(descriptionTextArea.getText());
        } else {
            useCase.setDescription("!@#$%^&*()_+");
        }

        // Set value for preCondition
        if (!preConditionTextField.getText().isEmpty()) {
            useCase.setPreCondition(preConditionTextField.getText());
        } else {
            useCase.setPreCondition("!@#$%^&*()_+");
        }

        useCaseDetailList.clear();
        // Get the text from the textAreas in the actorActionVBox and write them to the useCaseDetailList
        int actorNumber = 1;
        for (Node node : actorActionVBox.getChildren()) {
            HBox hBox = (HBox) node;
            TextArea textArea = (TextArea) hBox.getChildren().get(0);
            if (!textArea.getText().isEmpty()) {
                UseCaseDetail useCaseDetail = new UseCaseDetail(useCase.getUseCaseID(), "actor", actorNumber, textArea.getText());
                useCaseDetailList.addUseCaseDetail(useCaseDetail);
                actorNumber++;
            }
        }

        // Get the text from the textAreas in the systemActionVBox and write them to the useCaseDetailList
        int systemNumber = 1;
        for (Node node : systemActionVBox.getChildren()) {
            HBox hBox = (HBox) node;
            TextArea textArea = (TextArea) hBox.getChildren().get(0);
            if (!textArea.getText().isEmpty()) {
                UseCaseDetail useCaseDetail = new UseCaseDetail(useCase.getUseCaseID(), "system", systemNumber, textArea.getText());
                useCaseDetailList.addUseCaseDetail(useCaseDetail);
                systemNumber++;
            }
        }

        // Set value for postCondition
        if (!postConditionTextField.getText().isEmpty()) {
            useCase.setPostCondition(postConditionTextField.getText());
        } else {
            useCase.setPostCondition("!@#$%^&*()_+");
        }

        // Edit the useCase in the useCaseList
        useCaseListDataSource.writeData(useCaseList);
        // Edit the useCaseDetailList
        useCaseDetailListDataSource.writeData(useCaseDetailList);

        // send the project name and directory to HomePage
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(projectName);
        objects.add(directory);
        objects.add(positionList.findByPositionId(useCase.getPositionID()).getSubSystemID());

        FXRouter.goTo("HomePage", objects);

        // Close the current window
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    public void handleAddActorActionButton(ActionEvent actionEvent) {
        // if the last textArea is empty, do not add a new textArea
        if (!actorActionVBox.getChildren().isEmpty()) {
            HBox lastHBox = (HBox) actorActionVBox.getChildren().get(actorActionVBox.getChildren().size() - 1);
            TextArea lastTextArea = (TextArea) lastHBox.getChildren().get(0);
            if (lastTextArea.getText().isEmpty()) {
                errorLabel.setText("Please fill in the last text area before adding a new one.");
                return;
            }
        }
        errorLabel.setText("");
        // Create a new HBox to hold the textArea and delete button
        HBox hBox = new HBox();
        // add the textArea to the actorActionVBox
        TextArea textArea = new TextArea();
        // set textArea to size of a single line
        textArea.setPrefHeight(20);
        textArea.setWrapText(true);
        // Make textArea size change depending on the text
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setPrefHeight(textArea.getText().split("\n").length * 20);
        });
        // create a delete button to remove the textArea
        Button deleteButton = new Button("-");
        deleteButton.setPrefHeight(20);
        deleteButton.setOnAction(event -> {
            actorActionVBox.getChildren().remove(hBox);
        });
        // Add textArea and delete button to the HBox
        hBox.getChildren().add(textArea);
        hBox.getChildren().add(deleteButton);

        actorActionVBox.getChildren().add(hBox);
    }

    public void handleAddSystemActionButton(ActionEvent actionEvent) {
        // if the last textArea is empty, do not add a new textArea
        if (!systemActionVBox.getChildren().isEmpty()) {
            HBox lastHBox = (HBox) systemActionVBox.getChildren().get(systemActionVBox.getChildren().size() - 1);
            TextArea lastTextArea = (TextArea) lastHBox.getChildren().get(0);
            if (lastTextArea.getText().isEmpty()) {
                errorLabel.setText("Please fill in the last text area before adding a new one.");
                return;
            }
        }
        errorLabel.setText("");
        // Create a new HBox to hold the textArea and delete button
        HBox hBox = new HBox();
        // add the textArea to the systemActionVBox
        TextArea textArea = new TextArea();
        // set textArea to size of a single line
        textArea.setPrefHeight(20);
        textArea.setWrapText(true);
        // Make textArea size change depending on the text
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setPrefHeight(textArea.getText().split("\n").length * 15);
        });
        // create a delete button to remove the textArea
        Button deleteButton = new Button("-");
        deleteButton.setPrefHeight(20);
        deleteButton.setOnAction(event -> {
            actorActionVBox.getChildren().remove(hBox);
        });
        // Add textArea and delete button to the HBox
        hBox.getChildren().add(textArea);
        hBox.getChildren().add(deleteButton);

        systemActionVBox.getChildren().add(hBox);
    }

    public void handleActorChoiceButton(ActionEvent actionEvent) {
        // Show the actorList in a menu item and let the user choose an actor to add to the actorTextField
        ContextMenu contextMenu = new ContextMenu();
        for (int i = actorList.findFirstActorId(); i <= actorList.findLastActorId(); i++) {
            Actor actor = actorList.findByActorId(i);
            if (actor != null) {
                MenuItem menuItem = new MenuItem(actor.getActorName());
                menuItem.setOnAction(event -> {
                    if (actorTextField.getText().isEmpty()) {
                        actorTextField.setText(actor.getActorName());
                    } else {
                        actorTextField.appendText("/" + actor.getActorName());
                    }
                });
                contextMenu.getItems().add(menuItem);
            }
        }
        // Get the position of the button
        Button button = (Button) actionEvent.getSource();
        contextMenu.show(button, button.getLayoutX() + button.getScene().getX() + button.getScene().getWindow().getX(),
                button.getLayoutY() + button.getScene().getY() + button.getScene().getWindow().getY() + button.getHeight());

    }

    public void handlePreConditionChoiceButton(ActionEvent actionEvent) {
        // Show the useCaseList in a menu item and let the user choose a useCase to add to the preConditionTextField
        ContextMenu contextMenu = new ContextMenu();
        for (UseCase tempUseCase : useCaseList.getUseCaseList()) {
            // Do not add the current useCase to the postCondition
            if (tempUseCase.getUseCaseID() == useCase.getUseCaseID()) {
                // Show the disabled useCase in the menu
                MenuItem menuItem = new MenuItem(tempUseCase.getUseCaseName());
                menuItem.setDisable(true);
                continue;
            }
            MenuItem menuItem = new MenuItem(tempUseCase.getUseCaseName());
            menuItem.setOnAction(event -> {
                if (preConditionTextField.getText().isEmpty()) {
                    preConditionTextField.setText(tempUseCase.getUseCaseName());
                } else {
                    preConditionTextField.appendText("/" + useCase.getUseCaseName());
                }
                // set current useCase as the postCondition of the selected useCase
                if (Objects.equals(tempUseCase.getPostCondition(), "!@#$%^&*()_+")) {
                    tempUseCase.setPostCondition(useCase.getUseCaseName());
                } else {
                    tempUseCase.setPostCondition(tempUseCase.getPostCondition() + "/" + useCase.getUseCaseName());
                }
            });
            contextMenu.getItems().add(menuItem);
        }
        // Get the position of the button
        Button button = (Button) actionEvent.getSource();
        contextMenu.show(button, button.getLayoutX() + button.getScene().getX() + button.getScene().getWindow().getX(),
                button.getLayoutY() + button.getScene().getY() + button.getScene().getWindow().getY() + button.getHeight());
    }

    public void handlePostConditionChoiceButton(ActionEvent actionEvent) {
        // Show the useCaseList in a menu item and let the user choose a useCase to add to the preConditionTextField
        ContextMenu contextMenu = new ContextMenu();
        for (UseCase tempUseCase : useCaseList.getUseCaseList()) {
            // Do not add the current useCase to the postCondition
            if (tempUseCase.getUseCaseID() == useCase.getUseCaseID()) {
                MenuItem menuItem = new MenuItem(tempUseCase.getUseCaseName());
                menuItem.setDisable(true);
                continue;
            }
            MenuItem menuItem = new MenuItem(tempUseCase.getUseCaseName());
            menuItem.setOnAction(event -> {
                if (postConditionTextField.getText().isEmpty()) {
                    postConditionTextField.setText(tempUseCase.getUseCaseName());
                } else {
                    postConditionTextField.appendText("/" + tempUseCase.getUseCaseName());
                }
                // set current useCase as the preCondition of the selected useCase
                if (Objects.equals(tempUseCase.getPreCondition(), "!@#$%^&*()_+")) {
                    tempUseCase.setPreCondition(useCase.getUseCaseName());
                } else {
                    tempUseCase.setPreCondition(tempUseCase.getPreCondition() + "/" + useCase.getUseCaseName());
                }
            });
            contextMenu.getItems().add(menuItem);
        }
        // Get the position of the button
        Button button = (Button) actionEvent.getSource();
        contextMenu.show(button, button.getLayoutX() + button.getScene().getX() + button.getScene().getWindow().getX(),
                button.getLayoutY() + button.getScene().getY() + button.getScene().getWindow().getY() + button.getHeight());
    }
}
