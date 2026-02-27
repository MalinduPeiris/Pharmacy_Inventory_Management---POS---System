package service.custome.impl;

import model.tm.SalesTM;
import repository.RepositoryFactory;
import repository.custome.SalesRepository;
import service.custome.SaleService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.List;

public class SaleServiceImpl implements SaleService {

    SalesRepository salesRepository= RepositoryFactory.getInstance().getRepositoryType(RepositoryType.SALES);

    @Override
    public List<SalesTM> getAllSaleDetails() throws SQLException {
        return salesRepository.getAllSaleDetails();
    }
}
