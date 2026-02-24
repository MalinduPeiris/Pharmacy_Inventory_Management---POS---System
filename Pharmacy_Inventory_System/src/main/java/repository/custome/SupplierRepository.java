package repository.custome;

import model.Medicine;
import model.Supplier;
import model.tm.MedicineTM;
import model.tm.SupplierTM;
import repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface SupplierRepository extends CrudRepository<MedicineTM,String> {

    List<SupplierTM> getAllSupplierDetails() throws SQLException;

    List<SupplierTM> getSupplierDetailsByName(String supplierName) throws SQLException;

    boolean saveSupplier(Supplier supplier) throws SQLException;

    boolean updateSupplierDetails(Supplier supplier) throws SQLException;

    boolean deleteSupplierDetails(int supplierId) throws SQLException;

    int totalSupplierCount() throws SQLException;

}
