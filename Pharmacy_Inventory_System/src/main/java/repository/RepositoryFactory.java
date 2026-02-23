package repository;

import repository.custome.impl.DashboardRepositoryImpl;
import repository.custome.impl.MedicineRepositoryImpl;
import service.custome.impl.DashboardServiceImpl;
import util.RepositoryType;
import util.ServiceType;

public class RepositoryFactory {

    private static RepositoryFactory instance;

    private RepositoryFactory(){}

    public static RepositoryFactory getInstance(){
        return instance==null ? instance=new RepositoryFactory() : instance;
    }

    public <T extends SuperRepository>T getRepositoryType(RepositoryType type){
        switch (type){
            case DASHBOARD:
                return (T) new DashboardRepositoryImpl();
            case MEDICINE:
                return (T) new MedicineRepositoryImpl();
        }
        return null;
    }
}
