package repository.custome;

import model.OrderCustomer;
import model.OrderSale;
import model.tm.OrderCartTM;
import repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderSale,Integer> {

    boolean addOrderItems(List<OrderCartTM> salesItemList,int saleId);


}
