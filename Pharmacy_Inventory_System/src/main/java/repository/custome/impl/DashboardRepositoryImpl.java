package repository.custome.impl;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import model.tm.DashboardTm;
import model.tm.ExpiryMedicineTM;
import repository.custome.DashboardRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DashboardRepositoryImpl implements DashboardRepository {


    @Override
    public List<DashboardTm> getAllExpiryMedicineDetail() {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "SELECT medicine_id, name , dosage, expiry_date, " +
                            "DATEDIFF(expiry_date, CURDATE())  " +
                            "FROM medicines WHERE expiry_date >= CURDATE() "+
                            "AND expiry_date <= CURDATE() + INTERVAL 14 DAY " +
                            "AND status = 'active' " +
                            "ORDER BY expiry_date ASC "
            );

            ArrayList<DashboardTm> dashboardTmsList=new ArrayList<>();
            while (resultSet.next()){
                dashboardTmsList.add(
                        new DashboardTm(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                null,
                                resultSet.getString(3),
                                0,
                                0.0,
                                0.0,
                                resultSet.getDate(4).toLocalDate(),
                                resultSet.getString(5)
                        )
                );
            }

            return dashboardTmsList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public List<DashboardTm> getAllBestSoldMedicine() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "SELECT m.medicine_id , m.name , m.category , m.dosage , SUM(si.quantity_sold) ,"+
                            "AVG(si.unit_price) , SUM(si.subtotal) FROM sale_items si "+
                            "INNER JOIN medicines m ON si.medicine_id = m.medicine_id "+
                            "GROUP BY m.medicine_id, m.name, m.category, m.dosage "+
                            "ORDER BY SUM(si.subtotal) DESC"
            );

            ArrayList<DashboardTm> dashboardTmsList=new ArrayList<>();
            while (resultSet.next()){
                dashboardTmsList.add(
                        new DashboardTm(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5),
                                resultSet.getDouble(6),
                                resultSet.getDouble(7),
                                null,
                                null
                        )
                );
            }

            return dashboardTmsList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getMedicineCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT SUM(quantity) FROM medicines");
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getTodaySales() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(
                    "select SUM(net_total) FROM sales WHERE DATE(sale_date) = CURDATE()"
            );

            if(resultSet.next()){
                return resultSet.getDouble(1);
            }
            return 0.00;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCustomerCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM customer");
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSupplierCount() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select COUNT(*) FROM suppliers");
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            return 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
