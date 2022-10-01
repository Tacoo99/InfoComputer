module InfoKomputer.app.main {

    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    opens infokomputer to javafx.fxml;

    exports infokomputer;
}