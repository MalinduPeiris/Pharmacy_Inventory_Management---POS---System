package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.BestSellingMedicine;
import model.ExpiryMedicine;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "SELECT medicine_id, name , dosage, expiry_date, " +
                            "DATEDIFF(expiry_date, CURDATE()) AS days_left " +
                            "FROM medicines WHERE expiry_date >= CURDATE() "+
                            "AND expiry_date <= CURDATE() + INTERVAL 14 DAY " +
                            "AND status = 'active' " +
                            "ORDER BY expiry_date ASC "
            );
            ArrayList<ExpiryMedicine> expiryMedicinesList=new ArrayList<>();
            while (resultSet.next()){
                expiryMedicinesList.add(
                        new ExpiryMedicine(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDate(4).toLocalDate(),
                                resultSet.getString(5)
                        )
                );
            }
            colMediId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colMediName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colMediDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
            colMediExpiry.setCellValueFactory(new PropertyValueFactory<>("expiry"));
            colMedileftDays.setCellValueFactory(new PropertyValueFactory<>("leftDays"));

            tblExpiry.setItems(FXCollections.observableList(expiryMedicinesList));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadBestSellingTable(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "SELECT m.medicine_id , m.name , m.category , m.dosage , SUM(si.quantity_sold) ,"+
                    "AVG(si.unit_price) , SUM(si.subtotal) FROM sale_items si "+
            "INNER JOIN medicines m ON si.medicine_id = m.medicine_id "+
            "GROUP BY m.medicine_id, m.name, m.category, m.dosage "+
            "ORDER BY SUM(si.subtotal) DESC"
            );
            ArrayList<BestSellingMedicine> bestSellingMedicinesList=new ArrayList<>();
            while (resultSet.next()){
                bestSellingMedicinesList.add(
                        new BestSellingMedicine(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5),
                                resultSet.getDouble(6),
                                resultSet.getDouble(7)
                        )
                );
            }
            colSellingID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colSellingName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colSellingCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            colSellingDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
            colSellingQuantitySold.setCellValueFactory(new PropertyValueFactory<>("qtySold"));
            colSellingUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colSellingSubTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            tblBestSelling.setItems(FXCollections.observableList(bestSellingMedicinesList));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
