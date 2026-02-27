package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.tm.BestSellingMedicineTM;
import model.tm.DashboardTm;
import model.tm.ExpiryMedicineTM;
import service.ServiceFactory;
import service.custome.DashboardService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageComponentsController implements Initializable {

    @FXML
    private TableColumn colMediDosage;

    @FXML
    private TableColumn colMediExpiry;

    @FXML
    private TableColumn colMediId;

    @FXML
    private TableColumn colMediName;

    public TableColumn colMedileftDays;

    @FXML
    private TableColumn colSellingCategory;

    @FXML
    private TableColumn colSellingDosage;

    @FXML
    private TableColumn colSellingID;

    @FXML
    private TableColumn colSellingName;

    @FXML
    private TableColumn colSellingQuantitySold;

    @FXML
    private TableColumn colSellingSubTotal;

    @FXML
    private TableColumn colSellingUnitPrice;

    @FXML
    public Text lblCustomerCount;

    @FXML
    private Text lblRevenue;

    @FXML
    private Text lblSupplierCount;

    @FXML
    private Text lblTotalStockCount;

    @FXML
    private AnchorPane pagesAnchorPane;

    @FXML
    private TableView tblBestSelling;

    @FXML
    private TableView tblExpiry;

    DashboardService dashboardService= ServiceFactory
            .getInstance()
            .getServiceType(ServiceType.DASHBOARD);


    @FXML
    void btnAddNewMedicineOnAction(ActionEvent event) {

        try {
            URL resource = this.getClass().getResource("/view/medicinePage.fxml");

            assert resource!=null;

            Parent parent=FXMLLoader.load(resource);

            pagesAnchorPane.getChildren().clear();
            pagesAnchorPane.getChildren().add(parent);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void setLableValues() {

        lblSupplierCount.setText(dashboardService.getSupplierCount()+"");
        lblTotalStockCount.setText(dashboardService.getMedicineCount()+"");
        lblRevenue.setText("Rs. "+dashboardService.getTodaySales());
        lblCustomerCount.setText(dashboardService.getCustomerCount()+"");

    }

    private void loadExpiryTable(){
        List<DashboardTm> allExpiryMedicineDetail = dashboardService.getAllExpiryMedicineDetail();

        colMediId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMediName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMediDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colMediExpiry.setCellValueFactory(new PropertyValueFactory<>("expiry"));
        colMedileftDays.setCellValueFactory(new PropertyValueFactory<>("leftDays"));

        tblExpiry.setItems(FXCollections.observableList(allExpiryMedicineDetail));


    }

    private void loadBestSellingTable(){
        List<DashboardTm> allBestSoldMedicine = dashboardService.getAllBestSoldMedicine();

        colSellingID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSellingName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSellingCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSellingDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colSellingQuantitySold.setCellValueFactory(new PropertyValueFactory<>("qtySold"));
        colSellingUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colSellingSubTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblBestSelling.setItems(FXCollections.observableList(allBestSoldMedicine));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadExpiryTable();
        loadBestSellingTable();
        setLableValues();
    }
}
