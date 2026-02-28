package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogingController implements Initializable {

    private final String ADMINNAME="m";
    private final String ADMINPASSWORD="123";


    @FXML
    private AnchorPane logingPageAnchorPane;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    public Text lblAlert;
    public Text lblUserNamePassword;


    public void btnSignInOnAction(ActionEvent actionEvent) {
        lblAlert.setText("");
        lblAlert.setStyle("-fx-text-fill: #ff0000;");
        if(!txtUserName.getText().isEmpty()){
            if (!txtPassword.getText().isEmpty()){
                String username =txtUserName.getText();
                String password=txtPassword.getText();
                if(username.equals(ADMINNAME)){
                    if (password.equals(ADMINPASSWORD)){
                        lblAlert.setStyle("-fx-fill: #00d933;");
                        lblAlert.setText("Logging Success !!");

                        try {
                            URL resource =this.getClass().getResource("/view/mainPage.fxml");
                            Parent parent=FXMLLoader.load(resource);

                            logingPageAnchorPane.getChildren().clear();
                            logingPageAnchorPane.getChildren().add(parent);


                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }else {
                        new Alert(Alert.AlertType.WARNING,"Incorrect password .please try again !!");
                        txtPassword.clear();
                        lblAlert.setText("Incorrect password .please try again !!");
                    }
                }else {
                    new Alert(Alert.AlertType.WARNING,"Incorrect username .please try again !!");
                    txtPassword.clear();
                    txtUserName.clear();
                    lblAlert.setText("Incorrect username and password .please try again !!");
                }

            }else {
                lblAlert.setText("Please fill the password field and try again !!");
                new Alert(Alert.AlertType.WARNING,"Please fill the password field and try again !!");
            }

        }else {
            lblAlert.setText("Please fill the username field and try again !!");
            new Alert(Alert.AlertType.WARNING,"Please fill the username field and try again !!");
        }


    }

    public void btnHintOnAction(ActionEvent actionEvent) {
        lblUserNamePassword.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUserNamePassword.setVisible(false);
    }
}
