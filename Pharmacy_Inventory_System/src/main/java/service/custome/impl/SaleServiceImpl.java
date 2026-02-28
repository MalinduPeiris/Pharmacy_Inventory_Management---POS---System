package service.custome.impl;

import model.Sales;
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

    @Override
    public List<String> getSalesPageRectanglesValue() throws SQLException {
        return salesRepository.getSalesPageRectanglesValue();
    }

    @Override
    public Sales getSaleDetailsById(String data) throws SQLException {
        return salesRepository.getSaleDetailsById(data);
    }
}
