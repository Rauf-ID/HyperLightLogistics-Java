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

import com.hll.hyperlightlogistics.dto.DeliveryOptionDTO;
import com.hll.hyperlightlogistics.dto.DeliveryRequestDTO;
import com.hll.hyperlightlogistics.grpc.GrpcClient;
import com.hll.hyperlightlogistics.mapper.DeliveryOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proto.DeliveryRequest;
import proto.DeliveryResponse;

import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private GrpcClient grpcClient;

    @Autowired
    private DeliveryOptionMapper deliveryOptionMapper;

    public List<DeliveryOptionDTO> calculateDeliveryOptions(DeliveryRequestDTO deliveryRequest) {
        DeliveryRequest grpcRequest = deliveryOptionMapper.convertToGrpcRequest(deliveryRequest);
        DeliveryResponse grpcResponse = grpcClient.getDeliveryOptions(grpcRequest);
        return deliveryOptionMapper.convertToDto(grpcResponse);
    }

}
