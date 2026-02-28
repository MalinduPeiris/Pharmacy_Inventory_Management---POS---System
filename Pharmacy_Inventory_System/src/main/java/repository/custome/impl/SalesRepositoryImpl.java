package repository.custome.impl;

import db.DBConnection;
import model.Sales;
import model.tm.SalesTM;
import repository.custome.SalesRepository;

import java.sql.*;
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

    public Sales getSaleDetailsById(String data) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTM = connection.prepareStatement(
                "SELECT s.sale_id, s.sale_date, s.customer_name, s.customer_phone, " +
                        "s.customer_email, GROUP_CONCAT(m.name SEPARATOR ', ') AS medicine_items, " +
                        "SUM(si.quantity_sold) AS order_qty, s.total_amount AS sub_total, " +
                        "s.discount, s.net_total " +
                        "FROM sales s " +
                        "JOIN sale_items si ON s.sale_id = si.sale_id " +
                        "JOIN medicines m ON m.medicine_id = si.medicine_id " +
                        "Where s.sale_id = ? OR s.customer_name=? OR s.customer_phone=? GROUP BY s.sale_id"
        );
        try {
            int id = Integer.parseInt(data);
            psTM.setInt(1,id);

        }catch (NumberFormatException e){

            psTM.setInt(1,0);
            System.out.println("Not a number u searched in sales...");
        }

        psTM.setString(2,data);
        psTM.setString(3,data);
        ResultSet resultSet = psTM.executeQuery();

        if(resultSet.next()) {
            SalesTM salesTM = new SalesTM(
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
            );

            return Sales.builder()
                    .id(salesTM.getId())
                    .orderDate(salesTM.getOrderDate())
                    .customerName(salesTM.getCustomerName())
                    .phoneNumber(salesTM.getPhoneNumber())
                    .email(salesTM.getEmail())
                    .medicineItems(salesTM.getMedicineItems())
                    .orderQty(salesTM.getOrderQty())
                    .subTotal(salesTM.getSubTotal())
                    .discount(salesTM.getDiscount())
                    .netTotal(salesTM.getNetTotal())
                    .build();
        }else {
            return null;
        }
    }

    public List<String> getSalesPageRectanglesValue() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        List<String> list = new ArrayList<>();

        ResultSet todayRevenueSet = statement.executeQuery(
                "select SUM(net_total) FROM sales WHERE DATE(sale_date) = CURDATE()"
        );
        todayRevenueSet.next();
        list.add(todayRevenueSet.getString(1));

        ResultSet todayMaxOrderValueSet = statement.executeQuery(
                "SELECT MAX(net_total) FROM sales WHERE DATE(sale_date) = CURDATE()"
        );
        todayMaxOrderValueSet.next();
        list.add(todayMaxOrderValueSet.getString(1));

        ResultSet allSalesCountSet = statement.executeQuery(
                "SELECT COUNT(*) FROM sales"
        );
        allSalesCountSet.next();
        list.add(allSalesCountSet.getString(1));

        ResultSet bestSellingItemSet = statement.executeQuery(
                "SELECT m.name, SUM(si.quantity_sold) AS total_qty FROM sale_items si "+
                        "JOIN medicines m ON m.medicine_id = si.medicine_id GROUP BY si.medicine_id "+
                        "ORDER BY total_qty DESC LIMIT 1"
        );

        if (bestSellingItemSet.next()) {
            list.add(bestSellingItemSet.getString(1));
            list.add(bestSellingItemSet.getString(2));
        } else {
            list.add("0");
            list.add("0");
        }
        return list;
    }

}
