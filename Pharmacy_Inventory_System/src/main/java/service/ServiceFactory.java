package service;

import service.custome.impl.DashboardServiceImpl;
import service.custome.impl.MedicineServiceImpl;
import service.custome.impl.SupplierServiceImpl;
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
        }
        return null;
    }

}
