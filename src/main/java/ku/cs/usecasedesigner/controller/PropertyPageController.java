package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.ComponentPreference;
import ku.cs.usecasedesigner.models.ComponentPreferenceList;
import ku.cs.usecasedesigner.models.Preference;
import ku.cs.usecasedesigner.models.PreferenceList;
import ku.cs.usecasedesigner.services.ComponentPreferenceListFileDataSource;
import ku.cs.usecasedesigner.services.PreferenceListFileDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class PropertyPageController {

    private String projectName, directory, type;

    private Integer subSystemID, ID;

    private Preference preference;

    private PreferenceList preferenceList;

    private ComponentPreference componentPreference;

    private ComponentPreferenceList componentPreferenceList;

    @FXML private ChoiceBox<Integer> strokeWidthChoiceBox, fontSizeChoiceBox;

    @FXML private ColorPicker fontColorPicker, strokeColorPicker;

    @FXML private ToggleButton boldToggleButton, italicToggleButton, underlineToggleButton;

    @FXML void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            subSystemID = (Integer) objects.get(2);
            type = (String) objects.get(3);
            ID = (Integer) objects.get(4);

            // Read the component preference list from the file
            ComponentPreferenceListFileDataSource componentPreferenceListDataSource = new ComponentPreferenceListFileDataSource(directory, projectName + ".csv");
            componentPreferenceList = componentPreferenceListDataSource.readData();

            // Read the preference list from the file
            PreferenceListFileDataSource preferenceListDataSource = new PreferenceListFileDataSource(directory, projectName + ".csv");
            preferenceList = preferenceListDataSource.readData();

            // Get preference from preference list
            preferenceList.getPreferenceList().forEach(tempPreference -> {
                preference = tempPreference;
            });

            // Find the component preference by ID
            componentPreference = componentPreferenceList.findByIDAndType(ID, type);

            // Set the choice box
            strokeWidthChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
            if(componentPreference == null)
            {
                strokeWidthChoiceBox.setValue(preference.getStrokeWidth());
            } else {
                strokeWidthChoiceBox.setValue(componentPreference.getStrokeWidth());
            }

            // Set color picker
            if (componentPreference != null){
                strokeColorPicker.setValue(componentPreference.getStrokeColor());
            } else {
                strokeColorPicker.setValue(preference.getStrokeColor());
            }

//            fontSizeChoiceBox.getItems().addAll(8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30);
//            fontSizeChoiceBox.setValue(componentPreference.getFontSize());

            // Set the color picker to match the component preference color
            if (componentPreference != null){
                fontColorPicker.setValue(componentPreference.getFontColor());
            } else {
                fontColorPicker.setValue(preference.getFontColor());
            }

            // Set the toggle button
//            boldToggleButton.setSelected(componentPreference.isBold());
//            italicToggleButton.setSelected(componentPreference.isItalic());
//            underlineToggleButton.setSelected(componentPreference.isUnderline());
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        // Update the component preference
        if (componentPreference == null) {
            componentPreference = new ComponentPreference(
                    strokeWidthChoiceBox.getValue(),
                    strokeColorPicker.getValue(),
//                    fontSizeChoiceBox.getValue(),
                    fontColorPicker.getValue(),
//                    boldToggleButton.isSelected(),
//                    italicToggleButton.isSelected(),
//                    underlineToggleButton.isSelected(),
                    type,
                    ID
            );
            componentPreferenceList.addComponentPreference(componentPreference);
        } else {
            componentPreference.setStrokeWidth(strokeWidthChoiceBox.getValue());
            componentPreference.setStrokeColor(strokeColorPicker.getValue());
//            componentPreference.setFontSize(fontSizeChoiceBox.getValue());
            componentPreference.setFontColor(fontColorPicker.getValue());
//            componentPreference.setBold(boldToggleButton.isSelected());
//            componentPreference.setItalic(italicToggleButton.isSelected());
//            componentPreference.setUnderline(underlineToggleButton.isSelected());
        }


        // Write the component preference list to the file
        ComponentPreferenceListFileDataSource componentPreferenceListDataSource = new ComponentPreferenceListFileDataSource(directory, projectName + ".csv");
        componentPreferenceListDataSource.writeData(componentPreferenceList);

        // Close the current window
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
