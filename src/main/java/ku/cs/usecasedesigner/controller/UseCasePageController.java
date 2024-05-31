package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.ActorList;
import ku.cs.usecasedesigner.models.PositionList;
import ku.cs.usecasedesigner.models.UseCase;
import ku.cs.usecasedesigner.models.UseCaseList;
import ku.cs.usecasedesigner.services.ActorListFileDataSource;
import ku.cs.usecasedesigner.services.DataSource;
import ku.cs.usecasedesigner.services.PositionListFileDataSource;
import ku.cs.usecasedesigner.services.UseCaseListFileDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UseCasePageController {

    @FXML private Label errorLabel;

    @FXML private ChoiceBox actorChoiceBox;

    @FXML private TextField useCaseIDTextField ,useCaseNameTextField ,preConditionTextField, postConditionTextField;

    @FXML private TextArea descriptionTextArea, actorActionTextArea, systemActionTextArea;

    @FXML private VBox actorActionVBox, systemActionVBox;

    @FXML private ScrollPane actorActionScrollPane, systemActionScrollPane;

    private String directory;
    private String projectName;
    private UseCase useCase;

    private UseCaseList useCaseList;
    private ActorList actorList;
    private PositionList positionList;
    private DataSource<UseCaseList> useCaseListDataSource;
    private DataSource<ActorList> actorListFileDataSource;
    private DataSource<PositionList> positionListFileDataSource;

    @FXML void initialize() {
        // disable horizontal scroll bar
        actorActionScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        systemActionScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

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

            // Find the use case by ID
            useCase = useCaseList.findByUseCaseId(useCaseID);

            useCaseIDTextField.setText(useCase.getUseCaseID() + "");
            useCaseNameTextField.setText(useCase.getUseCaseName());

            // Add actors to the choice box
            for (int i = 0; i < actorList.getActorList().size(); i++) {
                actorChoiceBox.getItems().add(actorList.getActorList().get(i).getActorName());
            }

            if (useCase.getActorID() != 0)
            {
                actorChoiceBox.setValue(actorList.findByActorId(useCase.getActorID()).getActorName());

            }
            if (!Objects.equals(useCase.getDescription(), "!@#$%^&*()_+"))
            {
                descriptionTextArea.setText(useCase.getDescription());
            }
            if (!Objects.equals(useCase.getPreCondition(), "!@#$%^&*()_+"))
            {
                preConditionTextField.setText(useCase.getPreCondition());
            }
            if (!Objects.equals(useCase.getActorAction(), "!@#$%^&*()_+"))
            {
                actorActionTextArea.setText(useCase.getPostCondition());
            }
            if (!Objects.equals(useCase.getSystemAction(), "!@#$%^&*()_+"))
            {
                systemActionTextArea.setText(useCase.getPostCondition());
            }
            if (!Objects.equals(useCase.getPostCondition(), "!@#$%^&*()_+"))
            {
                postConditionTextField.setText(useCase.getPostCondition());
            }
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {

        // Set value for actorID
        if (actorChoiceBox.getValue() != null) {
            int actorID = actorList.findByActorName((String) actorChoiceBox.getValue()).getActorID();
            useCase.setActorID(actorID);
        }

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

        // Set value for description
        if (!descriptionTextArea.getText().isEmpty()) {
            useCase.setDescription(descriptionTextArea.getText());
        }

        // Set value for preCondition
        if (!preConditionTextField.getText().isEmpty()) {
            useCase.setPreCondition(preConditionTextField.getText());
        }

        // Set value for actorAction
        if (!actorActionTextArea.getText().isEmpty()) {
            useCase.setActorAction(actorActionTextArea.getText());
        }

        // Set value for systemAction
        if (!systemActionTextArea.getText().isEmpty()) {
            useCase.setSystemAction(systemActionTextArea.getText());
        }

        // Set value for postCondition
        if (!postConditionTextField.getText().isEmpty()) {
            useCase.setPostCondition(postConditionTextField.getText());
        }

        // Edit the useCase in the useCaseList
        useCaseListDataSource.writeData(useCaseList);

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
        textArea.setPrefWidth(actorActionVBox.getWidth());
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
        textArea.setPrefWidth(actorActionVBox.getWidth());
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
}
