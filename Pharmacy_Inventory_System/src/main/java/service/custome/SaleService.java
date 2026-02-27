package service.custome;

import model.tm.SalesTM;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface SaleService extends SuperService {

    List<SalesTM> getAllSaleDetails() throws SQLException;


}
