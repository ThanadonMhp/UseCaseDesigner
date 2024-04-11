package ku.cs.usecasedesigner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ku.cs.usecasedesigner.controller.RootLayout;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        try {

            Scene scene = new Scene(root,640,480);
            scene.getStylesheets().add(getClass().getResource("/ku/cs/usecasedesigner/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

        root.setCenter(new RootLayout());
    }

    public static void main(String[] args) {
        launch(args);
    }
}