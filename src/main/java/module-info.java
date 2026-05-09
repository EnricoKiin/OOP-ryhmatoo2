module ryhmatoo.oopryhmatoo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens ryhmatoo.oopryhmatoo2 to javafx.fxml;
    exports ryhmatoo.oopryhmatoo2;
}