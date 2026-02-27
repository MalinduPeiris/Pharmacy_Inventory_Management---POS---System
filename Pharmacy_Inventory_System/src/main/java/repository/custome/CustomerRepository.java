package repository.custome;

import model.OrderCustomer;
import model.tm.DashboardTm;
import repository.CrudRepository;

import java.sql.SQLException;

public interface CustomerRepository extends CrudRepository<OrderCustomer,Integer> {

    boolean addCustomer(OrderCustomer customer);

}
