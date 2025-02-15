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

import com.hll.hyperlightlogistics.dto.WarehouseRequestDTO;
import com.hll.hyperlightlogistics.service.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WarehouseControllerTest {

    @InjectMocks
    private WarehouseController warehouseController;

    @Mock
    private WarehouseService warehouseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWarehouse() {

        WarehouseRequestDTO warehouseRequest = new WarehouseRequestDTO();
        when(warehouseService.createWarehouse(warehouseRequest)).thenReturn("Warehouse created");

        ResponseEntity<String> response = warehouseController.createWarehouse(warehouseRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Warehouse created", response.getBody());
        verify(warehouseService, times(1)).createWarehouse(warehouseRequest);

    }

    @Test
    void createWarehouse_ShouldHandleExceptions() {

        WarehouseRequestDTO warehouseRequest = new WarehouseRequestDTO();
        warehouseRequest.setType("Abandoned");

        when(warehouseService.createWarehouse(any(WarehouseRequestDTO.class))).thenThrow(new RuntimeException("Service error"));

        try {
            warehouseController.createWarehouse(warehouseRequest);
        } catch (Exception e) {
            assertEquals("Service error", e.getMessage());
        }

    }

}
