package ku.cs.usecasedesigner.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    @FXML private ToggleGroup themeGroup;
    @FXML private RadioButton lightThemeRadioButton;
    @FXML private RadioButton darkThemeRadioButton;
    @FXML private ComboBox<String> fontComboBox;
    @FXML private ComboBox<String> sizeComboBox;
    @FXML private Label SettingLabel;
    @FXML private Label ThemeAppLabel;
    @FXML private Label TextformatLabel;
    @FXML private AnchorPane pane;
    @FXML private Button mode;

    private Alert alert;
    private String directory;
    private String projectName;
    private Integer subSystemID;
    public static boolean isLightMode = true;

    // This method is called when the FXML file is loaded
    // Load the data from the previous page
    @FXML void initialize() {
        themeGroup = new ToggleGroup();
        lightThemeRadioButton.setToggleGroup(themeGroup);
        darkThemeRadioButton.setToggleGroup(themeGroup);
        alert = new Alert(Alert.AlertType.NONE);

        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            subSystemID = (Integer) objects.get(2);
            if (objects.size() == 4) {
                int theme = (int) objects.get(3);
                if (theme == 1) {
                    lightThemeRadioButton.setSelected(true);
                } else if (theme == 2){
                    darkThemeRadioButton.setSelected(true);
                }
            } else{
                if (FXRouter.getTheme() == 1) {
                    lightThemeRadioButton.setSelected(true);
                } else if (FXRouter.getTheme() == 2) {
                    darkThemeRadioButton.setSelected(true);
                }
            }
        }
        fontComboBox.setItems(FXCollections.observableArrayList("LINE Seed Sans TH App", "Arial", "Tahoma"));
        sizeComboBox.setItems(FXCollections.observableArrayList("12", "14", "16"));
        fontComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(fontComboBox);
            if (newValue.equals("LINE Seed Sans TH App")) {
                // Load and set LINE Seed Sans TH App font
                Font font1 = Font.loadFont(getClass().getResourceAsStream("/style/font/LINESeedSansTH_A_Bd.ttf"), 14);
                SettingLabel.setFont(font1);
                ThemeAppLabel.setFont(font1);
                TextformatLabel.setFont(font1);

            } else {
                // Set default font for other options (Arial, Tahoma)
                SettingLabel.setFont(Font.getDefault());
                ThemeAppLabel.setFont(Font.getDefault());
                TextformatLabel.setFont(Font.getDefault());
            }
        });
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
    public void handleSaveButtonAction(ActionEvent actionEvent) throws IOException {

        RadioButton selectedThemeRadioButton = (RadioButton) themeGroup.getSelectedToggle();

        if (selectedThemeRadioButton == null) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please select a theme.");
            alert.show();
            return; // Exit the method if no theme is selected
        }

        String theme = selectedThemeRadioButton.getText();
        String font = fontComboBox.getValue();

        if (font == null || font.isBlank()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please select a font.");
            alert.show();
            return;
        }

        String sizeValue = sizeComboBox.getValue();

        if (sizeValue == null || sizeValue.isBlank()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please select a font size.");
            alert.show();
            return; // Exit the method if no size is selected
        }

        int size;
        try {
            size = Integer.parseInt(sizeValue);


            if (size <= 0) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Please enter a valid font size greater than 0.");
                alert.show();
                return;
            }
        } catch (NumberFormatException e) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please enter a valid number for the font size.");
            alert.show();
            return;
        }


        savePreference(1, font + " " , size, theme);
        FXRouter.goTo("HomePage");

        // Close the current window
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void handleLightThemeSelected(ActionEvent event) throws IOException {
        FXRouter.setTheme(1);

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

        ArrayList<Object> objects = (ArrayList) FXRouter.getData();
        objects.add(2);
        FXRouter.popup("PreferencePage", objects);

        // Close the current window
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
