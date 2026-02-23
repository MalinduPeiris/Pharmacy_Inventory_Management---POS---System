package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SupplierPageController implements Initializable {

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

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchSupplierOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
