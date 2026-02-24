package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Medicine;
import model.Supplier;
import model.tm.MedicineTM;
import model.tm.SupplierTM;
import service.ServiceFactory;
import service.custome.SupplierService;
import util.ServiceType;
import util.StatusType;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierPageController implements Initializable {

    public Text lblSupplierIdText;
    public Text lblSupplierIdValue;
    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colCompany;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPhone;

    @FXML
    private TableColumn colRegisterDate;

    @FXML
    private Text lblSupplierCount;

    @FXML
    private TableView tblSuppliers;

    @FXML
    private TextArea txtCompanyAddress;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPersonName;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtSearchSupplierByName;

    @FXML
    private TextField txtSearchSupplierByPhone;


    SupplierService supplierService= ServiceFactory.getInstance().getServiceType(ServiceType.SUPPLIER);


    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        String companyName = txtCompanyName.getText().trim();
        String supplierName = txtPersonName.getText().trim();
        String phone = txtPhoneNumber.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtCompanyAddress.getText().trim();

        if (companyName.isEmpty() || supplierName.isEmpty() || phone.isEmpty() || email.isEmpty()
                || address.isEmpty()) {

            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        try {

            boolean isAdded = supplierService.saveSupplier(
                    new Supplier(
                            0,companyName, supplierName, phone, email, address, LocalDate.now()
                    )
            );

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully!").show();
                loadTable();
                clearAllFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save failed!").show();
            }

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
    void btnDeleteSupplierOnAction(ActionEvent event) {
        String companyName = txtCompanyName.getText().trim();
        String supplierName = txtPersonName.getText().trim();
        String phone = txtPhoneNumber.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtCompanyAddress.getText().trim();

        if (companyName.isEmpty() || supplierName.isEmpty() || phone.isEmpty() || email.isEmpty()
                || address.isEmpty()) {

            new Alert(Alert.AlertType.WARNING, "Please first search or select supplier from table and then try again to delete !!").show();
            return;
        }

        int supplierId=Integer.parseInt(lblSupplierIdValue.getText());
        try {
            boolean isDeleted = supplierService.deleteSupplierDetails(supplierId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier delete successfully!").show();
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
    void btnSearchSupplierOnAction(ActionEvent event) {
        String supplierName=txtSearchSupplierByName.getText();
        if(!supplierName.isEmpty()){
            try {
                List<SupplierTM> supplierTMS = supplierService.getSupplierDetailsByName(supplierName);
                lblSupplierIdText.setText("Supplier ID : ");
                lblSupplierIdValue.setText(supplierTMS.get(0).getId()+"");

                tblSuppliers.setItems(FXCollections.observableList(supplierTMS));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            clearAllFields();
            new Alert(Alert.AlertType.ERROR,"Please first enter supplier name and try again !!").show();
        }
    }

    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {
        String companyName = txtCompanyName.getText().trim();
        String supplierName = txtPersonName.getText().trim();
        String phone = txtPhoneNumber.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtCompanyAddress.getText().trim();

        if (companyName.isEmpty() || supplierName.isEmpty() || phone.isEmpty() || email.isEmpty()
                || address.isEmpty()) {

            new Alert(Alert.AlertType.WARNING, "Please first search or select supplier from table and then try again to delete !!").show();
            return;
        }

        try {
            int id=Integer.parseInt(lblSupplierIdValue.getText());
            boolean isUpdated = supplierService.updateSupplierDetails(
                    new Supplier(
                            id,companyName, supplierName, phone, email, address, LocalDate.now()
                    )
            );

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier details update successfully !!").show();
                loadTable();
                clearAllFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "update failed !!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error ekak !!").show();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
        setFieldsFromTable();
        loadSupplierCount();
    }

    private void loadTable(){
        clearAllFields();
        try {
            List<SupplierTM> supplierTMS=supplierService.getAllSupplierDetails();
            setColumns();
            tblSuppliers.setItems(FXCollections.observableList(supplierTMS));

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    private void loadSupplierCount(){
        try {
            int count=supplierService.totalSupplierCount();

            lblSupplierCount.setText(count+"");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setColumns(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRegisterDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
    }

    private void clearAllFields(){
        lblSupplierIdText.setText("");
        lblSupplierIdValue.setText("");

        txtCompanyName.clear();
        txtPersonName.clear();
        txtPhoneNumber.clear();
        txtEmail.clear();
        txtCompanyAddress.clear();
    }

    private void setFieldsFromTable(){

        tblSuppliers.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {

            assert t1 != null;

            SupplierTM supplierTM = (SupplierTM) t1;

            if (supplierTM!=null) {

                Supplier supplier = new Supplier(
                        supplierTM.getId(),
                        supplierTM.getCompany(),
                        supplierTM.getName(),
                        supplierTM.getPhone(),
                        supplierTM.getEmail(),
                        supplierTM.getAddress(),
                        supplierTM.getRegisterDate()
                );
                setTextValuesforSelected(supplier);
            }
        });
    }

    private void setTextValuesforSelected(Supplier supplier){
        lblSupplierIdText.setText("Supplier ID : ");
        lblSupplierIdValue.setText(supplier.getId()+"");

        txtCompanyName.setText(supplier.getCompany());
        txtPersonName.setText(supplier.getName());
        txtPhoneNumber.setText(supplier.getPhone());
        txtEmail.setText(supplier.getEmail());
        txtCompanyAddress.setText(supplier.getAddress());
    }

    public void btnReloadTableOnAction(ActionEvent actionEvent) {
        loadTable();
    }
}
