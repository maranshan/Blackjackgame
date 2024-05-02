package Blackjack.FXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginApp extends Application {
    private static Stage stg;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();
    }
    
    public void changeScene(String fxml) throws IOException {
        if (fxml.equals("/Login.fxml")) {
            stg.setWidth(600);
            stg.setHeight(400);
            stg.setResizable(false);
        }
        else {
            stg.setHeight(500);
            stg.setWidth(600);
            stg.setResizable(false);
            stg.setTitle("Blackjack game by Jobin & Maran");
        }
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

}
