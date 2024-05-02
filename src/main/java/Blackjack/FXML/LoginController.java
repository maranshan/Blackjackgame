package Blackjack.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;

import Blackjack.BlackjackGame;

public class LoginController {

    @FXML
    private Button button;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField deposit;

    private BlackjackGame blackjackGame = new BlackjackGame();
    private static LoginController instance;

    public LoginController() {
        instance = this;
    }
    
    public TextField getUsername() {
        return username;
    }
    
    public static LoginController getInstance() {
        return instance;
    }

    public void userLogin(ActionEvent event) throws IOException {
        LoginApp m = new LoginApp();
        try {
            Double depositDouble = Double.parseDouble(deposit.getText());
            if (blackjackGame.Login(username.getText(), password.getText(), depositDouble)) {
                blackjackGame.Login(username.getText().toString(), password.getText().toString(), depositDouble);
                wrongLogin.setText("Success!");
                m.changeScene("/AfterLogin.fxml");
            }
            else {wrongLogin.setText("Wrong username or password!");}
    
        } catch (Exception e) {
            wrongLogin.setText("Please insert valid login inputs");
        }
    }
}
