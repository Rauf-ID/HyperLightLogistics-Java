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

package com.hll.hyperlightlogistics.controller;

import com.hll.hyperlightlogistics.dto.ProductRequestDTO;
import com.hll.hyperlightlogistics.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {

        ProductRequestDTO productRequest = new ProductRequestDTO();
        when(productService.createProduct(productRequest)).thenReturn("Product created");

        ResponseEntity<String> response = productController.createProduct(productRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Product created", response.getBody());
        verify(productService, times(1)).createProduct(productRequest);

    }

    @Test
    void createProduct_ShouldHandleExceptions() {

        ProductRequestDTO productRequest = new ProductRequestDTO();
        productRequest.setName("Invalid Product");

        when(productService.createProduct(any(ProductRequestDTO.class))).thenThrow(new RuntimeException("Service error"));

        try {
            productController.createProduct(productRequest);
        } catch (Exception e) {
            assertEquals("Service error", e.getMessage());
        }

    }
}
