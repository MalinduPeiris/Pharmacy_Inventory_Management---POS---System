package service.custome;

import model.Medicine;
import model.tm.MedicineTM;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface MedicineService extends SuperService {

    List<MedicineTM> getAllMedicineDetails() throws SQLException;

    List<MedicineTM> getMedicineDetailsByName(String mediName) throws SQLException;

    List<String> getSupplierIDs() throws SQLException;

    boolean saveMedicine(Medicine medicine) throws SQLException;

    boolean updateMedicineDetails(Medicine medicine) throws SQLException;

    boolean deleteMedicineDetails(int medicineId) throws SQLException;




}
