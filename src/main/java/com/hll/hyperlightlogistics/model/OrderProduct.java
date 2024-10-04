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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {

    @EmbeddedId
    private OrderProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Product product;

    private Integer quantity;

    public OrderProduct(Order order, Product product, Integer quantity) {
        this.id = new OrderProductId(order.getId(), product.getId());
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

}
