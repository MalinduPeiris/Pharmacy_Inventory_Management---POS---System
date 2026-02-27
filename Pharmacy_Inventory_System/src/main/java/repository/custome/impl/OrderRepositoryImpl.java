package repository.custome.impl;

import db.DBConnection;
import model.OrderCustomer;
import model.OrderSale;
import model.tm.OrderCartTM;
import repository.RepositoryFactory;
import repository.custome.CustomerRepository;
import repository.custome.MedicineRepository;
import repository.custome.OrderDetailRepository;
import repository.custome.OrderRepository;
import util.RepositoryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    CustomerRepository customerRepository= RepositoryFactory.getInstance()
            .getRepositoryType(RepositoryType.CUSTOMER);

    OrderDetailRepository orderDetailRepository=RepositoryFactory.getInstance()
            .getRepositoryType(RepositoryType.ORDERDETAIL);

    MedicineRepository medicineRepository=RepositoryFactory.getInstance()
            .getRepositoryType(RepositoryType.MEDICINE);

    public boolean placeOrder(List<OrderCartTM> saleItemList, OrderCustomer customer, OrderSale orderSale){

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement psTM = connection.prepareStatement(
                    "INSERT INTO sales (customer_name,customer_email,customer_phone,total_amount," +
                            "discount,net_total) VALUES (?,?,?,?,?,?)"
            );

            psTM.setString(1, customer.getCustName());
            psTM.setString(2, customer.getCustEmail());
            psTM.setString(3, customer.getCustPhoneNumber());
            psTM.setDouble(4, orderSale.getTotalAmount());
            psTM.setDouble(5, orderSale.getDiscount());
            psTM.setDouble(6, orderSale.getNetTotal());

            boolean isAddedSale=psTM.executeUpdate()>0;
            if (isAddedSale) {

                if(customerRepository.addCustomer(customer)){
                    if(medicineRepository.updateStock(saleItemList)){
                        int saleID = getSaleID(customer);
                        if(orderDetailRepository.addOrderItems(saleItemList,saleID)){
                            connection.commit();
                            connection.setAutoCommit(true);
                            return true;
                        }
                    }
                }
            }

            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private int getSaleID(OrderCustomer customer){
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTM = connection.prepareStatement(
                    "SELECT sale_id from sales WHERE customer_name = ? AND customer_phone = ?"
            );
            psTM.setString(1, customer.getCustName());
            psTM.setString(2,customer.getCustPhoneNumber());

            ResultSet resultSet = psTM.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
