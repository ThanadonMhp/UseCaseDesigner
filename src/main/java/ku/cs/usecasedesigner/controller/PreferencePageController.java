package ku.cs.usecasedesigner.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.Preference;
import ku.cs.usecasedesigner.models.PreferenceList;
import ku.cs.usecasedesigner.services.DataSource;
import ku.cs.usecasedesigner.services.PreferenceListFileDataSource;

import java.util.ArrayList;

public class PreferencePageController {
    @FXML private ToggleGroup themeGroup;

    @FXML private RadioButton lightThemeRadioButton;
    @FXML private RadioButton darkThemeRadioButton;

    @FXML private ComboBox<String> fontComboBox;

    @FXML private ComboBox<String> sizeComboBox;

    private String directory;
    private String projectName;

    // This method is called when the FXML file is loaded
    // Load the data from the previous page
    @FXML void initialize() {
        themeGroup = new ToggleGroup();
        lightThemeRadioButton.setToggleGroup(themeGroup);
        darkThemeRadioButton.setToggleGroup(themeGroup);

        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            directory = (String) objects.get(0);
            projectName = (String) objects.get(1);
        }
        fontComboBox.setItems(FXCollections.observableArrayList("LINE Seed Sans TH App", "Arial", "Tahoma"));
        sizeComboBox.setItems(FXCollections.observableArrayList("12", "14", "16"));
    }

    // Save the preference to the file
    public void savePreference(int strokeWidth, String font, int fontSize, String theme) {
        // Save the preference to the file
        PreferenceList preferenceList = new PreferenceList();
        DataSource<PreferenceList> preferenceListDataSource = new PreferenceListFileDataSource(directory, projectName + ".csv");
        Preference preference = new Preference(strokeWidth, font, fontSize, theme);
        preferenceList.addPreference(preference);

        preferenceListDataSource.writeData(preferenceList);
    }
    @FXML
    public void handleSaveButtonAction() {
        RadioButton selectedThemeRadioButton = (RadioButton) themeGroup.getSelectedToggle();
        String theme = selectedThemeRadioButton.getText();
        String font = fontComboBox.getValue();
        int size = Integer.parseInt(sizeComboBox.getValue());

        savePreference(1, font + " " , size, theme);
    }


}
