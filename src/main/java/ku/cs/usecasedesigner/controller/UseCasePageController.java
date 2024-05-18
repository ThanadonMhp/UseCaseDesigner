package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.ActorList;
import ku.cs.usecasedesigner.models.UseCase;
import ku.cs.usecasedesigner.models.UseCaseList;
import ku.cs.usecasedesigner.services.ActorListFileDataSource;
import ku.cs.usecasedesigner.services.DataSource;
import ku.cs.usecasedesigner.services.UseCaseListFileDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class UseCasePageController {

    @FXML private Label useCaseIDLabel, useCaseNameLabel;

    @FXML private ChoiceBox actorChoiceBox;

    @FXML private TextField preConditionTextField, postConditionTextField;

    @FXML private TextArea descriptionTextArea;

    private String directory;
    private String projectName;
    private UseCase useCase;

    private UseCaseList useCaseList;
    private ActorList actorList;
    private DataSource<UseCaseList> useCaseListDataSource;
    private DataSource<ActorList> actorListFileDataSource;

    @FXML void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            int useCaseID = (int) objects.get(2);

            // Read the useCase and actor list from the file
            useCaseListDataSource = new UseCaseListFileDataSource(directory, projectName + ".csv");
            useCaseList = useCaseListDataSource.readData();
            actorListFileDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
            actorList = actorListFileDataSource.readData();

            // Find the use case by ID
            useCase = useCaseList.findByUseCaseId(useCaseID);

            useCaseIDLabel.setText(useCase.getUseCaseID() + "");
            useCaseNameLabel.setText(useCase.getUseCaseName());

            // Add actors to the choice box
            for (int i = 0; i < actorList.getActorList().size(); i++) {
                actorChoiceBox.getItems().add(actorList.getActorList().get(i).getActorName());
            }

            if (useCase.getActorID() != 0)
            {
                actorChoiceBox.setValue(actorList.findByActorId(useCase.getActorID()).getActorName());

            }
            if (useCase.getDescription() != "!@#$%^&*()_+")
            {
                descriptionTextArea.setText(useCase.getDescription());
            }
            if (useCase.getPreCondition() != "!@#$%^&*()_+")
            {
                preConditionTextField.setText(useCase.getPreCondition());
            }
            if (useCase.getPostCondition() != "!@#$%^&*()_+")
            {
                postConditionTextField.setText(useCase.getPostCondition());
            }
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        System.out.println("Confirm button clicked.");

        // Set value for actorID
        if (actorChoiceBox.getValue() != null) {
            int actorID = actorList.findByActorName((String) actorChoiceBox.getValue()).getActorID();
            useCase.setActorID(actorID);
        }

        // Set value for description
        if (!descriptionTextArea.getText().isEmpty()) {
            useCase.setDescription(descriptionTextArea.getText());
        }

        // Set value for preCondition
        if (!preConditionTextField.getText().isEmpty()) {
            useCase.setPreCondition(preConditionTextField.getText());
        }

        // Set value for postCondition
        if (!postConditionTextField.getText().isEmpty()) {
            useCase.setPostCondition(postConditionTextField.getText());
        }

        // Edit the useCase in the useCaseList
        useCaseListDataSource.writeData(useCaseList);

        //send the project name and directory to HomePage
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(projectName);
        objects.add(directory);

        FXRouter.goTo("HomePage", objects);

        // Close the current window
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }
}
