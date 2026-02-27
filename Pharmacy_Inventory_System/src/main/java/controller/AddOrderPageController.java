package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.tm.MedicineTM;
import model.tm.OrderCartTM;
import service.ServiceFactory;
import service.custome.MedicineService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddOrderPageController implements Initializable {

    public Text lblOrderIdValue;

    @FXML
    private JFXComboBox cmbOrderItem;

    @FXML
    private TableColumn colCartDosage;

    @FXML
    private TableColumn colCartMediID;

    @FXML
    private TableColumn colCartName;

    @FXML
    private TableColumn colCartOrderQty;

    @FXML
    private TableColumn colCartTotal;

    @FXML
    private TableColumn colCartUnitPrice;

    @FXML
    private TableColumn colMediBrand;

    @FXML
    private TableColumn colMediCategory;

    @FXML
    private TableColumn colMediDosage;

    @FXML
    private TableColumn colMediExpiryDate;

    @FXML
    private TableColumn colMediID;

    @FXML
    private TableColumn colMediName;

    @FXML
    private TableColumn colMediQuantity;

    @FXML
    private TableColumn colMediSellingPrice;

    @FXML
    private TableColumn colMediStatus;

    @FXML
    private DatePicker dateExpiryDate;

    @FXML
    private Text lblCustEmailValue;

    @FXML
    private Text lblCustNameValue;

    @FXML
    private Text lblCustPhoneNumberValue;

    @FXML
    private Text lblDiscount;

    @FXML
    private Text lblGrandTotal;

    @FXML
    private Text lblSubTotal;

    @FXML
    private Text lblTax;

    @FXML
    private TableView tblCart;

    @FXML
    private TableView tblMedicineDetails;

    @FXML
    private TextField txtAvailabaleQty;

    @FXML
    private TextField txtBuyingQty;

    @FXML
    private TextField txtCustEmail;

    @FXML
    private TextField txtCustName;

    @FXML
    private TextField txtCustPhoneNumber;

    @FXML
    private TextField txtDeleteCartItemId;

    @FXML
    private TextField txtDiscountValue;

    @FXML
    private TextField txtDosage;

    @FXML
    private TextField txtItemStatus;

    @FXML
    private TextField txtSearchItemId;

    @FXML
    private TextField txtUnitPrice;


    MedicineService medicineService= ServiceFactory.getInstance().getServiceType(ServiceType.MEDICINE);


    @FXML
    void btnAddCartClearFormOnAction(ActionEvent event) {
        clearCartForm();
    }

    @FXML
    void btnAddCustomerFormClearOnAction(ActionEvent event) {
        Alert clearAlert = new Alert(Alert.AlertType.CONFIRMATION);
        clearAlert.setTitle("Confirmation Dialog");
        clearAlert.setHeaderText("Please Confirm Your Action");
        clearAlert.setContentText("Do you clear add customer form and added customer details both ? ");

        Optional<ButtonType> result = clearAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            lblCustNameValue.setText("");
            lblCustEmailValue.setText("");
            lblCustPhoneNumberValue.setText("");
            txtCustPhoneNumber.clear();
            txtCustEmail.clear();
            txtCustName.clear();
        } else {
            txtCustPhoneNumber.clear();
            txtCustEmail.clear();
            txtCustName.clear();
        }
    }

    @FXML
    void btnAddCustomeronAction(ActionEvent event) {
        String custName=txtCustName.getText();
        String custPhoneNumb=txtCustPhoneNumber.getText();
        String custEmail=txtCustEmail.getText();

        if(!custName.isEmpty() && !custPhoneNumb.isEmpty() && !custEmail.isEmpty()){

            if(!lblCustNameValue.getText().isEmpty() && !lblCustPhoneNumberValue.getText().isEmpty() &&
                    !lblCustEmailValue.getText().isEmpty()) {

                new Alert(Alert.AlertType.WARNING,"Already Added A Customer ..").show();
            }else{
                try {
                    int phoneNumber=Integer.parseInt(custPhoneNumb);
                    if (custPhoneNumb.length() == 10) {
                        if (custEmail.contains("@") && custEmail.contains(".com")) {
                            lblCustNameValue.setText(custName);
                            lblCustEmailValue.setText(custEmail);
                            lblCustPhoneNumberValue.setText(phoneNumber+"");
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Invalid Email Type Try Again !!").show();
                            txtCustEmail.clear();
                        }

                    } else {
                        txtCustPhoneNumber.clear();
                        new Alert(Alert.AlertType.WARNING, "Invalid PhoneNumber Try Again !!").show();
                    }
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.WARNING, "Invalid PhoneNumber Try Again !!").show();
                    txtCustPhoneNumber.clear();
                }
            }

        }else if(!lblCustNameValue.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Already Added A Customer ..").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Please first fill the customer form and then try again !!").show();
        }
    }

    private ArrayList<OrderCartTM> orderCartTMSList=new ArrayList<>();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String status=txtItemStatus.getText();
        String buyingQtyString=txtBuyingQty.getText();
        int availableQty=Integer.parseInt(txtAvailabaleQty.getText());
        if(!status.isEmpty() && !buyingQtyString.isEmpty()){

            if(status.equals("active")){
                try{
                    int buyingQty=Integer.parseInt(buyingQtyString);

                    if(buyingQty<=availableQty){
                        String medicineId=lblOrderIdValue.getText();
                        String medicineName=cmbOrderItem.getValue().toString().split(" - ")[1];
                        String medicineDosage=txtDosage.getText();
                        double medicineUnitPrice=Double.parseDouble(txtUnitPrice.getText());
                        double orderTotal=medicineUnitPrice*buyingQty;

                        orderCartTMSList.add(
                                new OrderCartTM(
                                    medicineId,medicineName,medicineDosage,medicineUnitPrice,
                                        buyingQty,orderTotal
                                )
                        );

                        loadCart();
                        setGrandTotalValue();
                        clearCartForm();

                    }else{
                        txtBuyingQty.clear();
                        new Alert(Alert.AlertType.WARNING,"Stock Not Available Right Now !!").show();
                    }

                } catch (Exception e) {
                    txtBuyingQty.clear();
                    new Alert(Alert.AlertType.WARNING,"Invalid Buying Qty Please Try Again !!").show();
                }


            }else if(status.equals("expired") || status.equals("discontinued")){
                clearCartForm();
                new Alert(Alert.AlertType.WARNING,"Item Was Expired Or Discontinued Please Select Another Item !!").show();
            }

        }else{
            new Alert(Alert.AlertType.WARNING,"Please Fill The All Fields !!").show();
        }
    }

    @FXML
    void btnApplyDiscountOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteCartItemOnAction(ActionEvent event) {
        if(!orderCartTMSList.isEmpty()) {
            String deleteId = txtDeleteCartItemId.getText();
            if (!deleteId.isEmpty()) {

                for (OrderCartTM orderCartTM : orderCartTMSList) {
                    if (deleteId.equals(orderCartTM.getMedicineId())) {
                        orderCartTMSList.remove(orderCartTM);
                        new Alert(Alert.AlertType.INFORMATION, "Cart Item Delete Success !!").show();
                        loadCart();

                        setGrandTotalValue();
                        return;
                    }
                }
                new Alert(Alert.AlertType.WARNING, "Id Not Available Please Check Again !!").show();
                txtDeleteCartItemId.clear();
            } else {
                new Alert(Alert.AlertType.WARNING, "Please enter your delete cart item id first !!").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Items not available in cart !!").show();
        }

    }

    private void setGrandTotalValue(){
        double subTotal=0;
        for(OrderCartTM orderCartTM: orderCartTMSList){
            subTotal+=orderCartTM.getOrderTotal();

        }

        double tax=subTotal*0.05;
        double grandTotal=subTotal+tax;

        lblSubTotal.setText(subTotal+"");
        lblTax.setText(tax+"");
        lblGrandTotal.setText(grandTotal+"");
    }

    @FXML
    void btnGenerateInvoiceOnAction(ActionEvent event) {
        String grandTotalString=lblGrandTotal.getText();
        if(!grandTotalString.isEmpty()) {
            double grandTotal = Double.parseDouble(lblGrandTotal.getText());
            if (grandTotal > 0) {


            } else {
                new Alert(Alert.AlertType.WARNING, "Please Add Some Medicine Items !!").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please Add Some Medicine Items !!").show();
        }
    }

    @FXML
    void btnSearchItemOnAction(ActionEvent event) {
        try {
            List<MedicineTM> medicineTMS = medicineService.getMedicineDetailsByName(txtSearchItemId.getText());

            tblMedicineDetails.setItems(FXCollections.observableList(medicineTMS));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbOrderItemOnAction(ActionEvent actionEvent) {
        Object cmbValue=cmbOrderItem.getValue();
        if(cmbValue!=null) {
            String id = cmbValue.toString().split(" - ")[0];
            lblOrderIdValue.setText(id);
            try {
                List<MedicineTM> medicineTMS = medicineService.getMedicineDetailsByName(lblOrderIdValue.getText());

                txtAvailabaleQty.setText(medicineTMS.get(0).getQuantity() + "");
                txtUnitPrice.setText(medicineTMS.get(0).getSellingPrice() + "");
                txtDosage.setText(medicineTMS.get(0).getDosage() + "");
                dateExpiryDate.setValue(medicineTMS.get(0).getDate());
                txtItemStatus.setText(medicineTMS.get(0).getStatus());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void btnReloadTableOnAction(ActionEvent actionEvent) {
        loadTable();
        new Alert(Alert.AlertType.INFORMATION,"Table Refreshed !!").show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblCustNameValue.setText("");
        lblCustPhoneNumberValue.setText("");
        lblCustEmailValue.setText("");
        loadTable();
    }

    private void loadTable(){
        clearAllFields();
        try {
            List<MedicineTM> medicineTMS=medicineService.getAllMedicineDetails();
            setMedicineTableColumns();
            setOrderItemCbmValues(medicineTMS);
            tblMedicineDetails.setItems(FXCollections.observableList(medicineTMS));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCart(){
        colCartMediID.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colCartName.setCellValueFactory(new PropertyValueFactory<>("orderName"));
        colCartDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colCartUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colCartOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colCartTotal.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));

        tblCart.setItems(FXCollections.observableList(orderCartTMSList));
    }

    private void setOrderItemCbmValues(List<MedicineTM> medicineTMSList){
        ArrayList<String> stringOrderItemNames=new ArrayList<>();
        for (MedicineTM medicineTM: medicineTMSList){
            stringOrderItemNames.add(medicineTM.getId()+" - "+medicineTM.getName());
        }

        cmbOrderItem.setItems(FXCollections.observableList(stringOrderItemNames));
    }

    private void setMedicineTableColumns(){
        colMediID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMediName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMediBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colMediCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colMediDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colMediSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colMediQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colMediExpiryDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMediStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }


    private void clearAllFields(){
        txtCustName.clear();
        txtCustPhoneNumber.clear();
        txtCustEmail.clear();
        cmbOrderItem.setValue(null);
        txtAvailabaleQty.clear();
        txtUnitPrice.clear();
        txtDosage.clear();
        dateExpiryDate.setValue(null);
        txtItemStatus.clear();
        txtBuyingQty.clear();
        txtDeleteCartItemId.clear();
        txtSearchItemId.clear();
        lblCustNameValue.setText("");
        lblCustPhoneNumberValue.setText("");
        lblCustEmailValue.setText("");
        lblOrderIdValue.setText("");
        txtDiscountValue.clear();
        lblSubTotal.setText("");
        lblDiscount.setText("");
        lblGrandTotal.setText("");
        lblTax.setText("");

    }

    private void clearCartForm(){
        cmbOrderItem.setValue(null);
        txtAvailabaleQty.clear();
        txtUnitPrice.clear();
        txtDosage.clear();
        dateExpiryDate.setValue(null);
        txtItemStatus.clear();
        txtBuyingQty.clear();
        lblOrderIdValue.setText("");

        txtDeleteCartItemId.clear();
    }

}
