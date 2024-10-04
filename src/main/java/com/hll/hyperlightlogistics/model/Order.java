/*
 * This file is part of HyperLightLogistics-Java.
 *
 * HyperLightLogistics-Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HyperLightLogistics-Java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HyperLightLogistics-Java.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2024 Rauf Agaguliev
 */

package com.hll.hyperlightlogistics.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "delivery_option_id")
    private DeliveryOption deliveryOption;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private LocalDateTime orderDate;
    private String status;
    private Double shippingPrice;

    public void addProduct(Product product, Integer quantity) {
        OrderProduct orderProduct = new OrderProduct(this, product, quantity);
        orderProducts.add(orderProduct);
    }

}
