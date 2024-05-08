package ku.cs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
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
        FXRouter.goTo("HomePage");
        FXRouter.popup("LandingPage");
   }

    private static void configRoute() {
        String packageStr = "ku/cs/usecasedesigner/";

        // Config route
        FXRouter.when("HomePage", packageStr + "home-page.fxml", "UseCaseDesigner");
        FXRouter.when("LandingPage", packageStr + "landing-page.fxml", "UseCaseDesigner | LandingPage");
        FXRouter.when("NewProjectPage", packageStr + "new-project-page.fxml", "UseCaseDesigner | New Project");
        FXRouter.when("EditLabelPage", packageStr + "edit-label-page.fxml", "UseCaseDesigner | Edit Label");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
