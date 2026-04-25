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

import com.hll.hyperlightlogistics.dto.DeliveryRequestDTO;
import com.hll.hyperlightlogistics.dto.ProductDTO;
import com.hll.hyperlightlogistics.dto.ProductDeliveryOptionDTO;
import com.hll.hyperlightlogistics.model.DeliveryOption;
import com.hll.hyperlightlogistics.model.Order;
import com.hll.hyperlightlogistics.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/orders", "/api/v1/orders"})
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/options")
    public ResponseEntity<List<ProductDeliveryOptionDTO>> getDeliveryOptions(@RequestBody DeliveryRequestDTO request) {
        List<ProductDeliveryOptionDTO> deliveryOptions = orderService.calculateDeliveryOptions(request);

        return ResponseEntity.ok(deliveryOptions);
    }

    @PostMapping("/createOrderAndInitiate/{customerId}")
    public ResponseEntity<String> createOrderAndInitiate(@PathVariable Long customerId) {
        orderService.createOrderAndInitiateDelivery(customerId);

        return ResponseEntity.ok("Delivery initiated");
    }

//    @PostMapping("/options")
//    public ResponseEntity<List<DeliveryOption>> prepareOrderAndGetDeliveryOptions(
//            @RequestBody Order orderRequest) {
//        Order order = orderService.createOrder(orderRequest);
//
//        List<DeliveryOption> deliveryOptions = orderService.requestDeliveryOptions(order);
//
//        return ResponseEntity.ok(deliveryOptions);
//    }

    @PostMapping("/{orderId}/delivery")
    public ResponseEntity<String> initiateDelivery(@PathVariable Long orderId) {

        orderService.initiateDelivery(orderId);
        return ResponseEntity.ok("Delivery initiated");

    }

//    @GetMapping("/history/{customerId}")
//    public ResponseEntity<List<ProductDTO>> getOrderHistory(@PathVariable Long customerId) {
//
//        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
//
//        List<ProductDTO> productList = orders.stream()
//                .flatMap(order -> order.getProducts().stream())
//                .map(product -> new ProductDTO(product.getName(), product.getDescription(), product.getPrice()))
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(productList);
//
//    }

}
