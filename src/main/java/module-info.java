module ku.cs.usecasedesigner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing;

    opens ku.cs.usecasedesigner.controller to javafx.fxml;

    exports ku.cs.usecasedesigner.controller;
    exports ku.cs;
}
