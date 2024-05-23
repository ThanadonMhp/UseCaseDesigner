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
import java.util.Objects;

public class LabelPageController {

    private double width, height, layoutX, layoutY;
    private String type;
    private String projectName, directory;
    private String editType;
    private int editID, subSystemID;

    @FXML private Text errorText, labelText;

    @FXML private TextField labelTextField;

    @FXML void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            type = (String) objects.get(2);
            if (Objects.equals(type, "editLabel")) {
                editType = (String) objects.get(3);
                editID = (int) objects.get(4);
                if (Objects.equals(editType, "actor")) {
                    DataSource<ActorList> actorListDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
                    ActorList actorList = actorListDataSource.readData();
                    Actor actor = actorList.findByActorId(editID);
                    labelTextField.setText(actor.getActorName());
                } else if (Objects.equals(editType, "subSystem")) {
                    DataSource<SubSystemList> subSystemListDataSource = new SubSystemListFileDataSource(directory, projectName + ".csv");
                    SubSystemList subSystemList = subSystemListDataSource.readData();
                    SubSystem subSystem = subSystemList.findBySubSystemId(editID);
                    labelTextField.setText(subSystem.getSubSystemName());
                }
                labelText.setText("Edit " + editType + " name :");
            } else {
                width = (double) objects.get(3);
                height = (double) objects.get(4);
                layoutX = (double) objects.get(5);
                layoutY = (double) objects.get(6);
                subSystemID = (int) objects.get(7);
                labelText.setText("Enter " + type + " name :");
            }

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
            if (!Objects.equals(type, "editLabel")){
                if (!Objects.equals(type,"subSystem")){
                    DataSource<PositionList> positionListDataSource = new PositionListFileDataSource(directory, projectName + ".csv");
                    PositionList positionList = positionListDataSource.readData();
                    Position position = new Position(
                            positionList.findLastPositionId() + 1,
                            layoutX,
                            layoutY,
                            width,
                            height,
                            0
                            , subSystemID
                    );

                    positionList.addPosition(position);
                    positionListDataSource.writeData(positionList);

                    if (type.equals("actor")) {
                        DataSource<ActorList> actorListDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
                        ActorList actorList = actorListDataSource.readData();
                        Actor actor = new Actor(
                                actorList.findLastActorId() + 1,
                                label,
                                position.getPositionID()
                        );
                        actorList.addActor(actor);
                        actorListDataSource.writeData(actorList);

                    } else if (type.equals("useCase")) {
                        DataSource<UseCaseList> useCaseListDataSource = new UseCaseListFileDataSource(directory, projectName + ".csv");
                        UseCaseList useCaseList = useCaseListDataSource.readData();
                        UseCase useCase = new UseCase(
                                useCaseList.findLastUseCaseId() + 1,
                                label,
                                position.getPositionID()
                        );
                        useCaseList.addUseCase(useCase);
                        useCaseListDataSource.writeData(useCaseList);
                }

                } else if (type.equals("subSystem")) {
                    DataSource<PositionList> positionListDataSource = new PositionListFileDataSource(directory, projectName + ".csv");
                    PositionList positionList = positionListDataSource.readData();
                    Position position = new Position(
                            positionList.findLastPositionId() + 1,
                            layoutX,
                            layoutY,
                            width,
                            height,
                            0
                            , subSystemID
                    );
                    positionList.addPosition(position);
                    positionListDataSource.writeData(positionList);

                    DataSource<SubSystemList> subSystemListDataSource = new SubSystemListFileDataSource(directory, projectName + ".csv");
                    SubSystemList subSystemList = subSystemListDataSource.readData();
                    SubSystem subSystem = new SubSystem(
                            subSystemList.findLastSubSystemId() + 1,
                            label,
                            position.getPositionID()
                    );
                    subSystemList.addSubSystem(subSystem);
                    subSystemListDataSource.writeData(subSystemList);
                }

            } else if (type.equals("editLabel")) {
                if (Objects.equals(editType, "actor")) {
                    DataSource<ActorList> actorListDataSource = new ActorListFileDataSource(directory, projectName + ".csv");
                    ActorList actorList = actorListDataSource.readData();
                    Actor actor = actorList.findByActorId(editID);
                    actor.setActorName(label);
                    actorListDataSource.writeData(actorList);
                } else if (Objects.equals(editType, "subSystem")) {
                    DataSource<SubSystemList> subSystemListDataSource = new SubSystemListFileDataSource(directory, projectName + ".csv");
                    SubSystemList subSystemList = subSystemListDataSource.readData();
                    SubSystem subSystem = subSystemList.findBySubSystemId(editID);
                    subSystem.setSubSystemName(label);
                    subSystemListDataSource.writeData(subSystemList);
                }
            }

            // Send the label to the previous page
            ArrayList<Object> objects = new ArrayList<>();
            objects.add(projectName);
            objects.add(directory);
            objects.add(subSystemID);

            FXRouter.goTo("HomePage", objects);

            // Close the current window
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }
}
