package controller;

import com.jfoenix.controls.JFXComboBox;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Medicine;
import model.tm.MedicineTM;
import service.ServiceFactory;
import service.custome.MedicineService;
import util.ServiceType;
import util.StatusType;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class MedicinePageController implements Initializable {

    public AnchorPane pagesAnchorPane;
    public Text lblMedicineText;
    public Text lblMedicineID;

    @FXML
    private JFXComboBox cmbStatus;

    @FXML
    private JFXComboBox cmbSupplierID;

    @FXML
    private TableColumn colBrand;

    @FXML
    private TableColumn colCategory;

    @FXML
    private TableColumn colDosage;

    @FXML
    private TableColumn colExpiryDate;

    @FXML
    private TableColumn colMediId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPurchasePrice;

    @FXML
    private TableColumn colQuantity;

    @FXML
    private TableColumn colSellingPrice;

    @FXML
    private TableColumn colStatus;

    @FXML
    private TableColumn colSupplierID;

    @FXML
    private DatePicker dateDate;

    @FXML
    private TableView tblMedicineDetails;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtDosage;

    @FXML
    private TextField txtMedicineName;

    @FXML
    private TextField txtPurchasePrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearchMedicineByName;

    @FXML
    private TextField txtSellingPrice;


    MedicineService medicineService= ServiceFactory.getInstance().getServiceType(ServiceType.MEDICINE);


    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        try {
            URL resource = this.getClass().getResource("/view/supplierPage.fxml");

            assert resource!=null;

            Parent parent= FXMLLoader.load(resource);

            pagesAnchorPane.getChildren().clear();
            pagesAnchorPane.getChildren().add(parent);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddMedicineOnAction(ActionEvent event) {
        String name = txtMedicineName.getText().trim();
        String brand = txtBrand.getText().trim();
        String category = txtCategory.getText().trim();
        String dosage = txtDosage.getText().trim();
        String qtyString = txtQty.getText().trim();
        String purchasePriceString = txtPurchasePrice.getText().trim();
        String sellingPriceString = txtSellingPrice.getText().trim();
        Object statusObj = cmbStatus.getValue();
        Object supplierObj = cmbSupplierID.getValue();

        if (name.isEmpty() || brand.isEmpty() || category.isEmpty() || dosage.isEmpty()
                || qtyString.isEmpty() || purchasePriceString.isEmpty()
                || sellingPriceString.isEmpty() || statusObj == null || supplierObj == null) {

            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        try {
            int supplierId = Integer.parseInt(supplierObj.toString());
            int qty = Integer.parseInt(qtyString);
            double purchasePrice = Double.parseDouble(purchasePriceString);
            double sellingPrice = Double.parseDouble(sellingPriceString);

            LocalDate expiryDate = dateDate.getValue();
            String status = statusObj.toString();

            boolean isAdded = medicineService.saveMedicine(
                    new Medicine(
                            0,name, brand, category, dosage, supplierId, purchasePrice, sellingPrice,
                            qty, expiryDate, status
                    )
            );

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Medicine saved successfully!").show();
                loadTable();
                clearAllFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save failed!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format!").show();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error!").show();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearAllFields();
    }

    @FXML
    void btnDeleteMedicineOnAction(ActionEvent event) {
        String name = txtMedicineName.getText().trim();
        String brand = txtBrand.getText().trim();
        String category = txtCategory.getText().trim();
        String dosage = txtDosage.getText().trim();
        String qtyString = txtQty.getText().trim();
        String purchasePriceString = txtPurchasePrice.getText().trim();
        String sellingPriceString = txtSellingPrice.getText().trim();
        Object statusObj = cmbStatus.getValue();
        Object supplierObj = cmbSupplierID.getValue();

        if (name.isEmpty() || brand.isEmpty() || category.isEmpty() || dosage.isEmpty()
                || qtyString.isEmpty() || purchasePriceString.isEmpty()
                || sellingPriceString.isEmpty() || statusObj == null || supplierObj == null) {

            new Alert(Alert.AlertType.WARNING, "Please first search or select medicine from table and then try again to delete !!").show();
            return;
        }

        int medicineId=Integer.parseInt(lblMedicineID.getText());
        try {
            boolean isDeleted = medicineService.deleteMedicineDetails(medicineId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Medicine delete successfully!").show();
                loadTable();
                clearAllFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "delete failed!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSearchMedicineOnAction(ActionEvent event) {
        String medicineName=txtSearchMedicineByName.getText();
        if(!medicineName.isEmpty()){
            try {
                List<MedicineTM> medicineTMS = medicineService.getMedicineDetailsByName(medicineName);
                lblMedicineText.setText("Medicine ID : ");
                lblMedicineID.setText(medicineTMS.get(0).getId()+"");

                tblMedicineDetails.setItems(FXCollections.observableList(medicineTMS));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            clearAllFields();
            new Alert(Alert.AlertType.ERROR,"Please first enter medicine name and try again !!").show();
        }
    }

    @FXML
    void btnUpdateMedicineOnAction(ActionEvent event) {
        String name = txtMedicineName.getText().trim();
        String brand = txtBrand.getText().trim();
        String category = txtCategory.getText().trim();
        String dosage = txtDosage.getText().trim();
        String qtyString = txtQty.getText().trim();
        String purchasePriceString = txtPurchasePrice.getText().trim();
        String sellingPriceString = txtSellingPrice.getText().trim();
        Object statusObj = cmbStatus.getValue();
        Object supplierObj = cmbSupplierID.getValue();

        if (name.isEmpty() || brand.isEmpty() || category.isEmpty() || dosage.isEmpty()
                || qtyString.isEmpty() || purchasePriceString.isEmpty()
                || sellingPriceString.isEmpty() || statusObj == null || supplierObj == null) {

            new Alert(Alert.AlertType.WARNING, "Please fill all fields !!").show();
            return;
        }

        try {
            int supplierId = Integer.parseInt(supplierObj.toString());
            int qty = Integer.parseInt(qtyString);
            double purchasePrice = Double.parseDouble(purchasePriceString);
            double sellingPrice = Double.parseDouble(sellingPriceString);

            LocalDate expiryDate = dateDate.getValue();
            String status = statusObj.toString();
            int medicineId=Integer.parseInt(lblMedicineID.getText());

            boolean isUpdated = medicineService.updateMedicineDetails(
                    new Medicine(
                            medicineId,name, brand, category, dosage, supplierId, purchasePrice, sellingPrice,
                            qty, expiryDate, status
                    )
            );

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Medicine update successfully !!").show();
                loadTable();
                clearAllFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "update failed !!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format !!").show();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error ekak !!").show();
            throw new RuntimeException(e);
        }


    }

    public void btnReloadTableOnAction(ActionEvent actionEvent) {
        loadTable();
        new Alert(Alert.AlertType.INFORMATION,"Table Refreshed !!").show();
    }

    private void setExpiryItems(){
        medicineService.updateExpiredItem();
    }

    private void loadTable(){
        clearAllFields();
        try {
            List<MedicineTM> medicineTMS=medicineService.getAllMedicineDetails();
            setColumns();
            tblMedicineDetails.setItems(FXCollections.observableList(medicineTMS));

        } catch (SQLException e) {


            throw new RuntimeException(e);
        }
    }

    private void setColumns(){
        colMediId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colPurchasePrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void clearAllFields(){
        lblMedicineText.setText("");
        lblMedicineID.setText("");

        txtBrand.clear();
        txtCategory.clear();
        txtDosage.clear();
        txtQty.clear();
        txtMedicineName.clear();
        txtPurchasePrice.clear();
        txtSearchMedicineByName.clear();
        txtSellingPrice.clear();
        cmbStatus.setValue("");
        cmbSupplierID.setValue("");
        dateDate.setValue(null);
    }

    private void loadComboBox(){
//        ArrayList<String> cmbStatusValueList=new ArrayList<>();
//        cmbStatusValueList.add(
//                StatusType.ACTIVE,
//                StatusType.DEACTIVE,
//                StatusType.HOLD
//        );
        cmbStatus.setItems(FXCollections.observableList(
                Arrays.asList(
                        StatusType.active,
                        StatusType.expired,
                        StatusType.discontinued
                )
        ));


        try {
            List<String> supplierIDs = medicineService.getSupplierIDs();
            cmbSupplierID.setItems(FXCollections.observableList(supplierIDs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setFieldsFromTable(){

        tblMedicineDetails.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {

            assert t1 != null;

            MedicineTM medicineTM = (MedicineTM) t1;

            if (medicineTM!=null) {

                Medicine medicine = new Medicine(
                        medicineTM.getId(),
                        medicineTM.getName(),
                        medicineTM.getBrand(),
                        medicineTM.getCategory(),
                        medicineTM.getDosage(),
                        medicineTM.getSupplierId(),
                        medicineTM.getPurchasePrice(),
                        medicineTM.getSellingPrice(),
                        medicineTM.getQuantity(),
                        medicineTM.getDate(),
                        medicineTM.getStatus()

                );
                setTextValuesforSelected(medicine);
            }
        });
    }

    private void setTextValuesforSelected(Medicine medicine){
        lblMedicineText.setText("Medicine ID : ");
        lblMedicineID.setText(medicine.getId()+"");

        txtBrand.setText(medicine.getBrand());
        txtCategory.setText(medicine.getCategory());
        txtDosage.setText(medicine.getDosage());
        txtQty.setText(medicine.getQuantity()+"");
        txtMedicineName.setText(medicine.getName());
        txtPurchasePrice.setText(medicine.getPurchasePrice()+"");
        txtSellingPrice.setText(medicine.getSellingPrice()+"");
        cmbSupplierID.setValue(medicine.getSupplierId()+"");
        StatusType status=medicine.getStatus().equals("active") ? StatusType.active :
                medicine.getStatus().equals("expired") ? StatusType.expired : StatusType.discontinued ;
        cmbStatus.setValue(status);
        dateDate.setValue(medicine.getDate());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        loadComboBox();
        setFieldsFromTable();
        setExpiryItems();
    }
}
