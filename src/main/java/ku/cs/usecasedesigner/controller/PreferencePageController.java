package ku.cs.usecasedesigner.controller;

import javafx.fxml.FXML;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.Preference;
import ku.cs.usecasedesigner.models.PreferenceList;
import ku.cs.usecasedesigner.services.DataSource;
import ku.cs.usecasedesigner.services.PreferenceListFileDataSource;

import java.util.ArrayList;

public class PreferencePageController {

    private String directory;
    private String projectName;

    // This method is called when the FXML file is loaded
    // Load the data from the previous page
    @FXML void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            directory = (String) objects.get(0);
            projectName = (String) objects.get(1);
        }
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


}
