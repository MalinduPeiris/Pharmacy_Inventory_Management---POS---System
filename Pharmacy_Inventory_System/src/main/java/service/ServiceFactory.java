package service;

import repository.custome.impl.OrderDetailRepositoryImpl;
import service.custome.impl.*;
import util.ServiceType;

public class ServiceFactory {

    private static ServiceFactory instance;

    private ServiceFactory(){

    }

    public static ServiceFactory getInstance(){
        return instance==null ? instance=new ServiceFactory() : instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType serviceType){
        switch (serviceType){
            case DASHBOARD :
                return (T) new DashboardServiceImpl();
            case MEDICINE :
                return (T) new MedicineServiceImpl();
            case SUPPLIER:
                return (T) new SupplierServiceImpl();
            case ORDER:
                return (T) new OrderServiceImpl();
        }
        return null;
    }

}
