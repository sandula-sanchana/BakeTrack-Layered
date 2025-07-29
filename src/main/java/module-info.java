module baketrack.project_bakery_x {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;
    requires twilio;
    requires net.sf.jasperreports.core;
    requires java.mail;

    exports edu.ijse.BakeTrack to javafx.fxml;
    exports edu.ijse.BakeTrack.controller to javafx.fxml;
    exports edu.ijse.BakeTrack.tm;
    opens edu.ijse.BakeTrack.tm to javafx.base;

    opens edu.ijse.BakeTrack to javafx.graphics, javafx.fxml;
    opens edu.ijse.BakeTrack.controller to javafx.fxml;
}
