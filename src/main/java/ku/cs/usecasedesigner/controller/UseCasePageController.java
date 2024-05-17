package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.ActorList;
import ku.cs.usecasedesigner.models.UseCase;

import java.util.ArrayList;

public class UseCasePageController {

    @FXML private Label useCaseIDLabel, useCaseNameLabel;

    @FXML private ChoiceBox actorChoiceBox;

    @FXML private TextField preConditionTextField, postConditionTextField;

    @FXML private TextArea descriptionTextArea;

    private String directory;
    private String projectName;
    private UseCase useCase;
    private ActorList actorList;

    @FXML void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            directory = (String) objects.get(0);
            projectName = (String) objects.get(1);
            useCase = (UseCase) objects.get(2);
            actorList = (ActorList) objects.get(3);

            useCaseIDLabel.setText(String.valueOf(useCase.getUseCaseID()));
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

    public void handleConfirmButton(ActionEvent actionEvent) {

    }
}
