package repository.custome;

import model.Medicine;
import model.tm.MedicineTM;
import model.tm.OrderCartTM;
import repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;

public interface MedicineRepository extends CrudRepository<MedicineTM,Integer> {

    List<MedicineTM> getAllMedicineDetails() throws SQLException;

    List<MedicineTM> getMedicineDetailsByName(String mediName) throws SQLException;

    List<String> getSupplierIDs() throws SQLException;

    boolean saveMedicine(Medicine medicine) throws SQLException;

    boolean updateMedicineDetails(Medicine medicine) throws SQLException;

    boolean deleteMedicineDetails(int medicineId) throws SQLException;

    boolean updateStock(List<OrderCartTM> saleItemList);

    void updateExpiredMedicines();

}
