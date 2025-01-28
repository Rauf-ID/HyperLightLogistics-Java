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
import com.hll.hyperlightlogistics.dto.InventoryRequestDTO;
import com.hll.hyperlightlogistics.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
public class MockMVCInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InventoryService inventoryService;

    @Test
    void testAddProductToInventory() throws Exception {

        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setWarehouseId(1L);
        request.setProductId(2L);
        request.setQuantity(100);

        when(inventoryService.addProductToInventory(1L, 2L, 100))
                .thenReturn("Product added successfully");

        mockMvc.perform(post("/api/inventory/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added successfully"));

    }

    @Test
    void testAddProductToInventoryWithFailure() throws Exception {

        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setWarehouseId(1L);
        request.setProductId(2L);
        request.setQuantity(100);

        when(inventoryService.addProductToInventory(1L, 2L, 100))
                .thenThrow(new RuntimeException("An unexpected error occurred. Please try again later."));

        mockMvc.perform(post("/api/inventory/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An unexpected error occurred. Please try again later."));

    }
}
