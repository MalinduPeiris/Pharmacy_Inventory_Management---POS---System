package repository.custome.impl;

import db.DBConnection;
import model.Medicine;
import model.tm.MedicineTM;
import repository.custome.DashboardRepository;
import repository.custome.MedicineRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepositoryImpl implements MedicineRepository {

    public List<MedicineTM> getAllMedicineDetails() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select medicine_id,name, brand , category ,dosage , supplier_id , " +
                        "purchase_price , selling_price , quantity , expiry_date , status from medicines "
        );

        ArrayList<MedicineTM> medicineTMS=new ArrayList<>();
        while(resultSet.next()){
            medicineTMS.add(
                    new MedicineTM(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getDouble(7),
                            resultSet.getDouble(8),
                            resultSet.getInt(9),
                            resultSet.getDate(10).toLocalDate(),
                            resultSet.getString(11)
                    )
            );
        }
        return medicineTMS;
    }

    public List<MedicineTM> getMedicineDetailsByName(String mediName) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql="select medicine_id,name, brand , category ,dosage , supplier_id , " +
                "purchase_price , selling_price , quantity , expiry_date , status from medicines WHERE name=? OR " +
                "medicine_id = ? OR status = ? OR category = ?";

        PreparedStatement psTM = connection.prepareStatement(sql);

        psTM.setString(1,mediName);
        psTM.setString(2,mediName);
        psTM.setString(3,mediName);
        psTM.setString(4,mediName);


        ResultSet resultSet = psTM.executeQuery();

        ArrayList<MedicineTM> medicineTMS=new ArrayList<>();
        while(resultSet.next()){
            medicineTMS.add(
                    new MedicineTM(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getDouble(7),
                            resultSet.getDouble(8),
                            resultSet.getInt(9),
                            resultSet.getDate(10).toLocalDate(),
                            resultSet.getString(11)
                    )
            );


        }
        return medicineTMS;
    }

    public List<String> getSupplierIDs() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select supplier_id from suppliers "
        );

        ArrayList<String> supplierIDsList=new ArrayList<>();
        while(resultSet.next()){
            supplierIDsList.add(
                    resultSet.getString(1)
            );
        }
        return supplierIDsList;
    }

    public boolean saveMedicine(Medicine medicine) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql="insert into medicines(name, brand , category ,dosage , supplier_id , " +
                "purchase_price , selling_price , quantity , expiry_date , status) VALUES(?,?,?,?,?,?,?,?,?,?) ";

        PreparedStatement psTM = connection.prepareStatement(sql);

        psTM.setString(1, medicine.getName());
        psTM.setString(2, medicine.getBrand());
        psTM.setString(3, medicine.getCategory());
        psTM.setString(4, medicine.getDosage());
        psTM.setInt(5, medicine.getSupplierId());
        psTM.setDouble(6, medicine.getPurchasePrice());
        psTM.setDouble(7, medicine.getSellingPrice());
        psTM.setInt(8, medicine.getQuantity());
        psTM.setObject(9, medicine.getDate());
        psTM.setString(10,medicine.getStatus());

        return psTM.executeUpdate()>0;


    }

    public boolean updateMedicineDetails(Medicine medicine) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql="UPDATE medicines SET name = ?, brand = ?, category = ?,  dosage = ?, " +
                "supplier_id = ?, purchase_price = ?, selling_price = ?,quantity = ?, " +
                "expiry_date = ?, status = ? WHERE medicine_id = ?";

        PreparedStatement psTM = connection.prepareStatement(sql);

        psTM.setString(1, medicine.getName());
        psTM.setString(2, medicine.getBrand());
        psTM.setString(3, medicine.getCategory());
        psTM.setString(4, medicine.getDosage());
        psTM.setInt(5, medicine.getSupplierId());
        psTM.setDouble(6, medicine.getPurchasePrice());
        psTM.setDouble(7, medicine.getSellingPrice());
        psTM.setInt(8, medicine.getQuantity());
        psTM.setDate(9, Date.valueOf(medicine.getDate()));
        psTM.setString(10, medicine.getStatus());
        psTM.setInt(11, medicine.getId());

        return psTM.executeUpdate()>0;


    }

    public boolean deleteMedicineDetails(int medicineId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql="DELETE FROM medicines WHERE medicine_id = ?";
        PreparedStatement psTM = connection.prepareStatement(sql);
        psTM.setInt(1, medicineId);

        return psTM.executeUpdate()>0;


    }

//    +" - "+resultSet.getString(2)+" ( "+resultSet.getString(3)+" )"
//,supplier_name,supplier_company



}
