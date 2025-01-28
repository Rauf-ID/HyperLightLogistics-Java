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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hll.hyperlightlogistics.model.Order;
import com.hll.hyperlightlogistics.model.Product;
import com.hll.hyperlightlogistics.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class MockMVCOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void testCreateOrder() throws Exception {

        Order mockOrder = new Order();
        mockOrder.setId(1L);

        when(orderService.createOrder()).thenReturn(mockOrder);

        mockMvc.perform(post("/api/orders")
                        .param("customerId", "1")
                        .param("productId", "2")
                        .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

    }

    @Test
    void testInitiateDelivery() throws Exception {

        mockMvc.perform(post("/api/orders/1/initiate-delivery"))
                .andExpect(status().isOk())
                .andExpect(content().string("Delivery initiated"));

    }

    @Test
    void testGetOrderHistory() throws Exception {

        Product product1 = new Product();
        product1.setName("Product1");
        product1.setDescription("Description1");
        product1.setPrice(100.00);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setDescription("Description2");
        product2.setPrice(200.00);

        Order order1 = new Order();
        order1.setProducts(Collections.singletonList(product1));

        Order order2 = new Order();
        order2.setProducts(Collections.singletonList(product2));

        List<Order> mockOrders = Arrays.asList(order1, order2);

        when(orderService.getOrdersByCustomerId(1L)).thenReturn(mockOrders);

        mockMvc.perform(get("/api/orders/history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product1"))
                .andExpect(jsonPath("$[0].description").value("Description1"))
                .andExpect(jsonPath("$[0].price").value(100.00))
                .andExpect(jsonPath("$[1].name").value("Product2"))
                .andExpect(jsonPath("$[1].description").value("Description2"))
                .andExpect(jsonPath("$[1].price").value(200.00));

    }
}
