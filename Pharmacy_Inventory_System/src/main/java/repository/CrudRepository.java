package repository;

import java.util.List;

public interface CrudRepository<T,ID> extends SuperRepository {

    List<T> getAllExpiryMedicineDetail();

    List<T> getAllBestSoldMedicine();

    int getMedicineCount();

    double getTodaySales();

    int getCustomerCount();

    int getSupplierCount();


}
