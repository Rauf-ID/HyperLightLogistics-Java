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
import com.hll.hyperlightlogistics.model.Product;
import com.hll.hyperlightlogistics.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

    }

    @Test
    void createProduct_ShouldReturnSuccessMessage_WhenProductIsSaved() {

        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setName("Name");
        productRequest.setDescription("Description");
        productRequest.setPrice(10.0);
        productRequest.setCategory("Category");
        productRequest.setWeight(5.0);
        productRequest.setLength(5.0);
        productRequest.setWidth(5.0);
        productRequest.setHeight(5.0);

        Product savedProduct = new Product();
        savedProduct.setId(1L);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        String result = productService.createProduct(productRequest);

        assertEquals("Product created with ID: 1", result);
        verify(productRepository, times(1)).save(any(Product.class));

    }

    @Test
    void createProduct_ShouldThrowDatabaseException_WhenSavingFails() {

        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setName("Name");
        productRequest.setDescription("Description");
        productRequest.setPrice(100.0);
        productRequest.setCategory("Category");
        productRequest.setWeight(1.5);
        productRequest.setLength(10.0);
        productRequest.setWidth(5.0);
        productRequest.setHeight(2.0);

        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));

        DatabaseException exception = assertThrows(DatabaseException.class, () -> productService.createProduct(productRequest));
        assertEquals("Failed to add product to the database", exception.getMessage());
        verify(productRepository, times(1)).save(any(Product.class));

    }
}

