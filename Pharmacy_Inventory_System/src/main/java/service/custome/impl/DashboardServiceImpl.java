package service.custome.impl;

import model.tm.DashboardTm;
import repository.RepositoryFactory;
import repository.custome.DashboardRepository;
import service.custome.DashboardService;
import util.RepositoryType;

import java.util.List;

public class DashboardServiceImpl implements DashboardService {

    DashboardRepository dashboardRepositoryImpl=
            RepositoryFactory
                    .getInstance()
                    .getRepositoryType(RepositoryType.DASHBOARD);


    @Override
    public List<DashboardTm> getAllExpiryMedicineDetail() {
        return dashboardRepositoryImpl.getAllExpiryMedicineDetail();
    }

    @Override
    public List<DashboardTm> getAllBestSoldMedicine() {
        return dashboardRepositoryImpl.getAllBestSoldMedicine();
    }

    @Override
    public int getMedicineCount() {
        return dashboardRepositoryImpl.getMedicineCount();
    }

    @Override
    public double getTodaySales() {
        return dashboardRepositoryImpl.getTodaySales();
    }

    @Override
    public int getCustomerCount() {
        return dashboardRepositoryImpl.getCustomerCount();
    }

    @Override
    public int getSupplierCount() {
        return dashboardRepositoryImpl.getSupplierCount();
    }
}
