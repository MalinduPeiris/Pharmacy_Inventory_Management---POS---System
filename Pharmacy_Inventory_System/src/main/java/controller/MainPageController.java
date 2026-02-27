package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
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

    public JFXButton btnSales;

    @FXML
    private AnchorPane changingPagesAnchorPane;

    @FXML
    private Text lblMainPageDate;

    @FXML
    private Text lblMainPageTime;

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        btnDashboard.setStyle(
                "-fx-background-color: #17a4c4; " +
                        "-fx-text-fill: white;"
        );
        btnSuppliers.setStyle(
                "-fx-background-color:  white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnInventory.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnOrders.setStyle(
                "-fx-background-color:  white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnSales.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );

        loadPage("dash");

    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) {
        btnInventory.setStyle(
                "-fx-background-color: #17a4c4; " +
                        "-fx-text-fill: white;"
        );
        btnSuppliers.setStyle(
                "-fx-background-color:  white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnDashboard.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnOrders.setStyle(
                "-fx-background-color:  white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnSales.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );

        loadPage("inventory");
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {
        btnOrders.setStyle(
                "-fx-background-color:  #17a4c4; " +
                        "-fx-text-fill: white;"
        );
        btnSuppliers.setStyle(
                "-fx-background-color:  white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnDashboard.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnInventory.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnSales.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );

        loadPage("orders");
    }

    @FXML
    void btnSuppliersOnAction(ActionEvent event) {
        btnSuppliers.setStyle(
                "-fx-background-color:  #17a4c4; " +
                        "-fx-text-fill: white;"
        );
        btnDashboard.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnInventory.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnOrders.setStyle(
                "-fx-background-color:  white; " +
                        "-fx-text-fill: #17a4c4"
        );
        btnSales.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        loadPage("suppliers");
    }

    public void btnSalesOnAction(ActionEvent actionEvent) {
        btnSales.setStyle(
                "-fx-background-color:  #17a4c4; " +
                        "-fx-text-fill: white;"
        );
        btnSuppliers.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnDashboard.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnInventory.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #17a4c4;"
        );
        btnOrders.setStyle(
                "-fx-background-color:  white; " +
                        "-fx-text-fill: #17a4c4"
        );
        loadPage("sales");

    }

    private void loadPage(String pageName){
        try {
            URL resource=null;
            switch (pageName){
                case "dash":
                    resource=this.getClass().getResource("/view/mainPageComponents.fxml");
                    break;
                case "inventory":
                    resource=this.getClass().getResource("/view/medicinePage.fxml");
                    break;
                case "suppliers":
                    resource=this.getClass().getResource("/view/supplierPage.fxml");
                    break;
                case "orders":
                    resource=this.getClass().getResource("/view/addOrderPage.fxml");
                    break;
                case "sales":
                    resource=this.getClass().getResource("/view/salesPage.fxml");
                    break;
            }

            assert resource!=null;

            Parent parent = FXMLLoader.load(resource);

            changingPagesAnchorPane.getChildren().clear();
            changingPagesAnchorPane.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblMainPageTime.setText(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        System.out.println("System Start At : "+LocalDate.now()+"    Time : "+customizeTime);

        loadPage("dash");
    }


}
