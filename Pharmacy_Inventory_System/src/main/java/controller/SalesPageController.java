package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import model.Sales;
import model.tm.SalesTM;
import service.ServiceFactory;
import service.custome.SaleService;
import util.ServiceType;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Label;
import java.util.ResourceBundle;

public class SalesPageController implements Initializable {


    public Line line3;
    public Line line4;
    public Line line2;
    public Line line1;
    public TextField txtSearchSale;
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

    public Text lblSelectedSaleId;
    public Text lblCustomerNameText;
    public Text lblCustomerEmailText;
    public Text lblCustomerPhoneText;
    public Text lblBoughtItemText;
    public Text lblOrderQtyText;
    public Text lblSubTotalText;
    public Text lblTaxText;
    public Text lblNetTotalText;
    public Text lblDiscountText;
    public Text lblSubTotalValue;
    public Text lblTaxValue;
    public Text lblNetTotalValue;
    public Text lblDiscountValue;
    public Text lblCustomerNameValue;
    public Text lblCustomerEmailValue;
    public Text lblCustomerPhoneValue;
    public Text lblBoughtItemValue;
    public Text lblOrderQtyValue;


    SaleService saleService= ServiceFactory.getInstance().getServiceType(ServiceType.SALES);



    @FXML
    void btnPrintReceiptOnAction(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION,"Print function is not available in this situation !!").show();
    }

    @FXML
    void btnReloadSalesTableOnAction(ActionEvent event) {
        loadTable();
        new Alert(Alert.AlertType.INFORMATION,"Table Refreshed !!").show();
    }

    @FXML
    void btnSearchSaleOnAction(ActionEvent event) {
        String data=txtSearchSale.getText();
        if(!data.isEmpty()){
            try {
                Sales saleDetailsById = saleService.getSaleDetailsById(data);

                if(saleDetailsById!=null){
                    setLablesBySearched(saleDetailsById);
                    txtSearchSale.clear();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Searched Value Is Not Found !!").show();
                    txtSearchSale.clear();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
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

    private void loadRectagleValues(){
        try {
            List<String> valueList = saleService.getSalesPageRectanglesValue();
            lblTodayRevenue.setText(valueList.get(0));
            lblHighestOrder.setText("Rs. "+valueList.get(1));
            lblTransactionCount.setText(valueList.get(2));
            lblBestSellingItem.setText(valueList.get(3));
            lblSoldBestSellingItemCount.setText("SOLD ("+valueList.get(4)+") Items");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearAllDetailFields(){
        lblSelectedSaleId.setText("SALE ID : ");
        lblOrderDate.setText("0000-00-00   -> 00:00");
        lblCustomerNameText.setText("");
        lblCustomerEmailText.setText("");
        lblCustomerPhoneText.setText("");
        lblBoughtItemText.setText("");
        lblOrderQtyText.setText("");
        lblSubTotalText.setText("");
        lblTaxText.setText("");
        lblDiscountText.setText("");
        lblNetTotalText.setText("");

        lblCustomerNameValue.setText("");
        lblCustomerEmailValue.setText("");
        lblCustomerPhoneValue.setText("");
        lblBoughtItemValue.setText("");
        lblOrderQtyValue.setText("");
        lblSubTotalValue.setText("");
        lblTaxValue.setText("");
        lblDiscountValue.setText("");
        lblNetTotalValue.setText("");

        line1.setVisible(false);
        line2.setVisible(false);
        line3.setVisible(false);
        line4.setVisible(false);

    }

    private void loadDataSelectFromTable(){
        tblSales.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {

            assert t1 != null;

            SalesTM salesTM = (SalesTM) t1;

            if (salesTM!=null) {
                try {
                    txtSearchSale.clear();

                    Sales saleDetailsById = saleService.getSaleDetailsById(salesTM.getId()+"");
                    if(saleDetailsById!=null){
                        setLablesBySearched(saleDetailsById);
                        txtSearchSale.clear();
                    }


                } catch (SQLException e) {
                    txtSearchSale.clear();
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void setLablesBySearched(Sales sales){
        lblCustomerEmailValue.setStyle("-fx-fill: #000000;");
        lblCustomerPhoneValue.setStyle("-fx-fill: #000000;");

        lblSelectedSaleId.setText("SALE ID : "+sales.getId());
        lblOrderDate.setText(sales.getOrderDate()+"");
        lblCustomerNameText.setText("Customer Name : ");
        lblCustomerEmailText.setText("Email : ");
        lblCustomerPhoneText.setText("Phone Number : ");
        lblBoughtItemText.setText("Bought Items :");
        lblOrderQtyText.setText("Order Qty ");
        lblSubTotalText.setText("Sub Total ");
        lblTaxText.setText("Tax  ");
        lblDiscountText.setText("Discount ");
        lblNetTotalText.setText("Net Total ");

        lblCustomerNameValue.setText(sales.getCustomerName());
        lblCustomerEmailValue.setText(sales.getEmail());
        lblCustomerPhoneValue.setText(sales.getPhoneNumber());
        lblBoughtItemValue.setText(sales.getMedicineItems());
        lblOrderQtyValue.setText(sales.getOrderQty()+"");

        if(sales.getEmail()==null) {
            lblCustomerEmailValue.setText("No Data ..");
            lblCustomerEmailValue.setStyle("-fx-fill: #ef1515;");
        }

        if (sales.getPhoneNumber()==null){
            lblCustomerPhoneValue.setText("No Data ..");
            lblCustomerPhoneValue.setStyle("-fx-fill: #ef1515;");
        }


        double subTotal=sales.getSubTotal();
        double tax=subTotal*0.05;

        lblSubTotalValue.setText(subTotal+"");
        lblTaxValue.setText(tax+"");
        lblDiscountValue.setText(sales.getDiscount()+"");
        lblNetTotalValue.setText(sales.getNetTotal()+"");

        line1.setVisible(true);
        line2.setVisible(true);
        line3.setVisible(true);
        line4.setVisible(true);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblBestSellingItem.setText("");
        lblTodayRevenue.setText("");
        lblHighestOrder.setText("");
        lblSoldBestSellingItemCount.setText("");
        lblTransactionCount.setText("");

        lblOrderDate.setText("");
        lblSelectedSaleId.setText("Sale ID : ");

        loadTable();
        loadRectagleValues();
        clearAllDetailFields();
        loadDataSelectFromTable();
    }


}
