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
 * Copyright (C) 2024 Vsevolod Batyrov
 */

package com.hll.hyperlightlogistics.service;

import com.hll.hyperlightlogistics.dto.ProductRequestDTO;
import com.hll.hyperlightlogistics.exceptions.DatabaseException;
import com.hll.hyperlightlogistics.mapper.ProductMapper;
import com.hll.hyperlightlogistics.model.Product;
import com.hll.hyperlightlogistics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String createProduct(ProductRequestDTO productRequest) {

        Product product = ProductMapper.INSTANCE.toEntity(productRequest);

        try {
            Product savedProduct = productRepository.save(product);
            return "Product created with ID: " + savedProduct.getId();
        }catch (Exception e) {
            throw new DatabaseException("Failed to add product to the database", e);

        }
    }
}
