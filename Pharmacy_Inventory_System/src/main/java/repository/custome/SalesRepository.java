package repository.custome;

import model.tm.OrderCartTM;
import model.tm.SalesTM;
import repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface SalesRepository extends CrudRepository<SalesTM,Integer> {

    List<SalesTM> getAllSaleDetails() throws SQLException;



}
