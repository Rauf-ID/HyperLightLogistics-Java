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

import com.hll.hyperlightlogistics.dto.ProductDTO;
import com.hll.hyperlightlogistics.model.Order;
import com.hll.hyperlightlogistics.model.Product;
import com.hll.hyperlightlogistics.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {

        when(orderService.createOrder()).thenReturn(new Order());

        ResponseEntity<Order> response = orderController.createOrder(1L,1L,4);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(new Order(), response.getBody());
        verify(orderService, times(1)).createOrder();

    }

    @Test
    void testInitiateDelivery() {

        ResponseEntity<String> response = orderController.initiateDelivery(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Delivery initiated", response.getBody());
        verify(orderService, times(1)).initiateDelivery(1L);

    }

    @Test
    void getOrderHistory_ShouldReturnProductList() {

        Long customerId = 1L;

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(100.0);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(200.0);

        Order order1 = new Order();
        order1.setProducts(List.of(product1));

        Order order2 = new Order();
        order2.setProducts(List.of(product2));

        List<Order> mockOrders = List.of(order1, order2);

        when(orderService.getOrdersByCustomerId(customerId)).thenReturn(mockOrders);

        ResponseEntity<List<ProductDTO>> response = orderController.getOrderHistory(customerId);

        assertEquals(200, response.getStatusCode().value());

        List<ProductDTO> productList = response.getBody();
        assert productList != null;
        assertEquals(2, productList.size());

        ProductDTO productDTO1 = productList.getFirst();
        assertEquals("Product 1", productDTO1.getName());
        assertEquals("Description 1", productDTO1.getDescription());
        assertEquals(100.0, productDTO1.getPrice());

        ProductDTO productDTO2 = productList.get(1);
        assertEquals("Product 2", productDTO2.getName());
        assertEquals("Description 2", productDTO2.getDescription());
        assertEquals(200.0, productDTO2.getPrice());

        verify(orderService, times(1)).getOrdersByCustomerId(customerId);

    }
}
