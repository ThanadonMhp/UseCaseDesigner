package ku.cs;

import ku.cs.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class ProjectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, 1280,720);
        configRoute();
        FXRouter.setTheme(1);
        FXRouter.goTo("TempHomePage");
        FXRouter.popup("LandingPage", true);

    }
    private void setWindowTitle(String title) {
        Stage stage = FXRouter.getStage();
        stage.setTitle(title);

    }

    private static void configRoute() {
        String packageStr = "ku/cs/usecasedesigner/";

        // Config route
        FXRouter.when("TempHomePage", packageStr + "home-page.fxml", "KU CS UseCaseDesigner");
        FXRouter.when("LandingPage", packageStr + "landing-page.fxml", "Welcome!");
        FXRouter.when("NewProjectPage", packageStr + "new-project-page.fxml", "New Project");
        FXRouter.when("LabelPage", packageStr + "label-page.fxml", "Label");
        FXRouter.when("PreferencePage", packageStr + "preference-page.fxml", "Preference");
        FXRouter.when("UseCasePage", packageStr + "use-case-page.fxml", "Use Case");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
