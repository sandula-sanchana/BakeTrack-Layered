package edu.ijse.BakeTrack.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class loadingScreenController implements Initializable {
    public ProgressIndicator spinner;
    public Label lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinner.setStyle("-fx-progress-color: AF3E3E;");
        ScaleTransition st = new ScaleTransition(Duration.seconds(1.5), spinner);
        st.setFromX(3.0);
        st.setFromY(3.0);
        st.setToX(3.3);
        st.setToY(3.3);
        st.setCycleCount(ScaleTransition.INDEFINITE);
        st.setAutoReverse(true);
        st.play();

        FadeTransition ft = new FadeTransition(Duration.seconds(0.7), lbl);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(FadeTransition.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }
}
