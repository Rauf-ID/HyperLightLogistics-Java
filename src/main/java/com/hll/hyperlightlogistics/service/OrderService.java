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

package com.hll.hyperlightlogistics.service;

import com.hll.hyperlightlogistics.dto.OrderRequestDTO;
import com.hll.hyperlightlogistics.kafka.KafkaProducer;
import com.hll.hyperlightlogistics.model.DeliveryOption;
import com.hll.hyperlightlogistics.model.Order;
import com.hll.hyperlightlogistics.repository.CustomerRepository;
import com.hll.hyperlightlogistics.repository.DeliveryOptionRepository;
import com.hll.hyperlightlogistics.repository.InventoryRepository;
import com.hll.hyperlightlogistics.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private DeliveryOptionRepository deliveryOptionRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    public Order createOrder(OrderRequestDTO orderRequest) {
        return new Order();
    }

    public List<DeliveryOption> requestDeliveryOptions(Order order) {
        String message = String.format("{ \"orderId\": %d, \"productId\": %d, \"customerId\": %d, \"quantity\": %d }",
                order.getId(), order.getProduct().getId(), order.getCustomer().getId(), order.getQuantity());

        return null;
    }

    public void initiateDelivery(Long orderId) {
        Order order = orderRepository.findOrderById(orderId).orElse(null);
        String message = null;
        if (order != null) {
            message = String.format("Order %d initiated for delivery", order.getId());
        }
        kafkaProducer.sendMessage("delivery-initiation-topic", message);
    }

}
