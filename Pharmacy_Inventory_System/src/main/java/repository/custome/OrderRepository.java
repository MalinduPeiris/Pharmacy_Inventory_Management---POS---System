package repository.custome;

import model.OrderCustomer;
import model.OrderSale;
import model.tm.OrderCartTM;
import repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface OrderRepository  extends CrudRepository<OrderCartTM,Integer> {

    boolean placeOrder(List<OrderCartTM> saleItemList, OrderCustomer customer, OrderSale orderSale);

}
