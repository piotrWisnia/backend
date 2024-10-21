package com.comarch.skodaapp.order;

import com.comarch.skodaapp.common.LoyaltyPoints;
import com.comarch.skodaapp.common.Money;
import com.comarch.skodaapp.customer.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final CustomerService customerService;
    private final OrderRepository orderRepository;

    public OrderService(CustomerService customerService, OrderRepository orderRepository) {
        this.customerService = customerService;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void addOrder(CreateOrderDto createOrderDto) {
        var orderAmount = new Money(createOrderDto.amount());
        orderRepository.save(new Order(createOrderDto.customerId(), orderAmount));
        customerService.addPoints(createOrderDto.customerId(), LoyaltyPoints.fromMoney(orderAmount));
    }
}
