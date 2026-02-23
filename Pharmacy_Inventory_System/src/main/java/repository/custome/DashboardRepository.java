package repository.custome;

import model.tm.DashboardTm;
import repository.CrudRepository;

import java.util.List;

public interface DashboardRepository extends CrudRepository<DashboardTm,String> {
    List<DashboardTm> getAllExpiryMedicineDetail();

    List<DashboardTm> getAllBestSoldMedicine();

    int getMedicineCount();

    double getTodaySales();

    int getCustomerCount();

    int getSupplierCount();

}
