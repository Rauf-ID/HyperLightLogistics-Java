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
import com.hll.hyperlightlogistics.dto.ProductDeliveryOptionDTO;
import org.springframework.stereotype.Component;
import proto.DeliveryAddress;
import proto.DeliveryOptions;
import proto.DeliveryRequest;
import proto.Product;
import proto.ProductDeliveryOptions;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryOptionMapper {

    public DeliveryRequest convertToGrpcRequest(DeliveryRequestDTO deliveryRequest) {
        DeliveryAddress address = DeliveryAddress.newBuilder()
                .setCountry(deliveryRequest.getDeliveryAddress().getCountry())
                .setState(deliveryRequest.getDeliveryAddress().getState())
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

    public List<ProductDeliveryOptionDTO> convertToDto(proto.DeliveryResponse grpcResponse) {
        List<ProductDeliveryOptionDTO> productDeliveryOptions = new ArrayList<>();

        for (proto.ProductDeliveryOptions productOption : grpcResponse.getProductsList()) {
            ProductDeliveryOptionDTO productDeliveryOptionDTO = new ProductDeliveryOptionDTO();
            productDeliveryOptionDTO.setProductId(productOption.getProductId());

            List<DeliveryOptionDTO> deliveryOptions = getDeliveryOptionDTOS(productOption);

            productDeliveryOptionDTO.setDeliveryOptions(deliveryOptions);
            productDeliveryOptions.add(productDeliveryOptionDTO);
        }

        return productDeliveryOptions;
    }

    private List<DeliveryOptionDTO> getDeliveryOptionDTOS(ProductDeliveryOptions productOption) {
        List<DeliveryOptionDTO> deliveryOptions = new ArrayList<>();
        for (DeliveryOptions option : productOption.getDeliveryOptionsList()) {
            DeliveryOptionDTO deliveryOptionDTO = new DeliveryOptionDTO();
            deliveryOptionDTO.setType(option.getType());
            deliveryOptionDTO.setDeliveryTime(option.getDeliveryTime());
            deliveryOptionDTO.setPrice(option.getPrice());
            deliveryOptions.add(deliveryOptionDTO);
        }
        return deliveryOptions;
    }

}
