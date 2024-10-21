package com.comarch.skodaapp.order;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void addOrder(@RequestBody @Valid CreateOrderDto createOrderDto) {
        orderService.addOrder(createOrderDto);
    }
}
