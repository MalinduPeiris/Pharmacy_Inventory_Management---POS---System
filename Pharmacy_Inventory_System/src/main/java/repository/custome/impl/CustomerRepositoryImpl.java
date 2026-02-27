package repository.custome.impl;

import db.DBConnection;
import model.OrderCustomer;
import repository.custome.CustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerRepositoryImpl implements CustomerRepository {


    @Override
    public boolean addCustomer(OrderCustomer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTM = connection.prepareStatement(
                    "INSERT INTO customer (customer_name,email,phone) VALUES (?,?,?)"
            );
            psTM.setString(1, customer.getCustName());
            psTM.setString(2, customer.getCustEmail());
            psTM.setString(3, customer.getCustPhoneNumber());

            return psTM.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
