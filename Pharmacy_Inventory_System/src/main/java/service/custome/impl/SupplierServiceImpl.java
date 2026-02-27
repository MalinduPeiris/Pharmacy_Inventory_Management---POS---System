package service.custome.impl;

import model.Supplier;
import model.tm.SupplierTM;
import repository.RepositoryFactory;
import repository.custome.SupplierRepository;
import service.custome.SupplierService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {

    SupplierRepository repository= RepositoryFactory.getInstance().getRepositoryType(RepositoryType.SUPPLIER);

    @Override
    public List<SupplierTM> getAllSupplierDetails() throws SQLException {
        return repository.getAllSupplierDetails();
    }

    @Override
    public List<SupplierTM> getSupplierDetailsByName(String supplierName) throws SQLException {
        return repository.getSupplierDetailsByName(supplierName);
    }

    @Override
    public boolean saveSupplier(Supplier supplier) throws SQLException {
        return repository.saveSupplier(supplier);
    }

    @Override
    public boolean updateSupplierDetails(Supplier supplier) throws SQLException {
        return repository.updateSupplierDetails(supplier);
    }

    @Override
    public boolean deleteSupplierDetails(int supplierId) throws SQLException {
        return repository.deleteSupplierDetails(supplierId);
    }

    @Override
    public int totalSupplierCount() throws SQLException {
        return repository.totalSupplierCount();
    }
}
