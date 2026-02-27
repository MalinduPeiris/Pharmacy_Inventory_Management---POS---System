package repository.custome.impl;

import db.DBConnection;
import model.tm.OrderCartTM;
import repository.custome.OrderDetailRepository;
import service.custome.OrderService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepositoryImpl implements OrderDetailRepository {


    @Override
    public boolean addOrderItems(List<OrderCartTM> salesItemList , int saleId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTM;
            for(OrderCartTM saleItem : salesItemList){

                psTM = connection.prepareStatement(
                        "INSERT INTO sale_items (sale_id,medicine_id,quantity_sold," +
                                "unit_price,subtotal) VALUES (?,?,?,?,?)"
                );

                psTM.setInt(1,saleId);
                psTM.setInt(2,saleItem.getMedicineId());
                psTM.setInt(3,saleItem.getOrderQty());
                psTM.setDouble(4,saleItem.getUnitPrice());
                psTM.setDouble(5,saleItem.getOrderTotal());

                if(psTM.executeUpdate()<1){
                    return false;
                }
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
