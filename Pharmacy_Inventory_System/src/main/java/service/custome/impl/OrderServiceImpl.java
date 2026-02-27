package service.custome.impl;

import model.OrderCustomer;
import model.OrderSale;
import model.tm.OrderCartTM;
import repository.RepositoryFactory;
import repository.custome.OrderRepository;
import service.custome.OrderService;
import util.RepositoryType;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository=RepositoryFactory.getInstance().getRepositoryType(RepositoryType.ORDER);

    @Override
    public boolean placeOrder(List<OrderCartTM> saleItemList, OrderCustomer customer, OrderSale orderSale) {
        return orderRepository.placeOrder(saleItemList,customer,orderSale);
    }
}
