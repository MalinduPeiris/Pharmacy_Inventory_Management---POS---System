package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnInventory;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnSuppliers;

    @FXML
    private AnchorPane changingPagesAnchorPane;

    @FXML
    private Text lblMainPageDate;

    @FXML
    private Text lblMainPageTime;

    @FXML
    void btnDashboardOnAction(ActionEvent event) {

    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {

    }

    @FXML
    void btnSuppliersOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDashboard.setStyle(
                "-fx-background-color: #17a4c4; " +
                        "-fx-text-fill: white;"
        );

        String time = LocalTime.now()+"";
        String customizeTime=time.charAt(0)+""+time.charAt(1)+""+time.charAt(2)+""+time.charAt(3)+""+time.charAt(4);

        lblMainPageDate.setText(LocalDate.now()+"");
        lblMainPageTime.setText(customizeTime);

        System.out.println("System Start At : "+LocalDate.now()+"    Time : "+customizeTime);

        try {
            URL resource =this.getClass().getResource("/view/mainPageComponents.fxml");
            Parent parent = FXMLLoader.load(resource);

            changingPagesAnchorPane.getChildren().clear();
            changingPagesAnchorPane.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
