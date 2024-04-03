module ku.cs.usecasedesigner {
    requires javafx.controls;
    requires javafx.fxml;

    opens ku.cs.usecasedesigner.controller to javafx.fxml;

    exports ku.cs.usecasedesigner;
}
