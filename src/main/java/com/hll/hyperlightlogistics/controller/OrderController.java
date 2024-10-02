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

package com.hll.hyperlightlogistics.controller;

import com.hll.hyperlightlogistics.dto.OrderRequestDTO;
import com.hll.hyperlightlogistics.model.DeliveryOption;
import com.hll.hyperlightlogistics.model.Order;
import com.hll.hyperlightlogistics.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/options")
    public ResponseEntity<List<DeliveryOption>> prepareOrderAndGetDeliveryOptions(
            @RequestBody OrderRequestDTO orderRequest) {
        Order order = orderService.createOrder(orderRequest);

        List<DeliveryOption> deliveryOptions = orderService.requestDeliveryOptions(order);

        return ResponseEntity.ok(deliveryOptions);
    }

    @PostMapping("/{orderId}/initiate-delivery")
    public ResponseEntity<String> initiateDelivery(@PathVariable Long orderId) {
        orderService.initiateDelivery(orderId);
        return ResponseEntity.ok("Delivery initiated");
    }

}
