package service.custome;

import model.tm.DashboardTm;
import service.SuperService;

import java.util.List;

public interface DashboardService extends SuperService {

    List<DashboardTm> getAllExpiryMedicineDetail();

    List<DashboardTm> getAllBestSoldMedicine();

    int getMedicineCount();

    double getTodaySales();

    int getCustomerCount();

    int getSupplierCount();
}
