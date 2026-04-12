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

import com.hll.hyperlightlogistics.dto.DeliveryRequestDTO;
import com.hll.hyperlightlogistics.dto.ProductDeliveryOptionDTO;
import com.hll.hyperlightlogistics.grpc.GrpcClient;
import com.hll.hyperlightlogistics.kafka.KafkaProducer;
import com.hll.hyperlightlogistics.mapper.DeliveryOptionMapper;
import com.hll.hyperlightlogistics.model.DeliveryOption;
import com.hll.hyperlightlogistics.model.Order;
import com.hll.hyperlightlogistics.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proto.DeliveryRequest;
import proto.DeliveryResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final GrpcClient grpcClient;
    private final KafkaProducer kafkaProducer;
    private final OrderRepository orderRepository;
    private final DeliveryOptionMapper deliveryOptionMapper;

    public List<ProductDeliveryOptionDTO> calculateDeliveryOptions(DeliveryRequestDTO deliveryRequest) {
        DeliveryRequest grpcRequest = deliveryOptionMapper.convertToGrpcRequest(deliveryRequest);
        DeliveryResponse grpcResponse = grpcClient.getDeliveryOptions(grpcRequest);
        return deliveryOptionMapper.convertToDto(grpcResponse);
    }

    public Order createOrder(Order orderRequest) {
        return new Order();
    }

    public void createOrderAndInitiateDelivery(Long customerId) {
        String message = String.format("Order %d initiated for delivery", 0);
        kafkaProducer.sendMessage("delivery-initiation-topic", message);
    }

    public List<DeliveryOption> requestDeliveryOptions(Order order) {
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
