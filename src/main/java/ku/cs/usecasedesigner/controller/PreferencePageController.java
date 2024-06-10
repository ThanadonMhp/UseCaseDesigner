package ku.cs.usecasedesigner.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.Preference;
import ku.cs.usecasedesigner.models.PreferenceList;
import ku.cs.usecasedesigner.services.DataSource;
import ku.cs.usecasedesigner.services.PreferenceListFileDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class PreferencePageController {

    public static boolean isLightMode = true;

    private Preference preference;

    private PreferenceList preferenceList;

    @FXML
    private ToggleGroup themeGroup;

    @FXML
    private RadioButton lightThemeRadioButton, darkThemeRadioButton;

    @FXML
    private Label SettingLabel, ThemeAppLabel, TextformatLabel;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button mode;

    @FXML private ChoiceBox<Integer> strokeWidthChoiceBox, fontSizeChoiceBox;

    @FXML private ColorPicker fontColorPicker, strokeColorPicker;

    private Alert alert;
    private String directory;
    private String projectName;
    private Integer subSystemID;

    // This method is called when the FXML file is loaded
    // Load the data from the previous page
    @FXML
    void initialize() {
        themeGroup = new ToggleGroup();
        lightThemeRadioButton.setToggleGroup(themeGroup);
        darkThemeRadioButton.setToggleGroup(themeGroup);
        alert = new Alert(Alert.AlertType.NONE);

        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            subSystemID = (Integer) objects.get(2);
            if (FXRouter.getTheme() == 1) {
                lightThemeRadioButton.setSelected(true);
            } else if (FXRouter.getTheme() == 2) {
                darkThemeRadioButton.setSelected(true);
            }

            PreferenceListFileDataSource preferenceListDataSource = new PreferenceListFileDataSource(directory, projectName + ".csv");
            preferenceList = preferenceListDataSource.readData();
            preferenceList.getPreferenceList().forEach(tempPreference -> {
                preference = tempPreference;
            });

            // Set the choice box
            strokeWidthChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
            strokeWidthChoiceBox.setValue(preference.getStrokeWidth());

            // Set color picker
            strokeColorPicker.setValue(preference.getStrokeColor());

            // Set the color picker to match the component preference color
            fontColorPicker.setValue(preference.getFontColor());

            // set the theme radio button
            if (preference.getTheme().equals("Light")) {
                lightThemeRadioButton.setSelected(true);
            } else {
                darkThemeRadioButton.setSelected(true);
            }
        }
    }

    // Save the preference to the file
    public void savePreference(int strokeWidth, Color strokeColor, Color fontColor, String theme) {
        // Save the preference to the file
        PreferenceList preferenceList = new PreferenceList();
        DataSource<PreferenceList> preferenceListDataSource = new PreferenceListFileDataSource(directory, projectName + ".csv");
        Preference preference = new Preference(strokeWidth, strokeColor, fontColor, theme);
        preferenceList.addPreference(preference);

        preferenceListDataSource.writeData(preferenceList);
    }

    @FXML
    public void handleSaveButtonAction(ActionEvent actionEvent) throws IOException {

        RadioButton selectedThemeRadioButton = (RadioButton) themeGroup.getSelectedToggle();

        if (selectedThemeRadioButton == null) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please select a theme.");
            alert.show();
            return; // Exit the method if no theme is selected
        }

        int strokeWidth = strokeWidthChoiceBox.getValue();
        Color strokeColor = strokeColorPicker.getValue();
        Color fontColor = fontColorPicker.getValue();

        if (darkThemeRadioButton.isSelected()) {
            savePreference(strokeWidth, strokeColor, fontColor, "Dark");
        } else {
            savePreference(strokeWidth, strokeColor, fontColor, "Light");
        }

        // Go back to the home page
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(projectName);
        objects.add(directory);
        objects.add(subSystemID);

        FXRouter.goTo("HomePage",objects);

        // Close the current window
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void handleLightThemeSelected(ActionEvent event) throws IOException {
        FXRouter.setTheme(1);
        PreferenceListFileDataSource preferenceListDataSource = new PreferenceListFileDataSource(directory, projectName + ".csv");
        preferenceList = preferenceListDataSource.readData();
        preferenceList.getPreferenceList().forEach(tempPreference -> {
            preference = tempPreference;
        });
        preference.setTheme("Light");
        // write the preference to the file
        preferenceListDataSource.writeData(preferenceList);

        ArrayList<Object> objects = (ArrayList) FXRouter.getData();
        objects.add(1);
        FXRouter.popup("PreferencePage", objects);

        // Close the current window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void handleDarkThemeSelected(ActionEvent event) throws IOException {
        FXRouter.setTheme(2);
        PreferenceListFileDataSource preferenceListDataSource = new PreferenceListFileDataSource(directory, projectName + ".csv");
        preferenceList = preferenceListDataSource.readData();
        preferenceList.getPreferenceList().forEach(tempPreference -> {
            preference = tempPreference;
        });
        preference.setTheme("Dark");
        // write the preference to the file
        preferenceListDataSource.writeData(preferenceList);

        ArrayList<Object> objects = (ArrayList) FXRouter.getData();
        objects.add(2);
        FXRouter.popup("PreferencePage", objects);

        // Close the current window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
