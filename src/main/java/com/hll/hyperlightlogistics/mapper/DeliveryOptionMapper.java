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

package com.hll.hyperlightlogistics.mapper;

import com.hll.hyperlightlogistics.dto.DeliveryOptionDTO;
import com.hll.hyperlightlogistics.dto.DeliveryRequestDTO;
import com.hll.hyperlightlogistics.dto.ProductDTO;
import org.springframework.stereotype.Component;
import proto.DeliveryAddress;
import proto.DeliveryRequest;
import proto.DeliveryResponse;
import proto.Product;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryOptionMapper {

    public DeliveryRequest convertToGrpcRequest(DeliveryRequestDTO deliveryRequest) {
        DeliveryAddress address = DeliveryAddress.newBuilder()
                .setCountry(deliveryRequest.getDeliveryAddress().getCountry())
                .setCity(deliveryRequest.getDeliveryAddress().getCity())
                .setStreet(deliveryRequest.getDeliveryAddress().getStreet())
                .setZipcode(deliveryRequest.getDeliveryAddress().getZipcode())
                .build();

        List<Product> products = new ArrayList<>();
        for (ProductDTO productDTO : deliveryRequest.getProducts()) {
            Product product = Product.newBuilder()
                    .setProductId(productDTO.getProductId())
                    .setQuantity(productDTO.getQuantity())
                    .build();
            products.add(product);
        }

        return DeliveryRequest.newBuilder()
                .setCustomerId(deliveryRequest.getCustomerId())
                .setDeliveryAddress(address)
                .addAllProducts(products)
                .build();
    }

    public List<DeliveryOptionDTO> convertToDto(DeliveryResponse grpcResponse) {
        List<DeliveryOptionDTO> deliveryOptions = new ArrayList<>();
        for (proto.DeliveryOptions option : grpcResponse.getDeliveryOptionsList()) {
            DeliveryOptionDTO deliveryOptionDTO = new DeliveryOptionDTO();
            deliveryOptionDTO.setType(option.getType());
            deliveryOptionDTO.setDeliveryTime(option.getDeliveryTime());
            deliveryOptionDTO.setPrice(option.getPrice());
            deliveryOptions.add(deliveryOptionDTO);
        }
        return deliveryOptions;
    }

}
