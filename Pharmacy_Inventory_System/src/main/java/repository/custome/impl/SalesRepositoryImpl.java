package repository.custome.impl;

import db.DBConnection;
import model.tm.SalesTM;
import repository.custome.SalesRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalesRepositoryImpl implements SalesRepository {

    public List<SalesTM> getAllSaleDetails() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "SELECT s.sale_id, s.sale_date, s.customer_name, s.customer_phone, " +
                        "s.customer_email, GROUP_CONCAT(m.name SEPARATOR ', ') AS medicine_items, " +
                        "SUM(si.quantity_sold) AS order_qty, s.total_amount AS sub_total, " +
                        "s.discount, s.net_total " +
                        "FROM sales s " +
                        "JOIN sale_items si ON s.sale_id = si.sale_id " +
                        "JOIN medicines m ON m.medicine_id = si.medicine_id " +
                        "GROUP BY s.sale_id"
        );

        ArrayList<SalesTM> salesTMList=new ArrayList<>();
        while(resultSet.next()){
            salesTMList.add(
                    new SalesTM(
                            resultSet.getInt(1),
                            resultSet.getDate(2).toLocalDate(),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getInt(7),
                            resultSet.getDouble(8),
                            resultSet.getDouble(9),
                            resultSet.getDouble(10)
                    )
            );

        }
        return salesTMList;
    }

}
