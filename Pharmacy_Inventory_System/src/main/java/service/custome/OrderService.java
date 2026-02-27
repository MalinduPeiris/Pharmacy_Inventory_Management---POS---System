package service.custome;

import model.OrderCustomer;
import model.OrderSale;
import model.tm.OrderCartTM;
import service.SuperService;

import java.util.List;

public interface OrderService extends SuperService {

    boolean placeOrder(List<OrderCartTM> saleItemList, OrderCustomer customer, OrderSale orderSale);

}
