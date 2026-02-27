package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.tm.SalesTM;
import service.ServiceFactory;
import service.custome.SaleService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SalesPageController implements Initializable {

    public Text lblSelectedSaleId;
    @FXML
    private TableColumn colCustName;

    @FXML
    private TableColumn colDiscount;

    @FXML
    private TableColumn colItems;

    @FXML
    private TableColumn colNetTotal;

    @FXML
    private TableColumn colOrderDate;

    @FXML
    private TableColumn colSaleId;

    @FXML
    private TableColumn colSubTotal;

    @FXML
    private Text lblBestSellingItem;

    @FXML
    private Text lblHighestOrder;

    @FXML
    private Text lblOrderDate;

    @FXML
    private Text lblSoldBestSellingItemCount;

    @FXML
    private Text lblTodayRevenue;

    @FXML
    private Text lblTransactionCount;

    @FXML
    private TableView tblSales;

    SaleService saleService= ServiceFactory.getInstance().getServiceType(ServiceType.SALES);


    @FXML
    void btnOpenCustomerTabOnAction(ActionEvent event) {

    }

    @FXML
    void btnOpenItemTabOnAction(ActionEvent event) {

    }

    @FXML
    void btnPrintReceiptOnAction(ActionEvent event) {

    }

    @FXML
    void btnReloadSalesTableOnAction(ActionEvent event) {
        loadTable();
        new Alert(Alert.AlertType.INFORMATION,"Table Refreshed !!");
    }

    @FXML
    void btnSearchSaleOnAction(ActionEvent event) {

    }

    private void loadTable(){
        try {
            List<SalesTM> salesTMList = saleService.getAllSaleDetails();
            setTableColoumns();

            tblSales.setItems(FXCollections.observableList(salesTMList));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTableColoumns(){
        colSaleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colItems.setCellValueFactory(new PropertyValueFactory<>("medicineItems"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colNetTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        lblBestSellingItem.setText("");
        lblTodayRevenue.setText("");
        lblHighestOrder.setText("");
        lblSoldBestSellingItemCount.setText("");
        lblOrderDate.setText("");
        lblTransactionCount.setText("");
        lblSelectedSaleId.setText("Sale ID : ");
    }


}
