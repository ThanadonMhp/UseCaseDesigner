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
        FXRouter.goTo("HomePage");
        FXRouter.popup("LandingPage");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/usecasedesigner/";

        // Config route
        FXRouter.when("HomePage", packageStr + "home-page.fxml", "UseCaseDesigner");
        FXRouter.when("LandingPage", packageStr + "landing-page.fxml", "UseCaseDesigner | LandingPage");
        FXRouter.when("NewProjectPage", packageStr + "new-project-page.fxml", "UseCaseDesigner | New Project");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
