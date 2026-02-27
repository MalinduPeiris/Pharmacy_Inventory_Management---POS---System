package service.custome;

import model.Supplier;
import model.tm.SupplierTM;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface SupplierService extends SuperService {

    List<SupplierTM> getAllSupplierDetails() throws SQLException;

    List<SupplierTM> getSupplierDetailsByName(String supplierName) throws SQLException;

    boolean saveSupplier(Supplier supplier) throws SQLException;

    boolean updateSupplierDetails(Supplier supplier) throws SQLException;

    boolean deleteSupplierDetails(int supplierId) throws SQLException;

    int totalSupplierCount() throws SQLException;

}
