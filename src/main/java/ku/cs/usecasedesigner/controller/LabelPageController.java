package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.*;
import ku.cs.usecasedesigner.services.*;

import java.io.IOException;
import java.util.ArrayList;

public class LabelPageController {

    private double width, height, layoutX, layoutY;
    private String type;
    private String projectName, directory;

    private ActorList actorList;
    private UseCaseList useCaseList;
    private PositionList positionList;
    private SubSystemList subSystemList;
    private DataSource<ActorList> actorListDataSource;
    private DataSource<UseCaseList> useCaseListDataSource;
    private DataSource<PositionList> positionListDataSource;
    private DataSource<SubSystemList> subSystemListDataSource;

    @FXML private Text errorText;

    @FXML private TextField labelTextField;

    @FXML void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            type = (String) objects.get(2);
            width = (double) objects.get(3);
            height = (double) objects.get(4);
            layoutX = (double) objects.get(5);
            layoutY = (double) objects.get(6);

            // Read the actor, use case, and position list from the file
            actorListDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
            actorList = actorListDataSource.readData();
            useCaseListDataSource = new UseCaseListFileDataSource(directory, projectName + ".csv");
            useCaseList = useCaseListDataSource.readData();
            positionListDataSource = new PositionListFileDataSource(directory, projectName + ".csv");
            positionList = positionListDataSource.readData();
            subSystemListDataSource = new SubSystemListFileDataSource(directory, projectName + ".csv");
            subSystemList = subSystemListDataSource.readData();
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        if (labelTextField.getText().isEmpty()) {
            errorText.setText("Please enter a label.");
        } else {
            errorText.setText("");

            // Get the label from the text field
            String label = labelTextField.getText();

            // Save the position of the component
            Position position = new Position(
                    positionList.findLastPositionId() + 1,
                    layoutX,
                    layoutY,
                    width,
                    height,
                    0
            );
            positionList.addPosition(position);
            positionListDataSource.writeData(positionList);

            if (type.equals("actor")) {
                Actor actor = new Actor(
                        actorList.findLastActorId() + 1,
                        label,
                        position.getPositionID()
                );
                actorList.addActor(actor);
                actorListDataSource.writeData(actorList);
            } else if (type.equals("useCase")) {
                UseCase useCase = new UseCase(
                        useCaseList.findLastUseCaseId() + 1,
                        label,
                        0,
                        "!@#$%^&*()_+",
                        "!@#$%^&*()_+",
                        "!@#$%^&*()_+",
                        position.getPositionID()
                );
                useCaseList.addUseCase(useCase);
                useCaseListDataSource.writeData(useCaseList);
            } else if (type.equals("subSystem")) {
                SubSystem subSystem = new SubSystem(
                        subSystemList.findLastSubsystemId() + 1,
                        label,
                        position.getPositionID()
                );
                subSystemList.addSubsystem(subSystem);
                subSystemListDataSource.writeData(subSystemList);
            }

            // Send the label to the previous page
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
}
