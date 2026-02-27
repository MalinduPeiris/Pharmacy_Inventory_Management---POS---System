package repository.custome.impl;

import db.DBConnection;
import model.Supplier;
import model.tm.SupplierTM;
import repository.custome.SupplierRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepositoryImpl implements SupplierRepository {

    public List<SupplierTM> getAllSupplierDetails() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select supplier_id ,supplier_company, supplier_name , phone ,email , address , " +
                        "created_at from suppliers "
        );

        ArrayList<SupplierTM> supplierTMS=new ArrayList<>();
        while(resultSet.next()){
            supplierTMS.add(
                    new SupplierTM(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getDate(7).toLocalDate()
                    )
            );
        }
        return supplierTMS;
    }

    public List<SupplierTM> getSupplierDetailsByName(String supplierName) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql="select supplier_id ,supplier_company, supplier_name , phone ,email , address , " +
                "created_at from suppliers WHERE supplier_name=? ";

        PreparedStatement psTM = connection.prepareStatement(sql);

        psTM.setString(1,supplierName);

        ResultSet resultSet = psTM.executeQuery();

        ArrayList<SupplierTM> supplierTMS=new ArrayList<>();

        while(resultSet.next()){
            supplierTMS.add(
                    new SupplierTM(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getDate(7).toLocalDate()
                    )
            );
        }
        return supplierTMS;
    }

    public boolean saveSupplier(Supplier supplier) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql="insert into suppliers(supplier_company, supplier_name , phone ,email , " +
                "address) VALUES(?,?,?,?,?) ";

        PreparedStatement psTM = connection.prepareStatement(sql);

        psTM.setString(1, supplier.getCompany());
        psTM.setString(2, supplier.getName());
        psTM.setString(3, supplier.getPhone());
        psTM.setString(4, supplier.getEmail());
        psTM.setString(5, supplier.getAddress());

        return psTM.executeUpdate()>0;


    }

    public boolean updateSupplierDetails(Supplier supplier) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql="UPDATE suppliers SET supplier_company = ?, supplier_name = ?, phone = ?,  " +
                "email = ?, address = ? WHERE supplier_id = ?";

        PreparedStatement psTM = connection.prepareStatement(sql);

        psTM.setString(1, supplier.getCompany());
        psTM.setString(2, supplier.getName());
        psTM.setString(3, supplier.getPhone());
        psTM.setString(4, supplier.getEmail());
        psTM.setString(5, supplier.getAddress());
        psTM.setDouble(6, supplier.getId());

        return psTM.executeUpdate()>0;

    }

    public boolean deleteSupplierDetails(int supplierId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql="DELETE FROM suppliers WHERE supplier_id = ?";
        PreparedStatement psTM = connection.prepareStatement(sql);
        psTM.setInt(1, supplierId);

        return psTM.executeUpdate()>0;


    }

    public int totalSupplierCount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select count(*) from suppliers "
        );

        resultSet.next();
        return resultSet.getInt(1);
    }



}
