package com.comarch.skodaapp.order;

import com.comarch.skodaapp.common.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "orders")
@Getter
class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long customerId;

    private Money amount;

    @Version
    private int version;

    protected Order() {
    }

    public Order(Long customerId, Money amount) {
        this.customerId = customerId;
        this.amount = amount;
    }
}
