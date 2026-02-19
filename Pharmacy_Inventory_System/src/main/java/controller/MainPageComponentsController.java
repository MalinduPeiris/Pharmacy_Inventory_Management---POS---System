package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainPageComponentsController implements Initializable {

    @FXML
    private TableColumn colMediCategory;

    @FXML
    private TableColumn colMediDosage;

    @FXML
    private TableColumn colMediExpiry;

    @FXML
    private TableColumn colMediId;

    @FXML
    private TableColumn colMediName;

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

    @FXML
    void btnAddNewMedicineOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadExpiryTable();
        loadBestSellingTable();
        setLableValues();
    }

    private void setLableValues() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSetQtyLbl = statement.executeQuery("SELECT SUM(quantity) FROM medicines");
            if(resultSetQtyLbl.next()){
                lblTotalStockCount.setText(resultSetQtyLbl.getInt(1)+"");
            }

            ResultSet resultSetTodayRevenueLbl = statement.executeQuery(
                    "select SUM(total_amount) FROM sales WHERE DATE(sale_date) = CURDATE()"
            );
            if(resultSetTodayRevenueLbl.next()){
                lblRevenue.setText("Rs. "+resultSetTodayRevenueLbl.getInt(1));
            }

            ResultSet resultSetCustomerCountLbl = statement.executeQuery(
                    "SELECT COUNT(*) FROM customer"
            );
            if(resultSetCustomerCountLbl.next()){
                lblCustomerCount.setText(resultSetCustomerCountLbl.getInt(1)+"");
            }

            ResultSet resultSetSupplierCountLbl = statement.executeQuery(
                    "select COUNT(*) FROM suppliers"
            );
            if(resultSetSupplierCountLbl.next()){
                lblSupplierCount.setText(resultSetSupplierCountLbl.getInt(1)+"");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadExpiryTable(){


    }

    private void loadBestSellingTable(){


    }
}
