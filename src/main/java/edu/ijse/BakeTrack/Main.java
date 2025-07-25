package edu.ijse.BakeTrack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent loadingParent = FXMLLoader.load(getClass().getResource("/View/loadingScreen.fxml"));
        Scene loadingScene = new Scene(loadingParent);


        stage.setTitle("BakeTrack v 1.0");
        stage.setScene(loadingScene);

        stage.show();



        Task<Scene> loadLoginPageTask = new Task<Scene>() {
            @Override
            protected Scene call() throws Exception {

                Thread.sleep(2000);


                Parent loginPageParent = FXMLLoader.load(getClass().getResource("/View/LogInPage.fxml"));

                return new Scene(loginPageParent);
            }
        };


        loadLoginPageTask.setOnSucceeded(event -> {

            Platform.runLater(() -> {
                Scene mainScene = loadLoginPageTask.getValue();

                stage.setScene(mainScene);
                stage.setTitle("BakeTrack v 1.0");

                stage.sizeToScene();
                stage.centerOnScreen();


            });
        });


        loadLoginPageTask.setOnFailed(event -> {
            Platform.runLater(() -> {
                System.err.println("Failed to load main application: " + loadLoginPageTask.getException().getMessage());
                loadLoginPageTask.getException().printStackTrace();

                stage.close();
            });
        });


        new Thread(loadLoginPageTask).start();
    }
}