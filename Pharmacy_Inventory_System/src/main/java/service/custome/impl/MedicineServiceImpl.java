package service.custome.impl;

import javafx.beans.property.ReadOnlyListWrapper;
import model.Medicine;
import model.tm.MedicineTM;
import repository.RepositoryFactory;
import repository.custome.MedicineRepository;
import service.custome.MedicineService;
import util.RepositoryType;
import util.ServiceType;

import java.sql.SQLException;
import java.util.List;

public class MedicineServiceImpl implements MedicineService {

    MedicineRepository repository= RepositoryFactory.getInstance().getRepositoryType(RepositoryType.MEDICINE);


    @Override
    public List<MedicineTM> getAllMedicineDetails() throws SQLException {
        return repository.getAllMedicineDetails();
    }

    @Override
    public List<MedicineTM> getMedicineDetailsByName(String mediName) throws SQLException {
        return repository.getMedicineDetailsByName(mediName);
    }

    @Override
    public List<String> getSupplierIDs() throws SQLException {
        return repository.getSupplierIDs();
    }

    @Override
    public boolean saveMedicine(Medicine medicine) throws SQLException {
        return repository.saveMedicine(medicine);
    }

    @Override
    public boolean updateMedicineDetails(Medicine medicine) throws SQLException {
        return repository.updateMedicineDetails(medicine);
    }

    @Override
    public boolean deleteMedicineDetails(int medicineId) throws SQLException {
        return repository.deleteMedicineDetails(medicineId);
    }
}
