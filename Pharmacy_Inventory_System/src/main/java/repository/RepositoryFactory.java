package repository;

import repository.custome.impl.DashboardRepositoryImpl;
import service.custome.impl.DashboardServiceImpl;
import util.RepositoryType;

public class RepositoryFactory {

    private static RepositoryFactory instance;

    private RepositoryFactory(){}

    public static RepositoryFactory getInstance(){
        return instance==null ? instance=new RepositoryFactory() : instance;
    }

    public <T extends SuperRepository>T getRepositoryType(RepositoryType repositoryType){
        switch (repositoryType){
            case DASHBOARD :
                return (T) new DashboardRepositoryImpl();
        }
        return null;
    }
}
